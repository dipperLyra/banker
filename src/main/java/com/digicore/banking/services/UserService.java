package com.digicore.banking.services;

import com.digicore.banking.dao.AccountDetailsDAO;
import com.digicore.banking.dao.UserDAO;
import com.digicore.banking.entity.AccountDetails;
import com.digicore.banking.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    AccountDetailsDAO accountDetailsDAO;
    @Autowired
    UserDAO userDAO;

    /**
     * Retrieve the account balance of a given user account number and password
     * @param accountNumber unique ten digit string
     * @param password user security password
     * @return nullable Double
     * @throws JsonProcessingException if there's an issue reading from the datastore
     */
    public Optional<Double> accountBalance(String accountNumber, String password) throws JsonProcessingException {
        // from userDAO use password and accountNumber to get id
        // from accountDetailsDAO use id above to get account balance
        User user = userDAO.getOne(accountNumber);
        if (user.password().equals(password)) {
            AccountDetails accountDetails = accountDetailsDAO.getOne(Integer.toString(user.id()));
            return Optional.ofNullable(accountDetails.balance());
        }
        return Optional.empty();
    }
}
