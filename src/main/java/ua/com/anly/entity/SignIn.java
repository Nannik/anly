package ua.com.anly.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class SignIn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String domen;

    private Date date;
}
