package io.github.flauberjp;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.forms.model.GitDir;
import io.github.flauberjp.forms.model.GitDirListRenderer;
import io.github.flauberjp.util.Util;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Random;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

public class GitProjectManipulatorThread extends SwingWorker<Void, Void> {
  private JList<GitDir> list = null;
  public File diretorioASerAnalisado = null;
  private JProgressBar progressBar;
  private JPanel panel;
  private PropertyChangeListener propertyChangeListener;

  void setContentPanelEnabled(Boolean isEnabled) {
    if(panel == null) {
      return;
    }
    panel.setEnabled(isEnabled);
    for (Component cp : panel.getComponents()) {
      if(cp.equals(progressBar)) {
    	  continue;
      }
      cp.setEnabled(isEnabled);
    }
  }
  /*
   * Main task. Executed in background thread.
   */
  @Override
  public Void doInBackground() {
    LOGGER.debug("GitProjectManipulator.doInBackground()");
    Random random = new Random();
    int progress = 0;
    configureProgressBar(true, true);
    setContentPanelEnabled(false);
    addPropertyChangeListener(propertyChangeListener);
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    Util.addGitFiles(diretorioASerAnalisado);
    // Build the model from previous Git Path using GitDir Class
    Util.buildDefaultListModel();

    updateList();

    configureProgressBar(false, false);
    setProgress(100);
    setCursor(null);
    JOptionPane.showMessageDialog(panel, "Analise concluída!");
    return null;
  }

  private void updateList() {
    if(list == null) {
      return;
    }
    list.setModel(Util.getListModel());
    list.setCellRenderer(new GitDirListRenderer());
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  private void configureProgressBar(boolean indeterminateMode, boolean visible) {
    if(progressBar == null) {
      return;
    }
    progressBar.setIndeterminate(indeterminateMode);
    progressBar.setString("Analisando diretórios...");
    progressBar.setVisible(visible);
  }

  /*
   * Executed in event dispatching thread
   */
  @Override
  public void done() {
    Toolkit.getDefaultToolkit().beep();
    setContentPanelEnabled(true);
    setCursor(null); //turn off the wait cursor
  }

  private void setCursor(Cursor cursor) {
    if(panel == null) {
      return;
    }
    panel.setCursor(cursor);
  }

  public static void executaProcessamento(JProgressBar progressBar, JPanel panel, File diretorioASerAnalisado, JList<GitDir> list) {
    LOGGER.debug("GitProjectManipulator.executaProcessamento("
        + "progressBar = {}, panel = {}, diretorioASerAnalisado = {}, list = {})",
        progressBar, panel, diretorioASerAnalisado, list
    );
    GitProjectManipulatorThread gitProjectManipulator = new GitProjectManipulatorThread();
    gitProjectManipulator.diretorioASerAnalisado = diretorioASerAnalisado;
    gitProjectManipulator.panel = panel;
    gitProjectManipulator.progressBar = progressBar;
    gitProjectManipulator.list = list;
    gitProjectManipulator.execute();
  }
}
