package io.github.flauberjp;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class UtilTest {

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
    Path currentRelativePath = Paths.get("C:\\");
    String s = currentRelativePath.toAbsolutePath().toString();
    assertFalse(Util.isThisGitProjectAGithubOne(s));
  }


}
