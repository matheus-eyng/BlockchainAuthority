package com.example.blockchainauthority.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Log extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b5060405161090038038061090083398101604081905261002f91610054565b600080546001600160a01b0319166001600160a01b0392909216919091179055610084565b60006020828403121561006657600080fd5b81516001600160a01b038116811461007d57600080fd5b9392505050565b61086d806100936000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806326defa73146100515780638da5cb5b14610066578063d544e01014610095578063e82b51fc146100a8575b600080fd5b61006461005f366004610485565b6100bb565b005b600054610079906001600160a01b031681565b6040516001600160a01b03909116815260200160405180910390f35b6100646100a3366004610485565b61019f565b6100646100b6366004610581565b610275565b6000546001600160a01b031633146101125760405162461bcd60e51b81526020600482015260156024820152742cb7ba9030b932903737ba103a34329037bbb732b960591b60448201526064015b60405180910390fd5b6001600160a01b03811660009081526003602052604090205460ff161561017b5760405162461bcd60e51b815260206004820152601c60248201527f417574686f7269747920616c72656164792072656769737465726564000000006044820152606401610109565b6001600160a01b03166000908152600360205260409020805460ff19166001179055565b6000546001600160a01b031633146101f15760405162461bcd60e51b81526020600482015260156024820152742cb7ba9030b932903737ba103a34329037bbb732b960591b6044820152606401610109565b6001600160a01b03811660009081526003602052604090205460ff166102545760405162461bcd60e51b8152602060048201526018602482015277105d5d1a1bdc9a5d1e481b9bdd081c9959da5cdd195c995960421b6044820152606401610109565b6001600160a01b03166000908152600360205260409020805460ff19169055565b3360009081526003602052604090205460ff166102cf5760405162461bcd60e51b8152602060048201526018602482015277105d5d1a1bdc9a5d1e481b9bdd081c9959da5cdd195c995960421b6044820152606401610109565b60008281526005602052604090205460ff161561032e5760405162461bcd60e51b815260206004820152601b60248201527f436572746966696361746520616c72656164792070726573656e7400000000006044820152606401610109565b60025433904261036e6040805160c08101825260609181018281528282018390526000608083015260a08201929092529081908152600060209091015290565b86815260208101869052600280546001810182556000919091528151805183926005027f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace019190829081906103c390826106f7565b50602082015160018201906103d890826106f7565b5060408201516002820155606082015160038201906103f790826106f7565b50505060209182015160049182015560008881526006835260408082208790558a8101518252918352819020849055805180820182526001600160a01b03871692810192909252868252517f0a61ea121f6b8bd5c885ad1d9dfd0f16486bc244d81be7ab6a2b8bfb804cd99590610473908990849087906107b7565b60405180910390a15050505050505050565b60006020828403121561049757600080fd5b81356001600160a01b03811681146104ae57600080fd5b9392505050565b634e487b7160e01b600052604160045260246000fd5b6040516080810167ffffffffffffffff811182821017156104ee576104ee6104b5565b60405290565b600082601f83011261050557600080fd5b813567ffffffffffffffff80821115610520576105206104b5565b604051601f8301601f19908116603f01168101908282118183101715610548576105486104b5565b8160405283815286602085880101111561056157600080fd5b836020870160208301376000602085830101528094505050505092915050565b60008060006060848603121561059657600080fd5b833567ffffffffffffffff808211156105ae57600080fd5b90850190608082880312156105c257600080fd5b6105ca6104cb565b8235828111156105d957600080fd5b6105e5898286016104f4565b8252506020830135828111156105fa57600080fd5b610606898286016104f4565b6020830152506040830135604082015260608301358281111561062857600080fd5b610634898286016104f4565b606083015250945060208601359350604086013591508082111561065757600080fd5b50610664868287016104f4565b9150509250925092565b600181811c9082168061068257607f821691505b6020821081036106a257634e487b7160e01b600052602260045260246000fd5b50919050565b601f8211156106f257600081815260208120601f850160051c810160208610156106cf5750805b601f850160051c820191505b818110156106ee578281556001016106db565b5050505b505050565b815167ffffffffffffffff811115610711576107116104b5565b6107258161071f845461066e565b846106a8565b602080601f83116001811461075a57600084156107425750858301515b600019600386901b1c1916600185901b1785556106ee565b600085815260208120601f198616915b828110156107895788860151825594840194600190910190840161076a565b50858210156107a75787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b8381526000602060608184015284516040606085015280518060a086015260005b818110156107f45782810184015186820160c0015283016107d8565b8181111561080657600060c083880101525b5095909101516001600160a01b0316608084015250506040810191909152601f91909101601f19160160c00191905056fea26469706673582212201b87d52bbc44ab8043e39b460937780a2dca4bb956580c868b7afc76cf199cbd64736f6c634300080f0033";

    public static final String FUNC_ADDAUTHORITY = "addAuthority";

    public static final String FUNC_APPENDCERTIFICATE = "appendCertificate";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_REMOVEAUTHORITY = "removeAuthority";

    public static final Event CERTIFICATEAPPENDED_EVENT = new Event("CertificateAppended", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<CertificationAuthority>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected Log(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Log(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Log(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Log(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<CertificateAppendedEventResponse> getCertificateAppendedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CERTIFICATEAPPENDED_EVENT, transactionReceipt);
        ArrayList<CertificateAppendedEventResponse> responses = new ArrayList<CertificateAppendedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CertificateAppendedEventResponse typedResponse = new CertificateAppendedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.certHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.authority = (CertificationAuthority) eventValues.getNonIndexedValues().get(1);
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CertificateAppendedEventResponse> certificateAppendedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<org.web3j.protocol.core.methods.response.Log, CertificateAppendedEventResponse>() {
            @Override
            public CertificateAppendedEventResponse apply(org.web3j.protocol.core.methods.response.Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CERTIFICATEAPPENDED_EVENT, log);
                CertificateAppendedEventResponse typedResponse = new CertificateAppendedEventResponse();
                typedResponse.log = log;
                typedResponse.certHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.authority = (CertificationAuthority) eventValues.getNonIndexedValues().get(1);
                typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CertificateAppendedEventResponse> certificateAppendedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CERTIFICATEAPPENDED_EVENT));
        return certificateAppendedEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> addAuthority(String authorityAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, authorityAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> appendCertificate(Person subject, byte[] certHash, byte[] authorityPublicKey) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPENDCERTIFICATE, 
                Arrays.<Type>asList(subject, 
                new org.web3j.abi.datatypes.generated.Bytes32(certHash), 
                new org.web3j.abi.datatypes.DynamicBytes(authorityPublicKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> removeAuthority(String authorityAddress) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REMOVEAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, authorityAddress)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Log load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Log(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Log load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Log(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Log load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Log(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Log load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Log(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Log> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)));
        return deployRemoteCall(Log.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Log> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)));
        return deployRemoteCall(Log.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Log> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)));
        return deployRemoteCall(Log.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Log> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _owner) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)));
        return deployRemoteCall(Log.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class CertificationAuthority extends DynamicStruct {
        public byte[] publicKey;

        public String location;

        public CertificationAuthority(byte[] publicKey, String location) {
            super(new org.web3j.abi.datatypes.DynamicBytes(publicKey),new org.web3j.abi.datatypes.Address(location));
            this.publicKey = publicKey;
            this.location = location;
        }

        public CertificationAuthority(DynamicBytes publicKey, Address location) {
            super(publicKey,location);
            this.publicKey = publicKey.getValue();
            this.location = location.getValue();
        }
    }

    public static class Person extends DynamicStruct {
        public String name;

        public String emailAddress;

        public byte[] publicKeyHash;

        public String cpf;

        public Person(String name, String emailAddress, byte[] publicKeyHash, String cpf) {
            super(new org.web3j.abi.datatypes.Utf8String(name),new org.web3j.abi.datatypes.Utf8String(emailAddress),new org.web3j.abi.datatypes.generated.Bytes32(publicKeyHash),new org.web3j.abi.datatypes.Utf8String(cpf));
            this.name = name;
            this.emailAddress = emailAddress;
            this.publicKeyHash = publicKeyHash;
            this.cpf = cpf;
        }

        public Person(Utf8String name, Utf8String emailAddress, Bytes32 publicKeyHash, Utf8String cpf) {
            super(name,emailAddress,publicKeyHash,cpf);
            this.name = name.getValue();
            this.emailAddress = emailAddress.getValue();
            this.publicKeyHash = publicKeyHash.getValue();
            this.cpf = cpf.getValue();
        }
    }

    public static class CertificateAppendedEventResponse extends BaseEventResponse {
        public byte[] certHash;

        public CertificationAuthority authority;

        public BigInteger timestamp;
    }
}
