package io.github.flauberjp.util;

import io.github.flauberjp.UserGithubProjectCreator;
import io.github.flauberjp.forms.model.GitDir;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.DefaultListModel;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import static io.github.flauberjp.util.MyLogger.logger;

public class Util {

  private static List<GitDir> gitDirList = new ArrayList<GitDir>();
  private static DefaultListModel<GitDir> listModel = new DefaultListModel();

  private Util() {
  }

  public static boolean isRunningFromJar() {
    logger.debug("Util.isRunningFromJar()");
    return Util
        .class
        .getResource("Util.class")
        .toString()
        .startsWith("jar:");
  }

  public static Properties getProperties(String propertiesFileName) throws IOException {
    logger.debug("Util.getProperties(propertiesFileName = {})", propertiesFileName);
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
    logger.debug("Util.getCurrentJarDirectory()");
    try {
      return new File(Util.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent();
    } catch (URISyntaxException exception) {
      exception.printStackTrace();
    }

    return null;
  }

  private static String getCurrentDirectory() {
    logger.debug("Util.getCurrentDirectory()");
    String result = ".";
    if (isRunningFromJar()) {
      result = getCurrentJarDirectory();
    } else {
      try {
        result = new File( "." ).getCanonicalPath();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return result;
  }

  public static String getSolutionDirectoryIn83Format() {
    logger.debug("Util.getSolutionDirectoryIn83Format()");
    String result = getCurrentDirectory();
    if(result.contains("Program Files")) {
      result = result.replace("Program Files", "progra~1");
    } else if(result.contains("Arquivos de Programas")) {
      result = result.replace("Arquivos de Programas", "arquiv~1");
    }
    return result;
  }


  public static void savePropertiesToFile(Properties properties, String propertiesFileName) {
    logger.debug("Util.savePropertiesToFile(properties = {}, propertiesFileName = {})", properties, propertiesFileName);
    try (
        FileOutputStream fileOut = new FileOutputStream(propertiesFileName);
        ) {
      properties.store(fileOut, "");
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static Properties readPropertiesFromFile(String propertiesFileName) {
    logger.debug("Util.readPropertiesFromFile(propertiesFileName = {})", propertiesFileName);
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

  public static void addGitFiles(File dir) {
    logger.debug("Util.addGitFiles(dir = {})", dir);
    try {
      File[] files = dir.listFiles();
      for (File file : files) {
        if (file.isDirectory()) {
          if (file.getName().equals(".git")) {
            if (!isThisGitProjectAGithubOne(file.getParentFile().getCanonicalPath())) {
              gitDirList.add(new GitDir(file.getParentFile().getCanonicalPath()));
            }
          }
          addGitFiles(file);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static List<GitDir> getGitDirList() {
    logger.debug("Util.getGitDirList()");
    return gitDirList;
  }

  public static List<GitDir> getSelectedGitDirList() {
    logger.debug("Util.getSelectedGitDirList()");
    List result = new ArrayList<GitDir>();
    for (GitDir gitDir : getGitDirList()) {
      if (gitDir.isSelected()) {
        result.add(gitDir);
      }
    }
    return result;
  }

  public static List<String> getSelectedGitDirStringList() {
    logger.debug("Util.getSelectedGitDirStringList()");
    List result = new ArrayList<String>();
    for (GitDir gitDir : getSelectedGitDirList()) {
      result.add(gitDir.getPath());
    }
    return result;
  }

  //Build ListModel containing gitDir's
  public static DefaultListModel<GitDir> buildDefaultListModel() {
    logger.debug("Util.buildDefaultListModel()");
    gitDirList.forEach(gitDir -> listModel.addElement(gitDir));

    return listModel;
  }

  public static DefaultListModel<GitDir> getListModel() {
    logger.debug("Util.getListModel()");
    return listModel;
  }


/**
   * @param resource e.g.: "initialProjectTemplate/template_index.html"
   * @param file     e.g.: "C:\Users\FLAVIA~1\AppData\Local\Temp\index.html""
   * @throws IOException
   */
  public static void convertResourceToFile(String resource, String file)
      throws IOException {
    logger.debug("Util.convertResourceToFile(resource = {}, file = {})", resource, file);
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
    logger.debug("Util.createProperties(values = {})", values);
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
    logger.debug("Util.getRandomStr()");
    return String.format("%4s", new Random().nextInt(10000)).replace(' ', '0');
  }

  public static void replaceStringOfAFile(String fileNameWithItsPath, String originalString, String newString) {
    logger.debug("Util.replaceStringOfAFile(fileNameWithItsPath = {}, originalString = {}, newString = {})",
        fileNameWithItsPath, originalString, newString);
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

  public static boolean isThisGitProjectAGithubOne(String fullPathDirectoryOfAGitProject) {
    logger.debug("Util.isThisGitProjectAGithubOne(fullPathDirectoryOfAGitProject = {})",
        fullPathDirectoryOfAGitProject);
    boolean result = false;
    if(Paths.get(fullPathDirectoryOfAGitProject).toFile().exists()) {
      if(Paths.get(fullPathDirectoryOfAGitProject + "/.git").toFile().exists()) {
        try {
          Git git = Git.open(new File(fullPathDirectoryOfAGitProject));
          StoredConfig config = git.getRepository().getConfig();
          String remoteOriginUrl = config.getString("remote", "origin", "url");
          if(remoteOriginUrl != null) {
            result = remoteOriginUrl.toLowerCase().contains("github.com");
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }


}
