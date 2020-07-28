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
import java.awt.Font;

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
    setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
    geraPainelPrincipal(gitProjectsNaoConfigurados);
  }

  private void geraPainelPrincipal(String gitProjectsNaoConfigurados) {
    LOGGER.debug(
        "FormAplicacaoConfiguracoesResult.geraPainelPrincipal(gitProjectsNaoConfigurados = {})",
        gitProjectsNaoConfigurados);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    
    pnContent = new JPanel();
    pnContent.setToolTipText("");
    pnContent.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(pnContent);
    pnContent.setLayout(null);

    JPanel pnMsg = new JPanel();

    pnMsg.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    pnMsg.setBounds(20, 53, 582, 129);
    pnContent.add(pnMsg);
    pnMsg.setLayout(new CardLayout(0, 0));

    JTextArea txtMsg = new JTextArea();
    txtMsg.setText(
        "Os projetos Git listados mais abaixo já continham o arquivo pre-commit.\n"
        + "Por favor inclua a linha de comando abaixo em cada um deles manualmente,\n"
        + "caso deseje que o uso do git neles seja refletido no github também. \n"
        + "Obs: o arquivo pre-commit em cada projeto Git desses localiza-se \n"
        + "em <projeto Git>\\.git\\hooks \n"
        + "Linha de comando: \n"
        + GenerateHook.getMainCommand());
    txtMsg.setText(txtMsg.getText() + "\n" + "Projetos:" + "\n" + gitProjectsNaoConfigurados);
    pnMsg.add(txtMsg, "name_26238090646100");
    JScrollPane scrollPne = new JScrollPane(txtMsg);
    pnMsg.add(scrollPne);    

    JLabel lblCfgAplicadas = new JLabel("Configurações aplicadas!");
    lblCfgAplicadas.setFont(new Font("Tahoma", Font.PLAIN, 11));
    lblCfgAplicadas.setHorizontalAlignment(SwingConstants.CENTER);
    lblCfgAplicadas.setBounds(20, 28, 582, 14);
    pnContent.add(lblCfgAplicadas);

    JButton btnOk = new JButton("OK");
    btnOk.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
    
    btnOk.setBounds(260, 199, 89, 23);
     
    pnContent.add(btnOk);
    
    setBounds(100, 100, 618, 272);

    if(gitProjectsNaoConfigurados.isEmpty()) {
      btnOk.setBounds(260, 45, 89, 23);
      setBounds(100, 100, 618, 120);
      pnMsg.setVisible(false);
    }
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
