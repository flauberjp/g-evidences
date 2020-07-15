package io.github.flauberjp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GenerateHookTest {
  static String gitProject;

  @BeforeAll
  static void setUp() throws IOException {
    String dirGeradoNaWorkspace = EvidenceGenerator.geraDirAleatorioNaWorkspace();
    File file = new File(dirGeradoNaWorkspace + "/.git/hooks");
    file.mkdirs();
    gitProject = dirGeradoNaWorkspace;
    GenerateHook.convertHookTemplateInHookFinal(".");
    GenerateHook.copyLocalHookToAGitProject(gitProject);
  }

  @Test
  void copyLocalHookToAGitProject() throws IOException {
    assertTrue(new File(gitProject + "/.git/hooks/" + GenerateHook.HOOK_NAME).exists());
  }

  @Test
  void isFileContainMainCommand() throws IOException {
    assertTrue(GenerateHook.isFileContainMainCommand(gitProject + "/.git/hooks/" + GenerateHook.HOOK_NAME));
  }
}
