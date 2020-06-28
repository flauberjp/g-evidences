package io.github.flauberjp.forms;

import io.github.flauberjp.EvidenceGenerator;
import io.github.flauberjp.GenerateHook;
import io.github.flauberjp.UserGithubInfo;
import io.github.flauberjp.UserGithubProjectCreator;
import io.github.flauberjp.Util;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import lombok.SneakyThrows;

public class FormForTesting extends JFrame {

  public static final String GIT_USER_FOR_TESTING = "mygitusageevicencesapp";
  private JPanel contentPane;
  private JTextField txtUsername;
  private JPasswordField passwordField;
  private JTextField txtRepoName;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          FormForTesting frame = new FormForTesting();
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
  public FormForTesting() {
    geraPainelPrincipal();

    botaoValidacao();

    botaoSalvarDados();

    botaoLerDados();

    botaoCriarProjetoRemoto();

    botaoGerarEvidencia();

    botaoGerarHook();

    botaoCriarDiretorioDaSolucao();

  }

  private void geraPainelPrincipal() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 525, 640);

    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblTeste = new JLabel("Área de testes");
    lblTeste.setBounds(220, 5, 203, 14);
    lblTeste.setForeground(Color.red);
    contentPane.add(lblTeste);

    JLabel lblTitle = new JLabel("Dados do seu Github");
    lblTitle.setBounds(192, 23, 203, 14);
    contentPane.add(lblTitle);

    JLabel lblUsername = new JLabel("Nome de Usu\u00E1rio");
    lblUsername.setBounds(35, 111, 111, 14);
    contentPane.add(lblUsername);

    txtUsername = new JTextField();
    txtUsername.setText(GIT_USER_FOR_TESTING);
    txtUsername.setColumns(10);
    txtUsername.setBounds(156, 108, 293, 20);
    contentPane.add(txtUsername);

    JLabel lblPassword = new JLabel("Password");
    lblPassword.setBounds(35, 138, 111, 14);
    contentPane.add(lblPassword);

    passwordField = new JPasswordField();
    passwordField.setToolTipText("e.g. 4e6e43db83b57aa5431e1280f2a50935cdfbf300");
    passwordField.setText("");
    passwordField.setBounds(156, 135, 293, 20);
    contentPane.add(passwordField);

    JLabel lblRepoName = new JLabel("Repositório");
    lblRepoName.setBounds(35, 161, 111, 14);
    contentPane.add(lblRepoName);

    txtRepoName = new JTextField();
    txtRepoName.setText("my-git-usage-evidences-repo");
    txtRepoName.setColumns(10);
    txtRepoName.setBounds(156, 158, 293, 20);
    contentPane.add(txtRepoName);
  }

  private void botaoGerarEvidencia() {
    JButton btnGenerateEvidence = new JButton("Gerar evidência");
    btnGenerateEvidence.addActionListener(new ActionListener() {
      @SneakyThrows
      public void actionPerformed(ActionEvent e) {
        try {
          UserGithubInfo userGithubInfo = UserGithubInfo.get();
          userGithubInfo.setRepoName(txtRepoName.getText());
          if(EvidenceGenerator.geraEvidenciaDeUsoDoGit(userGithubInfo)) {
            JOptionPane.showMessageDialog(contentPane, "Evidência gerada.");
          } else {
            JOptionPane.showMessageDialog(contentPane, "Problemas na geração de evidências.");
          }
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(contentPane, "Problemas na geração de evidências. Exception: " + ex.getMessage());
        }
      }
    });
    btnGenerateEvidence.setBounds(156, 189, 300, 23);
    contentPane.add(btnGenerateEvidence);
  }

  private void botaoCriarProjetoRemoto() {
    JButton btnCreateProject = new JButton("Criar projeto no repo remoto");
    btnCreateProject.addActionListener(new ActionListener() {
      @SneakyThrows
      public void actionPerformed(ActionEvent e) {
        try {
          UserGithubInfo userGithubInfo = UserGithubInfo.get();
          userGithubInfo.setRepoName(txtRepoName.getText());
          UserGithubProjectCreator.criaProjetoInicialNoGithub(userGithubInfo);
          JOptionPane.showMessageDialog(contentPane, "Projeto criado.");
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(contentPane, "Problemas na criação do projeto. Exception: " + ex.getMessage());
        }

      }
    });
    btnCreateProject.setBounds(156, 216, 300, 23);
    contentPane.add(btnCreateProject);
  }

  private void botaoLerDados() {
    JButton btnLerArq = new JButton("Ler conteúdo do arquivo " + UserGithubInfo.PROPERTIES_FILE);
    btnLerArq.addActionListener(new ActionListener() {
      @SneakyThrows
      public void actionPerformed(ActionEvent e) {
        if(!new File(UserGithubInfo.PROPERTIES_FILE).exists()) {
          JOptionPane.showMessageDialog(contentPane, "Arquivo inexistente, valide as suas credenciais primeiro!", "Erro", JOptionPane.ERROR_MESSAGE);
          return;
        }
        UserGithubInfo userGithubInfo = UserGithubInfo.get(Util.readPropertiesFromFile(UserGithubInfo.PROPERTIES_FILE));
        String output =
            "\tlogin=" + userGithubInfo.getUsername() + "\n" +
            "\tpassword=" + userGithubInfo.getPassword() + "\n" +
            "\tgithubName=" + userGithubInfo.getGithubName()+ "\n" +
            "\tgithubRepoNameFullPath=" + userGithubInfo.getRepoNameFullPath() + "\n" +
            "\tgithubEmail=" + userGithubInfo.getGithubEmail() + "\n" +
            "\trepoName=" + userGithubInfo.getRepoName();
        JOptionPane.showMessageDialog(contentPane, "Credenciais válidas!\n\n" + output);
      }
    });
    btnLerArq.setBounds(156, 243, 300, 23);
    contentPane.add(btnLerArq);
  }

  private void botaoSalvarDados() {
    JButton btnConfirm = new JButton("Salvar Dados em " + UserGithubInfo.PROPERTIES_FILE);
    btnConfirm.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          UserGithubInfo.reset();
          Util.savePropertiesToFile(UserGithubInfo.get(txtUsername.getText(), String.valueOf(passwordField.getPassword())).toProperties(), UserGithubInfo.PROPERTIES_FILE);
          JOptionPane.showMessageDialog(contentPane, "Dados salvos!");
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(contentPane, "Problemas ao tentar salvar dados. Exception: " + ex.getMessage());
        }
      }
    });
    btnConfirm.setBounds(156, 270, 300, 23);
    contentPane.add(btnConfirm);
  }

  private void botaoValidacao() {
    JButton btnValidacao = new JButton("Validar Credenciais");
    btnValidacao.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        UserGithubInfo.reset();
        if(UserGithubInfo.validarCredenciais(txtUsername.getText(), String.valueOf(passwordField.getPassword()))) {
          try {
            UserGithubInfo userGithubInfo = UserGithubInfo.get();
            String output =
                "\tlogin=" + userGithubInfo.getUsername() + "\n" +
                    "\tpassword=" + userGithubInfo.getPassword() + "\n" +
                    "\tgithubName=" + userGithubInfo.getGithubName()+ "\n" +
                    "\tgithubRepoNameFullPath=" + userGithubInfo.getRepoNameFullPath() + "\n" +
                    "\tgithubEmail=" + userGithubInfo.getGithubEmail() + "\n" +
                    "\trepoName=" + userGithubInfo.getRepoName();
            JOptionPane.showMessageDialog(contentPane, "Credenciais válidas!\n\n" + output);
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(contentPane, "Credenciais válidas, mas houve problemas ao ler propriedades. Exception: " + ex.getMessage());
          }
        } else {
          JOptionPane.showMessageDialog(contentPane, "Credenciais inválidas");
        }
      }
    });
    btnValidacao.setBounds(156, 297, 300, 23);
    contentPane.add(btnValidacao);
  }

  private void botaoGerarHook() {
    JButton btn = new JButton("Gerar hook(arquivo pre-commit)");
    btn.addActionListener(new ActionListener() {
      @SneakyThrows
      public void actionPerformed(ActionEvent e) {
        if(GenerateHook.generateHook()) {
          JOptionPane.showMessageDialog(contentPane, "Arquivo gerado.");
        } else {
          JOptionPane.showMessageDialog(contentPane, "Problemas na geração do hook.");
        }
      }
    });
    btn.setBounds(156, 324, 300, 23);
    contentPane.add(btn);
  }

  private void botaoCriarDiretorioDaSolucao() {
    JButton btn = new JButton("Criar diretório da solução");
    btn.addActionListener(new ActionListener() {
      @SneakyThrows
      public void actionPerformed(ActionEvent e) {
        if(Util.createSolutionDirectory()) {
          JOptionPane.showMessageDialog(contentPane, String.format("Diretório %s esta criado.", Util.getSolutionDirectory()));
        } else {
          JOptionPane.showMessageDialog(contentPane, String.format("Problemas ao criar diretório %s", Util.getSolutionDirectory()));
        }
      }
    });
    btn.setBounds(156, 351, 300, 23);
    contentPane.add(btn);
  }

}
