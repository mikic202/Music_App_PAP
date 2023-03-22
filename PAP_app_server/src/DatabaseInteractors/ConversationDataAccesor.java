package DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ConversationDataAccesor implements DataAccesorInterface {
    final static String TABLENAME = "";

    public static Hashtable<String, String> get_data(int id) {
        return get_data(null, null);
    }

    public static Hashtable<String, String> get_data(String column_name, String column_value) {
        String querry = String.format("Select * from %s where %s='%s'", TABLENAME, column_name, column_value);
        return get_querry_result(querry);
    }

    public static Hashtable<String, String> get_data(String column_name, int column_value) {
        return get_data(column_name, String.format("%d", column_value));
    }

    public static ArrayList<Integer> get_users_in_conversation() {
        ArrayList<Integer> users = new ArrayList<Integer>();
        return users;
    }

    public static ArrayList<Integer> get_mesages_in_conversation() {
        ArrayList<Integer> messages = new ArrayList<Integer>();
        return messages;
    }

    protected static Hashtable<String, String> process_result_to_full_data(ResultSet result) {
        Hashtable<String, String> umessage_data = new Hashtable<String, String>();

        try {
            while (result.next()) {
                umessage_data.put("ID", result.getString(1));
                umessage_data.put("sender", result.getString(2));
                umessage_data.put("conversation", result.getString(3));
                umessage_data.put("send_date", result.getString(4));
                umessage_data.put("text", result.getString(5));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return umessage_data;
    }

    protected static Hashtable<String, String> get_querry_result(String querry) {
        Hashtable<String, String> umessage_data = new Hashtable<String, String>();

        ResultSet result = null;

        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);
            umessage_data = process_result_to_full_data(result);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return umessage_data;
    }
}
