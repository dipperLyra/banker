package com.digicore.banking.model.response;

import com.digicore.banking.model.Account;

//{ int responseCode
//boolean success
//String message
//Object account
//{
//String accountName,
//String accountNumber,
//Double balance
//}
//}
public record AccountDetailsResponse (int responseCode,
                                      boolean success,
                                      String message,
                                      Account account) { }
