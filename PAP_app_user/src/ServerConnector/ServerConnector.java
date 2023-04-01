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

    public JSONObject send_request(JSONObject request) {
        String response = "";
        try {
            socket = new Socket("localhost", 8000);
            input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            output = new OutputStreamWriter(
                    socket.getOutputStream(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            output.write(request.toString());
            while (!input.ready()) {
            }
            response = input.readLine();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        return new JSONObject(response);
    }
}
