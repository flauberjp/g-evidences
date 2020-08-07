package io.github.flauberjp.model;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.github.flauberjp.util.GitUtil;

public class GitDirList {
  private static List<GitDir> gitDirList = new ArrayList<GitDir>();
  private GitDirList() {
  }
  
  public static List<GitDir> get() {
	  return gitDirList;
  }
  
  public static void reset() {
	gitDirList.clear();
  }
  
  public static void addGitDirectories(File dir) {
    try {
      File[] files = dir.listFiles();
      for (File file : files) {
        if (file.isDirectory()) {
          if (file.getName().equalsIgnoreCase("node_modules") && Files
              .exists(Paths.get(file.getPath() + "/../package.json"))) {
            LOGGER.info("Skipping directory: " + file.getPath());
            continue;
          }
          if (file.getName().equals(".git")) {
            if (!GitUtil.isThisGitProjectAGithubOne(file.getParentFile().getCanonicalPath())) {
              GitDirList.get().add(new GitDir(file.getParentFile().getCanonicalPath()));
            }
          } else {
            addGitDirectories(file);
          }
        }
      }
    } catch (IOException ex) {
      LOGGER.error(ex.getMessage(), ex);
    }
  }
  
  public static List<GitDir> getNotSelectedGitDirList() {
    List result = new ArrayList<GitDir>();
    for (GitDir gitDir : get()) {
      if (!gitDir.isSelected()) {
        result.add(gitDir);
      }
    }
    return result;
  }
  
  public static List<GitDir> getSelectedGitDirList() {
    List result = new ArrayList<GitDir>();
    for (GitDir gitDir : get()) {
      if (gitDir.isSelected()) {
        result.add(gitDir);
      }
    }
    return result;
  }
  
  public static List<String> getSelectedGitDirStringList() {
    List result = new ArrayList<String>();
    for (GitDir gitDir : getSelectedGitDirList()) {
      result.add(gitDir.getPath());
    }
    return result;
  }
}
