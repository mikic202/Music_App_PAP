package DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class UserDataAccesor implements DataAccesorInterface {

    final static String TABLENAME = DatabseInformation.USER_TABLE.value();

    public static Hashtable<String, String> get_data(int user_id) {

        String query = String.format("Select * from %s where user_id='%d'", TABLENAME, user_id);

        return get_querry_result(query);
    }

    public static Hashtable<String, String> get_data(String column_name, String column_value) {

        String query = String.format("Select * from %s where %s='%s'", TABLENAME, column_name, column_value);

        return get_querry_result(query);
    }

    public static Hashtable<String, String> get_data(String column_name, int column_value) {

        String query = String.format("Select * from %s where %s='%d'", TABLENAME, column_name, column_value);

        return get_querry_result(query);
    }

    public static Hashtable<String, String> get_data_with_email(String email) {
        String query = String.format("Select * from %s where email='%s'", TABLENAME, email);
        return get_querry_result(query);
    }

    public static ArrayList<Integer> get_user_conversations(int id) {
        ArrayList<Integer> conversations = new ArrayList<Integer>();

        String querry = String.format("Select conversation_id from %s where user_id='%s'",
                DatabseInformation.USER_CONVERSATION_TABLE.value(), id);

        ResultSet result;
        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);

            while (result.next()) {
                conversations.add(result.getInt(1));
            }

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }

        return conversations;
    }

    private static Hashtable<String, String> process_result_to_full_data(ResultSet result) {
        Hashtable<String, String> user_data = new Hashtable<String, String>();

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

    private static Hashtable<String, String> get_querry_result(String querry) {
        Hashtable<String, String> user_data = new Hashtable<String, String>();

        ResultSet result = null;

        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);
            user_data = process_result_to_full_data(result);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return user_data;
    }
}
