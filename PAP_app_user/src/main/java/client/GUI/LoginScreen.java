/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package client.GUI;

import com.formdev.flatlaf.FlatDarkLaf;

import client.GUI.guiListeners.LoggingListener;
import client.GUI.guiListeners.RetrievePasswordListener;

public class LoginScreen extends javax.swing.JFrame {

        boolean dark = true;
        boolean light = false;

        public LoginScreen() {
                FlatDarkLaf.setup();
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

                LoginScreen = new javax.swing.JPanel();
                Logo = new javax.swing.JLabel();
                ForgotPass = new javax.swing.JLabel();
                Email = new javax.swing.JLabel();
                Password = new javax.swing.JLabel();
                PasswordField = new javax.swing.JPasswordField();
                LoginButton = new javax.swing.JButton();
                InvalidLogin = new javax.swing.JLabel();
                RegisterText = new javax.swing.JLabel();
                Login1 = new javax.swing.JLabel();
                EmailField1 = new javax.swing.JTextField();
                RecoverButton = new javax.swing.JButton();
                RegisterButton = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
                setResizable(false);

                Logo.setFont(new java.awt.Font("Segoe UI Semilight", 3, 48)); // NOI18N
                Logo.setText("ConnectedByMusic");

                ForgotPass.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
                ForgotPass.setText("Forgot password?");

                Email.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                Email.setText("E-mail address:");

                Password.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
                Password.setText("Password:");

                PasswordField.setText("jPasswordField1");
                PasswordField.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                PasswordFieldActionPerformed(evt);
                        }
                });

                LoginButton.setLabel("Log in");
                LoginButton.setMaximumSize(new java.awt.Dimension(73, 23));
                LoginButton.setMinimumSize(new java.awt.Dimension(73, 23));
                LoginButton.addActionListener(new LoggingListener(this, EmailField1, PasswordField));

                InvalidLogin.setText("Inavild email or password!");

                RegisterText.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
                RegisterText.setText("Do not have an account?");

                Login1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
                Login1.setText("Login:");

                RecoverButton.setText("Recover password");
                RecoverButton.setMaximumSize(new java.awt.Dimension(73, 23));
                RecoverButton.setMinimumSize(new java.awt.Dimension(73, 23));
                RecoverButton.addActionListener(new RetrievePasswordListener(ForgotPass, EmailField1));

                RegisterButton.setLabel("Register");
                RegisterButton.setMaximumSize(new java.awt.Dimension(73, 23));
                RegisterButton.setMinimumSize(new java.awt.Dimension(73, 23));
                RegisterButton.setName(""); // NOI18N
                RegisterButton.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                RegisterButtonActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout LoginScreenLayout = new javax.swing.GroupLayout(LoginScreen);
                LoginScreen.setLayout(LoginScreenLayout);
                LoginScreenLayout.setHorizontalGroup(
                                LoginScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(LoginScreenLayout.createSequentialGroup()
                                                                .addGroup(LoginScreenLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(LoginScreenLayout
                                                                                                .createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addComponent(Logo,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                412,
                                                                                                                Short.MAX_VALUE))
                                                                                .addGroup(LoginScreenLayout
                                                                                                .createSequentialGroup()
                                                                                                .addGroup(LoginScreenLayout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addGroup(LoginScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGap(148, 148,
                                                                                                                                                148)
                                                                                                                                .addComponent(RegisterText))
                                                                                                                .addGroup(LoginScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGap(71, 71, 71)
                                                                                                                                .addGroup(LoginScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                false)
                                                                                                                                                .addGroup(LoginScreenLayout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addComponent(Password)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                                .addComponent(PasswordField,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                200,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                .addGroup(LoginScreenLayout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addComponent(Email)
                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                .addComponent(EmailField1,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                200,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                                                                .addGroup(LoginScreenLayout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGap(116, 116,
                                                                                                                                                116)
                                                                                                                                .addGroup(LoginScreenLayout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                false)
                                                                                                                                                .addComponent(LoginButton,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(RegisterButton,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                191,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addGroup(LoginScreenLayout
                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                .addGap(20, 20, 20)
                                                                                                                                                                .addComponent(ForgotPass)))))
                                                                                                .addGap(0, 0, Short.MAX_VALUE)))
                                                                .addContainerGap())
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginScreenLayout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(InvalidLogin,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                139,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(140, 140, 140))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, LoginScreenLayout
                                                                .createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addComponent(RecoverButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                189,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, Short.MAX_VALUE))
                                                .addGroup(LoginScreenLayout.createSequentialGroup()
                                                                .addGap(186, 186, 186)
                                                                .addComponent(Login1)
                                                                .addGap(0, 0, Short.MAX_VALUE)));
                LoginScreenLayout.setVerticalGroup(
                                LoginScreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(LoginScreenLayout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(Logo)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(Login1)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(LoginScreenLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(Email,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                33, Short.MAX_VALUE)
                                                                                .addComponent(EmailField1,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(LoginScreenLayout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(Password,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                33,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(PasswordField,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(InvalidLogin)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(LoginButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(RegisterText,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(RegisterButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(ForgotPass)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(RecoverButton,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(11, 11, 11)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(LoginScreen, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(LoginScreen, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE));

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void RegisterButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_RegisterButtonActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_RegisterButtonActionPerformed

        private void RecoverButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_RecoverButtonActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_RecoverButtonActionPerformed

        private void LoginButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_LoginButtonActionPerformed
                String writtenEmail = EmailField1.getText();
                char[] writtenPassword = PasswordField.getPassword();
                /*
                 * tu sprawdzamy czy serwer zwrócił true(jeśli są w bazie), false(jeśli nie)
                 */
                this.dispose();
        }// GEN-LAST:event_LoginButtonActionPerformed

        private void PasswordFieldActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_PasswordFieldActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_PasswordFieldActionPerformed

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
                // (optional) ">
                /*
                 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
                 * look and feel.
                 * For details see
                 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                                        .getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (InstantiationException ex) {
                        java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (IllegalAccessException ex) {
                        java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                        java.util.logging.Logger.getLogger(LoginScreen.class.getName()).log(
                                        java.util.logging.Level.SEVERE, null,
                                        ex);
                }
                // </editor-fold>
                // </editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                                new LoginScreen().setVisible(true);
                        }
                });
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JLabel Email;
        private javax.swing.JTextField EmailField1;
        private javax.swing.JLabel ForgotPass;
        private javax.swing.JLabel InvalidLogin;
        private javax.swing.JLabel Login1;
        private javax.swing.JButton LoginButton;
        private javax.swing.JPanel LoginScreen;
        private javax.swing.JLabel Logo;
        private javax.swing.JLabel Password;
        private javax.swing.JPasswordField PasswordField;
        private javax.swing.JButton RecoverButton;
        private javax.swing.JButton RegisterButton;
        private javax.swing.JLabel RegisterText;
        // End of variables declaration//GEN-END:variables
}
