package io.github.flauberjp.model;

import com.jcraft.jsch.Logger;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import static io.github.flauberjp.util.MyLogger.LOGGER;

public class GitDir {

  private String path;
  private boolean isSelected = false;
  private boolean isConfigured = false;
  private String author;
  private String[] authors;
  private String actualBranch;

  public GitDir(String path) {
    this.setPath(path);
  }

  public String getPath() {
    return path;
  }

  public String getActualBranch() {
    if(actualBranch == null) {
      try {
        Repository repo = new FileRepository(getPath() + "/.git");
        Git git = Git.open(new File(getPath()));
        actualBranch = git.getRepository().getBranch();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return actualBranch;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String[] getAuthors() {
    if(authors != null) {
      return authors;
    }
    ArrayList<String> result = new ArrayList<>();
    try {
      Repository repo = new FileRepository(getPath() + "/.git");
      Git git = Git.open(new File(getPath()));
      RevWalk walk = new RevWalk(repo);

      List<Ref> branches = git.branchList().call();

      for (Ref branch : branches) {
        String branchName = branch.getName();

        LOGGER.debug("Commits of branch: {}", branch.getName());

        Iterable<RevCommit> commits = git.log().all().call();

        for (RevCommit commit : commits) {
          if(result.indexOf(commit.getAuthorIdent().getEmailAddress()) == -1) {
            result.add(commit.getAuthorIdent().getEmailAddress());
          }
        }
      }
    } catch (IOException | GitAPIException e) {
      e.printStackTrace();
    }
    authors = result.toArray(new String[0]);
    return authors;
  }

  public boolean isSelected() {
    return isSelected;
  }
  
  public boolean isConfigured() {
    return isSelected;
  }

  public void setSelected(boolean isSelected) {
    this.isSelected = isSelected;
  }
  
  public void setPath(String path) {
	  this.path = path;
	  this.isConfigured = Files.exists( Paths.get(path + "/.git/hooks/pre-commit"));
	  this.isSelected = this.isConfigured;
  }

  public String toString() {
    return path;
  }
}
