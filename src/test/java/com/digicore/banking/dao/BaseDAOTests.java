package com.digicore.banking.dao;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface BaseDAOTests {
    String FILENAME = "";
    void init() throws IOException;
    void getOne() throws JsonProcessingException;
    void getAll() throws JsonProcessingException;
    void save() throws IOException;
    void exists() throws IOException;
    void delete() throws JsonProcessingException;
}
