package com.baio.money_minder.repositories;

import com.baio.money_minder.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsTagByName(String name);
}
