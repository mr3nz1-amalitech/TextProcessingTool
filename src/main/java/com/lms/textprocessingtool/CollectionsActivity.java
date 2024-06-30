package com.lms.textprocessingtool;

import java.io.*;

public class CollectionsActivity<T> {
    public void serialize(String file, T object) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(object);
        out.close();
        fileOut.close();
    }

    public T deserialize(String file) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        return (T) in.readObject();
    }

}