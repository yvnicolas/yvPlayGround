package com.dynamease.jsonrpc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dynamease.basicentities.DynPerson;
import com.dynamease.basicentities.DynSubscriber;
import com.dynamease.basicentities.DynSubscriber.SubLevel;
import com.googlecode.jsonrpc4j.JsonRpcParamName;

public class DynJsonUserServiceImpl implements DynJsonUserService {
    
    private static final Logger logger = LoggerFactory.getLogger(DynJsonUserServiceImpl.class);

    @Override
    public String upgradeLevelPostStorePayment(String subscriberId, String upgradeDesc, String storePaymentReference) {
       logger.debug("Upgrading subscriber id  {} to level {}", subscriberId, upgradeDesc);
        return SubLevel.SUBSCRIBER.toString();
    }

    @Override
    public DynSubscriber retrieveFromEmail( String email) {
       logger.debug("Retrieving email {}", email);
        return new DynSubscriber("Georges", "Feydeau");
    }

    @Override
    public DynSubscriber guestSignUp(DynPerson newGuest, String email, String phone) {
       logger.debug("Signing up guest : {} with email {} and phone {}");
       return new DynSubscriber(newGuest.getFirstName(), newGuest.getLastName());
    }

    @Override
    public boolean subscriberSignin(String email, String password) {
        logger.debug("Signing up subscriber  with email {} and password {}");
        return false;
    }

}
