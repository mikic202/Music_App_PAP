package Chat;

import java.util.Hashtable;
import java.util.ArrayList;

import DatabaseInteractors.ConversationDataAccesor;
import DatabaseInteractors.MessageDataAccesor;
import DatabaseInteractors.UserDataAccesor;

public class Chat {
    public ArrayList<Hashtable<String, String>> proces_requests(RequestTypes req_type, String request) {
        return _generate_response(req_type, request);
    }

    private ArrayList<Hashtable<String, String>> _get_messeges_in_conversation(String request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<Integer> messages = ConversationDataAccesor.get_mesages_in_conversation(Integer.parseInt(request));
        for (Integer message_id : messages) {
            response.add(MessageDataAccesor.get_data(message_id));
        }
        return response;
    }

    private ArrayList<Hashtable<String, String>> _get_users_conversations(String request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<Integer> conversations = UserDataAccesor.get_user_conversations(Integer.parseInt(request));
        for (Integer conversation_id : conversations) {
            response.add(ConversationDataAccesor.get_data(conversation_id));
        }
        return response;
    }

    private ArrayList<Hashtable<String, String>> _generate_response(RequestTypes req_type, String request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        switch (req_type) {
            case GET_MESSAGES:
                response = _get_messeges_in_conversation(request);
                break;
            case GET_USERS_CONVERSATIONS:
                response = _get_users_conversations(request);
                break;
        }
        return response;
    }

}
