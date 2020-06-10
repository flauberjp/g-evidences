package io.github.flauberjp.forms;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class FormGitProjects extends JFrame {

  private JPanel contentPane;
  private JTextField textField;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          FormGitProjects frame = new FormGitProjects();
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
  public FormGitProjects() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 525, 370);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblEscolherProjetos = new JLabel("Escolher Projetos");
    lblEscolherProjetos.setHorizontalAlignment(SwingConstants.TRAILING);
    lblEscolherProjetos.setBounds(166, 11, 134, 14);
    contentPane.add(lblEscolherProjetos);

    JLabel lblPastaPai = new JLabel("Caminho da Pasta Pai");
    lblPastaPai.setBounds(29, 39, 330, 14);
    contentPane.add(lblPastaPai);

    JButton btnSelect = new JButton("Selecionar");
    btnSelect.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
          File file = fileChooser.getSelectedFile();
          lblPastaPai.setText("Selecionado " + file.getAbsolutePath());
        } else {
          lblPastaPai.setText("Erro");
        }

      }
    });
    btnSelect.setBounds(387, 35, 96, 23);
    contentPane.add(btnSelect);


  }
}
