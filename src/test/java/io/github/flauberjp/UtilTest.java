package io.github.flauberjp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
}
