package com.mycompany.chatservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.chatservice.domain.Authority;
import com.mycompany.chatservice.domain.User;

@Repository
public interface AuthortiyRepository  extends JpaRepository<Authority, Long>{

}
