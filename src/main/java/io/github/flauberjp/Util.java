package io.github.flauberjp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
}
