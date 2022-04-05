package com.digicore.banking.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;


abstract class BaseDAO<T> implements DAO<T>{
    ObjectMapper mapper = new ObjectMapper();
    MockORM mockORM = new MockORM();
    protected String FILENAME;

    public void setFileName (String s) {
        String DIRECTORY = "datastore/";
        FILENAME = DIRECTORY + s;
    }

    /**
     * This method reads and loads the content of the database in memory
     * @return TreeMap<Integer, User>
     */
    public Stream<String> dbLoader() {
        if (!Files.exists(Path.of(FILENAME))){
            MockORM.create(FILENAME);
        }
        return mockORM.read(FILENAME);
    }

    /**
     * Deserialize the json read into memory to map
     * @return TreeMap<Integer, T>
     * @throws JsonProcessingException if the json is invalid
     */
    public abstract TreeMap<Integer, T> deserialize() throws JsonProcessingException;

    /**
     * Get a User by the id.
     *
     * @param s the unique key to select T
     * @return selected User
     * @throws JsonProcessingException if the json is invalid
     */
    public abstract T getOne(String s) throws JsonProcessingException;

    /**
     * Retrieve all T
     * @return List of all existing T
     * @throws JsonProcessingException if the json is invalid
     */
    public abstract List<T> getAll() throws JsonProcessingException;

    /**
     * Save user. This method can be used to a new t or update an existing t in the datastore
     * @param t to override or add to the datastore
     * @return true if t is successfully written to the datastore and false otherwise
     * @throws JsonProcessingException if the json is invalid
     */
    public abstract boolean save(T t) throws JsonProcessingException;

    /**
     * Check that user exists
     * @param id check for t by id
     * @return true if t exists and false otherwise
     * @throws JsonProcessingException if the json is invalid
     */
    public abstract boolean exists(int id) throws JsonProcessingException;

    /**
     * Delete t
     * @param id fetch t by id
     * @throws JsonProcessingException if the json is invalid
     */
    public abstract void delete(int id) throws JsonProcessingException;
}
