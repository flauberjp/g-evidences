package io.github.flauberjp;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class EvidenceGenerator {
  private static final Path workspace = Paths.get("delete-me");

  public static Path getWorkspace() {
    return workspace;
  }

  public static void main(String[] args) throws IOException {
    System.out.println("Programa iniciado às: " + LocalDateTime.now());

    System.out.println(geraEvidenciaDeUsoDoGit(UserGithubInfo.get()));

    System.out.println("Programa finalizado às: " + LocalDateTime.now());
  }

  public static boolean geraEvidenciaDeUsoDoGit(UserGithubInfo userGithubInfo) {
    String dataEHoraExecucao = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
    boolean result;
    try {

      CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(
          userGithubInfo.getUsername(), userGithubInfo.getPassword());

      Git git;

      resetWorkspace();
      String dir = getDirOndeRepositorioRemotoSeraClonado(userGithubInfo.getRepoName());

      git = Git.cloneRepository().setDirectory(new File(dir))
          .setCredentialsProvider(credentialsProvider).setURI(userGithubInfo.getRepoNameFullPath())
          .call();

      StoredConfig config = git.getRepository().getConfig();
      config.setString("user", null, "name", userGithubInfo.getGithubName());
      config.setString("user", null, "email", userGithubInfo.getGithubEmail()); //NOI18N
      config.save();

      // Gera evidencia em evidences.txt
      String fileNameWithItsPath = dir + "/evidences.txt";
      updateEvidenceFile(fileNameWithItsPath, dataEHoraExecucao);

      git.add().addFilepattern(".").call();
      git.commit().setMessage(dataEHoraExecucao).call();
      git.push().setCredentialsProvider(credentialsProvider).call();
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  }

  public static void resetWorkspace() throws IOException {
    if (Files.exists(getWorkspace())) {
      deletaDir(getWorkspace());
    }
    getWorkspace().toFile().mkdir();
  }

  public static String geraDirAleatorioNaWorkspace() {
    String dir = getWorkspace()
        + "/"
        + Util.getRandomStr();
    new File(dir).mkdir();
    return dir;
  }

  public static String getDirOndeRepositorioRemotoSeraClonado(String repoName) throws IOException {
    return geraDirAleatorioNaWorkspace() + "/" + repoName;
  }

  private static void deletaDir(Path path) throws IOException {
    if (Files.exists(path)) {
      Files.walk(path)
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
    }
  }

  private static void updateEvidenceFile(String fileNameWithItsPath, String dataEHoraExecucao) {
    Path filePath = Paths.get(fileNameWithItsPath);
    try {
      Stream<String> lines = Files.lines(filePath, Charset.forName("UTF-8"));
      List<String> replacedLine = lines
          .map(line ->
              line.replace("List of evidences of git usage:", "List of evidences of git usage:\n" + dataEHoraExecucao)
          )
          .collect(Collectors.toList());
      Files.write(filePath, replacedLine, Charset.forName("UTF-8"));
      lines.close();
    } catch (IOException e) {

      e.printStackTrace();
    }
  }
}
