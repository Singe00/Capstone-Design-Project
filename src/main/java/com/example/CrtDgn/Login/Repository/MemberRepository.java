package com.example.CrtDgn.Login.Repository;

import com.example.CrtDgn.Login.Domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.email = :email")
    UserDetails findMemberByEmail(@Param("email") String email);
    boolean existsByEmail(String email);
}
