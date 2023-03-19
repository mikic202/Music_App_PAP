package DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class UserDataAccesor implements DataAccesorInterface {

    final static String TABLENAME = "";

    public static Hashtable<String, String> get_data(int user_id) {

        Hashtable<String, String> user_data = new Hashtable<String, String>();

        String query = String.format("Select * from %s where user_id='%d'", TABLENAME, user_id);

        ResultSet result = get_querryResult(query);
        try {
            while (result.next()) {
                user_data.put("ID", result.getString(1));
                user_data.put("username", result.getString(2));
                user_data.put("email", result.getString(3));
                user_data.put("password", result.getString(4));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return user_data;
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

    public static ArrayList<Integer> get_user_conversations(int id) {
        ArrayList<Integer> conversations = new ArrayList<Integer>();
        return conversations;
    }

    private static ResultSet get_querryResult(String querry) {

        ResultSet result = null;

        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return result;
    }
}
