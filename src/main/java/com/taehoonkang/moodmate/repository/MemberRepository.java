package com.taehoonkang.moodmate.repository;

import com.taehoonkang.moodmate.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // JpaRepository<The name of the Entity I created, pk data type>

    // [JPA rule] The save(entity) method is built-in
    // [JPA rule] The findAll() method is built-in
    // [JPA rule] The deleteById(id) method is built-in
    // [JPA rule] The findByFieldName(parameter) method is built-in
        // findById(id) method is a built-in method for the pk value
        // Other fields must be specified by the user as below

    Optional<MemberEntity> findByMemberEmail(String memberEmail);
    // [General meaning] Finds the value of each field in the "field name" column of the table.
    // [mySQL meaning] select * from member_table where member_email=?
    // In Java, camelCase is used, so to find memberEmail, use findBymemberEmail(x) findByMemberEmail(o)
    // Receives as MemberEntity data type.
    // To handle null values, use Optional.
}
