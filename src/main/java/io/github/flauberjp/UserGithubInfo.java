package io.github.flauberjp;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import lombok.Getter;
import lombok.ToString;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

@Getter
@ToString
public class UserGithubInfo implements Serializable {
  private static UserGithubInfo userGithubInfo;
  //default serialVersion id
  private static final long serialVersionUID = 1L;
  private String repoName = "my-git-usage-evidences-repo";
  private String username = ""; // e.g. flauberjp
  private String password = ""; // e.g. passw0rd
  private String githubName = ""; // e.g. Flaviano Flauber
  private String githubEmail = ""; // e.g. flauberjp@gmail.com
  private Properties properties = null;
  private GitHub gitHub = null;
  private GHUser ghUser = null;
  private boolean credenciaisValidas = false;

  private UserGithubInfo() {
  }

  private UserGithubInfo(Properties properties) {
    this.properties = properties;
    username = properties.getProperty("login");
    password = properties.getProperty("password");
    repoName = properties.getProperty("repoName");

    githubName = properties.getProperty("githubName");
    githubEmail = properties.getProperty("githubEmail");
  }

  private UserGithubInfo(String username, String password) {
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

  public static UserGithubInfo get() throws IOException {
    if (userGithubInfo == null) {
      return get(Util.getProperties());
    }
    return userGithubInfo;
  }

  public static UserGithubInfo get(Properties properties) throws IOException {
    if (userGithubInfo == null) {
      userGithubInfo = new UserGithubInfo(properties);
    }
    return userGithubInfo;
  }

  public static UserGithubInfo get(String username, String password) {
    if (userGithubInfo == null) {
      userGithubInfo = new UserGithubInfo(username, password);
    }
    return userGithubInfo;
  }

  public String getGithub() {
    return "https://github.com/" + getUsername() + "/";
  }

  public String getRepoNameFullPath() {
    return getGithub() + getRepoName() + ".git";
  }

  public boolean isCredenciaisValidas() {
    return credenciaisValidas;
  }

  public static boolean validarCredenciais(String username, String password) {
    UserGithubInfo user = UserGithubInfo.get(username, password);
    return user.isCredenciaisValidas();
  }
}
