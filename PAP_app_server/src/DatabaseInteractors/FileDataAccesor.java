package DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

public class FileDataAccesor implements DataAccesorInterface {
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

    public static ArrayList<Integer> get_user_files(int user_id) {
        ArrayList<Integer> usr_data = new ArrayList<Integer>();
        return usr_data;
    }
}
