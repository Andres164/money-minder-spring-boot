package com.baio.money_minder.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "notifications")
@NoArgsConstructor
public class Notification {
    public Notification(String content, Date notifyDate) {
        this.content = content;
        this.notifyDate = notifyDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "id"
    )
    @JsonBackReference
    private User user;

    @Column(
        name = "content",
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
