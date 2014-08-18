package com.dynamease.jsonrpc.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dynamease.basicentities.DynPerson;
import com.dynamease.basicentities.DynSubscriber;
import com.dynamease.basicentities.DynSubscriber.SubLevel;

public class DynJsonUserServiceImpl implements DynJsonUserService {
    
    private static final Logger logger = LoggerFactory.getLogger(DynJsonUserServiceImpl.class);

    @Override
    public String upgradeLevelPostStorePayment(String subscriberId, String upgradeDesc, String storePaymentReference) {
       logger.debug("Upgrading subscriber id  {} to level {}", subscriberId, upgradeDesc);
        return SubLevel.SUBSCRIBER.toString();
    }

    @Override
    public DynSubscriber retrieveFromEmail(String email) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DynSubscriber guestSignUp(DynPerson newGuest, String email, String phone) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean subscriberSignin(String email, String password) {
        // TODO Auto-generated method stub
        return false;
    }

}
