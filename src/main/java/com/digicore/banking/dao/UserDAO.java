package com.digicore.banking.dao;

import com.digicore.banking.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 UseDAO exposes several APIs to store and access User data
 */
public class UserDAO extends BaseDAO<User> {

    private final String FILENAME = "user.json";

    /**
     * This method reads and loads the content of the database in memory
     * @return TreeMap<Integer, User>
     */
    Stream<String> dbLoader() {
        if (!Files.exists(Path.of(FILENAME))){
            MockORM.create(FILENAME);
        }

        return mockORM.read(FILENAME);
    }

    /**
     * Deserialize the json read into memory to map
     * @return TreeMap<Integer, User>
     * @throws JsonProcessingException if the json is invalid
     */
    TreeMap<Integer, User> deserialize() throws JsonProcessingException {
        String jsonString  = dbLoader().collect(Collectors.joining());
        TypeReference<TreeMap<Integer, User>> typeRef = new TypeReference<>() {};
        return mapper.readValue(jsonString, typeRef);
    }

    /**
     * Get a User by the id.
     *
     * @param email the unique key to select a User
     * @return selected User
     * @throws JsonProcessingException if the json is invalid
     */
    public User getOne(String email) throws JsonProcessingException {
        for (User u : deserialize().values()){
            if (u.email().equals(email)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Retrieve all Users
     * @return List of all existing Users
     * @throws JsonProcessingException if the json is invalid
     */
    public List<User> getAll() throws JsonProcessingException {
        return deserialize().values().stream().toList();
    }

    /**
     * Save user. This method can be used to a new user or update an existing user in the datastore
     * @param user to override or add to the datastore
     * @return true if user is successfully written to the datastore and false otherwise
     * @throws JsonProcessingException if the json is invalid
     */
    public boolean save(User user) throws JsonProcessingException {
        TreeMap<Integer, User> map = deserialize();

        ObjectMapper mapper = new ObjectMapper();
        String s;

        if (map.containsKey(user.id())) {
            map.put(user.id(), user);
        } else {
            int lastId = map.lastEntry().getKey();
            map.put(lastId + 1, user);
        }
        s = mapper.writeValueAsString(map);

        return mockORM.write(FILENAME, s);
    }

    /**
     * Check that user exists
     * @param id check user by id
     * @return true if user exists and false otherwise
     * @throws JsonProcessingException if the json is invalid
     */
    public boolean exists(int id) throws JsonProcessingException {
        return deserialize().containsKey(id);
    }

    /**
     * Delete user
     * @param id fetch user by id
     * @throws JsonProcessingException if the json is invalid
     */
    public void delete(int id) throws JsonProcessingException {
        TreeMap<Integer, User> map = deserialize();

        map.remove(id);

        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(map);
        mockORM.write(FILENAME, s);
    }

}
















