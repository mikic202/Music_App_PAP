package Chat;

import java.util.Hashtable;

import java.util.ArrayList;

import DatabaseInteractors.ConversationDataAccesor;
import DatabaseInteractors.ConversationDataSetter;
import DatabaseInteractors.ConversationDatabsaeInformation;
import DatabaseInteractors.MessageDataAccesor;
import DatabaseInteractors.MessageDataSetter;
import DatabaseInteractors.UserDataAccesor;
import DatabaseInteractors.UserDataSetter;

import java.sql.Timestamp;

import org.json.JSONObject;

public class Chat {
    public ArrayList<Hashtable<String, String>> proces_requests(RequestTypes req_type, JSONObject request) {
        return _generate_response(req_type, request);
    }

    private ArrayList<Hashtable<String, String>> _get_messeges_in_conversation(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<Integer> messages = ConversationDataAccesor
                .get_mesages_in_conversation(request.getInt("conversation_id"));
        for (Integer message_id : messages) {
            response.add(MessageDataAccesor.get_data(message_id));
        }
        return response;
    }

    private ArrayList<Hashtable<String, String>> _get_users_conversations(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        ArrayList<Integer> conversations = UserDataAccesor.get_user_conversations(request.getInt("user_id"));
        for (Integer conversation_id : conversations) {
            response.add(ConversationDataAccesor.get_data(conversation_id));
        }
        return response;
    }

    private ArrayList<Hashtable<String, String>> _send_message(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> data = new Hashtable<>();
        data.put("sender", request.getString("sender_id"));
        data.put("conversation", request.getString("conversation_id"));
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        data.put("send_date", timestamp.toString());
        data.put("text", request.getString("text"));
        ;
        int added_msg = MessageDataSetter.add_data(data);
        response.add(
                MessageDataAccesor.get_data(added_msg));
        return response;
    }

    private ArrayList<Hashtable<String, String>> _create_conversation(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<Hashtable<String, String>>();
        String name = request.getString("name");
        int number_of_users = request.getJSONArray("users").length();
        int new_conversation = _add_conversation(name, number_of_users);
        ArrayList<Integer> users = new ArrayList<>();
        for (int i = 0; i < request.getJSONArray("users").length(); i++) {
            users.add(request.getJSONArray("users").getInt(i));
        }
        _add_users_to_conversation(users, new_conversation);
        response.add(ConversationDataAccesor.get_data(new_conversation));
        return response;
    }

    private int _add_conversation(String name, int number_of_users) {
        Hashtable<String, String> data = new Hashtable<>();
        data.put("name", name);
        data.put("number_of_users", Integer.toString(number_of_users));
        return ConversationDataSetter.add_data(data);
    }

    private void _add_users_to_conversation(ArrayList<Integer> users, int conversation_id) {
        for (int user : users) {
            UserDataSetter.add_user_to_conversation(conversation_id, user);
        }
    }

    private ArrayList<Hashtable<String, String>> _process_add_users_to_conversation(JSONObject request) {
        ArrayList<Hashtable<String, String>> response = new ArrayList<>();
        int number_of_users = request.getJSONArray("users").length();
        int conversation_id = request.getInt("conversation_id");
        ArrayList<Integer> users = new ArrayList<>();
        for (int i = 0; i < request.getJSONArray("users").length(); i++) {
            users.add(request.getJSONArray("users").getInt(i));
        }
        _add_users_to_conversation(users, conversation_id);
        Hashtable<String, String> previous_data = ConversationDataAccesor.get_data(conversation_id);
        previous_data.put(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value(),
                previous_data.get(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value()) + number_of_users);
        ConversationDataSetter.set_data(conversation_id, previous_data);
        return response;
    }

    private ArrayList<Hashtable<String, String>> _generate_response(RequestTypes req_type, JSONObject request) {
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
            case ADD_USER_TO_CONVERSATION:
                response = _process_add_users_to_conversation(request);
                break;
        }
        return response;
    }

}
