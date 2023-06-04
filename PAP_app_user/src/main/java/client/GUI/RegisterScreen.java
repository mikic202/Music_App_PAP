/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package client.GUI;

import com.formdev.flatlaf.FlatDarkLaf;

import client.GUI.guiListeners.RegisterListener;
import client.ServerConnector.ServerConnector;

public class RegisterScreen extends javax.swing.JFrame {

	ServerConnector serverConnector;

	public RegisterScreen(ServerConnector serverConnector) {
		FlatDarkLaf.setup();
		this.serverConnector = serverConnector;
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		RegisterScreen = new javax.swing.JPanel();
		Logo = new javax.swing.JLabel();
		CreateAccount = new javax.swing.JLabel();
		Email = new javax.swing.JLabel();
		EmailField = new javax.swing.JTextField();
		Nickname = new javax.swing.JLabel();
		Password = new javax.swing.JLabel();
		ConfirmPassword = new javax.swing.JLabel();
		ConfirmPasswordField = new javax.swing.JPasswordField();
		PasswordField = new javax.swing.JPasswordField();
		NicknameField = new javax.swing.JTextField();
		RegisterButton = new javax.swing.JButton();
		HaveAccountText = new javax.swing.JLabel();
		LoginButton = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);

		Logo.setFont(new java.awt.Font("Segoe UI Semilight", 3, 48)); // NOI18N
		Logo.setText("ConnectedByMusic");

		CreateAccount.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
		CreateAccount.setText("Create account:");

		Email.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		Email.setText("E-mail address:");

		Nickname.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		Nickname.setText("Nickname:");

		Password.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		Password.setText("Password:");

		ConfirmPassword.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
		ConfirmPassword.setText("Confirm password:");

		ConfirmPasswordField.setText("");
		ConfirmPasswordField.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ConfirmPasswordFieldActionPerformed(evt);
			}
		});

		PasswordField.setText("");
		PasswordField.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				PasswordFieldActionPerformed(evt);
			}
		});

		RegisterButton.setLabel("Register");
		RegisterButton.setMaximumSize(new java.awt.Dimension(73, 23));
		RegisterButton.setMinimumSize(new java.awt.Dimension(73, 23));
		RegisterButton.setName(""); // NOI18N
		RegisterButton.addActionListener(new RegisterListener(EmailField, NicknameField, PasswordField,
				ConfirmPasswordField, serverConnector, CreateAccount));

		HaveAccountText.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
		HaveAccountText.setText("Already have account?");

		LoginButton.setText("Log in");
		LoginButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				LoginButtonActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout RegisterScreenLayout = new javax.swing.GroupLayout(RegisterScreen);
		RegisterScreen.setLayout(RegisterScreenLayout);
		RegisterScreenLayout.setHorizontalGroup(RegisterScreenLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(RegisterScreenLayout.createSequentialGroup().addGroup(RegisterScreenLayout
						.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(RegisterScreenLayout
								.createSequentialGroup().addGap(63, 63, 63).addGroup(RegisterScreenLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(RegisterScreenLayout.createSequentialGroup().addComponent(Email)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(EmailField, javax.swing.GroupLayout.PREFERRED_SIZE, 210,
														javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegisterScreenLayout
												.createSequentialGroup().addComponent(Nickname)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(NicknameField, javax.swing.GroupLayout.PREFERRED_SIZE,
														210, javax.swing.GroupLayout.PREFERRED_SIZE))
										.addGroup(RegisterScreenLayout.createSequentialGroup()
												.addComponent(ConfirmPassword)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(ConfirmPasswordField))
										.addGroup(RegisterScreenLayout.createSequentialGroup().addComponent(Password)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE,
														210, javax.swing.GroupLayout.PREFERRED_SIZE)))
								.addGap(26, 26, 26))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegisterScreenLayout
								.createSequentialGroup().addGap(119, 119, 119)
								.addGroup(RegisterScreenLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
												RegisterScreenLayout.createSequentialGroup().addComponent(CreateAccount)
														.addGap(134, 134, 134))
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegisterScreenLayout
												.createSequentialGroup()
												.addComponent(RegisterButton, javax.swing.GroupLayout.PREFERRED_SIZE,
														198, javax.swing.GroupLayout.PREFERRED_SIZE)
												.addGap(94, 94, 94))))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								RegisterScreenLayout.createSequentialGroup().addContainerGap().addComponent(Logo,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)))
						.addContainerGap())
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, RegisterScreenLayout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE)
						.addGroup(RegisterScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(HaveAccountText)
								.addGroup(RegisterScreenLayout.createSequentialGroup().addGap(6, 6, 6).addComponent(
										LoginButton, javax.swing.GroupLayout.PREFERRED_SIZE, 84,
										javax.swing.GroupLayout.PREFERRED_SIZE)))
						.addGap(143, 143, 143)));
		RegisterScreenLayout.setVerticalGroup(RegisterScreenLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(RegisterScreenLayout.createSequentialGroup().addContainerGap().addComponent(Logo)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(CreateAccount)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(RegisterScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(Email).addComponent(EmailField, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(RegisterScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(Nickname).addComponent(NicknameField,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(RegisterScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(Password))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(RegisterScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(ConfirmPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(ConfirmPassword))
						.addGap(18, 18, 18)
						.addComponent(RegisterButton, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(HaveAccountText)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(LoginButton)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(RegisterScreen,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				RegisterScreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void PasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_PasswordFieldActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_PasswordFieldActionPerformed

	private void ConfirmPasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_ConfirmPasswordFieldActionPerformed
		// TODO add your handling code here:
	}// GEN-LAST:event_ConfirmPasswordFieldActionPerformed

	private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_LoginButtonActionPerformed
		LoginScreen loginScreen = new LoginScreen(serverConnector);
		loginScreen.setVisible(true);
		this.dispose();
	}// GEN-LAST:event_LoginButtonActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(RegisterScreen.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(RegisterScreen.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(RegisterScreen.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(RegisterScreen.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {

			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel ConfirmPassword;
	private javax.swing.JPasswordField ConfirmPasswordField;
	private javax.swing.JLabel CreateAccount;
	private javax.swing.JLabel Email;
	private javax.swing.JTextField EmailField;
	private javax.swing.JLabel HaveAccountText;
	private javax.swing.JButton LoginButton;
	private javax.swing.JLabel Logo;
	private javax.swing.JLabel Nickname;
	private javax.swing.JTextField NicknameField;
	private javax.swing.JLabel Password;
	private javax.swing.JPasswordField PasswordField;
	private javax.swing.JButton RegisterButton;
	private javax.swing.JPanel RegisterScreen;
	// End of variables declaration//GEN-END:variables
}
