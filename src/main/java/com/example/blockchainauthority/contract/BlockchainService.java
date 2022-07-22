package com.example.blockchainauthority.contract;

import com.example.blockchainauthority.CertificationAuthority;
import com.example.blockchainauthority.entities.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;

import static com.example.blockchainauthority.contract.Contracts.CRL;
import static com.example.blockchainauthority.contract.Contracts.LOG;
import static com.example.blockchainauthority.contract.Contracts.PERSON_REGISTRY;

@Service
public class BlockchainService {

    private static final Logger log = LoggerFactory.getLogger(BlockchainService.class);

    private final CertificationAuthority ca;
    private final EthereumConnection connection;
    private final String walletKey = "3df3edab8c0d2c8189908412d67a9c0a831f2b96f17d445b0d79a98070eba7aa";
    private final String walletAddress = "0xe745ED4354d3666680464BFE881617a019F424Ee";
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
        log.info("Initializing blockchain service");
        this.web3 = connection.connectToBlockchain("http://localhost:7545");
        this.gasProvider = connection.buildGasProvider();
        this.credentials = connection.loadCaWallet(walletKey);
    }

    public void deployPersonRegistryContract() throws Exception {
        log.info("Deploying person registry contract...");
        PersonRegistry contract = PersonRegistry.
                deploy(web3, credentials, gasProvider, walletAddress, ca.getPublicKey().getEncoded())
                .send();
        loadedContracts.loadContract(PERSON_REGISTRY, contract.getContractAddress());
    }

    public void deployLogContract() throws Exception {
        log.info("Deploying log contract...");
        Log contract = Log.
                deploy(web3, credentials, gasProvider, walletAddress)
                .send();
        loadedContracts.loadContract(LOG, contract.getContractAddress());

        addAuthorityToLog(walletAddress);
    }

    public void deployCrlContract() throws Exception {
        log.info("Deploying crl contract...");
        Crl contract = Crl.
                deploy(web3, credentials, gasProvider, walletAddress, ca.getPublicKey().getEncoded())
                .send();
        loadedContracts.loadContract(CRL, contract.getContractAddress());
    }

    private void addAuthorityToLog(String walletAddress) throws Exception {
        log.info("Adding authority to log authorities list...");
        Log logContract = Log.load(loadedContracts.getContractAddress(LOG), this.web3, credentials, gasProvider);
        RemoteFunctionCall<TransactionReceipt> transaction = logContract.addAuthority(walletAddress);
        TransactionReceipt receipt = transaction.send();
        assert receipt.isStatusOK();
    }

    public String addPersonToRegistry(Person person) throws Exception {
        log.info("Adding person to registry...");
        PersonRegistry personRegistryContract = PersonRegistry
                .load(
                        loadedContracts.getContractAddress(PERSON_REGISTRY),
                        web3,
                        credentials,
                        gasProvider);

        RemoteFunctionCall<TransactionReceipt> transaction = personRegistryContract.registerPerson(
                person.getName(),
                person.getEmailAddress(),
                person.getPublicKeyHash(),
                person.getCpf());

        TransactionReceipt receipt = transaction.send();
        System.out.println(receipt.getLogs());
        System.out.println(receipt.getLogs().get(0).getData());
        return "Registered"; // FIXME
    }

    public void transact() throws Exception {
        byte[] testBytes = new byte[32];
        Log.Person person = new Log.Person("Joao", "joao@email.com", testBytes, "123456");
    }
}
