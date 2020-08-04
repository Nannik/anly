package ua.com.anly.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    private String description;
    private int price;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image")
    private Set<String> images;

    @ManyToOne
    private Category category;

    @ManyToMany
    @JoinTable(
            name = "desired",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<User> usersDesired = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "card",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") }
    )
    private List<User> usersCard = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_product",
            joinColumns = { @JoinColumn(name = "product_id") },
            inverseJoinColumns = { @JoinColumn(name = "order_id") }
    )
    private List<Order> orders = new ArrayList<>();

    public Product() {
    }

    public Product(String name, String description, int price, Category category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<String> getImages() {
        return images;
    }

    public void setImages(Set<String> images) {
        this.images = images;
    }

    public List<User> getUsersDesired() {
        return usersDesired;
    }

    public void setUsersDesired(List<User> desired) {
        this.usersDesired = desired;
    }

    public List<User> getUsersCard() {
        return usersCard;
    }

    public void setUsersCard(List<User> card) {
        this.usersCard = card;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj.getClass() == this.getClass() && this.id == ((Product)obj).id);
    }
}

