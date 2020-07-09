package io.github.flauberjp;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.forms.FormForTesting;
import io.github.flauberjp.util.Util;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;

public class UserGithubProjectCreator {

  public static void main(String[] args) throws IOException, URISyntaxException {
    LOGGER.info("Programa iniciado às: " + LocalDateTime.now());

    LOGGER.info("Projeto criado no Github com sucesso? " + criaProjetoInicialNoGithub(
        UserGithubInfo.get()));

    LOGGER.info("Programa finalizado às: " + LocalDateTime.now());
  }

  public static boolean criaProjetoInicialNoGithub(UserGithubInfo userGithubInfo) {
    LOGGER.debug("UserGithubProjectCreator.criaProjetoInicialNoGithub(userGithubInfo {})",
        userGithubInfo);
    boolean result = false;
    boolean repositorioExistente = false;
    try {
      repositorioExistente = UserGithubInfo.get().isRepoExistent();

      if (!repositorioExistente) {
        criaProjeto(userGithubInfo);
      }

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

      if (!repositorioExistente) {
        // Copia arquivos iniciais usando templates
        Util.convertResourceToFile("templates/initialGithubProject/template_index.html",
            dir + "/index.html");
        Util.convertResourceToFile("templates/initialGithubProject/template_README.md",
            dir + "/README.md");
      }

      String evidencesFilePath = dir + "/evidences.txt";
      if (!Util.isFileExist(evidencesFilePath)) {
        Util.convertResourceToFile("templates/initialGithubProject/template_evidences.txt",
            dir + "/evidences.txt");
        git.add().addFilepattern(".").call();
        git.commit().setMessage("Initial setup").call();
        git.push().setCredentialsProvider(credentialsProvider).call();
      }

      result = true;
    } catch (Exception ex) {
      LOGGER.error(ex.getMessage(), ex);
    }
    return result;
  }

  private static void criaProjeto(UserGithubInfo userGithubInfo) throws IOException {
    LOGGER.debug("UserGithubProjectCreator.criaProjeto(userGithubInfo {})",
        userGithubInfo);
    GitHub github = userGithubInfo.get().getGitHub();
    GHCreateRepositoryBuilder repo = github.createRepository(userGithubInfo.getRepoName());
    if (!userGithubInfo.getUsername().equalsIgnoreCase(FormForTesting.GIT_USER_FOR_TESTING)) {
      repo.private_(true);
    }
    repo.create();
  }
}
