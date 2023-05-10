package com.example.CrtDgn.Interest.Repository;

import com.example.CrtDgn.Interest.Domain.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<Interest,Long> {
    Optional<Interest> findByUserIdAndTourkey(Long userId,Long tourId);

    List<Interest> findAllByUserId(Long userId);
}