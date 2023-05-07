package server.DatabaseInteractors;

import java.util.Hashtable;

public interface DataAccesorInterface {
    static Hashtable<String, String> getData(int id) {
        Hashtable<String, String> data = new Hashtable<String, String>();
        return data;
    }

    static Hashtable<String, String> getData(String column_name, String column_value) {
        Hashtable<String, String> data = new Hashtable<String, String>();
        return data;
    }

    static Hashtable<String, String> getData(String column_name, int column_value) {
        Hashtable<String, String> data = new Hashtable<String, String>();
        return data;
    }

}