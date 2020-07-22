package io.github.flauberjp;

import static org.junit.jupiter.api.Assertions.*;

import io.github.flauberjp.util.Util;
import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GenerateHookTest {
  static String gitProject;
  static String hook;

  @BeforeEach
  void setUp() throws IOException {
    String dirGeradoNaWorkspace = EvidenceGenerator.geraDirAleatorioNaWorkspace();
    File file = new File(dirGeradoNaWorkspace + "/.git/hooks");
    file.mkdirs();
    gitProject = dirGeradoNaWorkspace;
    GenerateHook.convertHookTemplateInHookFinal(".");
    GenerateHook.copyHookFinalToAGitProject(gitProject);
    hook = gitProject + "/.git/hooks/" + GenerateHook.HOOK_NAME;
  }

  @Test
  void copyLocalHookToAGitProject() throws IOException {
    assertTrue(new File(hook).exists());
  }

  @Test
  void isFileContainMainCommand() throws IOException {
    assertTrue(GenerateHook.isFileContainMainCommand(hook));
  }

  @Test
  void removeMainCommandFromAFile() throws IOException {
    assertTrue(GenerateHook.isFileContainMainCommand(hook));
    GenerateHook.removeMainCommandFromAFile(hook);
    assertFalse(GenerateHook.isFileContainMainCommand(hook));
  }

  @Test
  void isGitProjectHookEqualsToLocalOne() throws IOException {
    String dirGeradoNaWorkspace = EvidenceGenerator.geraDirAleatorioNaWorkspace();
    File file = new File(dirGeradoNaWorkspace + "/.git/hooks");
    file.mkdirs();
    GenerateHook.copyHookFinalToAGitProject(dirGeradoNaWorkspace);
    Boolean isTrue = GenerateHook.isGitProjectHookEqualsToLocalOne(dirGeradoNaWorkspace);
    assertTrue(isTrue);
  }
}
