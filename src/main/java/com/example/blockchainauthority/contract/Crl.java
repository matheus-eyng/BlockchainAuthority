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
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
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
public class Crl extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162000ce338038062000ce383398101604081905262000034916200007c565b600080546001600160a01b0319166001600160a01b03841617905560016200005d82826200020b565b505050620002d7565b634e487b7160e01b600052604160045260246000fd5b600080604083850312156200009057600080fd5b82516001600160a01b0381168114620000a857600080fd5b602084810151919350906001600160401b0380821115620000c857600080fd5b818601915086601f830112620000dd57600080fd5b815181811115620000f257620000f262000066565b604051601f8201601f19908116603f011681019083821181831017156200011d576200011d62000066565b8160405282815289868487010111156200013657600080fd5b600093505b828410156200015a57848401860151818501870152928501926200013b565b828411156200016c5760008684830101525b8096505050505050509250929050565b600181811c908216806200019157607f821691505b602082108103620001b257634e487b7160e01b600052602260045260246000fd5b50919050565b601f8211156200020657600081815260208120601f850160051c81016020861015620001e15750805b601f850160051c820191505b818110156200020257828155600101620001ed565b5050505b505050565b81516001600160401b0381111562000227576200022762000066565b6200023f816200023884546200017c565b84620001b8565b602080601f8311600181146200027757600084156200025e5750858301515b600019600386901b1c1916600185901b17855562000202565b600085815260208120601f198616915b82811015620002a85788860151825594840194600190910190840162000287565b5085821015620002c75787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b6109fc80620002e76000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c80636b29fe4c1461005c5780638da5cb5b14610085578063925a34b8146100b0578063b949048b146100e3578063f183fd4a146100f8575b600080fd5b61006f61006a366004610627565b61010d565b60405161007c919061068d565b60405180910390f35b600054610098906001600160a01b031681565b6040516001600160a01b03909116815260200161007c565b6100d36100be366004610627565b60009081526004602052604090205460ff1690565b604051901515815260200161007c565b6100eb6103a2565b60405161007c91906106f8565b61010b6101063660046107be565b610430565b005b61013b6040518060800160405280600080191681526020016060815260200160608152602001606081525090565b60008281526004602052604090205460ff1661019e5760405162461bcd60e51b815260206004820152601760248201527f4365727469666963617465206e6f74207265766f6b656400000000000000000060448201526064015b60405180910390fd5b60008281526003602052604090205460028054829081106101c1576101c1610867565b9060005260206000209060040201604051806080016040529081600082015481526020016001820180546101f49061087d565b80601f01602080910402602001604051908101604052809291908181526020018280546102209061087d565b801561026d5780601f106102425761010080835404028352916020019161026d565b820191906000526020600020905b81548152906001019060200180831161025057829003601f168201915b505050505081526020016002820180546102869061087d565b80601f01602080910402602001604051908101604052809291908181526020018280546102b29061087d565b80156102ff5780601f106102d4576101008083540402835291602001916102ff565b820191906000526020600020905b8154815290600101906020018083116102e257829003601f168201915b505050505081526020016003820180546103189061087d565b80601f01602080910402602001604051908101604052809291908181526020018280546103449061087d565b80156103915780601f1061036657610100808354040283529160200191610391565b820191906000526020600020905b81548152906001019060200180831161037457829003601f168201915b505050505081525050915050919050565b600180546103af9061087d565b80601f01602080910402602001604051908101604052809291908181526020018280546103db9061087d565b80156104285780601f106103fd57610100808354040283529160200191610428565b820191906000526020600020905b81548152906001019060200180831161040b57829003601f168201915b505050505081565b6000546001600160a01b031633146104825760405162461bcd60e51b81526020600482015260156024820152742cb7ba9030b932903737ba103a34329037bbb732b960591b6044820152606401610195565b600084815260046020526040902054849060ff16156104e35760405162461bcd60e51b815260206004820152601b60248201527f436572746966696361746520616c7265616479207265766f6b656400000000006044820152606401610195565b6105116040518060800160405280600080191681526020016060815260200160608152602001606081525090565b858152602081018581526040820185905260608201849052600280546001810182556000919091528251600482027f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace81019182559251919284927f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5acf9091019061059a9082610906565b50604082015160028201906105af9082610906565b50606082015160038201906105c49082610906565b50505060008781526003602090815260408083208490556004825291829020805460ff1916600117905590518881527f44d80a438cb4fa3b5aedbe551ce3d9382e37f446917b783e1dee70a03b4180e3910160405180910390a150505050505050565b60006020828403121561063957600080fd5b5035919050565b6000815180845260005b818110156106665760208185018101518683018201520161064a565b81811115610678576000602083870101525b50601f01601f19169290920160200192915050565b602081528151602082015260006020830151608060408401526106b360a0840182610640565b90506040840151601f19808584030160608601526106d18383610640565b92506060860151915080858403016080860152506106ef8282610640565b95945050505050565b60208152600061070b6020830184610640565b9392505050565b634e487b7160e01b600052604160045260246000fd5b600067ffffffffffffffff8084111561074357610743610712565b604051601f8501601f19908116603f0116810190828211818310171561076b5761076b610712565b8160405280935085815286868601111561078457600080fd5b858560208301376000602087830101525050509392505050565b600082601f8301126107af57600080fd5b61070b83833560208501610728565b600080600080608085870312156107d457600080fd5b84359350602085013567ffffffffffffffff808211156107f357600080fd5b6107ff8883890161079e565b9450604087013591508082111561081557600080fd5b818701915087601f83011261082957600080fd5b61083888833560208501610728565b9350606087013591508082111561084e57600080fd5b5061085b8782880161079e565b91505092959194509250565b634e487b7160e01b600052603260045260246000fd5b600181811c9082168061089157607f821691505b6020821081036108b157634e487b7160e01b600052602260045260246000fd5b50919050565b601f82111561090157600081815260208120601f850160051c810160208610156108de5750805b601f850160051c820191505b818110156108fd578281556001016108ea565b5050505b505050565b815167ffffffffffffffff81111561092057610920610712565b6109348161092e845461087d565b846108b7565b602080601f83116001811461096957600084156109515750858301515b600019600386901b1c1916600185901b1785556108fd565b600085815260208120601f198616915b8281101561099857888601518255948401946001909101908401610979565b50858210156109b65787850151600019600388901b60f8161c191681555b5050505050600190811b0190555056fea2646970667358221220a2b0d5a5db0bd483c65e995783426417cb1b71cf6cc4e2b2372d35c78342010e64736f6c634300080f0033";

    public static final String FUNC_CERTISREVOKED = "certIsRevoked";

    public static final String FUNC_FINDCERT = "findCert";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_OWNERKEY = "ownerKey";

    public static final String FUNC_REVOKECERTIFICATE = "revokeCertificate";

    public static final Event CERTIFICATEREVOKED_EVENT = new Event("CertificateRevoked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
    ;

    @Deprecated
    protected Crl(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Crl(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Crl(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Crl(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<CertificateRevokedEventResponse> getCertificateRevokedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CERTIFICATEREVOKED_EVENT, transactionReceipt);
        ArrayList<CertificateRevokedEventResponse> responses = new ArrayList<CertificateRevokedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CertificateRevokedEventResponse typedResponse = new CertificateRevokedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.certHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CertificateRevokedEventResponse> certificateRevokedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, CertificateRevokedEventResponse>() {
            @Override
            public CertificateRevokedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CERTIFICATEREVOKED_EVENT, log);
                CertificateRevokedEventResponse typedResponse = new CertificateRevokedEventResponse();
                typedResponse.log = log;
                typedResponse.certHash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CertificateRevokedEventResponse> certificateRevokedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CERTIFICATEREVOKED_EVENT));
        return certificateRevokedEventFlowable(filter);
    }

    public RemoteFunctionCall<Boolean> certIsRevoked(byte[] _certHash) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CERTISREVOKED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_certHash)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Certificate> findCert(byte[] _certHash) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_FINDCERT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_certHash)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Certificate>() {}));
        return executeRemoteCallSingleValueReturn(function, Certificate.class);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<byte[]> ownerKey() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNERKEY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicBytes>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeCertificate(byte[] _certHash, byte[] _signature, String _name, byte[] _publicKey) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REVOKECERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_certHash), 
                new org.web3j.abi.datatypes.DynamicBytes(_signature), 
                new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.DynamicBytes(_publicKey)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Crl load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Crl(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Crl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Crl(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Crl load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Crl(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Crl load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Crl(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Crl> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _owner, byte[] _ownerKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.DynamicBytes(_ownerKey)));
        return deployRemoteCall(Crl.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Crl> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _owner, byte[] _ownerKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.DynamicBytes(_ownerKey)));
        return deployRemoteCall(Crl.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Crl> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _owner, byte[] _ownerKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.DynamicBytes(_ownerKey)));
        return deployRemoteCall(Crl.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Crl> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _owner, byte[] _ownerKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.DynamicBytes(_ownerKey)));
        return deployRemoteCall(Crl.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class Certificate extends DynamicStruct {
        public byte[] certHash;

        public byte[] signature;

        public String name;

        public byte[] publicKey;

        public Certificate(byte[] certHash, byte[] signature, String name, byte[] publicKey) {
            super(new org.web3j.abi.datatypes.generated.Bytes32(certHash),new org.web3j.abi.datatypes.DynamicBytes(signature),new org.web3j.abi.datatypes.Utf8String(name),new org.web3j.abi.datatypes.DynamicBytes(publicKey));
            this.certHash = certHash;
            this.signature = signature;
            this.name = name;
            this.publicKey = publicKey;
        }

        public Certificate(Bytes32 certHash, DynamicBytes signature, Utf8String name, DynamicBytes publicKey) {
            super(certHash,signature,name,publicKey);
            this.certHash = certHash.getValue();
            this.signature = signature.getValue();
            this.name = name.getValue();
            this.publicKey = publicKey.getValue();
        }
    }

    public static class CertificateRevokedEventResponse extends BaseEventResponse {
        public byte[] certHash;
    }
}
