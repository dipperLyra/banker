package com.digicore.banking.dao;

import com.digicore.banking.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MockORMTests {

    private final MockORM mockORM = new MockORM();
    ObjectMapper mapper = new ObjectMapper();
    private final User user = new User(1, "testUser", "testAddress", "testPassword");
    private final TreeMap<Integer, User> userMap = new TreeMap<>();
    private static final String filename = "user.json";
    private String jsonResult = "";
    private boolean created;
    private boolean written;


    @BeforeEach
    void init() throws JsonProcessingException {
        created = MockORM.create(filename);
        userMap.put(1, user);

        jsonResult = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(userMap);
        written = mockORM.write(filename, jsonResult);
    }

    @AfterEach
    void tearDown() {
        MockORM.delete(filename);
        jsonResult = "";
    }

    @Test
    void create() {
        assertTrue(created);
    }

    @Test
    void write() {
        assertTrue(written);
    }

    @Test
    void read() {
        var u = mockORM.read(filename);
        assertNotEquals(u.toList().size(), 0);
    }

}

