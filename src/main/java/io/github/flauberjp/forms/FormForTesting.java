package io.github.flauberjp.forms;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.EvidenceGenerator;
import io.github.flauberjp.GenerateHook;
import io.github.flauberjp.UserGithubInfo;
import io.github.flauberjp.UserGithubProjectCreator;
import io.github.flauberjp.forms.model.GitDir;
import io.github.flauberjp.forms.model.GitDirListRenderer;
import io.github.flauberjp.util.Util;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
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
    LOGGER.info("FormForTesting.main(args = {})", args);
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          FormForTesting frame = new FormForTesting();
          frame.setVisible(true);
        } catch (Exception ex) {
          LOGGER.error(ex.getMessage(), ex);
        }
      }
    });
  }

  /**
   * Create the frame.
   */
  public FormForTesting() {
    LOGGER.debug("FormForTesting.FormForTesting()");
    geraPainelPrincipal();

    botaoValidacao();

    botaoSalvarDados();

    botaoLerDados();

    botaoCriarProjetoRemoto();

    botaoGerarEvidencia();

    botaoGerarHook();

    areaEscolherProjetos();

  }

  private void geraPainelPrincipal() {
    LOGGER.debug("FormForTesting.geraPainelPrincipal()");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 865, 738);

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
    LOGGER.debug("FormForTesting.botaoGerarEvidencia()");
    JButton btnGenerateEvidence = new JButton("Gerar evidência");
    btnGenerateEvidence.addActionListener(new ActionListener() {
      @SneakyThrows
      public void actionPerformed(ActionEvent e) {
        LOGGER.info("Botão \"Gerar evidência\" pressionado");
        try {
          UserGithubInfo userGithubInfo = UserGithubInfo.get();
          userGithubInfo.setRepoName(txtRepoName.getText());
          if (EvidenceGenerator.geraEvidenciaDeUsoDoGit(userGithubInfo)) {
            JOptionPane.showMessageDialog(contentPane, "Evidência gerada.");
          } else {
            JOptionPane.showMessageDialog(contentPane, "Problemas na geração de evidências.");
          }
        } catch (Exception ex) {
          LOGGER.error(ex.getMessage(), ex);
          JOptionPane.showMessageDialog(contentPane,
              "Problemas na geração de evidências. Exception: " + ex.getMessage());
        }
      }
    });
    btnGenerateEvidence.setBounds(156, 189, 300, 23);
    contentPane.add(btnGenerateEvidence);
  }

  private void botaoCriarProjetoRemoto() {
    LOGGER.debug("FormForTesting.botaoCriarProjetoRemoto()");
    JButton btnCreateProject = new JButton("Criar projeto no repo remoto");
    btnCreateProject.addActionListener(new ActionListener() {
      @SneakyThrows
      public void actionPerformed(ActionEvent e) {
        LOGGER.info("Botão \"Criar projeto no repo remoto\" pressionado");
        try {
          UserGithubInfo userGithubInfo = UserGithubInfo.get();
          userGithubInfo.setRepoName(txtRepoName.getText());
          UserGithubProjectCreator.criaProjetoInicialNoGithub(userGithubInfo);
          JOptionPane.showMessageDialog(contentPane, "Projeto criado.");
        } catch (Exception ex) {
          LOGGER.error(ex.getMessage(), ex);
          JOptionPane.showMessageDialog(contentPane,
              "Problemas na criação do projeto. Exception: " + ex.getMessage());
        }

      }
    });
    btnCreateProject.setBounds(156, 216, 300, 23);
    contentPane.add(btnCreateProject);
  }

  private void botaoLerDados() {
    LOGGER.debug("FormForTesting.botaoLerDados()");
    JButton btnLerArq = new JButton("Ler conteúdo do arquivo " + UserGithubInfo.PROPERTIES_FILE);
    btnLerArq.addActionListener(new ActionListener() {
      @SneakyThrows
      public void actionPerformed(ActionEvent e) {
        LOGGER.info(
            "Botão \"Ler conteúdo do arquivo " + UserGithubInfo.PROPERTIES_FILE + "\" pressionado");
        if (!new File(UserGithubInfo.PROPERTIES_FILE).exists()) {
          JOptionPane.showMessageDialog(contentPane,
              "Arquivo inexistente, valide as suas credenciais primeiro!", "Erro",
              JOptionPane.ERROR_MESSAGE);
          return;
        }
        UserGithubInfo userGithubInfo = UserGithubInfo
            .get(Util.readPropertiesFromFile(UserGithubInfo.PROPERTIES_FILE));
        String output =
            "\tlogin=" + userGithubInfo.getUsername() + "\n" +
                "\tpassword=" + userGithubInfo.getPassword() + "\n" +
                "\tgithubName=" + userGithubInfo.getGithubName() + "\n" +
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
    LOGGER
        .debug("FormForTesting.botaoSalvarDados()");
    JButton btnConfirm = new JButton("Salvar Dados em " + UserGithubInfo.PROPERTIES_FILE);
    btnConfirm.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        LOGGER.info("Botão \"Salvar Dados em...\" pressioando");
        try {
          UserGithubInfo.reset();
          Util.savePropertiesToFile(
              UserGithubInfo.get(txtUsername.getText(), String.valueOf(passwordField.getPassword()))
                  .toProperties(), UserGithubInfo.PROPERTIES_FILE);
          JOptionPane.showMessageDialog(contentPane, "Dados salvos!");
        } catch (Exception ex) {
          LOGGER.error(ex.getMessage(), ex);
          JOptionPane.showMessageDialog(contentPane,
              "Problemas ao tentar salvar dados. Exception: " + ex.getMessage());
        }
      }
    });
    btnConfirm.setBounds(156, 270, 300, 23);
    contentPane.add(btnConfirm);
  }

  private void botaoValidacao() {
    LOGGER.debug("FormForTesting.botaoValidacao()");
    JButton btnValidacao = new JButton("Validar Credenciais");
    btnValidacao.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        LOGGER.info("Botão \"Validar Credenciais\" pressionado");
        UserGithubInfo.reset();
        if (UserGithubInfo.validarCredenciais(txtUsername.getText(),
            String.valueOf(passwordField.getPassword()))) {
          try {
            UserGithubInfo userGithubInfo = UserGithubInfo.get();
            String output =
                "\tlogin=" + userGithubInfo.getUsername() + "\n"
                + "\tpassword=" + userGithubInfo.getPassword() + "\n"
                + "\tgithubName=" + userGithubInfo.getGithubName() + "\n"
                + "\tgithubRepoNameFullPath=" + userGithubInfo.getRepoNameFullPath() + "\n"
                + "\tgithubEmail=" + userGithubInfo.getGithubEmail() + "\n"
                + "\trepoName=" + userGithubInfo.getRepoName();
            JOptionPane.showMessageDialog(contentPane, "Credenciais válidas!\n\n" + output);
          } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
            JOptionPane.showMessageDialog(contentPane,
                "Credenciais válidas, mas houve problemas ao ler propriedades. Exception: " + ex
                    .getMessage());
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
    LOGGER.debug("FormForTesting.botaoGerarHook()");
    JButton btn = new JButton("Gerar hook(arquivo pre-commit)");
    btn.addActionListener(new ActionListener() {
      @SneakyThrows
      public void actionPerformed(ActionEvent e) {
        LOGGER.info("Botão \"Gerar hook...\" pressionado");
        if (GenerateHook.generateHook()) {
          JOptionPane.showMessageDialog(contentPane, "Arquivo gerado.");
        } else {
          JOptionPane.showMessageDialog(contentPane, "Problemas na geração do hook.");
        }
      }
    });
    btn.setBounds(156, 324, 300, 23);
    contentPane.add(btn);
  }

  private void areaEscolherProjetos() {
    JLabel lblEscolherProjetos = new JLabel("Escolher Projetos");
    lblEscolherProjetos.setHorizontalAlignment(SwingConstants.LEFT);
    lblEscolherProjetos.setBounds(35, 361, 134, 14);
    contentPane.add(lblEscolherProjetos);

    JLabel lblPastaPai = new JLabel("Caminho da Pasta Pai");
    lblPastaPai.setBounds(174, 361, 388, 14);
    contentPane.add(lblPastaPai);

    JList<GitDir> list = new JList<GitDir>();
    contentPane.add(list);
    list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
    list.setBounds(156, 386, 559, 204);

    JButton btnSelect = new JButton("Selecionar");
    btnSelect.addActionListener(new ActionListener() {
      @SneakyThrows
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();

          Util.addGitFiles(file);
          // Build the model from previous Git Path using GitDir Class
          Util.buildDefaultListModel();

          // Set a JList containing GitDir's
          list.setModel(Util.getListModel());

          LOGGER.info(Util.getListModel().toString()); //

          // Use a GitDirListRenderer to renderer list cells
          list.setCellRenderer(new GitDirListRenderer());
          list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

          lblPastaPai.setText("Selecionado: " + file.getCanonicalPath());
        } else {
          lblPastaPai.setText("Erro");
        }
      }
    });
    btnSelect.setBounds(609, 357, 96, 23);
    contentPane.add(btnSelect);

    // Add a mouse listener to handle changing selection
    list.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent event) {
        JList<GitDir> list =
            (JList<GitDir>) event.getSource();

        // Get index of item clicked
        int index = list.locationToIndex(event.getPoint());
        GitDir item = (GitDir) list.getModel()
            .getElementAt(index);

        // Toggle selected state
        item.setSelected(!item.isSelected());

        // Repaint cell
        list.repaint(list.getCellBounds(index, index));

        LOGGER.info(item + " " + item.isSelected());
      }
    });
  }
}
