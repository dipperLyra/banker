package com.digicore.banking.dao;

import com.digicore.banking.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

abstract class BaseDAO<T> {
    ObjectMapper mapper = new ObjectMapper();
    MockORM mockORM = new MockORM();
    String FILENAME;

    abstract Stream<String> dbLoader();

    abstract TreeMap<Integer, User> deserialize() throws JsonProcessingException;

    abstract User getOne(String s) throws JsonProcessingException;

    abstract List<User> getAll() throws JsonProcessingException;

    abstract boolean save(T t) throws JsonProcessingException;

    abstract boolean exists(int id) throws JsonProcessingException;

    abstract void delete(int id) throws JsonProcessingException;
}
