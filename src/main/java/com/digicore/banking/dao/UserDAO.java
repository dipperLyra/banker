package com.digicore.banking.dao;

import com.digicore.banking.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 UseDAO exposes several APIs to store and access User data
 */
public class UserDAO extends BaseDAO<User> {

    public UserDAO() {
        setFileName("user.json");
    }


    @Override
    TreeMap<Integer, User> deserialize() throws JsonProcessingException {
        String jsonString  = dbLoader().collect(Collectors.joining());
        TypeReference<TreeMap<Integer, User>> typeRef = new TypeReference<>() {};
        return mapper.readValue(jsonString, typeRef);
    }

    @Override
    public User getOne(String email) throws JsonProcessingException {
        TreeMap<Integer, User> map = deserialize();
        for (User u : map.values()){
            if (u.email().equals(email)) {
                return u;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() throws JsonProcessingException {
        return deserialize().values().stream().toList();
    }

    @Override
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

    @Override
    public boolean exists(int id) throws JsonProcessingException {
        return deserialize().containsKey(id);
    }

    @Override
    public void delete(int id) throws JsonProcessingException {
        TreeMap<Integer, User> map = deserialize();

        map.remove(id);

        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(map);
        mockORM.write(FILENAME, s);
    }

}
















