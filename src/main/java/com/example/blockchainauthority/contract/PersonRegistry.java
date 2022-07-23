package com.example.blockchainauthority.contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
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
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
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
public class PersonRegistry extends Contract {
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162000fd538038062000fd583398101604081905262000034916200007c565b600580546001600160a01b0319166001600160a01b03841617905560066200005d82826200020b565b505050620002d7565b634e487b7160e01b600052604160045260246000fd5b600080604083850312156200009057600080fd5b82516001600160a01b0381168114620000a857600080fd5b602084810151919350906001600160401b0380821115620000c857600080fd5b818601915086601f830112620000dd57600080fd5b815181811115620000f257620000f262000066565b604051601f8201601f19908116603f011681019083821181831017156200011d576200011d62000066565b8160405282815289868487010111156200013657600080fd5b600093505b828410156200015a57848401860151818501870152928501926200013b565b828411156200016c5760008684830101525b8096505050505050509250929050565b600181811c908216806200019157607f821691505b602082108103620001b257634e487b7160e01b600052602260045260246000fd5b50919050565b601f8211156200020657600081815260208120601f850160051c81016020861015620001e15750805b601f850160051c820191505b818110156200020257828155600101620001ed565b5050505b505050565b81516001600160401b0381111562000227576200022762000066565b6200023f816200023884546200017c565b84620001b8565b602080601f8311600181146200027757600084156200025e5750858301515b600019600386901b1c1916600185901b17855562000202565b600085815260208120601f198616915b82811015620002a85788860151825594840194600190910190840162000287565b5085821015620002c75787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b610cee80620002e76000396000f3fe608060405234801561001057600080fd5b50600436106100625760003560e01c806308696c18146100675780638da5cb5b146100905780639e47a073146100bb578063a9265167146100f9578063b949048b1461010e578063e6cf134614610123575b600080fd5b61007a61007536600461086e565b610146565b60405161008791906108e3565b60405180910390f35b6005546100a3906001600160a01b031681565b6040516001600160a01b039091168152602001610087565b6100e96100c93660046109f1565b805160208183018101805160018252928201919093012091525460ff1681565b6040519015158152602001610087565b61010c610107366004610a2e565b6103cc565b005b610116610608565b6040516100879190610ac0565b61013661013136600461086e565b610696565b6040516100879493929190610ada565b6101746040518060800160405280606081526020016060815260200160008019168152602001606081525090565b60008281526003602052604090205460ff166101c75760405162461bcd60e51b815260206004820152600d60248201526c12d95e481b9bdd08199bdd5b99609a1b60448201526064015b60405180910390fd5b6000828152600460205260408120548154909190829081106101eb576101eb610b24565b906000526020600020906004020160405180608001604052908160008201805461021490610b3a565b80601f016020809104026020016040519081016040528092919081815260200182805461024090610b3a565b801561028d5780601f106102625761010080835404028352916020019161028d565b820191906000526020600020905b81548152906001019060200180831161027057829003601f168201915b505050505081526020016001820180546102a690610b3a565b80601f01602080910402602001604051908101604052809291908181526020018280546102d290610b3a565b801561031f5780601f106102f45761010080835404028352916020019161031f565b820191906000526020600020905b81548152906001019060200180831161030257829003601f168201915b505050505081526020016002820154815260200160038201805461034290610b3a565b80601f016020809104026020016040519081016040528092919081815260200182805461036e90610b3a565b80156103bb5780601f10610390576101008083540402835291602001916103bb565b820191906000526020600020905b81548152906001019060200180831161039e57829003601f168201915b505050505081525050915050919050565b6005546001600160a01b0316331461041e5760405162461bcd60e51b81526020600482015260156024820152742cb7ba9030b932903737ba103a34329037bbb732b960591b60448201526064016101be565b60018160405161042e9190610b74565b9081526040519081900360200190205460ff161561048e5760405162461bcd60e51b815260206004820152601c60248201527f506572736f6e20697320616c726561647920726567697374657265640000000060448201526064016101be565b6104bc6040518060800160405280606081526020016060815260200160008019168152602001606081525090565b8481526020810184905260408101839052606081018290526000805460018101825590805281518290600483027f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563019081906105189082610bdf565b506020820151600182019061052d9082610bdf565b50604082015160028201556060820151600382019061054c9082610bdf565b505050600180846040516105609190610b74565b908152604051908190036020018120805492151560ff19909316929092179091558190600290610591908690610b74565b90815260408051918290036020908101832093909355600087815260038452818120805460ff1916600117905560049093529091208290557f20947a6777b12de3d623d858cef0ed1339507ccb53ac30079cd6663ca2b4911f906105f89083908690610c9f565b60405180910390a1505050505050565b6006805461061590610b3a565b80601f016020809104026020016040519081016040528092919081815260200182805461064190610b3a565b801561068e5780601f106106635761010080835404028352916020019161068e565b820191906000526020600020905b81548152906001019060200180831161067157829003601f168201915b505050505081565b600081815481106106a657600080fd5b90600052602060002090600402016000915090508060000180546106c990610b3a565b80601f01602080910402602001604051908101604052809291908181526020018280546106f590610b3a565b80156107425780601f1061071757610100808354040283529160200191610742565b820191906000526020600020905b81548152906001019060200180831161072557829003601f168201915b50505050509080600101805461075790610b3a565b80601f016020809104026020016040519081016040528092919081815260200182805461078390610b3a565b80156107d05780601f106107a5576101008083540402835291602001916107d0565b820191906000526020600020905b8154815290600101906020018083116107b357829003601f168201915b5050505050908060020154908060030180546107eb90610b3a565b80601f016020809104026020016040519081016040528092919081815260200182805461081790610b3a565b80156108645780601f1061083957610100808354040283529160200191610864565b820191906000526020600020905b81548152906001019060200180831161084757829003601f168201915b5050505050905084565b60006020828403121561088057600080fd5b5035919050565b60005b838110156108a257818101518382015260200161088a565b838111156108b1576000848401525b50505050565b600081518084526108cf816020860160208601610887565b601f01601f19169290920160200192915050565b6020815260008251608060208401526108ff60a08401826108b7565b90506020840151601f198085840301604086015261091d83836108b7565b92506040860151606086015260608601519150808584030160808601525061094582826108b7565b95945050505050565b634e487b7160e01b600052604160045260246000fd5b600082601f83011261097557600080fd5b813567ffffffffffffffff808211156109905761099061094e565b604051601f8301601f19908116603f011681019082821181831017156109b8576109b861094e565b816040528381528660208588010111156109d157600080fd5b836020870160208301376000602085830101528094505050505092915050565b600060208284031215610a0357600080fd5b813567ffffffffffffffff811115610a1a57600080fd5b610a2684828501610964565b949350505050565b60008060008060808587031215610a4457600080fd5b843567ffffffffffffffff80821115610a5c57600080fd5b610a6888838901610964565b95506020870135915080821115610a7e57600080fd5b610a8a88838901610964565b9450604087013593506060870135915080821115610aa757600080fd5b50610ab487828801610964565b91505092959194509250565b602081526000610ad360208301846108b7565b9392505050565b608081526000610aed60808301876108b7565b8281036020840152610aff81876108b7565b90508460408401528281036060840152610b1981856108b7565b979650505050505050565b634e487b7160e01b600052603260045260246000fd5b600181811c90821680610b4e57607f821691505b602082108103610b6e57634e487b7160e01b600052602260045260246000fd5b50919050565b60008251610b86818460208701610887565b9190910192915050565b601f821115610bda57600081815260208120601f850160051c81016020861015610bb75750805b601f850160051c820191505b81811015610bd657828155600101610bc3565b5050505b505050565b815167ffffffffffffffff811115610bf957610bf961094e565b610c0d81610c078454610b3a565b84610b90565b602080601f831160018114610c425760008415610c2a5750858301515b600019600386901b1c1916600185901b178555610bd6565b600085815260208120601f198616915b82811015610c7157888601518255948401946001909101908401610c52565b5085821015610c8f5787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b828152604060208201526000610a2660408301846108b756fea264697066735822122093356ae10590f87f5b5f155956c8e743839f1a401a63debf2e9765cdbd9b710c64736f6c634300080f0033";

    public static final String FUNC_GETBYPUBLICKEY = "getByPublicKey";

    public static final String FUNC_ISCPFREGISTERED = "isCpfRegistered";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_OWNERKEY = "ownerKey";

    public static final String FUNC_REGISTERPERSON = "registerPerson";

    public static final String FUNC_REGISTERED = "registered";

    public static final Event PERSONREGISTERED_EVENT = new Event("PersonRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event PERSONREMOVED_EVENT = new Event("PersonRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Person>() {}));
    ;

    @Deprecated
    protected PersonRegistry(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PersonRegistry(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PersonRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PersonRegistry(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<PersonRegisteredEventResponse> getPersonRegisteredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PERSONREGISTERED_EVENT, transactionReceipt);
        ArrayList<PersonRegisteredEventResponse> responses = new ArrayList<PersonRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PersonRegisteredEventResponse typedResponse = new PersonRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.cpf = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PersonRegisteredEventResponse> personRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PersonRegisteredEventResponse>() {
            @Override
            public PersonRegisteredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PERSONREGISTERED_EVENT, log);
                PersonRegisteredEventResponse typedResponse = new PersonRegisteredEventResponse();
                typedResponse.log = log;
                typedResponse.index = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.cpf = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<PersonRegisteredEventResponse> personRegisteredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PERSONREGISTERED_EVENT));
        return personRegisteredEventFlowable(filter);
    }

    public List<PersonRemovedEventResponse> getPersonRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PERSONREMOVED_EVENT, transactionReceipt);
        ArrayList<PersonRemovedEventResponse> responses = new ArrayList<PersonRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PersonRemovedEventResponse typedResponse = new PersonRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.person = (Person) eventValues.getNonIndexedValues().get(0);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<PersonRemovedEventResponse> personRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, PersonRemovedEventResponse>() {
            @Override
            public PersonRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PERSONREMOVED_EVENT, log);
                PersonRemovedEventResponse typedResponse = new PersonRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.person = (Person) eventValues.getNonIndexedValues().get(0);
                return typedResponse;
            }
        });
    }

    public Flowable<PersonRemovedEventResponse> personRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PERSONREMOVED_EVENT));
        return personRemovedEventFlowable(filter);
    }

    public RemoteFunctionCall<Person> getByPublicKey(byte[] hashedKey) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBYPUBLICKEY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hashedKey)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Person>() {}));
        return executeRemoteCallSingleValueReturn(function, Person.class);
    }

    public RemoteFunctionCall<Boolean> isCpfRegistered(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISCPFREGISTERED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

    public RemoteFunctionCall<TransactionReceipt> registerPerson(String _name, String _email, byte[] _publicKeyHash, String _cpf) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_REGISTERPERSON, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_name), 
                new org.web3j.abi.datatypes.Utf8String(_email), 
                new org.web3j.abi.datatypes.generated.Bytes32(_publicKeyHash), 
                new org.web3j.abi.datatypes.Utf8String(_cpf)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple4<String, String, byte[], String>> registered(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REGISTERED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteFunctionCall<Tuple4<String, String, byte[], String>>(function,
                new Callable<Tuple4<String, String, byte[], String>>() {
                    @Override
                    public Tuple4<String, String, byte[], String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, String, byte[], String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (byte[]) results.get(2).getValue(), 
                                (String) results.get(3).getValue());
                    }
                });
    }

    @Deprecated
    public static PersonRegistry load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PersonRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PersonRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PersonRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PersonRegistry load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PersonRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PersonRegistry load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PersonRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PersonRegistry> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _owner, byte[] _ownerKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.DynamicBytes(_ownerKey)));
        return deployRemoteCall(PersonRegistry.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<PersonRegistry> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _owner, byte[] _ownerKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.DynamicBytes(_ownerKey)));
        return deployRemoteCall(PersonRegistry.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PersonRegistry> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _owner, byte[] _ownerKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.DynamicBytes(_ownerKey)));
        return deployRemoteCall(PersonRegistry.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<PersonRegistry> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _owner, byte[] _ownerKey) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner), 
                new org.web3j.abi.datatypes.DynamicBytes(_ownerKey)));
        return deployRemoteCall(PersonRegistry.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
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

    public static class PersonRegisteredEventResponse extends BaseEventResponse {
        public BigInteger index;

        public String cpf;
    }

    public static class PersonRemovedEventResponse extends BaseEventResponse {
        public Person person;
    }
}
