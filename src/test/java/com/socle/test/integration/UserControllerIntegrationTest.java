package com.socle.test.integration;

import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.socle.MainApplication;
import com.socle.controller.exception.ApiError;
import com.socle.repository.IUserRepository;
import com.socle.repository.model.User;
import com.socle.service.dto.UserDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplateTest;

    @LocalServerPort
    private int port;

    @Autowired
    private IUserRepository userRepository;

    private String getRootUrl() {
        return "http://localhost:" + port + "/api/v1/";
    }

    @Before
    public void setUp() {
        // given
        User user = new User();
        user.setFirstName("Lokesh");
        user.setLastName("Gupta");
        user.setEmail("howtodoinjava@gmail.com");
        user.setPassword("test");
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        // save test data
        userRepository.save(user);
    }

    @Test
    public void testGetAllUsers() {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<UserDto[]> response = restTemplateTest.exchange(getRootUrl() + "users", HttpMethod.GET, entity,
                UserDto[].class);

        Assert.assertNotNull(response.getBody());
        Assert.assertTrue(response.getBody().length == 1);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetUserById() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        Long id = userRepository.findAll().get(0).getId();

        ResponseEntity<UserDto> response = restTemplateTest.exchange(getRootUrl() + "users/" + id, HttpMethod.GET,
                entity, UserDto.class);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(response.getBody().getEmail(), "howtodoinjava@gmail.com");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetUserById_caseNotFound() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<ApiError> response = restTemplateTest.exchange(getRootUrl() + "users/100", HttpMethod.GET,
                entity, ApiError.class);

        Assert.assertNotNull(response.getBody().getMessage());
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setFirstName("admin");
        user.setLastName("admin");
        user.setPassword("test");

        ResponseEntity<UserDto> response = restTemplateTest.postForEntity(getRootUrl() + "users", user, UserDto.class);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getBody().getId());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCreateUser_caseBadRequest() {
        User user = new User();
        user.setEmail("admin"); // bad request
        user.setFirstName("admin");
        user.setLastName("admin");
        // password missing

        ResponseEntity<ApiError> response = restTemplateTest.postForEntity(getRootUrl() + "users", user,
                ApiError.class);

        Assert.assertNotNull(response);
        Assert.assertTrue(response.getBody().getErrors().size() == 2);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testUpdateUser() throws URISyntaxException, JSONException, JsonProcessingException {
        // UserDto user = new UserDto();
        // user.setId(0L);
        // user.setFirstName("update");
        // user.setLastName("update");
        // user.setEmail("howtodoinjava@gmail.com");
        // user.setPassword("test");

        Long id = 1l;
        String requestBody = "{ \"email\": \"string@hh.com\", \"firstName\": \"string\", \"id\": 0, \"lastName\": \"string\", \"password\": \"string\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
        String uri = getRootUrl() + "users/{id}";
        ResponseEntity<UserDto> response = restTemplateTest.exchange(uri, HttpMethod.PUT, entity, UserDto.class, id);

        Assert.assertNotNull(response.getBody());
        Assert.assertEquals(userRepository.findById(id).get().getFirstName(), response.getBody().getFirstName());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testUpdateUser_caseNotFound() {
        Long id = 100l;
        String requestBody = "{ \"email\": \"string@hh.com\", \"firstName\": \"string\", \"id\": 0, \"lastName\": \"string\", \"password\": \"string\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
        String uri = getRootUrl() + "users/{id}";
        ResponseEntity<ApiError> response = restTemplateTest.exchange(uri, HttpMethod.PUT, entity, ApiError.class, id);

        Assert.assertNotNull(response.getBody().getMessage());
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdatePost_caseBadRequest() {
        Long id = 1l;
        String requestBody = "{ \"email\": \"strin.com\", \"firstName\": \"string\", \"id\": 0, \"lastName\": \"string\", \"password\": \"string\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
        String uri = getRootUrl() + "users/{id}";
        ResponseEntity<ApiError> response = restTemplateTest.exchange(uri, HttpMethod.PUT, entity, ApiError.class, id);

        Assert.assertNotNull(response);
        Assert.assertTrue(response.getBody().getErrors().size() == 1);
        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void testDeleteUser() {

        String url = getRootUrl() + "user/{id}";

        Long id = userRepository.findAll().stream().findAny().get().getId();

        // exchange
        ResponseEntity<Map> result = restTemplateTest.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY, Map.class, id);

        Assert.assertTrue(result.getBody().containsKey("deleted") == Boolean.TRUE);
    }

    @Test
    public void testDeleteUser_notFound() {

        String url = getRootUrl() + "user/{id}";

        // exchange
        ResponseEntity<ApiError> result = restTemplateTest.exchange(url, HttpMethod.DELETE, HttpEntity.EMPTY,
                ApiError.class, 0);

        Assert.assertTrue(result.getStatusCode() == HttpStatus.NOT_FOUND);
    }

}
