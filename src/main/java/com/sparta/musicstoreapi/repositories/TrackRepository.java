package com.sparta.musicstoreapi.repositories;

import com.sparta.musicstoreapi.entities.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Integer>
{
    List<Track> findByNameContains(String name);

    @Query(value = "SELECT * FROM track ", nativeQuery = true)
    List<Track> getAllByName();


}