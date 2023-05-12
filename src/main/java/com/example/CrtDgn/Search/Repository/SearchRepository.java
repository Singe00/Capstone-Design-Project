package com.example.CrtDgn.Search.Repository;

import com.example.CrtDgn.Search.Domain.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search,Long> {

    List<Search> findFirst5ByOrderByScoreDesc();

    Search findByTourid(Long tourKey);
    List<Search> findAllByTourid(Long tourKey);

    List<Search> findAllByTitleContaining(String searchString);
}
