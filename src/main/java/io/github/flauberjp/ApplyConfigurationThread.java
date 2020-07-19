package io.github.flauberjp;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.forms.FormAplicacaoConfiguracoesResult;
import io.github.flauberjp.forms.model.GitDir;
import io.github.flauberjp.util.Util;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class ApplyConfigurationThread extends SwingWorker<Void, Void> {
  private static JFrame parentFrame;
  private JProgressBar progressBar;
  private JPanel panel;
  private PropertyChangeListener propertyChangeListener;
  private String username;
  private String password;
  private FormAplicacaoConfiguracoesResult form;

  /*
   * Main task. Executed in background thread.
   */
  @Override
  public Void doInBackground() {
    LOGGER.debug("ApplyConfigurationThread.doInBackground()");
    Random random = new Random();
    int progress = 0;
    List deletedProjects = new ArrayList<GitDir>();
    configureProgressBar(true, true);
    Util.enableComponents(progressBar, panel, false);
    addPropertyChangeListener(propertyChangeListener);
    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

    UserGithubInfo userGithubInfo = UserGithubInfo.get(username,
        password);
    Util.savePropertiesToFile(userGithubInfo.toProperties(), UserGithubInfo.PROPERTIES_FILE);
    UserGithubProjectCreator.criaProjetoInicialNoGithub(userGithubInfo);
    deletedProjects = GenerateHook.destroyHook(Util.getNotSelectedGitDirList());
    System.out.println(deletedProjects);
    String gitProjectsNaoConfigurados = GenerateHook.generateHook(Util.getSelectedGitDirStringList());

    configureProgressBar(false, false);
    setProgress(100);
    setCursor(null);

    form = new FormAplicacaoConfiguracoesResult(parentFrame, gitProjectsNaoConfigurados);
    form.setVisible(true);

    return null;
  }

  private void configureProgressBar(boolean indeterminateMode, boolean visible) {
    if(progressBar == null) {
      return;
    }
    progressBar.setIndeterminate(indeterminateMode);
    progressBar.setString("Aplicando configurações...");
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
    if(panel == null) {
      return;
    }
    panel.setCursor(cursor);
  }

  public static void executaProcessamento(JFrame frame, JProgressBar progressBar, JPanel panel, String username, String password) {
    LOGGER.debug("ApplyConfigurationThread.executaProcessamento("
        + "progressBar = {}, panel = {}, username = {}, password = XXX)",
        progressBar, panel, username
    );
    ApplyConfigurationThread gitProjectManipulator = new ApplyConfigurationThread();
    parentFrame = frame;
    gitProjectManipulator.panel = panel;
    gitProjectManipulator.progressBar = progressBar;
    gitProjectManipulator.username = username;
    gitProjectManipulator.password = password;
    gitProjectManipulator.execute();
  }
}
