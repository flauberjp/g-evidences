package io.github.flauberjp;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Util {

  public static final String GITHUB_INFORMATION_FILE = ".github";

  private Util() {
  }

  public static boolean isRunningFromJar() {
    return Util
        .class
        .getResource("Util.class")
        .toString()
        .startsWith("jar:");
  }

  public static Properties getProperties() throws IOException {
    Properties properties = new Properties();

    InputStream inputStream;
    if(isRunningFromJar()) {
      String filePath = new File(".").getCanonicalPath() + "/" + GITHUB_INFORMATION_FILE;
      File file = new File(filePath);
      if (!file.exists()) {
        throw new RuntimeException("Arquivo " + filePath + " esperado não existe. ");
      }
      inputStream = new FileInputStream(file);
    } else {
      inputStream = UserGithubProjectCreator.class.getResourceAsStream(GITHUB_INFORMATION_FILE);
    }
    properties.load(inputStream);

    return properties;
  }
  
  public static void WriteObjectToFile(Object userObj) {
      try {

          FileOutputStream fileOut = new FileOutputStream("User.txt");
          ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
          objectOut.writeObject(userObj);
          objectOut.close();
          System.out.println("The Object  was succesfully written to a file");
          
          ReadObjectToFile();

      } catch (Exception ex) {
          ex.printStackTrace();
      }
		
  }
  
  public static void ReadObjectToFile() {
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

  /**
   *
   * @param resource e.g.: "initialProjectTemplate/index.html"
   * @param file e.g.: "C:\Users\FLAVIA~1\AppData\Local\Temp\index.html""
   * @throws IOException
   */
  public static void convertResourceToFile(String resource, String file)
      throws IOException {
    byte[] buffer = new byte[UserGithubProjectCreator.class.getResourceAsStream(resource).available()];
    UserGithubProjectCreator.class.getResourceAsStream(resource).read(buffer);
    File targetFile = new File(file);
    OutputStream outStream = new FileOutputStream(targetFile);
    outStream.write(buffer);
  }

  /**
   * Code reused from: http://www.java2s.com/Tutorial/Java/0140__Collections/CreatePropertiesfromStringarray.htm
   */
  public static Properties createProperties( String[] values )
      throws IllegalArgumentException
  {
    if( values.length % 2 != 0 )
      throw new IllegalArgumentException( "One value is missing.");

    Properties props = new Properties();

    for( int i = 0; i < values.length; i += 2 )
    {
      props.setProperty( values[i], values[i+1] );
    }

    return props;
  }
}
