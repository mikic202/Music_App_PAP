package Chat;

import java.util.Hashtable;

import java.util.ArrayList;

import DatabaseInteractors.ConversationDataAccesor;
import DatabaseInteractors.MessageDataAccesor;
import DatabaseInteractors.MessageDataSetter;
import DatabaseInteractors.UserDataAccesor;
import java.sql.Timestamp;

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

    private ArrayList<Hashtable<String, String>> _send_message(String request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<String> decoded_req = _decode_send_message_request(request);
        // data.get("sender"), data.get("conversation"), data.get("send_date"),
        // data.get("text")
        Hashtable<String, String> data = new Hashtable<>();
        data.put("sender", decoded_req.get(0));
        data.put("conversation", decoded_req.get(1));
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        data.put("send_date", timestamp.toString());
        data.put("text", decoded_req.get(2));
        MessageDataSetter.add_data(data);
        return response;
    }

    private ArrayList<String> _decode_send_message_request(String request) {
        String[] split_req = request.split(";", 3);
        ArrayList<String> decoded_req = new ArrayList<>();
        decoded_req.add(split_req[0]);
        decoded_req.add(split_req[1]);
        decoded_req.add(split_req[2]);
        return decoded_req;
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
            case SEND_MESSAGE:
                response = _send_message(request);
                break;
        }
        return response;
    }

}
