package com.digicore.banking.services;

import org.springframework.stereotype.Service;

@Service
public class HelperService {

    public boolean isAccountNumberOk(String accountNumber) {
        return accountNumber.length() == 10;
    }
}
