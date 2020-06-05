package io.github.flauberjp;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileHandling {

	public static void WriteObjectToFile(Object serObj) {
		 
        try {
 
            FileOutputStream fileOut = new FileOutputStream("User.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");
 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
        String inputFile = "User.txt";
		try (
                ObjectInputStream objectInput
                    = new ObjectInputStream(new FileInputStream(inputFile));
            ){
 
            while (true) {
                UserGithubInfo user = (UserGithubInfo) objectInput.readObject();
 
                System.out.print(user.getGithub() + "\t");
                System.out.print(user.getGithubName() + "\t");
                System.out.print(user.getGithubEmail() + "\t");
                System.out.println(user.getUsername());
            }
 
        } catch (EOFException eof) {
            System.out.println("Reached end of file");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
		
    }
}
