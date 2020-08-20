package io.github.flauberjp.util;

import java.io.IOException;

public class TemplateUtil {

  private TemplateUtil() {
  }

  public static boolean createIndexFileFromTemplateIfNotExist(String destinationDir) throws IOException {
    return TemplateUtil.createFileFromTemplateIfNotExist(destinationDir + "/index.html", "templates/initialGithubProject/template_index.html");
  }

  public static boolean createReadmeFileFromTemplateIfNotExist(String destinationDir) throws IOException {
    return TemplateUtil.createFileFromTemplateIfNotExist(destinationDir + "/README.md", "templates/initialGithubProject/template_README.md");
  }

  public static boolean createEvidencesFileFromTemplateIfNotExist(String destinationDir) throws IOException {
    return TemplateUtil.createFileFromTemplateIfNotExist(destinationDir + "/evidences.txt", "templates/initialGithubProject/template_evidences.txt");
  }

  public static boolean createFileFromTemplateIfNotExist(String evidencesFilePath, String templateFilePath) throws IOException {
    boolean foiPrecisoCriarArquivo = false;
    if (!Util.isFileExist(evidencesFilePath)) {
      foiPrecisoCriarArquivo = true;
      Util.convertResourceToFile(templateFilePath, evidencesFilePath);
    }
    return foiPrecisoCriarArquivo;
  }

}
