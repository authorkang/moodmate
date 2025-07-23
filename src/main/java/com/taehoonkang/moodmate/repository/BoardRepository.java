package com.taehoonkang.moodmate.repository;

import com.taehoonkang.moodmate.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // JpaRepository<The name of the Entity I created, pk data type>

    // [JPA rule] The save(entity) method is built-in
    // [JPA rule] The findAll() method is built-in
    // [JPA rule] The deleteById(id) method is built-in
    // [JPA rule] The findById(id) method is built-in (used for primary key)

}
