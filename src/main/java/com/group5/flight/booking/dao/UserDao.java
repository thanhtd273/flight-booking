package com.group5.flight.booking.dao;

import com.group5.flight.booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.status <> 0 AND u.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u WHERE u.status <> 0 AND u.userId = :userId")
    User findByUserId(@Param("userId") Long userId);
}