package com.dynamease.jsonrpc.services;

import com.dynamease.basicentities.DynPerson;
import com.dynamease.basicentities.DynSubscriber;
import com.googlecode.jsonrpc4j.JsonRpcParam;



public interface DynJsonUserService {

    /**
     * Json Service to upgrade subscriber level from a smartphone post payment on the appstore of the smartphone
     * @param subscriberId
     * @param upgradeDesc
     * @param storePaymentReference
     * @returns a String representing the new validity date for the subscriber level status
     */
    public String upgradeLevelPostStorePayment(@JsonRpcParam("subId") String subscriberId, @JsonRpcParam("upgrade") String upgradeDesc, @JsonRpcParam("storeRef") String storePaymentReference);
    
    
    public DynSubscriber retrieveFromEmail (@JsonRpcParam("email") String email);
    
    public DynSubscriber guestSignUp(@JsonRpcParam("Person") DynPerson newGuest, @JsonRpcParam("email") String email, @JsonRpcParam("phone")String phone);
    
    public boolean subscriberSignin(@JsonRpcParam("email") String email, @JsonRpcParam("pwd") String password);
 
    
    // guestSignUp {"Person" : {"firstName":"Olivier", "lastName" : "Py"}, "email" : "th@kurt", "phone":"0232360162"}
}
