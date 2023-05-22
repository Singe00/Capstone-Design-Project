package com.example.CrtDgn.Search.Repository;

import com.example.CrtDgn.Search.Domain.Search;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search,Integer> {

    @Query(value = "SELECT * FROM tour ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Search> findRandomFiveTours();

    @Query("SELECT t.tourId, t.title, t.roadaddress, t.latitude, t.longitude, t.phoneno, t.tag, t.introduction, t.imagepath, " +
            "CASE WHEN i.userId IS NOT NULL THEN 1 ELSE 0 END AS isInterested " +
            "FROM Search2 t LEFT JOIN Interest i ON t.tourId = i.tourkey AND i.userId = " +
            "(SELECT m.id FROM Member m WHERE m.email = :email) " +
            "WHERE t.title LIKE %:title%")
    List<Object[]> getTourWithInterestByTitle(@Param("email") String email, @Param("title") String title);

    @Query("SELECT t.tourId, t.title, t.roadaddress, t.latitude, t.longitude, t.phoneno, t.tag, t.introduction, t.imagepath, " +
            "CASE WHEN i.userId IS NOT NULL THEN 1 ELSE 0 END AS isInterested " +
            "FROM Search2 t LEFT JOIN Interest i ON t.tourId = i.tourkey AND i.userId = " +
            "(SELECT m.id FROM Member m WHERE m.email = :email) " +
            "WHERE t.tag LIKE %:tag%")
    List<Object[]> getTourWithInterestByTag(@Param("email") String email, @Param("tag") String tag);

    @Query("SELECT t.tourId, t.title, t.roadaddress, t.latitude, t.longitude, t.phoneno, t.tag, t.introduction, t.imagepath, " +
            "CASE WHEN i.userId IS NOT NULL THEN 1 ELSE 0 END AS isInterested " +
            "FROM Search2 t " +
            "LEFT JOIN Interest i ON t.tourId = i.tourkey AND i.userId = :userId " +
            "WHERE t.tourId IN :tourIds")
    List<Object[]> getTourWithInterestByTourIds(@Param("userId") Long userId, @Param("tourIds") List<Long> tourIds);

    @Query("SELECT t FROM Search t ORDER BY FUNCTION('ST_DISTANCE', t.latitude, t.longitude, :latitude, :longitude) ASC")
    Search findNearestTouristSpot(@Param("latitude") double latitude, @Param("longitude") double longitude);

    Search findByTourid(Integer tourKey);

    List<Search> findAllByTourid(int tourid);

}
