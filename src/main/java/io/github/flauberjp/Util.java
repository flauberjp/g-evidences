package io.github.flauberjp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.SneakyThrows;


public class Util {
  private Util() {
  }

  public static boolean isRunningFromJar() {
    return Util
        .class
        .getResource("Util.class")
        .toString()
        .startsWith("jar:");
  }

  public static Properties getProperties(String propertiesFileName) throws IOException {
    Properties properties = new Properties();

    InputStream inputStream;
    if (isRunningFromJar()) {
      String filePath = getCurrentJarDirectory() + "/" + propertiesFileName;
      File file = new File(filePath);
      if (!file.exists()) {
        throw new RuntimeException("Arquivo " + filePath + " esperado não existe. ");
      }
      inputStream = new FileInputStream(file);
    } else {
      inputStream = UserGithubProjectCreator.class.getResourceAsStream(propertiesFileName);
    }
    properties.load(inputStream);

    return properties;
  }

  private static String getCurrentJarDirectory() {
    try {
      return new File(Util.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
    } catch (URISyntaxException exception) {
      exception.printStackTrace();
    }

    return null;
  }

  public static void savePropertiesToFile(Properties properties, String propertiesFileName) {
    try (
        FileOutputStream fileOut = new FileOutputStream(propertiesFileName);
        ) {
      properties.store(fileOut, "");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static Properties readPropertiesFromFile(String propertiesFileName) {
    Properties result = new Properties();
    try (
        InputStream input = new FileInputStream(propertiesFileName)
    ) {
      result.load(input);
    } catch (IOException io) {
      io.printStackTrace();
    }
    return result;
  }

  /**
   * @param resource e.g.: "templates/initialGithubProject/template_index.html"
   * @param file     e.g.: "C:\Users\FLAVIA~1\AppData\Local\Temp\index.html""
   * @throws IOException
   */
  public static void convertResourceToFile(String resource, String file)
      throws IOException {
    InputStream resourceAsStream = UserGithubProjectCreator.class.getResourceAsStream(resource);
    byte[] buffer = new byte[resourceAsStream.available()];
    resourceAsStream.read(buffer);
    File targetFile = new File(file);
    OutputStream outStream = new FileOutputStream(targetFile);
    outStream.write(buffer);
  }

  /**
   * Code reused from: http://www.java2s.com/Tutorial/Java/0140__Collections/CreatePropertiesfromStringarray.htm
   */
  public static Properties createProperties(String[] values)
      throws IllegalArgumentException {
    if (values.length % 2 != 0) {
      throw new IllegalArgumentException("One value is missing.");
    }

    Properties props = new Properties();

    for (int i = 0; i < values.length; i += 2) {
      props.setProperty(values[i], values[i + 1]);
    }

    return props;
  }

  public static String getRandomStr() {
    return String.format("%4s", new Random().nextInt(10000)).replace(' ', '0');
  }

  @SneakyThrows
  public static String getSolutionDirectory() {
    String result = System.getenv("ProgramFiles");
    String resultIfOsLangIsPortuguese = "C:\\Arquivos de Programas";
    if(new File(resultIfOsLangIsPortuguese).exists()) {
        result = resultIfOsLangIsPortuguese;
    }
    return result + "\\my-git-usage-evidences";
  }

  /**
   * Para este método funcionar é preciso que o programa tenha sido executado em admin mode.
   *
   * @return
   */
  public static boolean createSolutionDirectory() {
    Path solutionPath = Paths.get(getSolutionDirectory());
    boolean result = true;
    if(!solutionPath.toFile().exists()) {
      result = solutionPath.toFile().mkdir();
    }
    if(!result) {
      throw new Error(String.format("Houve um problema criando a pasta \"%s\". Possível razão: talvez porque este programa não tenha sido executado em admin mode.", solutionPath.toString()));
    }
    return result;
  }

  public static void replaceStringOfAFile(String fileNameWithItsPath, String originalString, String newString) {
    Path filePath = Paths.get(fileNameWithItsPath);
    try {
      Stream<String> lines = Files.lines(filePath, Charset.forName("UTF-8"));
      List<String> replacedLine = lines
          .map(line ->
              line.replace(originalString, newString)
          )
          .collect(Collectors.toList());
      Files.write(filePath, replacedLine, Charset.forName("UTF-8"));
      lines.close();
    } catch (IOException e) {

      e.printStackTrace();
    }
  }
}
