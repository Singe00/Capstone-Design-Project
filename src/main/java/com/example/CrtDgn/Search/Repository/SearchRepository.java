package com.example.CrtDgn.Search.Repository;

import com.example.CrtDgn.Interest.Domain.Interest;
import com.example.CrtDgn.Search.Domain.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search,Long> {

    @Query(value = "SELECT * FROM tour ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Search> findRandomFiveTours();

    @Query("SELECT t.tourId, t.title, t.roadaddress, t.latitude, t.longitude, t.phoneno, t.tag, t.introduction, t.imagepath, " +
            "CASE WHEN i.userId IS NOT NULL THEN 1 ELSE 0 END AS isInterested " +
            "FROM Search2 t LEFT JOIN Interest i ON t.tourId = i.tourkey AND i.userId = " +
            "(SELECT m.id FROM Member m WHERE m.email = :email) " +
            "WHERE t.title LIKE %:title%")
    List<Object[]> getTourWithInterestByTitle(@Param("email") String email, @Param("title") String title);

    Search findByTourid(Long tourKey);
    List<Search> findAllByTourid(Long tourKey);

    List<Search> findAllByTitleContaining(String searchString);
}
