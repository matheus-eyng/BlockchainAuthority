package com.example.blockchainauthority.contract;

import com.example.blockchainauthority.CertificationAuthority;
import com.example.blockchainauthority.entities.Person;
import org.bouncycastle.cert.X509CertificateHolder;
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
    private final String walletKey = "d0caf0de7d002a75d5441fc21b0a90cd55fac7650cb460099852567cc1beadd4";
    private final String walletAddress = "0x7223e1978EfF7b45F36F4150F9c870A7655ad467";
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
        log.info("Authority added!");
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
        log.info("Person with cpf: " + person.getCpf() + " was registered to the blockchain!");
        return "Person with cpf: " + person.getCpf() + " was registered to the blockchain";
    }

    public boolean checkIfRegistered(String cpf) {
        log.info("Checking if cpf " + cpf + " is registered in the blockchain...");
        PersonRegistry personRegistryContract = PersonRegistry
                .load(
                        loadedContracts.getContractAddress(PERSON_REGISTRY),
                        web3,
                        credentials,
                        gasProvider);

        RemoteFunctionCall<Boolean> transaction = personRegistryContract.isCpfRegistered(cpf);
        try {
            return transaction.send();
        } catch (Exception e) {
            return false;
        }
    }

    public PersonRegistry.Person getPersonFromRegistry(String cpf) throws Exception {
        PersonRegistry personRegistryContract = PersonRegistry
                .load(
                        loadedContracts.getContractAddress(PERSON_REGISTRY),
                        web3,
                        credentials,
                        gasProvider);

        RemoteFunctionCall<PersonRegistry.Person> transaction = personRegistryContract.getByCpf(cpf);
        return transaction.send();
    }


    public void addAuthority(String address) throws Exception {
        log.info("Adding authroity to blockchain...");
        Log logContract = Log.load(
                loadedContracts.getContractAddress(LOG),
                web3,
                credentials,
                gasProvider);
        RemoteFunctionCall transaction = logContract.addAuthority(address);
        transaction.send();
        log.info("Authority added!");
    }

    public byte[] loadPreCertificateIntoLog(byte[] certificateHash, PersonRegistry.Person person) throws Exception {
        log.info("Loading pre certificate into Log contract...");
        Log logContract = Log
                .load(
                        loadedContracts.getContractAddress(LOG),
                        web3,
                        credentials,
                        gasProvider);

        Log.Person logPerson = new Log.Person(person.name, person.emailAddress, person.publicKeyHash, person.cpf);
        RemoteFunctionCall<TransactionReceipt> transaction = logContract.appendCertificate(logPerson, certificateHash, ca.getPublicKey().getEncoded());
        TransactionReceipt receipt = transaction.send();
        return receipt.getBlockHash().getBytes();
    }

}
