package io.github.flauberjp.forms;

import io.github.flauberjp.UserGithubInfo;
import io.github.flauberjp.Util;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;

public class FormSignUp extends JFrame {

	private JPanel contentPane;
	private JTextField txtRepoName;
	private JTextField txtUsername;
	private JPasswordField passwordField;
	private JLabel lblGithubName;
	private JTextField txtGithubName;
	private JLabel lblGithubEmail;
	private JTextField txtGithubEmail;

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
		
		txtRepoName = new JTextField();
		txtRepoName.setText("e.g. \"xxxx\" or \"contributions-cal\"");
		txtRepoName.setBounds(156, 66, 293, 20);
		contentPane.add(txtRepoName);
		txtRepoName.setColumns(10);
		
		JLabel lblRepoName = new JLabel("Nome do Reposit\u00F3rio");
		lblRepoName.setBounds(35, 69, 111, 14);
		contentPane.add(lblRepoName);
		
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
		
		lblGithubName = new JLabel("Nome do Github");
		lblGithubName.setBounds(35, 198, 111, 14);
		contentPane.add(lblGithubName);
		
		txtGithubName = new JTextField();
		txtGithubName.setText("e.g. My github name");
		txtGithubName.setColumns(10);
		txtGithubName.setBounds(156, 195, 293, 20);
		contentPane.add(txtGithubName);
		
		lblGithubEmail = new JLabel("Email do Github");
		lblGithubEmail.setBounds(35, 239, 111, 14);
		contentPane.add(lblGithubEmail);
		
		txtGithubEmail = new JTextField();
		txtGithubEmail.setText("e.g. My github email");
		txtGithubEmail.setColumns(10);
		txtGithubEmail.setBounds(156, 236, 293, 20);
		contentPane.add(txtGithubEmail);
		
		JButton btnConfirm = new JButton("Confirmar");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String repoName = txtRepoName.getText();
					String username = txtUsername.getText();
					String password = new String(passwordField.getPassword());
					String githubname = txtGithubName.getText();
					String githubemail = txtGithubEmail.getText();

					String [] args = new String[] {
							"repoName", repoName,
							"login", username,
							"password", password,
							"githubName", githubname,
							"githubEmail", githubemail
					};
					UserGithubInfo user = UserGithubInfo.get(Util.createProperties(args));

					Util.WriteObjectToFile(user);
			        System.out.println(LocalDateTime.now());
			        
			        JOptionPane.showMessageDialog (null, "Cadastrado com Sucesso");
			        
			        FormGitProjects formGitProjects = new FormGitProjects();
			        formGitProjects.setVisible(true);
			        
					dispose(); //fecha a janela
				}
				catch(Exception erro){
					JOptionPane.showMessageDialog(null,erro.getMessage());
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
