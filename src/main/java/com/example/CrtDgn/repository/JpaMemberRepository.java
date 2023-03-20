package com.example.CrtDgn.repository;

import com.example.CrtDgn.Demain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;

public class JpaMemberRepository {
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    public Member save(Member member){
        em.persist(member);
        return member;
    }


}
