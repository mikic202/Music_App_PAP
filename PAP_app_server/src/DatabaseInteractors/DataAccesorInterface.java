package DatabaseInteractors;

import java.util.Hashtable;

public interface DataAccesorInterface {
    Hashtable<String, String> get_data(int id);

    Hashtable<String, String> get_data(String column_name, String column_value);

    Hashtable<String, String> get_data(String column_name, int column_value);

}