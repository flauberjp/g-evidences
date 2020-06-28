package io.github.flauberjp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GenerateHook {

  public static void main(String[] args) throws IOException, URISyntaxException {
    System.out.println("Programa iniciado às: " + LocalDateTime.now());

    System.out.println(generateHook());

    System.out.println("Programa finalizado às: " + LocalDateTime.now());
  }

  public static boolean generateHook() {
    return generateHook(new ArrayList<String>());
  }

  public static boolean generateHook(List<String> gitDirProjects) {
    try {
      String hookName = "pre-commit";
      Util.convertResourceToFile("templates/gerarEvidencias.bat", hookName);
      Util.replaceStringOfAFile(hookName, "<solution_directory>",
          Util.getSolutionDirectory());
      for (String gitDirProjectPath : gitDirProjects) {
        Files.copy(Paths.get(hookName), Paths.get(gitDirProjectPath + "/.git/hooks/" + hookName),
            StandardCopyOption.REPLACE_EXISTING);
      }
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}
