package io.github.flauberjp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.Test;

class UtilTest {

  @Test
  void convertIndexHtmlResourceToFileTest() throws IOException {
    convertResourceToFileTest("index.html");
  }

  @Test
  void convertReadmeMdResourceToFileTest() throws IOException {
    convertResourceToFileTest("README.md");
  }

  private void convertResourceToFileTest(String filename) throws IOException {
    String randomFilename = System.getProperty("java.io.tmpdir") +
        String.format("%4s", new Random().nextInt(10000)).replace(' ', '0');
    Util.convertResourceToFile("initialProjectTemplate/" + filename, randomFilename);
    assertTrue(new File(randomFilename).exists());
  }
}
