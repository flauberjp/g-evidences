package io.github.flauberjp.forms;

import io.github.flauberjp.UserGithubInfo;
import io.github.flauberjp.Util;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FormSignUp extends JFrame {

  private JPanel contentPane;
  private JTextField txtUsername;
  private JPasswordField passwordField;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          FormSignUp frame = new FormSignUp();
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
  public FormSignUp() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 525, 370);
    contentPane = new JPanel();
    contentPane.setToolTipText("e.g. passw0rd");
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblUsername = new JLabel("Nome de Usu\u00E1rio");
    lblUsername.setBounds(35, 111, 111, 14);
    contentPane.add(lblUsername);

    txtUsername = new JTextField();
    txtUsername.setText("e.g. flauberjp");
    txtUsername.setColumns(10);
    txtUsername.setBounds(156, 108, 293, 20);
    contentPane.add(txtUsername);

    passwordField = new JPasswordField();
    passwordField.setToolTipText("e.g. passw0rd");
    passwordField.setBounds(156, 153, 293, 20);
    contentPane.add(passwordField);

    JLabel lblPassword = new JLabel("Password");
    lblPassword.setBounds(35, 156, 111, 14);
    contentPane.add(lblPassword);

    JButton btnConfirm = new JButton("Confirmar");
    btnConfirm.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          String username = txtUsername.getText();
          String password = new String(passwordField.getPassword());

          String[] args = new String[]{
              "repoName", "<MUDAR_REPO_NAME>",
              "login", username,
              "password", password,
              "githubName", "<MUDAR_GITHUB_NAME>",
              "githubEmail", "<MUDAR_GITHUB_EMAIL"
          };
          UserGithubInfo user = UserGithubInfo.get(Util.createProperties(args));

          Util.WriteObjectToFile(user);
          System.out.println(LocalDateTime.now());

          JOptionPane.showMessageDialog(null, "Cadastrado com Sucesso");

          FormGitProjects formGitProjects = new FormGitProjects();
          formGitProjects.setVisible(true);

          dispose(); //fecha a janela
        } catch (Exception erro) {
          JOptionPane.showMessageDialog(null, erro.getMessage());
        }
      }
    });
    btnConfirm.setBounds(217, 297, 89, 23);
    contentPane.add(btnConfirm);

    JLabel lblTitle = new JLabel("Dados do seu Github");
    lblTitle.setBounds(192, 23, 203, 14);
    contentPane.add(lblTitle);
  }
}
