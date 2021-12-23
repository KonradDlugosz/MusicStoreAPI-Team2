package com.sparta.musicstoreapi.controllers;
import com.sparta.musicstoreapi.config.JdbcConfigurator;
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
        JdbcConfigurator cfg = new JdbcConfigurator();
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
        JdbcConfigurator cfg = new JdbcConfigurator();
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

    @GetMapping(value = "genres/top5")
    public List<String> getTopFiveGenres() throws IOException {
        JdbcConfigurator cfg = new JdbcConfigurator();
        DataSource ds = cfg.dataSource();

        JdbcTemplate template = new JdbcTemplate(ds);
        List<Map<String, Object>> rows = template.queryForList("select genre.name, count(genre.name) as counter from track\n" +
                "inner join genre on track.genreid = genre.genreid\n" +
                "inner join invoiceline on track.TrackId = invoiceline.TrackId\n" +
                "group by genre.name\n" +
                "order by counter desc\n" +
                "limit 5");

        List<String> output = new ArrayList<>();

        for(Map<String, Object> row : rows)
        {
            output.add((String) row.get("name"));
        }
        return output;
    }

    @GetMapping(value = "artists/top5")
    public List<String> getTopFiveArtists() throws IOException {
        JdbcConfigurator cfg = new JdbcConfigurator();
        DataSource ds = cfg.dataSource();

        JdbcTemplate template = new JdbcTemplate(ds);
        List<Map<String, Object>> rows = template.queryForList("select artist.name, count(artist.name) as counter from artist\n" +
                "inner join album on artist.ArtistId = album.artistid\n" +
                "inner join track on track.AlbumId = album.albumid\n" +
                "inner join invoiceline on invoiceline.trackid = track.trackid\n" +
                "group by artist.name\n" +
                "order by counter desc\n" +
                "limit 5");

        List<String> output = new ArrayList<>();

        for(Map<String, Object> row : rows)
        {
            output.add((String) row.get("name"));
        }
        return output;
    }
}
