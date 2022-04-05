package com.digicore.banking.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTests {

    UserService userService;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
    }

    @Test
    public void accountBalance() throws JsonProcessingException {
        String accountNumber = "0123456789";
        String password = "secret";
        Optional<Double> balance = Optional.of(10.0);

        when(userService.accountBalance(accountNumber, password)).thenReturn(balance);

        assertEquals(userService.accountBalance(accountNumber, password), Optional.of(10.0));
    }
}
