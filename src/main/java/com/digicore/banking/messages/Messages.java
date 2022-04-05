package com.digicore.banking.messages;

public enum Messages {
    ACCOUNT_DETAILS_RESPONSE_SUCCESS("Account details retrieval was successful"),
    ACCOUNT_DETAILS_RESPONSE_FAIL("Account details retrieval failed, wrong account number");

    public final String value;

    Messages(String s) {
        this.value = s;
    }
}
