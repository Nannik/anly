package ua.com.anly.entity.message;

import ua.com.anly.entity.User;

import javax.persistence.*;

@Entity
public class ChildMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    private User user;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    private ParentMessage parentMessage;

    public ChildMessage() {
    }

    public ChildMessage(User user, String text, ParentMessage parentMessage) {
        this.user = user;
        this.text = text;
        this.parentMessage = parentMessage;
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

    public ParentMessage getParentMessage() {
        return parentMessage;
    }

    public void setParentMessage(ParentMessage parentMessage) {
        this.parentMessage = parentMessage;
    }
}
