package io.github.flauberjp;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserGithubInfo implements Serializable {

  private static UserGithubInfo userGithubInfo;
  //default serialVersion id
  private static final long serialVersionUID = 1L;
  private String repoName = ""; // e.g. "my-git-usage-evidences"
  private String username = ""; // e.g. flauberjp
  private String password = ""; // e.g. passw0rd
  private String githubName = ""; // e.g. Flaviano Flauber
  private String githubEmail = ""; // e.g. flauberjp@gmail.com
  private Properties properties = null;

  private UserGithubInfo() {
  }

  private UserGithubInfo(Properties properties) {
    this.properties = properties;
    repoName = properties.getProperty("repoName");
    username = properties.getProperty("login");
    password = properties.getProperty("password");
    githubName = properties.getProperty("githubName");
    githubEmail = properties.getProperty("githubEmail");
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

  public String getGithub() {
    return "https://github.com/" + getUsername() + "/";
  }

  public String getRepoNameFullPath() {
    return getGithub() + getRepoName() + ".git";
  }
}
