package com.sparta.musicstoreapi.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TokenControllerTest
{
    private String token;
    private final String email = "testCustomer@testerino.com";

    @BeforeAll
    public void setToken() throws IOException{

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpUriRequest httppost = RequestBuilder.post()
                    .setUri(new URI("http://localhost:8080/token/add/" + email))
                    .build();

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                token = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFindAll() throws IOException{
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpUriRequest httppost = RequestBuilder.get()
                    .setUri(new URI("http://localhost:8080/token/findAll"))
                    .addParameter("email", email)
                    .build();

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                assert(response.getStatusLine().getStatusCode() == 200);

            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testFindByToken() throws IOException
    {
        try (CloseableHttpClient httpclient = HttpClients.createDefault())
        {
            URIBuilder uri = new URIBuilder("http://localhost:8080/token/findByToken/"+ token);
            HttpUriRequest httppost = RequestBuilder.get()
                    .setUri(uri.toString())
                    .build();
            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode node = mapper.readTree(EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8));
                assert(node.get("token").textValue().equals(token));
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testDeleteByToken() throws IOException
    {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            URIBuilder uri = new URIBuilder("http://localhost:8080/token/deleteByToken/"+ token);
            HttpUriRequest httppost = RequestBuilder.delete()
                    .setUri(uri.toString())
                    .build();

            try (CloseableHttpResponse response = httpclient.execute(httppost)) {
                assert(response.getStatusLine().getStatusCode() == 200);
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
