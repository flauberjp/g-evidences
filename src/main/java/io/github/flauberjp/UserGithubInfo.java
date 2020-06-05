package io.github.flauberjp;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserGithubInfo implements Serializable{
	//default serialVersion id
    private static final long serialVersionUID = 1L;
    
    private String repoName = ""; // e.g. "my-git-usage-evidences"
    private String username = ""; // e.g. flauberjp
    private String password = ""; // e.g. passw0rd
    private String githubName = ""; // e.g. Flaviano Flauber
    private String githubEmail = ""; // e.g. flauberjp@gmail.com

    public UserGithubInfo(String[] args) {
        repoName = args[0];
        username = args[1];
        password = args[2];
        githubName = args[3];
        githubEmail = args[4];
    }

    public String getGithub() {
        return "https://github.com/" + getUsername() + "/";
    }

    public String getRepoNameFullPath() {
        return getGithub() + getRepoName() + ".git";
    }
}
