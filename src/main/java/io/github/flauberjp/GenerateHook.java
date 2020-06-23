package io.github.flauberjp;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;

public class GenerateHook {

  public static void main(String[] args) throws IOException, URISyntaxException {
    System.out.println("Programa iniciado às: " + LocalDateTime.now());

    System.out.println(generateHook());

    System.out.println("Programa finalizado às: " + LocalDateTime.now());
  }

  public static boolean generateHook() {
    try {
      Util.convertResourceToFile("templates/gerarEvidencias.bat", "pre-push");
      Util.replaceStringOfAFile("pre-push", "<solution_directory>",
          Util.getSolutionDirectory());
      return true;
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
  }
}
