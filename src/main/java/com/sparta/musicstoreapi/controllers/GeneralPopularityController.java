package com.sparta.musicstoreapi.controllers;
import com.sparta.musicstoreapi.config.SpringJdbcConfig;
import com.sparta.musicstoreapi.entities.Track;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/chinook")
public class GeneralPopularityController
{
    @GetMapping(value = "tracks/top5")
    public List<String> getTopFiveTracks() throws IOException {
        SpringJdbcConfig cfg = new SpringJdbcConfig();
        DataSource ds = cfg.dataSource();

        JdbcTemplate template = new JdbcTemplate(ds);

        List<Map<String, Object>> rows = template.queryForList("select t.name, count(invoiceline.trackid) counter from track as t\n" +
                "inner join invoiceline on t.trackid = invoiceline.trackid\n" +
                "group by name\n" +
                "order by counter desc\n" +
                "limit 5");

        List<String> output = new ArrayList<>();

        for(Map<String, Object> row : rows)
        {
            output.add((String) row.get("name"));
        }
        return output;
    }

    @GetMapping(value = "albums/top5")
    public List<String> getTopFiveAlbums() throws IOException {
        SpringJdbcConfig cfg = new SpringJdbcConfig();
        DataSource ds = cfg.dataSource();

        JdbcTemplate template = new JdbcTemplate(ds);
        List<Map<String, Object>> rows = template.queryForList("select album.title, count(album.title) as counter from track as t\n" +
                "inner join album on t.AlbumId = album.AlbumId\n" +
                "inner join invoiceline on t.trackid = invoiceline.trackid\n" +
                "group by album.title\n" +
                "order by counter desc\n" +
                "limit 5");

        List<String> output = new ArrayList<>();

        for(Map<String, Object> row : rows)
        {
            output.add((String) row.get("title"));
        }
        return output;
    }

    @GetMapping(value = "playlists/top5")
    public List<String> getTopFivePlaylists() throws IOException {
        SpringJdbcConfig cfg = new SpringJdbcConfig();
        DataSource ds = cfg.dataSource();

        JdbcTemplate template = new JdbcTemplate(ds);
        List<Map<String, Object>> rows = template.queryForList("");

        List<String> output = new ArrayList<>();

        for(Map<String, Object> row : rows)
        {
            output.add((String) row.get("title"));
        }
        return output;
    }

    @GetMapping(value = "genres/top5")
    public List<String> getTopFiveGenres() throws IOException {
        SpringJdbcConfig cfg = new SpringJdbcConfig();
        DataSource ds = cfg.dataSource();

        JdbcTemplate template = new JdbcTemplate(ds);
        List<Map<String, Object>> rows = template.queryForList("");

        List<String> output = new ArrayList<>();

        for(Map<String, Object> row : rows)
        {
            output.add((String) row.get("title"));
        }
        return output;
    }

    @GetMapping(value = "artists/top5")
    public List<String> getTopFiveArtists() throws IOException {
        SpringJdbcConfig cfg = new SpringJdbcConfig();
        DataSource ds = cfg.dataSource();

        JdbcTemplate template = new JdbcTemplate(ds);
        List<Map<String, Object>> rows = template.queryForList("");

        List<String> output = new ArrayList<>();

        for(Map<String, Object> row : rows)
        {
            output.add((String) row.get("title"));
        }
        return output;
    }
}
