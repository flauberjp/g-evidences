package io.github.flauberjp.forms;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.GenerateHook;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class FormAplicacaoConfiguracoesResult extends JDialog {

  private JPanel pnContent;

  /**
   * Create the frame.
   */
  public FormAplicacaoConfiguracoesResult(JFrame parent, String gitProjectsNaoConfigurados) {
    super(parent, true);
    LOGGER.debug(
        "FormAplicacaoConfiguracoesResult.FormAplicacaoConfiguracoesResult(gitProjectsNaoConfigurados = {})",
        gitProjectsNaoConfigurados);
    setTitle("Resultado da aplicação das configurações");
    setModal(true);
    setResizable(false);
    LOGGER.debug(
        "FormAplicacaoConfiguracoesResult.FormAplicacaoConfiguracoesResult()");
    geraPainelPrincipal("");
    setResizable(false);
    setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
    geraPainelPrincipal(gitProjectsNaoConfigurados);
  }

  private void geraPainelPrincipal(String gitProjectsNaoConfigurados) {
    LOGGER.debug(
        "FormAplicacaoConfiguracoesResult.geraPainelPrincipal(gitProjectsNaoConfigurados = {})",
        gitProjectsNaoConfigurados);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(100, 100, 459, 272);
    pnContent = new JPanel();
    pnContent.setToolTipText("");
    pnContent.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(pnContent);
    pnContent.setLayout(null);

    JPanel pnMsg = new JPanel();

    pnMsg.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    pnMsg.setBounds(20, 53, 404, 129);
    pnContent.add(pnMsg);
    pnMsg.setLayout(new CardLayout(0, 0));

    JTextArea txtMsg = new JTextArea();
    txtMsg.setText(
        "Lista de git projects que já continham o arquivo pre-commit. Por favor inclua esta linha de comando em cada um deles manualmente, caso deseje que o uso do git neles seja refletido no github também: \n"
        + GenerateHook.getMainCommand());
    txtMsg.setText(txtMsg.getText() + "\n" + "Projetos:" + "\n" + gitProjectsNaoConfigurados);
    pnMsg.add(txtMsg, "name_26238090646100");

    JScrollPane scrollPne = new JScrollPane(txtMsg);
    pnMsg.add(scrollPne);

    JLabel lblCfgAplicadas = new JLabel("Configurações aplicadas!");
    lblCfgAplicadas.setHorizontalAlignment(SwingConstants.CENTER);
    lblCfgAplicadas.setBounds(10, 28, 414, 14);
    pnContent.add(lblCfgAplicadas);

    JButton btnOk = new JButton("OK");
    btnOk.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    btnOk.setBounds(181, 199, 89, 23);
    pnContent.add(btnOk);

  }


  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    LOGGER.info("FormMain.main(args = {})", args);
    showForm();
  }

  public static void showForm() {
    LOGGER.debug("FormAplicacaoConfiguracoesResult.showForm()");
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          FormAplicacaoConfiguracoesResult frame = new FormAplicacaoConfiguracoesResult(null, "");
          frame.setVisible(true);
        } catch (Exception ex) {
          LOGGER.error(ex.getMessage(), ex);
        }
      }
    });
  }
}
