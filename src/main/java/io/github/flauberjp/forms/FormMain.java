package io.github.flauberjp.forms;

import io.github.flauberjp.GenerateHook;
import io.github.flauberjp.UserGithubInfo;
import io.github.flauberjp.UserGithubProjectCreator;
import io.github.flauberjp.Util;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FormMain extends JFrame {

  private JPanel contentPane;
  private JTextField txtUsername;
  private JPasswordField passwordField;
  private JTextField txtReponame;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          FormMain frame = new FormMain();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public FormMain() {
    geraPainelPrincipal();

    botaoConfigurar();
  }

  private void geraPainelPrincipal() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 525, 370);
    contentPane = new JPanel();
    contentPane.setToolTipText("");
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblTitle = new JLabel("Credenciais do Github");
    setLabelUnderline(lblTitle);
    lblTitle.setBounds(35, 23, 203, 14);
    contentPane.add(lblTitle);

    JLabel lblUsername = new JLabel("Nome de Usu\u00E1rio:");
    lblUsername.setBounds(35, 52, 111, 14);
    contentPane.add(lblUsername);

    txtUsername = new JTextField();
    txtUsername.setText("mygitusageevicencesapp");
    txtUsername.setColumns(10);
    txtUsername.setBounds(160, 50, 293, 20);
    contentPane.add(txtUsername);

    JLabel lblPassword = new JLabel("Password:");
    lblPassword.setBounds(35, 77, 111, 14);
    contentPane.add(lblPassword);

    passwordField = new JPasswordField();
    passwordField.setToolTipText("e.g. passw0rd");
    passwordField.setText("44dbb46ec17d03c3545a4301370565c45e870ce3");
    passwordField.setBounds(160, 75, 293, 20);
    contentPane.add(passwordField);

    JLabel lblRepoNameArea = new JLabel("Repositório no seu Github que irá registrar o seu uso local do git");
    setLabelUnderline(lblRepoNameArea);
    lblRepoNameArea.setBounds(35, 107, 450, 14);
    contentPane.add(lblRepoNameArea);

    JLabel lblRepoName = new JLabel("Nome do Repositório:");
    lblRepoName.setBounds(35, 134, 123, 14);
    contentPane.add(lblRepoName);

    txtReponame = new JTextField();
    txtReponame.setText("my-git-usage-evidences");
    txtReponame.setColumns(10);
    txtReponame.setBounds(160, 132, 293, 20);
    contentPane.add(txtReponame);

    JLabel lblProjects = new JLabel("Projetos que não são do Github que terão o uso local do git registrado no Github");
    setLabelUnderline(lblProjects);
    lblProjects.setBounds(35, 159, 450, 14);
    contentPane.add(lblProjects);
  }

  private void setLabelUnderline(JLabel label) {
    Font font = label.getFont();
    Map attributes = font.getAttributes();
    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    label.setFont(font.deriveFont(attributes));
  }

  private void botaoConfigurar() {
    JButton btn = new JButton("Aplicar configurações");
    btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          UserGithubInfo userGithubInfo = UserGithubInfo.get(txtUsername.getText(), String.valueOf(passwordField.getPassword()));
          userGithubInfo.setRepoName(txtReponame.getText());
          Util.savePropertiesToFile(userGithubInfo.toProperties(), UserGithubInfo.PROPERTIES_FILE);
          UserGithubProjectCreator.criaProjetoInicialNoGithub(userGithubInfo);
          GenerateHook.generateHook();
          JOptionPane.showMessageDialog(contentPane, "Configurações aplicadas!");
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(contentPane, "Problemas ao aplicar configurações. Exception: " + ex.getMessage());
        }
      }
    });
    btn.setBounds(175, 297, 170, 23);
    contentPane.add(btn);
  }
}
