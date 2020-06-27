package io.github.flauberjp.forms;

import io.github.flauberjp.Util;
import lombok.SneakyThrows;
import io.github.flauberjp.forms.model.GitDir;
import io.github.flauberjp.forms.model.GitDirListRenderer;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JList;

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
    scrollPane.setBounds(10, 64, 561, 286);
    contentPane.add(scrollPane);
    
    JList<GitDir> list = new JList<GitDir>();
    scrollPane.setViewportView(list);

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
        //Build the model from previous Git Path using GitDir Class
          Util.buildDefaultListModel();
          
       // Set a JList containing GitDir's
          list.setModel(Util.getListModel());
          
          System.out.println(Util.getListModel()); //
          
       // Use a GitDirListRenderer to renderer list cells
          list.setCellRenderer(new GitDirListRenderer());
          list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
          lblPastaPai.setText("Selecionado: " + file.getCanonicalPath());
        } else {
          lblPastaPai.setText("Erro");
        }
        
      }
    });
    btnSelect.setBounds(464, 35, 96, 23);
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
          
          System.out.println(item + " " +item.isSelected());
       }
    });
    


  }
}
