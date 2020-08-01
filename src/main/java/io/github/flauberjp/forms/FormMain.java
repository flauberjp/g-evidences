package io.github.flauberjp.forms;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.ApplyConfigurationThread;
import io.github.flauberjp.GitProjectManipulatorThread;
import io.github.flauberjp.UserGithubInfo;
import io.github.flauberjp.Version;
import io.github.flauberjp.forms.model.GitDir;
import io.github.flauberjp.util.Util;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.util.Map;
import java.util.Properties;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import lombok.SneakyThrows;

public class FormMain extends JFrame {
  private static FormMain frame;
  private JPanel contentPane;
  private JTextField txtUsername;
  private JPasswordField passwordField;
  private JProgressBar progressBar;

  /**
   * Create the frame.
   */
  public FormMain() {
    LOGGER.debug("FormMain.FormMain()");
    setTitle("My git usage evidences");
    setResizable(false);

    geraPainelPrincipal();

    botaoConfigurar();

    adicionarLblProgramName();

    selecionadorDeProjetosGit();
    
    configuraProgressBar();    

    inicializaForm();
  }

  private void inicializaForm() {
    LOGGER.debug("FormMain.inicializaForm()");
    if (Util.isPropertiesFileExist(UserGithubInfo.PROPERTIES_FILE)) {
      Properties properties = Util.readPropertiesFromFile(UserGithubInfo.PROPERTIES_FILE);
      txtUsername.setText(properties.getProperty("login"));
      passwordField.setText(properties.getProperty("password"));
    }
  }

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    LOGGER.info("FormMain.main(args = {})", args);
    showFormMain();
  }

  public static void showFormMain() {
    LOGGER.debug("FormMain.showFormMain()");
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          frame = new FormMain();
          frame.setLocationRelativeTo(null);
          frame.setVisible(true);
        } catch (Exception ex) {
          LOGGER.error(ex.getMessage(), ex);
        }
      }
    });
  }


  private void geraPainelPrincipal() {
    LOGGER.debug("FormMain.geraPainelPrincipal()");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 538, 725);
    contentPane = new JPanel();
    contentPane.setToolTipText("");
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblTitle = new JLabel("Credenciais do Github");
    lblTitle.setBounds(35, 70, 203, 14);
    contentPane.add(lblTitle);

    JLabel lblUsername = new JLabel("Usu\u00E1rio:");
    lblUsername.setBounds(35, 99, 111, 14);
    contentPane.add(lblUsername);

    ButtonGroup G = new ButtonGroup();

    txtUsername = new JTextField();
    txtUsername.setToolTipText("Username do seu usuário no Github, ex: passw0rd");
    txtUsername.setText("");
    txtUsername.setColumns(10);
    txtUsername.setBounds(160, 97, 325, 20);
    contentPane.add(txtUsername);

    JLabel lblPassword = new JLabel("Password:");
    lblPassword.setBounds(35, 124, 111, 14);
    contentPane.add(lblPassword);

    passwordField = new JPasswordField();
    passwordField.setToolTipText("Password do seu usuário no Github, ex: passw0rd");
    passwordField.setText("");
    passwordField.setBounds(160, 122, 325, 20);
    contentPane.add(passwordField);

    JLabel lblProjects = new JLabel(
        "Projetos Git (não Github) que terão o seu uso local do git registrado no Github");
    lblProjects.setBounds(35, 185, 450, 14);
    contentPane.add(lblProjects);
  }

  private void setLabelUnderline(JLabel label) {
    LOGGER.debug("FormMain.setLabelUnderline(label = {})", label);
    Font font = label.getFont();
    Map attributes = font.getAttributes();
    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
    label.setFont(font.deriveFont(attributes));
  }

  private void botaoConfigurar() {
    LOGGER.debug("FormMain.botaoConfigurar()");
    JButton btn = new JButton("Aplicar configurações");
    btn.setToolTipText("Aplicar configurações nos projetos Git selecionados");
    btn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          LOGGER.info("Botão \"Aplicar configurações\" pressionando");
          LOGGER.debug("Lista de projetos git selecionados: " + Util.getSelectedGitDirStringList()
              .toString());
          if (txtUsername.getText().isEmpty() || txtUsername.getText().isBlank() ||
              String.valueOf(passwordField.getPassword()).isEmpty() || String
              .valueOf(passwordField.getPassword()).isBlank()) {
            JOptionPane.showMessageDialog(contentPane,
                "Usuário e senha do Github devem estar definidos!");
            return;
          }
          ApplyConfigurationThread.executaProcessamento(frame, progressBar, contentPane,
              txtUsername.getText(),
        	  String.valueOf(passwordField.getPassword()));
        } catch (Exception ex) {
          LOGGER.error(ex.getMessage(), ex);
          JOptionPane.showMessageDialog(contentPane,
              "Problemas ao aplicar configurações. Exception: " + ex.getMessage());
        }
      }
    });
    btn.setBounds(188, 615, 170, 23);
    contentPane.add(btn);
  }

  private void adicionarLblProgramName() {
    LOGGER.debug("FormMain.adicionarLblProgramName()");
    JLabel lblProgramName = new JLabel("my-git-usage-evidences Program");
    lblProgramName.setToolTipText(Version.getVersionFromPom());
    lblProgramName.setFont(new Font("Tahoma", Font.BOLD, 16));
    lblProgramName.setHorizontalAlignment(SwingConstants.CENTER);
    lblProgramName.setBounds(35, 11, 450, 23);
    contentPane.add(lblProgramName);
  }
  
  private void configuraProgressBar() {
    progressBar = new JProgressBar();
    progressBar.setString("");
    progressBar.setStringPainted(true);
    progressBar.setBounds(111, 169, 308, 14);
    progressBar.setVisible(false);
    contentPane.add(progressBar);
    
    JLabel lblPastaPai = new JLabel("Pasta Pai dos Projetos Git selecionada:");
    lblPastaPai.setBounds(35, 235, 223, 14);
    contentPane.add(lblPastaPai);
    
    JLabel lblProjetosGit = new JLabel("Projetos Git detectados:");
    lblProjetosGit.setToolTipText("Lista de projetos Git para seleção");
    lblProjetosGit.setBounds(35, 280, 323, 14);
    contentPane.add(lblProjetosGit);
    
    JSeparator separator = new JSeparator();
    separator.setBounds(35, 199, 396, 2);
    contentPane.add(separator);
  }

  private void selecionadorDeProjetosGit() {
    LOGGER.debug("FormMain.selecionadorDeProjetosGit()");
    String label = "Selecione a Pasta Pai dos Projetos Git";
    JLabel lblPastaPai = new JLabel("---");
    lblPastaPai.setBounds(45, 255, 440, 14);
    contentPane.add(lblPastaPai);

    JList<GitDir> list = new JList<GitDir>();

    JScrollPane scrollPane = new JScrollPane(list);
    scrollPane.setBounds(36, 297, 447, 307);
    contentPane.add(scrollPane);

    JButton btnSelect = new JButton("Selecionar a Pasta Pai dos Projetos Git para análise");
    btnSelect.setToolTipText(label);
    btnSelect.addActionListener(new ActionListener() {
      @SneakyThrows
      public void actionPerformed(ActionEvent e) {
        LOGGER.info("Botão \"Selecionar\" pressionado");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          lblPastaPai.setToolTipText(file.getCanonicalPath());
          lblPastaPai.setText(file.getCanonicalPath());
          GitProjectManipulatorThread.executaProcessamento(progressBar, contentPane, file, list);
        } else {
          lblPastaPai.setText(label);
        }

      }
    });
    btnSelect.setBounds(35, 210, 450, 23);
    contentPane.add(btnSelect);

    JSeparator separator = new JSeparator();
    separator.setBounds(35, 85, 396, 2);
    contentPane.add(separator);

    // Add a mouse listener to handle changing selection
    list.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent event) {
        JList<GitDir> list = (JList<GitDir>) event.getSource();

        // Get index of item clicked
        int index = list.locationToIndex(event.getPoint());
        GitDir item = (GitDir) list.getModel().getElementAt(index);

        // Toggle selected state
        item.setSelected(!item.isSelected());

        // Repaint cell
        list.repaint(list.getCellBounds(index, index));

        LOGGER.info(item + " " + item.isSelected());
      }
    });
  }
}
