package io.github.flauberjp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
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
