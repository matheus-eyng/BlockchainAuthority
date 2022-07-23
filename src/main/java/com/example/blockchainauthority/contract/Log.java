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
import org.web3j.abi.datatypes.StaticStruct;
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
    public static final String BINARY = "60806040523480156200001157600080fd5b50604051620011e1380380620011e18339818101604052810190620000379190620000e8565b806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550506200011a565b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b6000620000b08262000083565b9050919050565b620000c281620000a3565b8114620000ce57600080fd5b50565b600081519050620000e281620000b7565b92915050565b6000602082840312156200010157620001006200007e565b5b60006200011184828501620000d1565b91505092915050565b6110b7806200012a6000396000f3fe608060405234801561001057600080fd5b506004361061004c5760003560e01c806326defa73146100515780638da5cb5b1461006d578063d544e0101461008b578063e82b51fc146100a7575b600080fd5b61006b60048036038101906100669190610790565b6100d7565b005b61007561024d565b60405161008291906107cc565b60405180910390f35b6100a560048036038101906100a09190610790565b610271565b005b6100c160048036038101906100bc9190610ada565b6103e6565b6040516100ce9190610bbc565b60405180910390f35b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff1614610165576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161015c90610c34565b60405180910390fd5b600360008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff16156101f2576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016101e990610ca0565b60405180910390fd5b6001600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff02191690831515021790555050565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff16146102ff576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016102f690610c34565b60405180910390fd5b600360008273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1661038b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161038290610d0c565b60405180910390fd5b6000600360008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff02191690831515021790555050565b6103ee610670565b600360003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff1661047a576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040161047190610d0c565b60405180910390fd5b6005600084815260200190815260200160002060009054906101000a900460ff16156104db576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004016104d290610d78565b60405180910390fd5b60003390506000600280549050905060004290506104f76106a0565b87816000018190525086816020018181525050600281908060018154018082558091505060019003906000526020600020906005020160009091909190915060008201518160000160008201518160000190816105549190610faf565b50602082015181600101908161056a9190610faf565b5060408201518160020155606082015181600301908161058a9190610faf565b50505060208201518160040155505082600660008981526020019081526020016000208190555081600460008a604001518152602001908152602001600020819055506105d56106c3565b84816020019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff168152505086816000018190525061061e610670565b4281600001818152505033816020019073ffffffffffffffffffffffffffffffffffffffff16908173ffffffffffffffffffffffffffffffffffffffff16815250508096505050505050509392505050565b604051806040016040528060008152602001600073ffffffffffffffffffffffffffffffffffffffff1681525090565b60405180604001604052806106b36106f3565b8152602001600080191681525090565b604051806040016040528060608152602001600073ffffffffffffffffffffffffffffffffffffffff1681525090565b6040518060800160405280606081526020016060815260200160008019168152602001606081525090565b6000604051905090565b600080fd5b600080fd5b600073ffffffffffffffffffffffffffffffffffffffff82169050919050565b600061075d82610732565b9050919050565b61076d81610752565b811461077857600080fd5b50565b60008135905061078a81610764565b92915050565b6000602082840312156107a6576107a5610728565b5b60006107b48482850161077b565b91505092915050565b6107c681610752565b82525050565b60006020820190506107e160008301846107bd565b92915050565b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b610835826107ec565b810181811067ffffffffffffffff82111715610854576108536107fd565b5b80604052505050565b600061086761071e565b9050610873828261082c565b919050565b600080fd5b600080fd5b600080fd5b600067ffffffffffffffff8211156108a2576108a16107fd565b5b6108ab826107ec565b9050602081019050919050565b82818337600083830152505050565b60006108da6108d584610887565b61085d565b9050828152602081018484840111156108f6576108f5610882565b5b6109018482856108b8565b509392505050565b600082601f83011261091e5761091d61087d565b5b813561092e8482602086016108c7565b91505092915050565b6000819050919050565b61094a81610937565b811461095557600080fd5b50565b60008135905061096781610941565b92915050565b600060808284031215610983576109826107e7565b5b61098d608061085d565b9050600082013567ffffffffffffffff8111156109ad576109ac610878565b5b6109b984828501610909565b600083015250602082013567ffffffffffffffff8111156109dd576109dc610878565b5b6109e984828501610909565b60208301525060406109fd84828501610958565b604083015250606082013567ffffffffffffffff811115610a2157610a20610878565b5b610a2d84828501610909565b60608301525092915050565b600067ffffffffffffffff821115610a5457610a536107fd565b5b610a5d826107ec565b9050602081019050919050565b6000610a7d610a7884610a39565b61085d565b905082815260208101848484011115610a9957610a98610882565b5b610aa48482856108b8565b509392505050565b600082601f830112610ac157610ac061087d565b5b8135610ad1848260208601610a6a565b91505092915050565b600080600060608486031215610af357610af2610728565b5b600084013567ffffffffffffffff811115610b1157610b1061072d565b5b610b1d8682870161096d565b9350506020610b2e86828701610958565b925050604084013567ffffffffffffffff811115610b4f57610b4e61072d565b5b610b5b86828701610aac565b9150509250925092565b6000819050919050565b610b7881610b65565b82525050565b610b8781610752565b82525050565b604082016000820151610ba36000850182610b6f565b506020820151610bb66020850182610b7e565b50505050565b6000604082019050610bd16000830184610b8d565b92915050565b600082825260208201905092915050565b7f596f7520617265206e6f7420746865206f776e65720000000000000000000000600082015250565b6000610c1e601583610bd7565b9150610c2982610be8565b602082019050919050565b60006020820190508181036000830152610c4d81610c11565b9050919050565b7f417574686f7269747920616c7265616479207265676973746572656400000000600082015250565b6000610c8a601c83610bd7565b9150610c9582610c54565b602082019050919050565b60006020820190508181036000830152610cb981610c7d565b9050919050565b7f417574686f72697479206e6f7420726567697374657265640000000000000000600082015250565b6000610cf6601883610bd7565b9150610d0182610cc0565b602082019050919050565b60006020820190508181036000830152610d2581610ce9565b9050919050565b7f436572746966696361746520616c72656164792070726573656e740000000000600082015250565b6000610d62601b83610bd7565b9150610d6d82610d2c565b602082019050919050565b60006020820190508181036000830152610d9181610d55565b9050919050565b600081519050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b60006002820490506001821680610dea57607f821691505b602082108103610dfd57610dfc610da3565b5b50919050565b60008190508160005260206000209050919050565b60006020601f8301049050919050565b600082821b905092915050565b600060088302610e657fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff82610e28565b610e6f8683610e28565b95508019841693508086168417925050509392505050565b6000819050919050565b6000610eac610ea7610ea284610b65565b610e87565b610b65565b9050919050565b6000819050919050565b610ec683610e91565b610eda610ed282610eb3565b848454610e35565b825550505050565b600090565b610eef610ee2565b610efa818484610ebd565b505050565b5b81811015610f1e57610f13600082610ee7565b600181019050610f00565b5050565b601f821115610f6357610f3481610e03565b610f3d84610e18565b81016020851015610f4c578190505b610f60610f5885610e18565b830182610eff565b50505b505050565b600082821c905092915050565b6000610f8660001984600802610f68565b1980831691505092915050565b6000610f9f8383610f75565b9150826002028217905092915050565b610fb882610d98565b67ffffffffffffffff811115610fd157610fd06107fd565b5b610fdb8254610dd2565b610fe6828285610f22565b600060209050601f8311600181146110195760008415611007578287015190505b6110118582610f93565b865550611079565b601f19841661102786610e03565b60005b8281101561104f5784890151825560018201915060208501945060208101905061102a565b8683101561106c5784890151611068601f891682610f75565b8355505b6001600288020188555050505b50505050505056fea264697066735822122065f19a98a15e30e14be6bf784704f400305bdf76eda58f03103eef998103de6b64736f6c634300080f0033";

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

    public static class Timestamp extends StaticStruct {
        public BigInteger timestamp;

        public String authorityAddress;

        public Timestamp(BigInteger timestamp, String authorityAddress) {
            super(new org.web3j.abi.datatypes.generated.Uint256(timestamp),new org.web3j.abi.datatypes.Address(authorityAddress));
            this.timestamp = timestamp;
            this.authorityAddress = authorityAddress;
        }

        public Timestamp(Uint256 timestamp, Address authorityAddress) {
            super(timestamp,authorityAddress);
            this.timestamp = timestamp.getValue();
            this.authorityAddress = authorityAddress.getValue();
        }
    }

    public static class CertificateAppendedEventResponse extends BaseEventResponse {
        public byte[] certHash;

        public CertificationAuthority authority;

        public BigInteger timestamp;
    }
}
