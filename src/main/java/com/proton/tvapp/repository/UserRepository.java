package com.proton.tvapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proton.tvapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
