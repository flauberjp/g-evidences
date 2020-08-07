package io.github.flauberjp.util;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.UserGithubProjectCreator;
import io.github.flauberjp.model.GitDir;
import io.github.flauberjp.model.GitDirList;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.DefaultListModel;
import javax.swing.JProgressBar;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class GitUtil {

  private GitUtil() {
  }
  
  public static boolean isThisGitProjectAGithubOne(String fullPathDirectoryOfAGitProject) {
    LOGGER.debug("GitUtil.isThisGitProjectAGithubOne(fullPathDirectoryOfAGitProject = {})",
        fullPathDirectoryOfAGitProject);
    boolean result = false;
    if (Paths.get(fullPathDirectoryOfAGitProject).toFile().exists()) {
      if (Paths.get(fullPathDirectoryOfAGitProject + "/.git").toFile().exists()) {
        try {
          Git git = Git.open(new File(fullPathDirectoryOfAGitProject));
          StoredConfig config = git.getRepository().getConfig();
          String remoteOriginUrl = config.getString("remote", "origin", "url");
          if (remoteOriginUrl != null) {
            result = remoteOriginUrl.toLowerCase().contains("github.com");
          }
        } catch (Exception ex) {
          LOGGER.error(ex.getMessage(), ex);
        }
      }
    }
    return result;
  }
}
