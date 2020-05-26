package io.github.flauberjp;

import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserGithubProjectCreator {
    public static void main(String[] args) throws IOException, URISyntaxException {
        GitHub github = new GitHubBuilder().withPassword("flauberjp", "xxxxxx").build();

        GHCreateRepositoryBuilder repo = github.createRepository("new-repository");
        repo.description("description-of-new-repository");
        repo.private_(true);
        repo.create();
    }
}
