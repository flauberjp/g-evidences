package io.github.flauberjp;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;
import org.junit.jupiter.api.Test;

class UtilTest {

  @Test
  void convertIndexHtmlResourceToFileTest() throws IOException {
    InputStream resourceAsStream = UserGithubProjectCreator.class
        .getResourceAsStream("initialProjectTemplate/template_index.html");
    byte[] buffer = new byte[resourceAsStream.available()];
    resourceAsStream.read(buffer);

    String tmpdir = "/tmp";
    FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
    Path tmp = fs.getPath(tmpdir);
    Files.createDirectory(tmp);

    Path hello = tmp.resolve("index.html"); // /tmp/index.html
    Files.write(hello, buffer, StandardOpenOption.CREATE);
    assertTrue(Files.exists(hello));
    assertTrue(Files.readString(hello).contains("<html>"));
  }

  @Test
  void convertReadmeMdResourceToFileTest() throws IOException {
    convertResourceToFileTest("template_README.md");
  }

  private void convertResourceToFileTest(String filename) throws IOException {
    Path tmpFolder = Files.createTempDirectory("tmp");
    System.out.println(tmpFolder);

    String randomFilename = System.getProperty("java.io.tmpdir") +
        String.format("%4s", new Random().nextInt(10000)).replace(' ', '0');
    Util.convertResourceToFile("initialProjectTemplate/" + filename, randomFilename);
    assertTrue(new File(randomFilename).exists());
  }
}
