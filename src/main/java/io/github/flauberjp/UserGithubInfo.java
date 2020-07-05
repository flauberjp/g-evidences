package io.github.flauberjp;

import io.github.flauberjp.util.Util;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import lombok.Getter;
import lombok.ToString;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;
import static io.github.flauberjp.util.MyLogger.logger;

@Getter
@ToString
public class UserGithubInfo implements Serializable {
  public static final String PROPERTIES_FILE = "propriedades.txt";
  private static UserGithubInfo userGithubInfo;
  // default serialVersion id
  private static final long serialVersionUID = 1L;
  private String repoName = "my-git-usage-evidences-repo";
  private String username = ""; // e.g. flauberjp
  private String password = ""; // e.g. passw0rd
  private String githubName = ""; // e.g. Flaviano Flauber
  private String githubEmail = ""; // e.g. flauberjp@gmail.com
  private GitHub gitHub = null;
  private GHUser ghUser = null;
  private String hookType = ""; // e.g. pre-commit
  private boolean credenciaisValidas = false;

  private UserGithubInfo() {
  }

  private UserGithubInfo(Properties properties) {
    logger.debug("UserGithubInfo.UserGithubInfo(properties = {}", properties);
    username = properties.getProperty("login");
    password = properties.getProperty("password");
    repoName = properties.getProperty("repoName");
    hookType = properties.getProperty("hookType");

    githubName = properties.getProperty("githubName");
    githubEmail = properties.getProperty("githubEmail");
  }

  private UserGithubInfo(String username, String password) {
    logger.debug("UserGithubInfo.UserGithubInfo(username {}, password XXX)", username);
    try {
      this.gitHub = GitHub.connectUsingPassword(username, password);
      this.ghUser = gitHub.getUser(username);
      this.username = username;
      this.password = password;
      this.credenciaisValidas = true;
      this.githubName = ghUser.getName();
      this.githubEmail = ghUser.getEmail();
    } catch (IOException e) {
      this.credenciaisValidas = false;
    }
  }

  private UserGithubInfo(String username, String password, String hookType) {
    try {
      this.gitHub = GitHub.connectUsingPassword(username, password);
      this.ghUser = gitHub.getUser(username);
      this.username = username;
      this.password = password;
      this.credenciaisValidas = true;
      this.githubName = ghUser.getName();
      this.githubEmail = ghUser.getEmail();
      this.hookType = hookType;
    } catch (IOException e) {
      this.credenciaisValidas = false;
    }
  }

  public static void setRepoName(String repoName) {
    logger.debug("UserGithubInfo.setRepoName(repoName = {})", repoName);
    userGithubInfo.repoName = repoName;
  }

  public static UserGithubInfo get() throws IOException {
    logger.debug("UserGithubInfo.get()");
    if (userGithubInfo == null) {
      return get(Util.getProperties(PROPERTIES_FILE));
    }
    return userGithubInfo;
  }

  public static UserGithubInfo get(Properties properties) throws IOException {
    logger.debug("UserGithubInfo.get(properties = {})", properties);
    if (userGithubInfo == null) {
      userGithubInfo = new UserGithubInfo(properties);
    }
    return userGithubInfo;
  }

  public static UserGithubInfo get(String username, String password) {
    logger.debug("UserGithubInfo.get(username {}, password XXX)", username);
    if (userGithubInfo == null) {
      userGithubInfo = new UserGithubInfo(username, password);
    }
    return userGithubInfo;
  }

  public static UserGithubInfo get(String username, String password, String hookType) {
    if (userGithubInfo == null) {
      userGithubInfo = new UserGithubInfo(username, password, hookType);
    }
    return userGithubInfo;
  }

  public static void reset() {
    logger.debug("UserGithubInfo.reset()");
    userGithubInfo = null;
  }

  public Properties toProperties() {
    logger.debug("UserGithubInfo.toProperties()");
    return Util.createProperties(new String[]{
        "repoName", getRepoName(),
        "login", getUsername(),
        "password", getPassword(),
        "githubName", getGithubName(),
        "githubEmail", getGithubEmail(),
        "hookType", getHookType()
      });
  };

  public String getGithub() {
    logger.debug("UserGithubInfo.getGithub()");
    return "https://github.com/" + getUsername() + "/";
  }

  public String getRepoNameFullPath() {
    logger.debug("UserGithubInfo.getRepoNameFullPath()");
    return getGithub() + getRepoName() + ".git";
  }

  public boolean isCredenciaisValidas() {
    logger.debug("UserGithubInfo.isCredenciaisValidas()");
    return credenciaisValidas;
  }

  public static boolean validarCredenciais(String username, String password) {
    logger.debug("UserGithubInfo.validarCredenciais(username {}, password XXX)", username);
    UserGithubInfo user = UserGithubInfo.get(username, password);
    return user.isCredenciaisValidas();
  }
}
