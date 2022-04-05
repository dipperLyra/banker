package com.digicore.banking.dao;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

public interface DAO<T> {
    void setFileName (String s);
    Stream<String> dbLoader();
    TreeMap<Integer, T> deserialize() throws JsonProcessingException;
    T getOne(String s) throws JsonProcessingException;
    List<T> getAll() throws JsonProcessingException;
    boolean save(T t) throws JsonProcessingException;
    boolean exists(int id) throws JsonProcessingException;
    void delete(int id) throws JsonProcessingException;
}
