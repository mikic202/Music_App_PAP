package Chat;

import java.util.Hashtable;
import java.util.ArrayList;

import DatabaseInteractors.ConversationDataAccesor;
import DatabaseInteractors.MessageDataAccesor;

public class Chat {
    public ArrayList<Hashtable<String, String>> proces_requests(RequestTypes req_type, String request) {
        return generate_response(req_type, request);
    }

    private ArrayList<Hashtable<String, String>> _get_messeges_in_conversation(String request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<Integer> messages = ConversationDataAccesor.get_mesages_in_conversation(Integer.parseInt(request));
        for (Integer message_id : messages) {
            response.add(MessageDataAccesor.get_data(message_id));
        }
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
