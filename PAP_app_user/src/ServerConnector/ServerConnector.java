package ServerConnector;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerConnector {
    Socket socket;
    BufferedReader input;
    OutputStream output;

    public ServerConnector(Socket socket) {
        this.socket = socket;
        try {
            input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            output = new DataOutputStream(this.socket.getOutputStream());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public JSONObject send_request(JSONObject request) {
        String response = "";
        try {
            output.write((request.toString() + "\n").getBytes());
            response = input.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return new JSONObject(response);
    }
}
