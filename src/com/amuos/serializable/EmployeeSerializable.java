package com.amuos.serializable;

/**
 * Created by juan.wang on 12/20/16.
 */
public class EmployeeSerializable implements java.io.Serializable{
    public String name;
    public String address;
    public transient int SSN;
    public int number;
    public void mailCheck() {
        System.out.println("Mailing a check to " + name + " " + address);
    }
}
