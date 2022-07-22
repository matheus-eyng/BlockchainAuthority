package com.example.blockchainauthority.contract;

import com.example.blockchainauthority.CertificationAuthority;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;

@Service
public class BlockchainService {

    private static final Logger log = LoggerFactory.getLogger(BlockchainService.class);

    private final CertificationAuthority ca;
    private final EthereumConnection connection;
    private final String walletKey = "09db5beb729ae69c538f64ce74ea4d2dae7002d67e16578b98c700e00793cb66";
    private final String walletAddress = "0xEa9f744c0ec3B9e862F82F1bED3e00E55D7E9EAC";
    private final LoadedContracts loadedContracts;

    private Web3j web3;
    private Credentials credentials;
    private ContractGasProvider gasProvider;



    public BlockchainService(CertificationAuthority ca, EthereumConnection connection, LoadedContracts loadedContracts) {
        this.ca = ca;
        this.connection = connection;
        this.loadedContracts = loadedContracts;
    }

    public void init() {
        log.info("INITIALIZING BLOCKCHAIN SERVICE");
        this.web3 = connection.connectToBlockchain("http://localhost:7545");
        this.gasProvider = connection.buildGasProvider();
        this.credentials = connection.loadCaWallet(walletKey);
    }

    public void deployPersonRegistryContract() throws Exception {
        log.info("DEPLOYING PERSON REGISTRY CONTRACT");
        PersonRegistry contract = PersonRegistry.
                deploy(web3, credentials, gasProvider, walletAddress, ca.getPublicKey().getEncoded())
                .send();
        loadedContracts.loadPersonRegistry(contract);
    }

    public void deployLogContract() throws Exception {
        log.info("DEPLOYING LOG CONTRACT");
        Log contract = Log.
                deploy(web3, credentials, gasProvider, walletAddress)
                .send();
        loadedContracts.loadLog(contract);
    }

    public void deployCrlContract() throws Exception {
        log.info("DEPLOYING CRL CONTRACT");
        Crl contract = Crl.
                deploy(web3, credentials, gasProvider, walletAddress, ca.getPublicKey().getEncoded())
                .send();
        loadedContracts.loadCrl(contract);
    }
}
