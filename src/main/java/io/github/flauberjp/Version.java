package io.github.flauberjp;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.util.Util;
import java.io.FileReader;
import java.io.IOException;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class Version {

  public static void main(String[] args)
      throws IOException, XmlPullParserException {
    LOGGER.info(Version.getVersionFromPom());
  }

  public static String getVersionFromPom() {
    LOGGER.debug("Version.getVersionFromPom()");
    String result = "Version: ";
    if(Util.isRunningFromJar()) {
      result += Version.class.getPackage().getImplementationVersion();
    } else {
      try {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        result += model.getVersion();
      } catch (IOException | XmlPullParserException ex) {
        LOGGER.error(ex.getMessage(), ex);
      }
    }
    return result;
  }
}
