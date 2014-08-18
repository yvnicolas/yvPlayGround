package com.dynamease.basicentities;

public class DynPerson {

   
    
    private String firstName;
    private String lastName;


   
    public DynPerson(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String buildFullName() {
        StringBuilder sb = new StringBuilder();
        if (this.getFirstName() != null) {
            sb.append(this.getFirstName().replace('"', '\"'));
        }
        sb.append(" ");
        if (this.getLastName() != null) {
            sb.append(this.getLastName().replace('"', '\"'));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getClass().toString()+ "[" + buildFullName() + "]";
    }
}
