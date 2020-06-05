package io.github.flauberjp;

import static io.github.flauberjp.Util.isRunningFromJar;

import java.util.Properties;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserGithubProjectCreator {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Properties properties = Util.getProperties();

        GitHub github = GitHubBuilder
                .fromProperties(properties)
                .build();

        GHCreateRepositoryBuilder repo = github.createRepository("new-repository");
        repo.description("description-of-new-repository");
        repo.private_(true);
        repo.create();
    }
}
