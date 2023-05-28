package client.GUI.guiWorkers;

import javax.swing.SwingWorker;
import client.ServerConnector.ServerConnector;
import client.GUI.LoginScreen;

public class LoggingWorker extends SwingWorker<Boolean, Void> {

    LoggingWorker(LoginScreen loggingScreen, ServerConnector serverConnector) {
        this.loggingScreen = loggingScreen;
        this.serverConnector = serverConnector;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'doInBackground'");
    }

    private LoginScreen loggingScreen;
    private ServerConnector serverConnector;
}

// /*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
// to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit
// this template
// */
// package client.GUI;

// import java.net.Socket;

// import org.json.JSONObject;

// import client.GUI.listeners.LoggingActionListener;
// import client.ServerConnector.ServerConnector;
// import client.login_and_account_accessors.LoginAccessors;

// /**
// *
// * @author Adam
// */
// public class LoginScreen extends javax.swing.JFrame {

// /**
// * Creates new form LoginScreen
// */
// private ServerConnector server_connector;
// private LoginAccessors logging_accesor;
// private Socket socket;

// public LoginScreen() {
// // try {
// // server_connector = new ServerConnector(new Socket("144.91.114.89", 8000));
// // } catch (Exception e) {
// // System.out.println(e);
// // System.exit(-1);
// // }
// try {
// socket = new Socket("144.91.114.89", 8000);
// this.server_connector = new ServerConnector(socket);
// } catch (Exception e) {
// System.out.println(e);
// }
// logging_accesor = new LoginAccessors(this.server_connector);
// initComponents();
// }

// private void succesfull_logging(JSONObject user_info) {
// user_info.remove("outcome");
// this.dispose();
// MainScreen mainScreen = new MainScreen(server_connector, user_info);
// mainScreen.setVisible(true);

// }

// public void try_logging() {
// if (!jTextField1.getText().equals("") &&
// !String.valueOf(jPasswordField1.getPassword()).equals("")) {
// JSONObject response =
// logging_accesor.send_user_login_data(jTextField1.getText(),
// jPasswordField1.getPassword());
// if (response.getJSONObject("value").getBoolean("outcome")) {
// response.getJSONObject("value").put("email", jTextField1.getText());
// succesfull_logging(response.getJSONObject("value"));
// }
// }
// }
