package com.example.blockchainauthority.contract;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@Setter
public class LoadedContracts {

    private static Map<Contracts, String> loadedContracts = new HashMap<>();

    public void loadContract(Contracts contractType, String address) {
        loadedContracts.put(contractType, address);
    }

    public String getContractAddress(Contracts contractType) {
        return loadedContracts.get(contractType);
    }
}