package Chat;

import java.util.Hashtable;
import java.util.ArrayList;

public class Chat {
    public ArrayList<Hashtable<String, String>> proces_requests(Hashtable<RequestTypes, String> request) {
        return generate_response(request);
    }

    public void send_user_information() {
    }

    private ArrayList<Hashtable<String, String>> generate_response(Hashtable<RequestTypes, String> request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        return response;
    }

}
