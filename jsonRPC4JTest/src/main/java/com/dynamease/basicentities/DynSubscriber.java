package com.dynamease.basicentities;



public class DynSubscriber extends DynPerson {
    
    
    public DynSubscriber(String firstName, String lastName) {
        super(firstName, lastName);
        
    }



    public static enum SubLevel {
        UNKNOWN, GUEST, SUBSCRIBER
    };
    
    private int subscriberId;

  

    private SubLevel level;



    public int getSubscriberId() {
        return subscriberId;
    }



    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }



    public SubLevel getLevel() {
        return level;
    }



    public void setLevel(SubLevel level) {
        this.level = level;
    }




}
