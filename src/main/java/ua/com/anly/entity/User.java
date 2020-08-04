package ua.com.anly.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String password;
    private String email;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Order> orders;

    private String activationCode;
    private boolean isActivated;

    private String passwordSecretCode;

    @ManyToMany
    @JoinTable(
            name = "desired",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<Product> desired = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "card",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<Product> card = new ArrayList<>();

    public User() {
    }

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public int getProductInCardQuantity(Product product) {
        int num = 0;

        for (Product p : card) {
            if (product.equals(p)) num++;
        }

        return num;
    }

    public List<Product> getUniqueProductsInCard() {
        List<Product> products = new ArrayList<>();

        for (Product product : card) {
            if (!products.contains(product))
                products.add(product);
        }

        return products;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public boolean isActivated() {
        return isActivated;
    }

    public void setActivated(boolean activated) {
        isActivated = activated;
    }

    public String getPasswordSecretCode() {
        return passwordSecretCode;
    }

    public void setPasswordSecretCode(String passwordActivationCode) {
        this.passwordSecretCode = passwordActivationCode;
    }

    public List<Product> getDesired() {
        return desired;
    }

    public void setDesired(List<Product> desired) {
        this.desired = desired;
    }

    public List<Product> getCard() {
        return card;
    }

    public void setCard(List<Product> card) {
        this.card = card;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj.getClass() == this.getClass() && this.id == ((User)obj).id);
    }
}