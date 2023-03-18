package DatabaseInteractors;

import java.util.Hashtable;

public class MessageDataAccesor implements DataAccesorInterface {
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

    public static Hashtable<String, String> get_data_with_email(String email) {
        Hashtable<String, String> usr_data = new Hashtable<String, String>();
        return usr_data;
    }
}
