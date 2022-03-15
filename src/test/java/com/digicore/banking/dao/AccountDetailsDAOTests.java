package com.digicore.banking.dao;

import com.digicore.banking.model.AccountDetails;
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

public class AccountDetailsDAOTests implements BaseDAOTests {

    private final MockORM mockORM = new MockORM();
    private final AccountDetailsDAO detailsDAO = new AccountDetailsDAO();
    private final int id = 1;
    private final String accountNumber = "0123456789";
    private final Double balance = 0.0;
    private final int userId = 1;
    private final TreeMap<Integer, AccountDetails> detailsMap = new TreeMap<>();

    private final AccountDetails accountDetails = new AccountDetails(id, userId, accountNumber, balance);
    private static final String FILENAME = "datastore/account-details.json";

    @BeforeEach
    public void init() throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        detailsMap.put(accountDetails.id(), accountDetails);

        String s = mapper.writeValueAsString(detailsMap);
        mockORM.write(FILENAME, s);
    }

    @AfterAll
    static void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(FILENAME));
    }

    @Test
    public void getOne() throws JsonProcessingException {
        AccountDetails a = detailsDAO.getOne(Integer.toString(userId));
        assertEquals(a.accountNumber(), accountNumber);
    }

    @Test
    public void getAll() throws JsonProcessingException {
        String accountNumber = "0023456789";
        AccountDetails detail = new AccountDetails(2, userId + 1, accountNumber, balance);
        assertTrue(detailsDAO.save(detail));

        List<AccountDetails> details = detailsDAO.getAll();
        assertTrue(details.size() > 1);
    }

    @Test
    public void save() throws IOException {
        String accountNumber = "0022456789";
        AccountDetails detail = new AccountDetails(3, userId + 1, accountNumber, balance);
        assertTrue(detailsDAO.save(detail));
    }

    @Test
    public void exists() throws IOException {
        assertTrue(detailsDAO.exists(userId));
    }

    @Test
    public void delete() throws JsonProcessingException {
        String accountNumber = "0023456789";
        AccountDetails detail = new AccountDetails(2, userId + 1, accountNumber, balance);
        assertTrue(detailsDAO.save(detail));

        detailsDAO.delete(userId);
        assertFalse(detailsDAO.exists(userId));
    }
}
