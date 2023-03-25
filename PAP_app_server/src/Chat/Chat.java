package Chat;

import java.util.Hashtable;
import java.util.ArrayList;

public class Chat {
    public ArrayList<Hashtable<String, String>> proces_requests(RequestTypes req_type, String request) {
        return generate_response(req_type, request);
    }

    private ArrayList<Hashtable<String, String>> _get_messeges_in_conversation(String request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        return response;
    }

    private ArrayList<Hashtable<String, String>> generate_response(RequestTypes req_type, String request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        switch (req_type) {
            case GET_MESSAGES:
                response = _get_messeges_in_conversation(request);
                break;
        }
        return response;
    }

}
