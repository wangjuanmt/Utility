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
    public void testObjectOutputStream1() throws IOException {
        FileOutputStream fos = new FileOutputStream("t.tmp");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeInt(12345);
//        oos.writeObject("Today");
//        oos.writeObject(new Date());
        oos.close();
    }

    @Test
    public void testObjectInputStream() throws IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream("t.tmp");
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object x = ois.readInt();
        ois.close();
    }

}
