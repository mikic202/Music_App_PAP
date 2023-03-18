package DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

public class ConversationDataAccesor implements DataAccesorInterface {
    public static Hashtable<String, String> get_data(int id) {
        Hashtable<String, String> usr_data = new Hashtable<String, String>();
        return usr_data;
    }

    public static Hashtable<String, String> get_data(String column_name, String column_value) {
        Hashtable<String, String> usr_data = new Hashtable<String, String>();
        return usr_data;
    }

    public static Hashtable<String, String> get_data(String column_name, int column_value) {
        Hashtable<String, String> usr_data = new Hashtable<String, String>();
        return usr_data;
    }

    public static ArrayList<Integer> get_users_in_conversation(String email) {
        ArrayList<Integer> users = new ArrayList<Integer>();
        return users;
    }
}
