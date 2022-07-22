package com.example.blockchainauthority.contract;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class LoadedContracts {

    private static PersonRegistry personRegistryContract;
    private static Log logContract;
    private static Crl crlContract;

    public void loadLog(Log log) {
        logContract = log;
    }

    public void loadPersonRegistry(PersonRegistry personRegistry) {
        personRegistryContract = personRegistry;
    }

    public void loadCrl(Crl crl) {
        crlContract = crl;
    }

    public PersonRegistry getPersonRegistryContract() {
        return personRegistryContract;
    }

    public Log getLogContract() {
        return logContract;
    }
    public Crl getCrlContract() {
        return crlContract;
    }
}