package ua.com.anly.entity.message;

import ua.com.anly.entity.Product;
import ua.com.anly.entity.User;

import javax.persistence.*;
import java.util.List;

@Entity
public class ParentMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User user;

    private String text;
    private byte rate;

    @ManyToOne
    private Product product;

    @OneToMany
    @JoinColumn(name = "parent_message_id")
    private List<ChildMessage> childMessages;

    public ParentMessage() {
    }

    public ParentMessage(User user, String text, byte rate, Product product) {
        this.user = user;
        this.text = text;
        this.rate = rate;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public byte getRate() {
        return rate;
    }

    public void setRate(byte rate) {
        this.rate = rate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<ChildMessage> getChildMessages() {
        return childMessages;
    }

    public void setChildMessages(List<ChildMessage> childMessages) {
        this.childMessages = childMessages;
    }
}
