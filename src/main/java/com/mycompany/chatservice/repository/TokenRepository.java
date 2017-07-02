package com.mycompany.chatservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mycompany.chatservice.domain.Token;

@Repository
public interface TokenRepository  extends JpaRepository<Token, String>{

}
