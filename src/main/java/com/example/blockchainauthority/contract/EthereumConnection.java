package com.example.blockchainauthority.contract;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Component
public class EthereumConnection {
    private static Logger logger = LoggerFactory.getLogger(EthereumConnection.class);

    private static final String wallet = "f551d258b083e1691289cf7234a1131e9aae741c0a6e3a972822732481fd79cf";

    public void connectToBlockchain() {
        logger.info("CONNECTING TO NETWORK");
        Web3j web3j = Web3j.build(new HttpService("http://0.0.0.0:7545"));
        logger.info("LOADING CREDENTIALS");
        Credentials credentials = Credentials.create(wallet);
    }

}
