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
    public static final String BINARY = "60806040523480156200001157600080fd5b5060405162001d7338038062001d73833981810160405281019062000037919062000294565b81600560006101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550806006908162000089919062000545565b5050506200062c565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000620000d382620000a6565b9050919050565b620000e581620000c6565b8114620000f157600080fd5b50565b6000815190506200010581620000da565b92915050565b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b620001608262000115565b810181811067ffffffffffffffff8211171562000182576200018162000126565b5b80604052505050565b60006200019762000092565b9050620001a5828262000155565b919050565b600067ffffffffffffffff821115620001c857620001c762000126565b5b620001d38262000115565b9050602081019050919050565b60005b8381101562000200578082015181840152602081019050620001e3565b8381111562000210576000848401525b50505050565b60006200022d6200022784620001aa565b6200018b565b9050828152602081018484840111156200024c576200024b62000110565b5b62000259848285620001e0565b509392505050565b600082601f8301126200027957620002786200010b565b5b81516200028b84826020860162000216565b91505092915050565b60008060408385031215620002ae57620002ad6200009c565b5b6000620002be85828601620000f4565b925050602083015167ffffffffffffffff811115620002e257620002e1620000a1565b5b620002f08582860162000261565b9150509250929050565b600081519050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b600060028204905060018216806200034d57607f821691505b60208210810362000363576200036262000305565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b600060088302620003cd7fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff826200038e565b620003d986836200038e565b95508019841693508086168417925050509392505050565b6000819050919050565b6000819050919050565b600062000426620004206200041a84620003f1565b620003fb565b620003f1565b9050919050565b6000819050919050565b620004428362000405565b6200045a62000451826200042d565b8484546200039b565b825550505050565b600090565b6200047162000462565b6200047e81848462000437565b505050565b5b81811015620004a6576200049a60008262000467565b60018101905062000484565b5050565b601f821115620004f557620004bf8162000369565b620004ca846200037e565b81016020851015620004da578190505b620004f2620004e9856200037e565b83018262000483565b50505b505050565b600082821c905092915050565b60006200051a60001984600802620004fa565b1980831691505092915050565b600062000535838362000507565b9150826002028217905092915050565b6200055082620002fa565b67ffffffffffffffff8111156200056c576200056b62000126565b5b62000578825462000334565b62000585828285620004aa565b600060209050601f831160018114620005bd5760008415620005a8578287015190505b620005b4858262000527565b86555062000624565b601f198416620005cd8662000369565b60005b82811015620005f757848901518255600182019150602085019450602081019050620005d0565b8683101562000617578489015162000613601f89168262000507565b8355505b6001600288020188555050505b505050505050565b611737806200063c6000396000f3fe608060405234801561001057600080fd5b506004361061007d5760003560e01c80639e47a0731161005b5780639e47a07314610100578063a926516714610130578063b949048b1461014c578063e6cf13461461016a5761007d565b806308696c18146100825780630bce8775146100b25780638da5cb5b146100e2575b600080fd5b61009c60048036038101906100979190610c5e565b61019d565b6040516100a99190610da4565b60405180910390f35b6100cc60048036038101906100c79190610efb565b610412565b6040516100d99190610da4565b60405180910390f35b6100ea61069d565b6040516100f79190610f85565b60405180910390f35b61011a60048036038101906101159190610efb565b6106c3565b6040516101279190610fbb565b60405180910390f35b61014a60048036038101906101459190610fd6565b6106f9565b005b610154610983565b60405161016191906110e6565b60405180910390f35b610184600480360381019061017f919061113e565b610a11565b60405161019494939291906111c4565b60405180910390f35b6101a5610be9565b6003600083815260200190815260200160002060009054906101000a900460ff16610205576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101fc9061126a565b60405180910390fd5b600060046000848152602001908152602001600020549050600081815481106102315761023061128a565b5b906000526020600020906004020160405180608001604052908160008201805461025a906112e8565b80601f0160208091040260200160405190810160405280929190818152602001828054610286906112e8565b80156102d35780601f106102a8576101008083540402835291602001916102d3565b820191906000526020600020905b8154815290600101906020018083116102b657829003601f168201915b505050505081526020016001820180546102ec906112e8565b80601f0160208091040260200160405190810160405280929190818152602001828054610318906112e8565b80156103655780601f1061033a57610100808354040283529160200191610365565b820191906000526020600020905b81548152906001019060200180831161034857829003601f168201915b5050505050815260200160028201548152602001600382018054610388906112e8565b80601f01602080910402602001604051908101604052809291908181526020018280546103b4906112e8565b80156104015780601f106103d657610100808354040283529160200191610401565b820191906000526020600020905b8154815290600101906020018083116103e457829003601f168201915b505050505081525050915050919050565b61041a610be9565b60018260405161042a9190611355565b908152602001604051809103902060009054906101000a900460ff16610485576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161047c906113b8565b60405180910390fd5b60006002836040516104979190611355565b9081526020016040518091039020549050600081815481106104bc576104bb61128a565b5b90600052602060002090600402016040518060800160405290816000820180546104e5906112e8565b80601f0160208091040260200160405190810160405280929190818152602001828054610511906112e8565b801561055e5780601f106105335761010080835404028352916020019161055e565b820191906000526020600020905b81548152906001019060200180831161054157829003601f168201915b50505050508152602001600182018054610577906112e8565b80601f01602080910402602001604051908101604052809291908181526020018280546105a3906112e8565b80156105f05780601f106105c5576101008083540402835291602001916105f0565b820191906000526020600020905b8154815290600101906020018083116105d357829003601f168201915b5050505050815260200160028201548152602001600382018054610613906112e8565b80601f016020809104026020016040519081016040528092919081815260200182805461063f906112e8565b801561068c5780601f106106615761010080835404028352916020019161068c565b820191906000526020600020905b81548152906001019060200180831161066f57829003601f168201915b505050505081525050915050919050565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6001818051602081018201805184825260208301602085012081835280955050505050506000915054906101000a900460ff1681565b600560009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610789576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161078090611424565b60405180910390fd5b6001816040516107999190611355565b908152602001604051809103902060009054906101000a900460ff16156107f5576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016107ec906113b8565b60405180910390fd5b6107fd610be9565b848160000181905250838160200181905250828160400181815250508181606001819052506000808054905090506000829080600181540180825580915050600190039060005260206000209060040201600090919091909150600082015181600001908161086c91906115f0565b50602082015181600101908161088291906115f0565b506040820151816002015560608201518160030190816108a291906115f0565b505050600180846040516108b69190611355565b908152602001604051809103902060006101000a81548160ff021916908315150217905550806002846040516108ec9190611355565b90815260200160405180910390208190555060016003600086815260200190815260200160002060006101000a81548160ff0219169083151502179055508060046000868152602001908152602001600020819055507f20947a6777b12de3d623d858cef0ed1339507ccb53ac30079cd6663ca2b4911f81846040516109739291906116d1565b60405180910390a1505050505050565b60068054610990906112e8565b80601f01602080910402602001604051908101604052809291908181526020018280546109bc906112e8565b8015610a095780601f106109de57610100808354040283529160200191610a09565b820191906000526020600020905b8154815290600101906020018083116109ec57829003601f168201915b505050505081565b60008181548110610a2157600080fd5b9060005260206000209060040201600091509050806000018054610a44906112e8565b80601f0160208091040260200160405190810160405280929190818152602001828054610a70906112e8565b8015610abd5780601f10610a9257610100808354040283529160200191610abd565b820191906000526020600020905b815481529060010190602001808311610aa057829003601f168201915b505050505090806001018054610ad2906112e8565b80601f0160208091040260200160405190810160405280929190818152602001828054610afe906112e8565b8015610b4b5780601f10610b2057610100808354040283529160200191610b4b565b820191906000526020600020905b815481529060010190602001808311610b2e57829003601f168201915b505050505090806002015490806003018054610b66906112e8565b80601f0160208091040260200160405190810160405280929190818152602001828054610b92906112e8565b8015610bdf5780601f10610bb457610100808354040283529160200191610bdf565b820191906000526020600020905b815481529060010190602001808311610bc257829003601f168201915b5050505050905084565b6040518060800160405280606081526020016060815260200160008019168152602001606081525090565b6000604051905090565b600080fd5b600080fd5b6000819050919050565b610c3b81610c28565b8114610c4657600080fd5b50565b600081359050610c5881610c32565b92915050565b600060208284031215610c7457610c73610c1e565b5b6000610c8284828501610c49565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b83811015610cc5578082015181840152602081019050610caa565b83811115610cd4576000848401525b50505050565b6000601f19601f8301169050919050565b6000610cf682610c8b565b610d008185610c96565b9350610d10818560208601610ca7565b610d1981610cda565b840191505092915050565b610d2d81610c28565b82525050565b60006080830160008301518482036000860152610d508282610ceb565b91505060208301518482036020860152610d6a8282610ceb565b9150506040830151610d7f6040860182610d24565b5060608301518482036060860152610d978282610ceb565b9150508091505092915050565b60006020820190508181036000830152610dbe8184610d33565b905092915050565b600080fd5b600080fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610e0882610cda565b810181811067ffffffffffffffff82111715610e2757610e26610dd0565b5b80604052505050565b6000610e3a610c14565b9050610e468282610dff565b919050565b600067ffffffffffffffff821115610e6657610e65610dd0565b5b610e6f82610cda565b9050602081019050919050565b82818337600083830152505050565b6000610e9e610e9984610e4b565b610e30565b905082815260208101848484011115610eba57610eb9610dcb565b5b610ec5848285610e7c565b509392505050565b600082601f830112610ee257610ee1610dc6565b5b8135610ef2848260208601610e8b565b91505092915050565b600060208284031215610f1157610f10610c1e565b5b600082013567ffffffffffffffff811115610f2f57610f2e610c23565b5b610f3b84828501610ecd565b91505092915050565b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000610f6f82610f44565b9050919050565b610f7f81610f64565b82525050565b6000602082019050610f9a6000830184610f76565b92915050565b60008115159050919050565b610fb581610fa0565b82525050565b6000602082019050610fd06000830184610fac565b92915050565b60008060008060808587031215610ff057610fef610c1e565b5b600085013567ffffffffffffffff81111561100e5761100d610c23565b5b61101a87828801610ecd565b945050602085013567ffffffffffffffff81111561103b5761103a610c23565b5b61104787828801610ecd565b935050604061105887828801610c49565b925050606085013567ffffffffffffffff81111561107957611078610c23565b5b61108587828801610ecd565b91505092959194509250565b600081519050919050565b600082825260208201905092915050565b60006110b882611091565b6110c2818561109c565b93506110d2818560208601610ca7565b6110db81610cda565b840191505092915050565b6000602082019050818103600083015261110081846110ad565b905092915050565b6000819050919050565b61111b81611108565b811461112657600080fd5b50565b60008135905061113881611112565b92915050565b60006020828403121561115457611153610c1e565b5b600061116284828501611129565b91505092915050565b600082825260208201905092915050565b600061118782610c8b565b611191818561116b565b93506111a1818560208601610ca7565b6111aa81610cda565b840191505092915050565b6111be81610c28565b82525050565b600060808201905081810360008301526111de818761117c565b905081810360208301526111f2818661117c565b905061120160408301856111b5565b8181036060830152611213818461117c565b905095945050505050565b7f4b6579206e6f7420666f756e6400000000000000000000000000000000000000600082015250565b6000611254600d8361116b565b915061125f8261121e565b602082019050919050565b6000602082019050818103600083015261128381611247565b9050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052603260045260246000fd5b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061130057607f821691505b602082108103611313576113126112b9565b5b50919050565b600081905092915050565b600061132f82610c8b565b6113398185611319565b9350611349818560208601610ca7565b80840191505092915050565b60006113618284611324565b915081905092915050565b7f506572736f6e20697320616c7265616479207265676973746572656400000000600082015250565b60006113a2601c8361116b565b91506113ad8261136c565b602082019050919050565b600060208201905081810360008301526113d181611395565b9050919050565b7f596f7520617265206e6f7420746865206f776e65720000000000000000000000600082015250565b600061140e60158361116b565b9150611419826113d8565b602082019050919050565b6000602082019050818103600083015261143d81611401565b9050919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b6000600883026114a67fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82611469565b6114b08683611469565b95508019841693508086168417925050509392505050565b6000819050919050565b60006114ed6114e86114e384611108565b6114c8565b611108565b9050919050565b6000819050919050565b611507836114d2565b61151b611513826114f4565b848454611476565b825550505050565b600090565b611530611523565b61153b8184846114fe565b505050565b5b8181101561155f57611554600082611528565b600181019050611541565b5050565b601f8211156115a45761157581611444565b61157e84611459565b8101602085101561158d578190505b6115a161159985611459565b830182611540565b50505b505050565b600082821c905092915050565b60006115c7600019846008026115a9565b1980831691505092915050565b60006115e083836115b6565b9150826002028217905092915050565b6115f982610c8b565b67ffffffffffffffff81111561161257611611610dd0565b5b61161c82546112e8565b611627828285611563565b600060209050601f83116001811461165a5760008415611648578287015190505b61165285826115d4565b8655506116ba565b601f19841661166886611444565b60005b828110156116905784890151825560018201915060208501945060208101905061166b565b868310156116ad57848901516116a9601f8916826115b6565b8355505b6001600288020188555050505b505050505050565b6116cb81611108565b82525050565b60006040820190506116e660008301856116c2565b81810360208301526116f8818461117c565b9050939250505056fea264697066735822122012ecfbcfe8ed833ade59f7a380b1c1859a92bf72a36855e02133bb539790942064736f6c634300080f0033";

    public static final String FUNC_GETBYCPF = "getByCpf";

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

    public RemoteFunctionCall<Person> getByCpf(String _cpf) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETBYCPF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_cpf)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Person>() {}));
        return executeRemoteCallSingleValueReturn(function, Person.class);
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
