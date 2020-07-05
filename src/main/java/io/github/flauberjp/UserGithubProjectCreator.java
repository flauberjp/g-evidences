package io.github.flauberjp;

import io.github.flauberjp.forms.FormForTesting;
import io.github.flauberjp.util.Util;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static io.github.flauberjp.util.MyLogger.logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;

public class UserGithubProjectCreator {

  public static void main(String[] args) throws IOException, URISyntaxException {
    logger.info("Programa iniciado às: " + LocalDateTime.now());

    logger.info("Projeto criado no Github com sucesso? " + criaProjetoInicialNoGithub(UserGithubInfo.get()));

    logger.info("Programa finalizado às: " + LocalDateTime.now());
  }

  public static boolean criaProjetoInicialNoGithub(UserGithubInfo userGithubInfo) {
    logger.debug("UserGithubProjectCreator.criaProjetoInicialNoGithub(userGithubInfo {})",
        userGithubInfo);
    String dataEHoraExecucao = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(LocalDateTime.now());
    boolean result;
    try {
      GitHub github = GitHub.connectUsingPassword(userGithubInfo.getUsername(), userGithubInfo.getPassword());

      GHCreateRepositoryBuilder repo = github.createRepository(userGithubInfo.getRepoName());
      if(!userGithubInfo.getUsername().equalsIgnoreCase(FormForTesting.GIT_USER_FOR_TESTING)) {
        repo.private_(true);
      }
      repo.create();

      CredentialsProvider credentialsProvider = new UsernamePasswordCredentialsProvider(
          userGithubInfo.getUsername(), userGithubInfo.getPassword());

      Git git;

      String dir = EvidenceGenerator
          .getDirOndeRepositorioRemotoSeraClonado(userGithubInfo.getRepoName());

      git = Git.cloneRepository().setDirectory(new File(dir))
          .setCredentialsProvider(credentialsProvider).setURI(userGithubInfo.getRepoNameFullPath())
          .call();

      StoredConfig config = git.getRepository().getConfig();
      config.setString("user", null, "name", userGithubInfo.getGithubName());
      config.setString("user", null, "email", userGithubInfo.getGithubEmail()); //NOI18N
      config.save();

      // Copia arquivos iniciais usando templates
      Util.convertResourceToFile("templates/initialGithubProject/template_index.html", dir + "/index.html");
      Util.convertResourceToFile("templates/initialGithubProject/template_evidences.txt", dir + "/evidences.txt");
      Util.convertResourceToFile("templates/initialGithubProject/template_README.md", dir + "/README.md");

      git.add().addFilepattern(".").call();
      git.commit().setMessage("Initial setup").call();
      git.push().setCredentialsProvider(credentialsProvider).call();

      result = true;
    } catch (Exception e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  }
}
