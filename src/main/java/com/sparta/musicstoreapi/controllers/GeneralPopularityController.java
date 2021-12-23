package com.sparta.musicstoreapi.controllers;
import com.sparta.musicstoreapi.config.JdbcConfigurator;
import com.sparta.musicstoreapi.entities.Token;
import com.sparta.musicstoreapi.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping(value = "/chinook")
public class GeneralPopularityController
{
    @Autowired
    private TokenRepository tokenRepository;

    @GetMapping(value = "tracks/top5/{token}")
    public List<String> getTopFiveTracks(@PathVariable String token) throws IOException {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
                JdbcConfigurator cfg = new JdbcConfigurator();
                DataSource ds = cfg.dataSource();

                JdbcTemplate template = new JdbcTemplate(ds);

                List<Map<String, Object>> rows = template.queryForList("select t.name, count(invoiceline.trackid) counter from track as t\n" +
                        "inner join invoiceline on t.trackid = invoiceline.trackid\n" +
                        "group by name\n" +
                        "order by counter desc\n" +
                        "limit 5");

                List<String> output = new ArrayList<>();

                for (Map<String, Object> row : rows) {
                    output.add((String) row.get("name"));
                }
                return output;
            }
        }
        return null;
    }

    @GetMapping(value = "albums/top5/{token}")
    public List<String> getTopFiveAlbums(@PathVariable String token) throws IOException {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
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

                for (Map<String, Object> row : rows) {
                    output.add((String) row.get("title"));
                }
                return output;
            }
        }
        return null;
    }

    @GetMapping(value = "genres/top5/{token}")
    public List<String> getTopFiveGenres(@PathVariable String token) throws IOException {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
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

                for (Map<String, Object> row : rows) {
                    output.add((String) row.get("name"));
                }
                return output;
            }
        }
        return null;
    }

    @GetMapping(value = "artists/top5/{token}")
    public List<String> getTopFiveArtists(@PathVariable String token) throws IOException {
        Optional<Token> tokenResult = tokenRepository.findByToken(token);
        if (tokenResult.isPresent()) {
            if (tokenResult.get().getPermissionLevel() >= 1) {
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

                for (Map<String, Object> row : rows) {
                    output.add((String) row.get("name"));
                }
                return output;
            }
        }
        return null;
    }
}
