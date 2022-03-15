package com.digicore.banking.dao;

import com.digicore.banking.model.AccountDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AccountDetailsDAO extends BaseDAO<AccountDetails> {

    public AccountDetailsDAO() {
        setFileName("account-details.json");
    }

    @Override
    TreeMap<Integer, AccountDetails> deserialize() throws JsonProcessingException {
        String jsonString  = dbLoader().collect(Collectors.joining());
        TypeReference<TreeMap<Integer, AccountDetails>> typeRef = new TypeReference<>() {};
        return mapper.readValue(jsonString, typeRef);
    }

    /**
     *
     * @param s is the String representation of AccountDetails.userId used to select T
     * @return AccountDetails
     * @throws JsonProcessingException
     */
    @Override
    AccountDetails getOne(String s) throws JsonProcessingException {
        TreeMap<Integer, AccountDetails> map = deserialize();
        for (AccountDetails a : map.values()){
            if (Integer.toString(a.userId()).equals(s)) {
                return a;
            }
        }
        return null;
    }

    @Override
    List<AccountDetails> getAll() throws JsonProcessingException {
        return deserialize().values().stream().toList();
    }

    @Override
    boolean save(AccountDetails accountDetails) throws JsonProcessingException {
        TreeMap<Integer, AccountDetails> map = deserialize();
        int lastId = map.lastEntry().getKey();

        ObjectMapper mapper = new ObjectMapper();
        String s;

        map.keySet().stream()
                .findFirst()
                .filter(key -> key == accountDetails.id())
                .ifPresentOrElse(a -> map.put(accountDetails.id(), accountDetails), () -> map.put(lastId + 1, accountDetails));

        s = mapper.writeValueAsString(map);

        return mockORM.write(FILENAME, s);
    }

    @Override
    boolean exists(int userId) throws JsonProcessingException {
        return deserialize().values().stream()
                .anyMatch(d -> d.userId() == userId);
    }

    @Override
    void delete(int userId) throws JsonProcessingException {
        TreeMap<Integer, AccountDetails> map = deserialize();

        map.values().stream()
                .findFirst()
                .filter(detail -> detail.userId() == userId)
                        .ifPresent(detail -> map.remove(detail.id()));

        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(map);
        mockORM.write(FILENAME, s);
    }
}
