package io.github.flauberjp;

import io.github.flauberjp.forms.FormMain;
import io.github.flauberjp.util.Util;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import static io.github.flauberjp.util.MyLogger.logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class EvidenceGenerator {
  private static final Path workspace = Paths.get("delete-me");

  public static Path getWorkspace() {
    logger.debug("EvidenceGenerator.getWorkspace()");
    return workspace;
  }

  public static void main(String[] args) throws IOException {
    logger.debug("EvidenceGenerator.main(args = {})", args);
    if (args != null && args.length == 0) {
      logger.info("Exibindo FormMain");
      FormMain.showFormMain();
    } else {
      logger.info("Gerando evidência via linha de comando.");
      boolean result = geraEvidenciaDeUsoDoGit(UserGithubInfo.get());
      logger.debug("Resultado da geração de evidência: {}", result);
    }
  }

  public static boolean geraEvidenciaDeUsoDoGit(UserGithubInfo userGithubInfo) {
    logger.debug("EvidenceGenerator.geraEvidenciaDeUsoDoGit(userGithubInfo = {})", userGithubInfo);

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
    logger.debug("EvidenceGenerator.resetWorkspace()");
    if (Files.exists(getWorkspace())) {
      deletaDir(getWorkspace());
    }
    getWorkspace().toFile().mkdir();
  }

  public static String geraDirAleatorioNaWorkspace() {
    logger.debug("EvidenceGenerator.geraDirAleatorioNaWorkspace()");
    String dir = getWorkspace()
        + "/"
        + Util.getRandomStr();
    new File(dir).mkdir();
    return dir;
  }

  public static String getDirOndeRepositorioRemotoSeraClonado(String repoName) throws IOException {
    logger.debug(
        "EvidenceGenerator.getDirOndeRepositorioRemotoSeraClonado(repoName = {})", repoName);
    return geraDirAleatorioNaWorkspace() + "/" + repoName;
  }

  private static void deletaDir(Path path) throws IOException {
    logger.debug("EvidenceGenerator.deletaDir(path = {})", path);
    if (Files.exists(path)) {
      Files.walk(path)
          .sorted(Comparator.reverseOrder())
          .map(Path::toFile)
          .forEach(File::delete);
    }
  }

  private static void updateEvidenceFile(String fileNameWithItsPath, String dataEHoraExecucao) {
    logger.debug(
        "EvidenceGenerator.updateEvidenceFile(fileNameWithItsPath = {}, dataEHoraExecucao = {})",
        fileNameWithItsPath, dataEHoraExecucao);
    Util.replaceStringOfAFile(fileNameWithItsPath, "List of evidences of git usage:",
        "List of evidences of git usage:\n" + dataEHoraExecucao);
  }
}
