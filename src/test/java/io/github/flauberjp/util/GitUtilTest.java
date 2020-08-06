package io.github.flauberjp.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class GitUtilTest {
  @Test
  void isThisGitProjectAGithubOneTest_checkTrue() {
    Path currentRelativePath = Paths.get("");
    String s = currentRelativePath.toAbsolutePath().toString();
    assertTrue(GitUtil.isThisGitProjectAGithubOne(s));
  }

  @Test
  void isThisGitProjectAGithubOneTest_checkFalse() {
    Path currentRelativePath = Paths.get("/");
    String s = currentRelativePath.toAbsolutePath().toString();
    assertFalse(GitUtil.isThisGitProjectAGithubOne(s));
  }
}
