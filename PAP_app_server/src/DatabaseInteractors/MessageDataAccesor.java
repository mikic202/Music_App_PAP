package DatabaseInteractors;

import java.util.Hashtable;

public class MessageDataAccesor implements DataAccesorInterface {
    public static Hashtable<String, String> get_data(int id) {
        Hashtable<String, String> data = new Hashtable<String, String>();
        return data;
    }

    public static Hashtable<String, String> get_data(String column_name, String column_value) {
        Hashtable<String, String> data = new Hashtable<String, String>();
        return data;
    }

    public static Hashtable<String, String> get_data(String column_name, int column_value) {
        Hashtable<String, String> data = new Hashtable<String, String>();
        return data;
    }

    public static String get_message_text(int message_id) {
        String text = new String();
        return text;
    }
}
