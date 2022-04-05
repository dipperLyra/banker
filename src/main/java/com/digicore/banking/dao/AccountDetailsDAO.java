package com.digicore.banking.dao;

import com.digicore.banking.entity.AccountDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class AccountDetailsDAO extends BaseDAO<AccountDetails> {

    public AccountDetailsDAO() {
        setFileName("account-details.json");
    }

    @Override
    public TreeMap<Integer, AccountDetails> deserialize() throws JsonProcessingException {
        String jsonString  = dbLoader().collect(Collectors.joining());
        TypeReference<TreeMap<Integer, AccountDetails>> typeRef = new TypeReference<>() {};
        return mapper.readValue(jsonString, typeRef);
    }

    /**
     *
     * @param userId is the String representation of AccountDetails.userId used to select T
     * @return AccountDetails
     * @throws JsonProcessingException
     */
    @Override
    public AccountDetails getOne(String userId) throws JsonProcessingException {
        TreeMap<Integer, AccountDetails> map = deserialize();
        for (AccountDetails a : map.values()){
            if (Integer.toString(a.userId()).equals(userId)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public List<AccountDetails> getAll() throws JsonProcessingException {
        return deserialize().values().stream().toList();
    }

    @Override
    public boolean save(AccountDetails accountDetails) throws JsonProcessingException {
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
    public boolean exists(int userId) throws JsonProcessingException {
        return deserialize().values().stream()
                .anyMatch(d -> d.userId() == userId);
    }

    @Override
    public void delete(int userId) throws JsonProcessingException {
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
