package com.tjalia.userprofile.repository;

import com.tjalia.userprofile.entity.UserProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

    Page<UserProfileEntity> findAll(Pageable pageable);
}
