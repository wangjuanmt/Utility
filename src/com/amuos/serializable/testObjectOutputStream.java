package com.amuos.serializable;

import org.junit.Test;

import java.io.*;
import java.util.Date;

/**
 * Classes ObjectInputStream and ObjectOutputStream are high-level streams that contain the methods for
 * serializing and deserializing an object.
 * Created by juan.wang on 12/20/16.
 */
public class testObjectOutputStream {

    @Test
    public void testObjectOutputStreamInt() throws IOException {
        FileOutputStream fos = new FileOutputStream("t.tmp");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeInt(12345);
        oos.close();
        fos.close();
    }

    @Test
    public void testObjectInputStreamInt() throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream("t.tmp");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object x = ois.readInt();
        ois.close();
        fis.close();
    }

    @Test
    public void testObjectOutputStreamSerializableClass(){
        EmployeeSerializable e = new EmployeeSerializable();
        e.name = "Reyan Ali";
        e.address = "Phokka Kuan, Ambehta Peer";
        e.SSN = 11122333;
        e.number = 101;

        try {
            FileOutputStream fileOut =
                    new FileOutputStream("employee.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(e);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in employee.ser");
        }catch(IOException i) {
            i.printStackTrace();
        }
    }

    @Test
    public void testObjectInputStreamSerializableClass(){
        EmployeeSerializable e = null;
        try {
            FileInputStream fileIn = new FileInputStream("employee.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            e = (EmployeeSerializable) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i) {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c) {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }

        System.out.println("Deserialized Employee...");
        System.out.println("Name: " + e.name);
        System.out.println("Address: " + e.address);
        System.out.println("SSN: " + e.SSN);
        System.out.println("Number: " + e.number);
    }







}
