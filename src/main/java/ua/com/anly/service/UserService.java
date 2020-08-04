package ua.com.anly.service;

import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.anly.entity.Order;
import ua.com.anly.entity.Product;
import ua.com.anly.entity.Role;
import ua.com.anly.entity.User;
import ua.com.anly.entity.message.ParentMessage;
import ua.com.anly.repository.ChildMessageRepo;
import ua.com.anly.repository.ParentMessageRepo;
import ua.com.anly.repository.ProductRepo;
import ua.com.anly.repository.UserRepo;

import javax.mail.MessagingException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final ParentMessageRepo parentMessageRepo;
    private final ChildMessageRepo childMessageRepo;
    private final MailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    private final int secretPasswordCodeLength = 5;

    public UserService(UserRepo userRepo, ProductRepo productRepo, ParentMessageRepo parentMessageRepo, ChildMessageRepo childMessageRepo, MailSender mailSender) {
        this.userRepo = userRepo;
        this.productRepo = productRepo;
        this.parentMessageRepo = parentMessageRepo;
        this.childMessageRepo = childMessageRepo;
        this.mailSender = mailSender;
        this.passwordEncoder = new BCryptPasswordEncoder(8);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByName(username);
    }

    public String addUser(String name, String password, String email) {
        User userFromDb = userRepo.findByName(name);
        if (userFromDb != null) {
            return "User with this name is exists";
        }

        userFromDb = userRepo.findByEmail(email);
        if(userFromDb != null) {
            return "User with this email is exists";
        }

        User user = new User(name, passwordEncoder.encode(password), email);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivated(false);

        userRepo.save(user);

        if (!createAndSendEmailActivationCode(user)) {
            return "Couldn't send email activation code";
        }

        return "User created";
    }

    public boolean createAndSendEmailActivationCode(User user) {
        if (!user.isActivated()) {
            user.setActivationCode(UUID.randomUUID().toString());

            userRepo.save(user);

            if (!user.getEmail().isEmpty()) {
                String subject = "Account activation";
                String message = String.format(
                        "Activate your account <br>"+
                                "Click <a href='http://localhost:8080/user/%s/activate/%s'>this</a> link",
                        user.getId(),
                        user.getActivationCode()
                );

                try {
                    mailSender.send(user.getEmail(), subject, message);
                } catch (MessagingException e) {
                    return false;
                }
            }
        }

        return true;
    }

    public String activateUser(User user, String activationCode, SessionRegistry sessionRegistry) {
        if (!user.isActivated() && user.getActivationCode().equals(activationCode)) {
            user.setActivated(true);
            userRepo.save(user);

            for (Object obj : sessionRegistry.getAllPrincipals()) {
                User u = (User) obj;
                if (u.getId().equals(user.getId())) {
                    u.setActivated(true);
                }
            }

            return "Account activated";
        } else {
            return "Activation code is incorrect or expired";
        }
    }

    public String sendPasswordActivationCode(User user) {
        String code = generateSecretCode(secretPasswordCodeLength);

        String subject = "Change password";

        String message = String.format(
                "Code: %s",
                code
        );

        try {
            mailSender.send(user.getEmail(), subject, message);
        } catch (MessagingException e) {
            e.printStackTrace();

            return "Can't send secret code";
        }

        user.setPasswordSecretCode(code);
        userRepo.save(user);

        return "Secret code has send to " + user.getEmail();
    }

    private String generateSecretCode(int length) {
        Random r = new Random();

        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (char) (r.nextInt(26) + 'a');
        }

        return new String(chars).toUpperCase();
    }

    public String changePassword(User user, String lastPassword, String newPassword, String secretCode) {
        if (!passwordEncoder.matches(lastPassword, user.getPassword())) {
            return "Password is wrong";
        }

        if (user.getPasswordSecretCode().toUpperCase().equals(secretCode.toUpperCase())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);

            return "Password successfully changed";
        } else {
            return "Secret code is wrong";
        }
    }

    public void deleteUser(int id) {
        List<ParentMessage> parentMessages = parentMessageRepo.findAllByUserId(id);

        for (ParentMessage parentMessage : parentMessages) {
            childMessageRepo.deleteAll(childMessageRepo.findAllByParentMessageId(parentMessage.getId()));
        }

        parentMessageRepo.deleteAll(parentMessages);

        User user = userRepo.findById(id);
        userRepo.delete(user);
    }

    public void updateUser(int id, String name, String password, String email) {
        User user = userRepo.findById(id);
        user.setName(name);
        user.setPassword(password);
        user.setEmail(email);

        user.getRoles().clear();

        userRepo.save(user);
    }

    public void setUserRoles(int id, Map<String, String> form) {
        User user = userRepo.findById(id);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    public void addToDesired(User user, Product product) {
        user.getDesired().add(product);
        product.getUsersDesired().add(user);

        userRepo.save(user);
    }

    public void removeFromDesired(User user, Product product) {
        user.getDesired().remove(product);
        product.getUsersDesired().remove(user);

        userRepo.save(user);
        productRepo.save(product);
    }

    public void addToCard(User user, Product product) {
        user.getCard().add(product);
        product.getUsersCard().add(user);

        userRepo.save(user);
    }

    public void removeFromCard(User user, Product product) {
        user.getCard().remove(product);
        product.getUsersCard().remove(user);

        userRepo.save(user);
        productRepo.save(product);
    }

    public void removeAllFromCard(User user, Product product) {
        user.getCard().removeIf(product::equals);
        product.getUsersCard().removeIf(user::equals);

        userRepo.save(user);
        productRepo.save(product);
    }

    public void removeAllFromCard(User user) {
        user.getCard().clear();
        userRepo.save(user);
    }

    public void addOrder(User user, Order order) {
        user.getOrders().add(order);
    }
}
