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
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162000f2238038062000f2283398101604081905262000034916200007c565b600480546001600160a01b0319166001600160a01b03841617905560056200005d82826200020b565b505050620002d7565b634e487b7160e01b600052604160045260246000fd5b600080604083850312156200009057600080fd5b82516001600160a01b0381168114620000a857600080fd5b602084810151919350906001600160401b0380821115620000c857600080fd5b818601915086601f830112620000dd57600080fd5b815181811115620000f257620000f262000066565b604051601f8201601f19908116603f011681019083821181831017156200011d576200011d62000066565b8160405282815289868487010111156200013657600080fd5b600093505b828410156200015a57848401860151818501870152928501926200013b565b828411156200016c5760008684830101525b8096505050505050509250929050565b600181811c908216806200019157607f821691505b602082108103620001b257634e487b7160e01b600052602260045260246000fd5b50919050565b601f8211156200020657600081815260208120601f850160051c81016020861015620001e15750805b601f850160051c820191505b818110156200020257828155600101620001ed565b5050505b505050565b81516001600160401b0381111562000227576200022762000066565b6200023f816200023884546200017c565b84620001b8565b602080601f8311600181146200027757600084156200025e5750858301515b600019600386901b1c1916600185901b17855562000202565b600085815260208120601f198616915b82811015620002a85788860151825594840194600190910190840162000287565b5085821015620002c75787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b610c3b80620002e76000396000f3fe608060405234801561001057600080fd5b50600436106100575760003560e01c8063246982c41461005c5780638da5cb5b14610085578063a9265167146100b0578063b949048b146100c5578063e6cf1346146100da575b600080fd5b61006f61006a3660046107f6565b6100fd565b60405161007c919061086b565b60405180910390f35b600454610098906001600160a01b031681565b6040516001600160a01b03909116815260200161007c565b6100c36100be366004610979565b610368565b005b6100cd610590565b60405161007c9190610a0b565b6100ed6100e83660046107f6565b61061e565b60405161007c9493929190610a25565b61012b6040518060800160405280606081526020016060815260200160008019168152602001606081525090565b600054829081106101735760405162461bcd60e51b815260206004820152600d60248201526c092dcecc2d8d2c840d2dcc8caf609b1b60448201526064015b60405180910390fd5b6000838154811061018657610186610a6f565b90600052602060002090600402016040518060800160405290816000820180546101af90610a85565b80601f01602080910402602001604051908101604052809291908181526020018280546101db90610a85565b80156102285780601f106101fd57610100808354040283529160200191610228565b820191906000526020600020905b81548152906001019060200180831161020b57829003601f168201915b5050505050815260200160018201805461024190610a85565b80601f016020809104026020016040519081016040528092919081815260200182805461026d90610a85565b80156102ba5780601f1061028f576101008083540402835291602001916102ba565b820191906000526020600020905b81548152906001019060200180831161029d57829003601f168201915b50505050508152602001600282015481526020016003820180546102dd90610a85565b80601f016020809104026020016040519081016040528092919081815260200182805461030990610a85565b80156103565780601f1061032b57610100808354040283529160200191610356565b820191906000526020600020905b81548152906001019060200180831161033957829003601f168201915b50505050508152505091505b50919050565b6004546001600160a01b031633146103ba5760405162461bcd60e51b81526020600482015260156024820152742cb7ba9030b932903737ba103a34329037bbb732b960591b604482015260640161016a565b6001816040516103ca9190610ab9565b9081526040519081900360200190205460ff161561042a5760405162461bcd60e51b815260206004820152601c60248201527f506572736f6e20697320616c7265616479207265676973746572656400000000604482015260640161016a565b6104586040518060800160405280606081526020016060815260200160008019168152602001606081525090565b8481526020810184905260408101839052606081018290526000805460018101825590805281518290600483027f290decd9548b62a8d60345a988386fc84ba6bc95484008f6362f93160ef3e563019081906104b49082610b24565b50602082015160018201906104c99082610b24565b5060408201516002820155606082015160038201906104e89082610b24565b505050600180846040516104fc9190610ab9565b908152604051908190036020018120805492151560ff1990931692909217909155819060029061052d908690610ab9565b9081526040805160209281900383018120939093556000878152600390925290208290557f20947a6777b12de3d623d858cef0ed1339507ccb53ac30079cd6663ca2b4911f906105809083908690610be4565b60405180910390a1505050505050565b6005805461059d90610a85565b80601f01602080910402602001604051908101604052809291908181526020018280546105c990610a85565b80156106165780601f106105eb57610100808354040283529160200191610616565b820191906000526020600020905b8154815290600101906020018083116105f957829003601f168201915b505050505081565b6000818154811061062e57600080fd5b906000526020600020906004020160009150905080600001805461065190610a85565b80601f016020809104026020016040519081016040528092919081815260200182805461067d90610a85565b80156106ca5780601f1061069f576101008083540402835291602001916106ca565b820191906000526020600020905b8154815290600101906020018083116106ad57829003601f168201915b5050505050908060010180546106df90610a85565b80601f016020809104026020016040519081016040528092919081815260200182805461070b90610a85565b80156107585780601f1061072d57610100808354040283529160200191610758565b820191906000526020600020905b81548152906001019060200180831161073b57829003601f168201915b50505050509080600201549080600301805461077390610a85565b80601f016020809104026020016040519081016040528092919081815260200182805461079f90610a85565b80156107ec5780601f106107c1576101008083540402835291602001916107ec565b820191906000526020600020905b8154815290600101906020018083116107cf57829003601f168201915b5050505050905084565b60006020828403121561080857600080fd5b5035919050565b60005b8381101561082a578181015183820152602001610812565b83811115610839576000848401525b50505050565b6000815180845261085781602086016020860161080f565b601f01601f19169290920160200192915050565b60208152600082516080602084015261088760a084018261083f565b90506020840151601f19808584030160408601526108a5838361083f565b9250604086015160608601526060860151915080858403016080860152506108cd828261083f565b95945050505050565b634e487b7160e01b600052604160045260246000fd5b600082601f8301126108fd57600080fd5b813567ffffffffffffffff80821115610918576109186108d6565b604051601f8301601f19908116603f01168101908282118183101715610940576109406108d6565b8160405283815286602085880101111561095957600080fd5b836020870160208301376000602085830101528094505050505092915050565b6000806000806080858703121561098f57600080fd5b843567ffffffffffffffff808211156109a757600080fd5b6109b3888389016108ec565b955060208701359150808211156109c957600080fd5b6109d5888389016108ec565b94506040870135935060608701359150808211156109f257600080fd5b506109ff878288016108ec565b91505092959194509250565b602081526000610a1e602083018461083f565b9392505050565b608081526000610a38608083018761083f565b8281036020840152610a4a818761083f565b90508460408401528281036060840152610a64818561083f565b979650505050505050565b634e487b7160e01b600052603260045260246000fd5b600181811c90821680610a9957607f821691505b60208210810361036257634e487b7160e01b600052602260045260246000fd5b60008251610acb81846020870161080f565b9190910192915050565b601f821115610b1f57600081815260208120601f850160051c81016020861015610afc5750805b601f850160051c820191505b81811015610b1b57828155600101610b08565b5050505b505050565b815167ffffffffffffffff811115610b3e57610b3e6108d6565b610b5281610b4c8454610a85565b84610ad5565b602080601f831160018114610b875760008415610b6f5750858301515b600019600386901b1c1916600185901b178555610b1b565b600085815260208120601f198616915b82811015610bb657888601518255948401946001909101908401610b97565b5085821015610bd45787850151600019600388901b60f8161c191681555b5050505050600190811b01905550565b828152604060208201526000610bfd604083018461083f565b94935050505056fea264697066735822122057420fd2ec13bfcad929edece5979a39ef6472c2f5161fb8e385eb46514dbf5764736f6c634300080f0033";

    public static final String FUNC_GETPERSON = "getPerson";

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

    public RemoteFunctionCall<Person> getPerson(BigInteger index) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETPERSON, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(index)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Person>() {}));
        return executeRemoteCallSingleValueReturn(function, Person.class);
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
