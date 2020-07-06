package io.github.flauberjp;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.util.Util;
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
    LOGGER.info("Programa iniciado às: " + LocalDateTime.now());

    LOGGER.info("Geração do hook funcionou? " + generateHook());

    LOGGER.info("Programa finalizado às: " + LocalDateTime.now());
  }

  public static boolean generateHook() {
    LOGGER.debug("GenerateHook.generateHook()");
    return generateHook(new ArrayList<String>());
  }

  public static boolean generateHook(List<String> gitDirProjects) {
    boolean result = false;
    LOGGER.debug("GenerateHook.generateHook(gitDirProjects = {})", gitDirProjects);
    try {
      String hookName = "pre-commit";
      Util.convertResourceToFile("templates/gerarEvidencias.bat", hookName);
      Util.replaceStringOfAFile(hookName, "<solution_directory>",
          Util.getSolutionDirectoryIn83Format());
      for (String gitDirProjectPath : gitDirProjects) {
        Files.copy(Paths.get(hookName), Paths.get(gitDirProjectPath + "/.git/hooks/" + hookName),
            StandardCopyOption.REPLACE_EXISTING);
      }
      result = true;
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return result;
  }
}
