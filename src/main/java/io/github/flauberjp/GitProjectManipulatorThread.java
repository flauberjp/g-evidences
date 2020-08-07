package io.github.flauberjp;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.forms.component.ProjetosGitDetectadosTableComponent;
import io.github.flauberjp.model.GitDir;
import io.github.flauberjp.model.GitDirList;
import io.github.flauberjp.util.Util;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import java.util.Random;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class GitProjectManipulatorThread extends SwingWorker<Void, Void> {

  public File diretorioASerAnalisado = null;
  private JProgressBar progressBar;
  private JPanel panel;
  private PropertyChangeListener propertyChangeListener;
  private ProjetosGitDetectadosTableComponent tabelaPanel;
  private String username;

  /*
   * Main task. Executed in background thread.
   */
  @Override
  public Void doInBackground() {
    LOGGER.debug("GitProjectManipulator.doInBackground()");
    Random random = new Random();
    int progress = 0;
    configureProgressBar(true, true);
    Util.enableComponents(progressBar, panel, false);
    addPropertyChangeListener(propertyChangeListener);
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    GitDirList.reset();
    GitDirList.addGitDirectories(diretorioASerAnalisado);

    atualizarTabela();

    configureProgressBar(false, false);
    setProgress(100);
    setCursor(null);
    JOptionPane.showMessageDialog(panel, "Análise concluída!");
    return null;
  }

  private void atualizarTabela() {
    if (tabelaPanel != null) {
      this.tabelaPanel.atualizarTabela(GitDirList.get(), username);
    }
  }

  private void configureProgressBar(boolean indeterminateMode, boolean visible) {
    if (progressBar == null) {
      return;
    }
    progressBar.setIndeterminate(indeterminateMode);
    progressBar.setString("Analisando pastas...");
    progressBar.setVisible(visible);
  }

  /*
   * Executed in event dispatching thread
   */
  @Override
  public void done() {
    Toolkit.getDefaultToolkit().beep();
    Util.enableComponents(progressBar, panel, true);
    setCursor(null); //turn off the wait cursor
  }

  private void setCursor(Cursor cursor) {
    if (panel == null) {
      return;
    }
    panel.setCursor(cursor);
  }

  public static void executaProcessamento(JProgressBar progressBar, JPanel panel,
      File diretorioASerAnalisado,
      ProjetosGitDetectadosTableComponent tabelaPanel,
      String username) {
    LOGGER.debug("GitProjectManipulator.executaProcessamento("
            + "progressBar = {}, panel = {}, diretorioASerAnalisado = {}, list = {}, tabelaPanel = {}, username = {})",
        progressBar, panel, diretorioASerAnalisado, tabelaPanel, username
    );
    GitProjectManipulatorThread gitProjectManipulator = new GitProjectManipulatorThread();
    gitProjectManipulator.diretorioASerAnalisado = diretorioASerAnalisado;
    gitProjectManipulator.panel = panel;
    gitProjectManipulator.progressBar = progressBar;
    gitProjectManipulator.tabelaPanel = tabelaPanel;
    gitProjectManipulator.username = username;
    gitProjectManipulator.execute();
  }
}
