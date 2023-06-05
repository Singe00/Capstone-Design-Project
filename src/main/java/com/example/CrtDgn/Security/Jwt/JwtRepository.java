package com.example.CrtDgn.Security.Jwt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JwtRepository extends JpaRepository<JwtDomain,Long> {
    JwtDomain findByToken(String token);

    JwtDomain findByUserId(long uid);

    List<JwtDomain> findAllByUserId(long uid);
}
