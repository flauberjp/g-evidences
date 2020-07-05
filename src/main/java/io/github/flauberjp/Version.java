package io.github.flauberjp;

import java.io.FileReader;
import java.io.IOException;
import static io.github.flauberjp.util.MyLogger.logger;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

public class Version {
  public static void main(String[] args)
      throws IOException, XmlPullParserException {
    MavenXpp3Reader reader = new MavenXpp3Reader();
    logger.info(Version.getVersionFromPom());
  }

  public static String getVersionFromPom() {
		logger.debug("Version.getVersionFromPom()");
    String result = "";
	try {
	  MavenXpp3Reader reader = new MavenXpp3Reader();
	  Model model = reader.read(new FileReader("pom.xml"));
	  result = "Version: " + model.getVersion();
	} catch(IOException | XmlPullParserException ex) {
	  ex.printStackTrace();
	}
	return result;
  }
}
