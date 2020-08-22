package io.github.flauberjp.forms;

import static io.github.flauberjp.util.MyLogger.LOGGER;

import io.github.flauberjp.ApplyConfigurationThread;
import io.github.flauberjp.GitProjectManipulatorThread;
import io.github.flauberjp.UserGithubInfo;
import io.github.flauberjp.Version;
import io.github.flauberjp.model.GitDir;
import io.github.flauberjp.model.GitDirList;
import io.github.flauberjp.util.Util;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
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
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.Image;

import io.github.flauberjp.forms.component.ProjetosGitDetectadosTableComponent;
import java.awt.Toolkit;
import java.awt.Canvas;
import javax.swing.ImageIcon;
import java.net.URI;

public class FormMain extends JFrame {
  private static FormMain frame;
  private JPanel contentPane;
  private JTextField txtUsername;
  private JPasswordField passwordField;
  private JProgressBar progressBar;

  /**
   * Create the frame.
   * @throws MalformedURLException 
   */
  public FormMain() throws MalformedURLException {
    LOGGER.debug("FormMain.FormMain()");
    configuraFormMain();

    geraPainelPrincipal();

    botaoConfigurar();

    selecionadorDeProjetosGit();
    
    configuraProgressBar();
    
    geraGithubLink();
    
    inicializaForm();
  }

  private void configuraFormMain() {
	setTitle("My git usage evidences");
    setResizable(false);
  	setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\my-git-usage-evidences\\src\\main\\resources\\images\\my-git-usage-evidences.png"));
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
    setBounds(100, 100, 900, 530);
    contentPane = new JPanel();
    contentPane.setToolTipText("");
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblTitle = new JLabel("Credenciais do Github");
    lblTitle.setToolTipText(Version.getVersionFromPom());
    lblTitle.setBounds(12, 11, 488, 14);
    contentPane.add(lblTitle);

    JLabel lblUsername = new JLabel("Usu\u00E1rio:");
    lblUsername.setBounds(12, 40, 49, 14);
    contentPane.add(lblUsername);

    ButtonGroup G = new ButtonGroup();

    txtUsername = new JTextField();
    txtUsername.setToolTipText("Username do seu usuário no Github, ex: passw0rd");
    txtUsername.setText("");
    txtUsername.setColumns(10);
    txtUsername.setBounds(71, 37, 308, 20);
    contentPane.add(txtUsername);

    JLabel lblPassword = new JLabel("Password:");
    lblPassword.setBounds(401, 40, 66, 14);
    contentPane.add(lblPassword);

    passwordField = new JPasswordField();
    passwordField.setToolTipText("Password do seu usuário no Github, ex: passw0rd");
    passwordField.setText("");
    passwordField.setBounds(477, 37, 308, 20);
    contentPane.add(passwordField);

    JLabel lblProjects = new JLabel(
        "Projetos Git que terão o seu uso local do Git registrado no Github");
    lblProjects.setBounds(12, 78, 599, 14);
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
          LOGGER.debug("Lista de projetos git selecionados: " + GitDirList.getSelectedGitDirStringList()
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
    btn.setBounds(695, 450, 170, 23);
    contentPane.add(btn);
  }
 
  private void selecionadorDeProjetosGit() {
    LOGGER.debug("FormMain.selecionadorDeProjetosGit()");
    String label = "Selecione a Pasta Pai dos Projetos Git";
    JLabel lblPastaPai = new JLabel("---");
    lblPastaPai.setBounds(24, 122, 507, 14);
    contentPane.add(lblPastaPai);
    
    JPanel tablePanel = new JPanel();
    tablePanel.setBackground(Color.RED);
    tablePanel.setBounds(13, 162, 666, 311);
    contentPane.add(tablePanel);
    tablePanel.setLayout(new GridLayout(0, 1, 0, 0));
    
    ProjetosGitDetectadosTableComponent tabelaPanel = new ProjetosGitDetectadosTableComponent();
    tablePanel.add(tabelaPanel);

    JButton btnSelect = new JButton("Selecionar Pasta");
    btnSelect.setToolTipText("Selecione a pasta pai dos projetos Git para análise");
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
          GitProjectManipulatorThread.executaProcessamento(progressBar, contentPane, file,
              tabelaPanel, txtUsername.getText());
        } else {
          lblPastaPai.setText(label);
        }

      }
    });
    btnSelect.setBounds(695, 99, 170, 23);
    contentPane.add(btnSelect);

    JSeparator separator = new JSeparator();
    separator.setBounds(12, 25, 815, 2);
    contentPane.add(separator);
  }
  
  private void configuraProgressBar() {
    progressBar = new JProgressBar();
    progressBar.setString("");
    progressBar.setStringPainted(true);
    progressBar.setBounds(303, 137, 308, 14);
    progressBar.setVisible(false);
    contentPane.add(progressBar);
    
    JLabel lblPastaPai = new JLabel("Pasta Pai dos Projetos Git selecionada:");
    lblPastaPai.setBounds(12, 103, 439, 14);
    contentPane.add(lblPastaPai);
    
    JLabel lblProjetosGit = new JLabel("Projetos Git detectados:");
    lblProjetosGit.setToolTipText("Lista de projetos Git para seleção");
    lblProjetosGit.setBounds(12, 140, 452, 14);
    contentPane.add(lblProjetosGit);
    
    JSeparator separator = new JSeparator();
    separator.setBounds(12, 92, 850, 2);
    contentPane.add(separator);
    
    JSeparator separator_1 = new JSeparator();
    separator_1.setBounds(12, 154, 850, 2);
    contentPane.add(separator_1);
  }

  private void geraGithubLink() {
	JLabel lblGithubLink = new JLabel("");
	lblGithubLink.addMouseListener(new MouseAdapter() {
		@Override
		@SneakyThrows
		public void mouseClicked(MouseEvent e) {
			if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
			    Desktop.getDesktop().browse(new URI("https://github.com/flauberjp/my-git-usage-evidences"));
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}
		@Override
		public void mouseExited(MouseEvent e) {
			setCursor(null);
		}
	});
	lblGithubLink.setIcon(new ImageIcon("C:\\my-git-usage-evidences\\src\\main\\resources\\icon\\GitHub-Mark-32px.png"));
	lblGithubLink.setFont(new Font("Tahoma", Font.BOLD, 11));
	lblGithubLink.setToolTipText("Acesse o repositório do my-git-usage-evidences no Github. Se gostou do programa, não esqueça de deixar uma STAR.");
	lblGithubLink.setBounds(833, 11, 32, 32);
	contentPane.add(lblGithubLink);	
  }
}
