package io.github.flauberjp;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.model.GitDir;
import io.github.flauberjp.util.Util;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GenerateHook {

  public static final String HOOK_NAME = "pre-commit";
  public static final String MAIN_COMMAND = "start /MIN \"cmd /C <solution_directory>/g-evidences gerarevidencia\"";

  public static void main(String[] args) throws IOException, URISyntaxException {
    LOGGER.info("Programa iniciado às: " + LocalDateTime.now());

    generateHook(new ArrayList<String>());

    LOGGER.info("Programa finalizado às: " + LocalDateTime.now());
  }

  public static String generateHook(List<String> gitDirProjects) {
    String gitProjectsNaoConfigurados = "";
    LOGGER.debug("GenerateHook.generateHook(gitDirProjects = {})", gitDirProjects);
    try {
      convertHookTemplateInHookFinal();
      for (String gitDirProjectPath : gitDirProjects) {
        // Se o hook não existe no git project, fazemos a copia
        if (!isHookExistAtGitProject(gitDirProjectPath)) {
          copyHookFinalToAGitProject(gitDirProjectPath);
        }
        // Se o hook git project for diferente do hook local
        // e se o hook do git project nã conter o comando que gerará evidencia
        else if (!isGitProjectHookEqualsToLocalOne(gitDirProjectPath)
            && !isGitProjectHookContainMainCommand(gitDirProjectPath)) {
          // Notificamos ao usuário que esse hook ele terá que manipular manualmente mesmo
          gitProjectsNaoConfigurados += gitDirProjectPath.toString() + "\n";
        }
      }
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return gitProjectsNaoConfigurados;
  }

  // Recebe como parametro Util.getNotSelectedGitDirList
  public static List<GitDir> destroyHook(List<GitDir> notSelectedGitDirProjectList) {
    List deletedProjects = new ArrayList<GitDir>();
    LOGGER.debug("GenerateHook.destroyHook(notSelectedGitDirProjectList = {})",
        notSelectedGitDirProjectList);
    try {
      for (GitDir notSelectedGitDirProject : notSelectedGitDirProjectList) {
        if (isHookExistAtGitProject(notSelectedGitDirProject.getPath())) {
          File file = new File(notSelectedGitDirProject.getPath() + "/.git/hooks/" + HOOK_NAME);
          deletedProjects.add(notSelectedGitDirProject);

          if (!isGitProjectHookEqualsToLocalOne(notSelectedGitDirProject.getPath())) {
            if (isFileContainMainCommand(
                notSelectedGitDirProject.getPath() + "/.git/hooks/" + HOOK_NAME)) {
              removeMainCommandFromAFile(
                  notSelectedGitDirProject.getPath() + "/.git/hooks/" + HOOK_NAME);
            }
          } else {
            file.delete();
          }
        }
      }
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
    return deletedProjects;
  }

  public static boolean isHookExistAtGitProject(String gitDirProjectPath) {
    return Files.exists(Paths.get(gitDirProjectPath + "/.git/hooks/" + HOOK_NAME));
  }

  /**
   * Converte o template de hook gerarEvidencias.bat localizado em resources/io.github.flauberjp.templates
   * no hook final armazenando-o no diretório de onde o programa esta sendo executado.
   *
   * @throws IOException
   */
  private static void convertHookTemplateInHookFinal() throws IOException {
    LOGGER.debug("GenerateHook.generateHookLocally()");
    convertHookTemplateInHookFinal(".");
  }

  /**
   * Converte o template de hook gerarEvidencias.bat localizado em resources/io.github.flauberjp.templates
   * no hook final armazenando-o no diretório passado por parametro.
   *
   * @param path Diretório onde a versão final do hook será gerada.
   * @throws IOException
   */
  public static void convertHookTemplateInHookFinal(String path) throws IOException {
    LOGGER.debug("GenerateHook.generateHookLocally()");
    Util.convertResourceToFile("templates/gerarEvidencias.bat", HOOK_NAME);
    Util.replaceStringOfAFile(path + "/" + HOOK_NAME, "<main_command>",
        getMainCommand());
  }

  /**
   * Retorna a linha de comando principal que iniciará a geração de evidência. É utilizada na
   * geração do hook final. Pode ser utilizado também para detectar se um hook existente contém o
   * comando para geração de evidência ou não.
   *
   * @return Ex. de retorno: start /MIN "cmd /C C:\g-evidences/g-evidences gerarevidencia"
   * @throws IOException
   */
  public static String getMainCommand() {
    LOGGER.debug("GenerateHook.generateMainCommand()");
    return MAIN_COMMAND.replace("<solution_directory>", Util.getSolutionDirectoryIn83Format());
  }

  /**
   * Remove o main command de um arquivo, ideal quando se esta desconfigurando
   * um Git Project.
   *
   * @param fileNameWithItsPath
   */
  public static void removeMainCommandFromAFile(String fileNameWithItsPath) {
    LOGGER.debug("GenerateHook.removeMainCommandFromAFile(fileNameWithItsPath = {})",
        fileNameWithItsPath);
    Util.removeStringOfAFile(fileNameWithItsPath, getMainCommand());
  }

  /**
   * Copia o hook gerado para um diretório especificado no parâmetro.
   *
   * @param gitDirProjectPath
   * @throws IOException
   */
  public static void copyHookFinalToAGitProject(String gitDirProjectPath) throws IOException {
    LOGGER.debug("GenerateHook.copyHookFinalToAGitProject(gitDirProjectPath = {})",
        gitDirProjectPath);
    Files.copy(Paths.get(HOOK_NAME), Paths.get(gitDirProjectPath + "/.git/hooks/" + HOOK_NAME),
        StandardCopyOption.REPLACE_EXISTING);
  }

  /**
   * Verifica se o arquivo passado por parametro contém o mesmo conteúdo que o hook.
   *
   * @param gitDirProjectPath
   * @return
   * @throws IOException
   */

  public static boolean isGitProjectHookEqualsToLocalOne(String gitDirProjectPath)
      throws IOException {
    boolean result = false;
    if (isLocalHookExists() && isHookExistAtGitProject(gitDirProjectPath)) {
      String hookPath = gitDirProjectPath + "/.git/hooks/" + HOOK_NAME;
      result = Util.compareContentFileByteToByte("./" + HOOK_NAME, hookPath);
    }
    return result;
  }

  public static boolean isLocalHookExists(){
    return Util.isFileExist(GenerateHook.HOOK_NAME);
  }
  /**
   * Verifica se o arquivo passado por parametro contém o comando que gerará a evidência.
   *
   * @param fileNameWithItsPath
   * @return True: contém o comando que gera evidência; Falso: caso contrário.
   * @throws IOException
   */
  public static boolean isFileContainMainCommand(String fileNameWithItsPath) throws IOException {
    return Util.isFileContainAString(fileNameWithItsPath, getMainCommand());
  }

  /**
   * Verifica se o arquivo passado por parametro contém o comando que gerará a evidência.
   *
   * @param gitDirProjectPath
   * @return True: contém o comando que gera evidência; Falso: caso contrário.
   * @throws IOException
   */
  public static boolean isGitProjectHookContainMainCommand(String gitDirProjectPath)
      throws IOException {
    return isFileContainMainCommand(gitDirProjectPath + "/.git/hooks/" + HOOK_NAME);
  }
}
