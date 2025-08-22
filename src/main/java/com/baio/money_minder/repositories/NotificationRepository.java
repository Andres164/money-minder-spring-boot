package com.baio.money_minder.repositories;

import com.baio.money_minder.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface NotificationRepository extends JpaRepository<Notification, Long> { }
