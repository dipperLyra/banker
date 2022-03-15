package com.digicore.banking.dao;

import com.digicore.banking.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class UserDAOTests {
    private final MockORM mockORM = new MockORM();
    private final UserDAO userDAO = new UserDAO();
    private final int userId = 1;
    private final String name = "testName";
    private final String password = "password";
    private final String email = "test@test.com";
    private final TreeMap<Integer, User> userMap = new TreeMap<>();
    private final User user = new User(userId, name, email, password);
    private static final String FILENAME = "user.json";

    @BeforeEach
    void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        userMap.put(user.id(), user);
        String s = mapper.writeValueAsString(userMap);
        mockORM.write(FILENAME, s);
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(FILENAME));
    }

    @Test
    void getOne() throws JsonProcessingException {
        User u = userDAO.getOne(email);
        assertEquals(u.email(), email);
    }

    @Test
    void getAll() throws JsonProcessingException {
        String email = "test1@test.com";
        User user1 = new User(3, name, email, password);
        assertTrue(userDAO.save(user1));

        List<User> users = userDAO.getAll();
        assertTrue(users.size() > 1);
    }

    @Test
    void save() throws IOException {
        String email = "test1@test.com";
        User user1 = new User(2, name, email, password);
        assertTrue(userDAO.save(user1));
    }

    @Test
    void exists() throws IOException {
        assertTrue(userDAO.exists(userId));
    }

    @Test
    void delete() throws JsonProcessingException {
        String email = "test1@test.com";
        User user1 = new User(0, name, email, password);
        assertTrue(userDAO.save(user1));

        userDAO.delete(userId);
        assertFalse(userDAO.exists(userId));
    }
}
