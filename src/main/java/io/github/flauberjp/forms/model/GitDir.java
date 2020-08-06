package io.github.flauberjp.forms.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

public class GitDir {

  private String path;
  private boolean isSelected = false;
  private boolean isConfigured = false;
  private String author;

  public GitDir(String path) {
    this.setPath(path);
  }

  public String getPath() {
    return path;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String[] getAuthors() {
    ArrayList<String> result = new ArrayList<>();
    Git git = null;
    try {
      git = Git.open(new File(getPath()));

      List<Ref> branches = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();
      RevWalk revWalk = new RevWalk(git.getRepository());

      branches.stream()
          .forEach(branch -> {
            try {
              RevCommit commit = revWalk.parseCommit(branch.getObjectId());
              if(result.indexOf(commit.getAuthorIdent().getEmailAddress()) == -1) {
                result.add(commit.getAuthorIdent().getEmailAddress());
              }
            } catch (IOException e) {
              e.printStackTrace();
            }
          });
    } catch (IOException | GitAPIException e) {
      e.printStackTrace();
    }
    return result.toArray(new String[0]);
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
	this.isConfigured = Files.exists( Path.of(path + "/.git/hooks/pre-commit"));
	this.isSelected = this.isConfigured;
  }

  public String toString() {
    return path;
  }
}
