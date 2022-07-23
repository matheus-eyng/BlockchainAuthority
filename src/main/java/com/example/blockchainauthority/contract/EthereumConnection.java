package com.example.blockchainauthority.contract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

@Component
public class EthereumConnection {
    private static Logger log = LoggerFactory.getLogger(EthereumConnection.class);


    public Web3j connectToBlockchain(String url) {
        log.info("CONNECTING TO NETWORK");
        return Web3j.build(new HttpService(url));
    }

    public ContractGasProvider buildGasProvider() {
        return new DefaultGasProvider();
    }

    public Credentials loadCaWallet(String walletKey) {
        log.info("LOADING CREDENTIALS");
        Credentials credentials = Credentials.create(walletKey);
        log.info("CREDENTIALS LOADED");
        return credentials;
    }
}
