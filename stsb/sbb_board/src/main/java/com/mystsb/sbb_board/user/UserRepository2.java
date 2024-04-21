package com.mystsb.sbb_board.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository2 extends JpaRepository<SiteUser, Long>{
	SiteUser findByUsername(String username);
}