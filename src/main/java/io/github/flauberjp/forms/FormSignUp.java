package io.github.flauberjp.forms;

import io.github.flauberjp.UserGithubInfo;
import io.github.flauberjp.Util;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    contentPane.setToolTipText("");
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblUsername = new JLabel("Nome de Usu\u00E1rio");
    lblUsername.setBounds(35, 111, 111, 14);
    contentPane.add(lblUsername);

    txtUsername = new JTextField();
    txtUsername.setText("mygitusageevicencesapp");
    txtUsername.setColumns(10);
    txtUsername.setBounds(156, 108, 293, 20);
    contentPane.add(txtUsername);

    passwordField = new JPasswordField();
    passwordField.setToolTipText("e.g. passw0rd");
    passwordField.setText("44dbb46ec17d03c3545a4301370565c45e870ce3");
    passwordField.setBounds(156, 153, 293, 20);
    contentPane.add(passwordField);

    JLabel lblPassword = new JLabel("Password");
    lblPassword.setBounds(35, 156, 111, 14);
    contentPane.add(lblPassword);

    JButton btnConfirm = new JButton("Confirmar");
    btnConfirm.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          if(UserGithubInfo.validarCredenciais(txtUsername.getText(), String.valueOf(passwordField.getPassword()))) {
            try {
              Util.savePropertiesToFile(UserGithubInfo.get().toProperties(), UserGithubInfo.PROPERTIES_FILE);
              JOptionPane.showMessageDialog(contentPane, "Credenciais válidas!");
            } catch (Exception ex) {
              JOptionPane.showMessageDialog(contentPane, "Credenciais válidas, mas houve problemas ao ler propriedades. Exception: " + ex.getMessage());
            }
          } else {
            JOptionPane.showMessageDialog(contentPane, "Credenciais inválidas");
          }

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
