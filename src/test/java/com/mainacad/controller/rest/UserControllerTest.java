package com.mainacad.controller.rest;

import com.mainacad.model.User;
import com.mainacad.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test", "rest"})
class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void save() throws URISyntaxException {
        User user = new User();
        RequestEntity<User> request = new RequestEntity<>(user, HttpMethod.PUT, new URI("/user"));

        when(userService.save(any(User.class))).thenReturn(user);
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(response.getStatusCode(), HttpStatus.OK);

        verify(userService, times(1)).save(any(User.class));
    }

    @Test
    void update() {
        User user = new User();
        when(userService.update(any(User.class))).thenReturn(user);
        RequestEntity<User> request = new RequestEntity<>(user, HttpMethod.POST, URI.create("/user"));
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(userService, times(1)).update(any(User.class));
        System.out.println("request.getType() = " + request.getType());
    }

    @Test
    void getByLoginAndPassword() {

        URI uri = URI.create("/user/auth");
        User user = new User("ivanov255", "1547", "Ivanov", "Dmitro");

        when(userService.getByLoginAndPassword(anyString(), anyString())).thenReturn(user);

        RequestEntity request =
                new RequestEntity("{\"login\":\"ivanov255\",\"password\":\"1547\"}", HttpMethod.POST, uri);
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).getByLoginAndPassword(anyString(), anyString());
    }

    @Test
    void getByLoginAndPasswordNegativeCase() {

        URI uri = URI.create("/user/auth");
        RequestEntity request =
                new RequestEntity("{\"login\":\"ignatenko2207\",\"password\":\"123456\"}", HttpMethod.POST, uri);

        when(userService.getByLoginAndPassword("ignatenko2207", "123456")).thenReturn(null);
        ResponseEntity response = testRestTemplate.postForEntity(uri, request, Object.class);

        assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
        assertTrue(response.getBody() == null);

        verify(userService, times(1)).getByLoginAndPassword("ignatenko2207", "123456");

    }


    @Test
    void getUser() {
        User user = new User();

        when(userService.getById(anyInt())).thenReturn(user);

        RequestEntity request = new RequestEntity(HttpMethod.GET, URI.create("/user/111"));
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).getById(anyInt());
    }

    @Test
    void getAllUsers() {
        User user1 = new User();
        User user2 = new User();

        List<User> users = Arrays.asList(user1, user2);
        RequestEntity request = new RequestEntity<>(HttpMethod.GET, URI.create("/user"));

        when(userService.getAll()).thenReturn(users);
        ResponseEntity<List<User>> response = testRestTemplate.exchange(request, new ParameterizedTypeReference<List<User>>() {
        });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(User.class, response.getBody().get(0).getClass());

        verify(userService, times(1)).getAll();
    }

    @Test
    void delete() {
        User user = new User();

        doNothing().when(userService).delete(any(User.class));

        RequestEntity request = new RequestEntity(user, HttpMethod.DELETE, URI.create("/user"));
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).delete(any(User.class));
    }

    @Test
    void deleteById() {
        User user = new User();

        doNothing().when(userService).delete(anyInt());

        RequestEntity request = new RequestEntity(HttpMethod.DELETE, URI.create("/user/1"));
        ResponseEntity<User> response = testRestTemplate.exchange(request, User.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).delete(anyInt());
    }
}