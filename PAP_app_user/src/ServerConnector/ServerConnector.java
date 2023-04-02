package ServerConnector;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerConnector {
    Socket socket;
    BufferedReader input;
    OutputStreamWriter output;

    public ServerConnector(Socket socket) {
        this.socket = socket;
        try {
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new OutputStreamWriter(
                    socket.getOutputStream(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public JSONObject send_request(JSONObject request) {
        String response = "";
        try {
            output.write(request.toString());
            while (!input.ready()) {
            }
            response = input.readLine();
        } catch (Exception e) {
            System.out.println(e);
        }
        return new JSONObject(response);
    }
}
