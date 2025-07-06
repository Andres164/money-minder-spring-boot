package com.baio.money_minder.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "notifications")
public class Notification {
    public Notification(User user, String content, Date notifyDate) {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id"
    )
    private User user;

    @Column(
        name = "content",
        length = 255,
        nullable = false
    )
    private String content;

    @Column(
        name = "notify_date",
        nullable = false
    )
    private Date notifyDate;

    @Column(
        name = "has_been_read",
        nullable = false
    )
    private boolean hasBeenRead = false;
}
