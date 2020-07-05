package io.github.flauberjp;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.File;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

class EvidenceGeneratorTest {

  @SneakyThrows
  @Test
  void resetWorkspace() {
    String dirGeradoNaWorkspace = EvidenceGenerator.geraDirAleatorioNaWorkspace();
    EvidenceGenerator.resetWorkspace();
    assertFalse(new File(dirGeradoNaWorkspace).exists());
  }
}
