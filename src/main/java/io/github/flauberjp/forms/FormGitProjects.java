package io.github.flauberjp.forms;

import io.github.flauberjp.Util;
import lombok.SneakyThrows;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

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
    setBounds(100, 100, 608, 413);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
    contentPane.setLayout(null);

    JLabel lblEscolherProjetos = new JLabel("Escolher Projetos");
    lblEscolherProjetos.setHorizontalAlignment(SwingConstants.TRAILING);
    lblEscolherProjetos.setBounds(221, 14, 134, 14);
    contentPane.add(lblEscolherProjetos);

    JLabel lblPastaPai = new JLabel("Caminho da Pasta Pai");
    lblPastaPai.setBounds(29, 39, 388, 14);
    contentPane.add(lblPastaPai);
	  
    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setBounds(10, 64, 572, 286);
    contentPane.add(scrollPane);
    
    JTextArea textArea = new JTextArea();
    scrollPane.setViewportView(textArea);

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
          
          Util.getGitDir().forEach(item->textArea.append(item + "\n"));
        
          lblPastaPai.setText("Selecionado: " + file.getCanonicalPath());
        } else {
          lblPastaPai.setText("Erro");
        }
        
      }
    });
    btnSelect.setBounds(464, 35, 96, 23);
    contentPane.add(btnSelect);
    
   
    
    


  }
}
