package com.digicore.banking.controller;

import com.digicore.banking.messages.Messages;
import com.digicore.banking.model.Account;
import com.digicore.banking.model.response.AccountDetailsResponse;
import com.digicore.banking.services.HelperService;
import com.digicore.banking.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping()
public class Controller {

    @Autowired
    UserService userService;
    @Autowired
    HelperService helperService;
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();


    @GetMapping("/account_info/{accountNumber}")
    public ResponseEntity<AccountDetailsResponse> getAccountBalance(@PathVariable String accountNumber) throws JsonProcessingException {

        if (helperService.isAccountNumberOk(accountNumber)) {
            Optional<Double> accountBalance = userService.accountBalance(accountNumber, auth.getCredentials().toString());

            Account account = new Account(auth.getName(), accountNumber, accountBalance.orElse(0.0));
            var accountDetails = new AccountDetailsResponse(HttpStatus.OK.value(), true, Messages.ACCOUNT_DETAILS_RESPONSE_SUCCESS.value, account);
            return ResponseEntity.of(Optional.of(accountDetails));
        }

        var accountDetails = new AccountDetailsResponse(HttpStatus.FORBIDDEN.value(), false, Messages.ACCOUNT_DETAILS_RESPONSE_FAIL.value, null);
        return ResponseEntity.of(Optional.of(accountDetails));
    }

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.of(Optional.of("Hello world!"));
    }

}
