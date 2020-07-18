package io.github.flauberjp.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.Test;

class UtilTest {

  @Test
  void checkContentOfTemplateEvidencesFile() throws IOException {
    convertResourceToFileTest("template_index.html");
  }

  @Test
  void convertIndexHtmlResourceToFileTest() throws IOException {
    convertResourceToFileTest("template_index.html");
  }

  @Test
  void convertReadmeMdResourceToFileTest() throws IOException {
    convertResourceToFileTest("template_README.md");
  }

  private void convertResourceToFileTest(String filename) throws IOException {
    String randomFilename = Files.createTempDirectory("tmp").toString() + "/" +
        Util.getRandomStr();
    Util.convertResourceToFile("templates/initialGithubProject/" + filename, randomFilename);
    assertTrue(new File(randomFilename).exists());
  }

  @Test
  void isThisGitProjectAGithubOneTest_checkTrue() {
    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString();
    assertTrue(Util.isThisGitProjectAGithubOne(s));
  }

  @Test
  void isThisGitProjectAGithubOneTest_checkFalse() {
    Path currentRelativePath = Paths.get("/");
    String s = currentRelativePath.toAbsolutePath().toString();
    assertFalse(Util.isThisGitProjectAGithubOne(s));
  }

  @Test
  void contentOfTemplateEvidencesFile() throws IOException {
    String randomFilename = Files.createTempDirectory("tmp").toString() + "/" +
        Util.getRandomStr();
    Util.convertResourceToFile("templates/initialGithubProject/template_evidences.txt", randomFilename);
    List<String> lines = Util.readFileContent(randomFilename);
    assertEquals(1, lines.size());
    assertEquals("List of evidences of git usage:", lines.get(0));
  }

  @Test
  void appendStringToEvidencesFile() throws IOException {
    String appendedString = "appendedString";
    String randomFilename = Files.createTempDirectory("tmp").toString() + "/" +
        Util.getRandomStr();
    Util.convertResourceToFile("templates/initialGithubProject/template_evidences.txt", randomFilename);
    Util.appendStringToAFile(randomFilename, appendedString);
    List<String> lines = Util.readFileContent(randomFilename);
    assertEquals(2, lines.size());
    assertEquals("List of evidences of git usage:", lines.get(0));
    assertEquals(appendedString, lines.get(1));
  }

  @Test
  void removeStringOfAFile() throws IOException {
    String stringToBeRemoved = "stringToBeRemoved";
    String randomFilename = Files.createTempDirectory("tmp").toString() + "/" +
        Util.getRandomStr();
    Util.convertResourceToFile("templates/initialGithubProject/template_evidences.txt", randomFilename);
    Util.appendStringToAFile(randomFilename, stringToBeRemoved);
    assertTrue(Util.isFileContainAString(randomFilename, stringToBeRemoved));
    Util.removeStringOfAFile(randomFilename, stringToBeRemoved);
    assertFalse(Util.isFileContainAString(randomFilename, stringToBeRemoved));
  }
}
