package com.socle.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.socle.repository.model.User;

@Repository
public interface IUserRepository extends IBaseRepository<User, Long> {

    Optional<User> findByEmail(String userEmail);
}
