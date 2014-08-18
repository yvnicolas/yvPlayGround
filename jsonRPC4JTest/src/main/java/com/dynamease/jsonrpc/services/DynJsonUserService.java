package com.dynamease.jsonrpc.services;

import com.dynamease.basicentities.DynPerson;
import com.dynamease.basicentities.DynSubscriber;



public interface DynJsonUserService {

    /**
     * Json Service to upgrade subscriber level from a smartphone post payment on the appstore of the smartphone
     * @param subscriberId
     * @param upgradeDesc
     * @param storePaymentReference
     * @returns a String representing the new validity date for the subscriber level status
     */
    public String upgradeLevelPostStorePayment(String subscriberId, String upgradeDesc, String storePaymentReference);
    
    
    public DynSubscriber retrieveFromEmail (String email);
    
    public DynSubscriber guestSignUp(DynPerson newGuest, String email, String phone);
    
    public boolean subscriberSignin(String email, String password);
    
}
