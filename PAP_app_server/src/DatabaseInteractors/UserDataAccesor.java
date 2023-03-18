package DatabaseInteractors;

import java.util.Hashtable;

public interface UserDataAccesor {
    Hashtable<String, String> get_user_data(int user_id);

    Hashtable<String, String> get_user_data(String username);

    Hashtable<String, String> get_user_data_using_email(String email);

}