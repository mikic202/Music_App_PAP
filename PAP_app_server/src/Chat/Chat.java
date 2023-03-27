package Chat;

import java.util.Hashtable;

import java.util.ArrayList;

import DatabaseInteractors.ConversationDataAccesor;
import DatabaseInteractors.ConversationDataSetter;
import DatabaseInteractors.MessageDataAccesor;
import DatabaseInteractors.MessageDataSetter;
import DatabaseInteractors.MessagesDatabaseInformation;
import DatabaseInteractors.UserDataAccesor;
import DatabaseInteractors.UserDataSetter;

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
        Hashtable<String, String> data = new Hashtable<>();
        data.put("sender", decoded_req.get(0));
        data.put("conversation", decoded_req.get(1));
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        data.put("send_date", timestamp.toString());
        data.put("text", decoded_req.get(2));
        // String time_stamp_string = timestamp.toString().split("\\.")[0];
        int added_msg = MessageDataSetter.add_data(data);
        response.add(
                MessageDataAccesor.get_data(added_msg));
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

    private ArrayList<Hashtable<String, String>> _create_conversation(String request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<String> processed_request = _process_add_conversation_request(request);
        String name = processed_request.get(0);
        int number_of_users = processed_request.size() - 1;
        int new_conversation = _add_conversation(name, number_of_users);
        processed_request.remove(0);
        _add_users_to_conversation(processed_request, new_conversation);
        response.add(ConversationDataAccesor.get_data(new_conversation));
        return response;
    }

    private int _add_conversation(String name, int number_of_users) {
        Hashtable<String, String> data = new Hashtable<>();
        data.put("name", name);
        data.put("number_of_users", Integer.toString(number_of_users));
        return ConversationDataSetter.add_data(data);
    }

    private ArrayList<String> _process_add_conversation_request(String request) {
        ArrayList<String> processed_request = new ArrayList<>();
        String[] split_req = request.split(";");
        for (String item : split_req) {
            processed_request.add(item);
        }
        return processed_request;
    }

    private void _add_users_to_conversation(ArrayList<String> users, int conversation_id) {
        for (String user : users) {
            UserDataSetter.add_user_to_conversation(conversation_id, Integer.parseInt(user));
        }
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
            case CREATE_CONVERSATION:
                response = _create_conversation(request);
                break;
        }
        return response;
    }

}
