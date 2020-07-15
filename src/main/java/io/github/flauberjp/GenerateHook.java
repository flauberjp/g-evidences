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
import java.util.Arrays;
import java.util.List;

public class GenerateHook {
  public static final String HOOK_NAME = "pre-commit";
  public static final String MAIN_COMMAND = "start /MIN \"cmd /C <solution_directory>/mgue gerarevidencia\"";

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
      convertHookTemplateInHookFinal();
      for (String gitDirProjectPath : gitDirProjects) {
        copyLocalHookToAGitProject(gitDirProjectPath);
      }
      result = true;
    } catch (IOException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return result;
  }

  /**
   * Converte o template de hook gerarEvidencias.bat localizado em resources/io.github.flauberjp.templates
   * no hook final armazenando-o no diretório de onde o programa esta sendo executado.
   * @throws IOException
   */
  private static void convertHookTemplateInHookFinal() throws IOException {
    LOGGER.debug("GenerateHook.generateHookLocally()");
    convertHookTemplateInHookFinal(".");
  }

  /**
   * Converte o template de hook gerarEvidencias.bat localizado em resources/io.github.flauberjp.templates
   * no hook final armazenando-o no diretório passado por parametro.
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
   * Retorna a linha de comando principal que iniciará a geração de evidência.
   * É utilizada na geração do hook final.
   * Pode ser utilizado também para detectar se um hook existente contém o comando
   * para geração de evidência ou não.
   * @return Ex. de retorno: start /MIN "cmd /C C:\my-git-usage-evidences/mgue gerarevidencia"
   * @throws IOException
   */
  public static String getMainCommand() throws IOException {
    LOGGER.debug("GenerateHook.generateMainCommand()");
    return MAIN_COMMAND.replace("<solution_directory>", Util.getSolutionDirectoryIn83Format()) ;
  }

  /**
   * Copia o hook gerado para um diretório especificado no parâmetro.
   * @param gitDirProjectPath
   * @throws IOException
   */
  public static void copyLocalHookToAGitProject(String gitDirProjectPath) throws IOException {
    LOGGER.debug("GenerateHook.copyLocalHookToAGitProject(gitDirProjectPath = {})",
        gitDirProjectPath);
    Files.copy(Paths.get(HOOK_NAME), Paths.get(gitDirProjectPath + "/.git/hooks/" + HOOK_NAME),
        StandardCopyOption.REPLACE_EXISTING);
  }

  /**
   * Verifica se o arquivo passado por parametro contém o mesmo conteúdo que o hook.
   * @param gitDirProjectPath
   * @return
   * @throws IOException
   */
  public boolean isGitProjectHookEqualsToLocalOne(String gitDirProjectPath) throws IOException {
    byte[] f1 = Files.readAllBytes(Paths.get(HOOK_NAME));
    byte[] f2 = Files.readAllBytes(Paths.get(gitDirProjectPath + "/.git/hooks/" + HOOK_NAME));
    return Arrays.equals(f1, f2);
  }

  /**
   * Verifica se o arquivo passado por parametro contém o comando que gerará a evidência.
   * @param fileNameWithItsPath
   * @return True: contém o comando que gera evidência; Falso: caso contrário.
   * @throws IOException
   */
  public static boolean isFileContainMainCommand(String fileNameWithItsPath) throws IOException {
    boolean result = false;
    List<String> lines = Util.readFileContent(fileNameWithItsPath);
    for(String line: lines) {
      if(line.equalsIgnoreCase(getMainCommand())) {
        result = true;
        break;
      }
    }
    return result;
  }
}
