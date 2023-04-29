package server.DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class ConversationDataAccesor implements DataAccesorInterface {
    final static String TABLENAME = ConversationDatabsaeInformation.CONVERSATION_TABLE.value();

    public static Hashtable<String, String> getData(int id) {
        return getData(ConversationDatabsaeInformation.ID_COLUMN.value(), id);
    }

    public static Hashtable<String, String> getData(String column_name, String column_value) {
        String querry = String.format("Select * from %s where %s='%s'", TABLENAME, column_name, column_value);
        return get_querry_result(querry);
    }

    public static Hashtable<String, String> getData(String column_name, int column_value) {
        return getData(column_name, String.format("%d", column_value));
    }

    public static ArrayList<Integer> get_users_in_conversation(int conversation_id) {
        ArrayList<Integer> users = new ArrayList<Integer>();
        ResultSet result = null;

        String querry = String.format("Select %s from %s where %s = '%s'",
                ConversationDatabsaeInformation.USER_COLUMN.value(),
                UserDatabaseInformation.USER_CONVERSATION_TABLE.value(),
                UserDatabaseInformation.CONVERSATION_ID_COLUMN.value(), conversation_id);

        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);
            while (result.next()) {
                users.add(result.getInt(1));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return users;
    }

    public static ArrayList<Integer> get_mesages_in_conversation(int conversation_id) {
        ArrayList<Integer> messages = new ArrayList<Integer>();
        ResultSet result = null;

        String querry = String.format("Select %s from %s where %s = '%s'",
                MessagesDatabaseInformation.ID_COLUMN.value(),
                MessagesDatabaseInformation.MESSAGES_TABLE.value(),
                MessagesDatabaseInformation.CONVERSATION_COLUMN.value(), conversation_id);

        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);
            while (result.next()) {
                messages.add(result.getInt(1));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return messages;
    }

    public static int get_latest_conversation() {
        ResultSet result = null;
        int id = 0;

        String querry = String.format("Select MAX(%s) from %s",
                ConversationDatabsaeInformation.ID_COLUMN.value(),
                TABLENAME);

        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);
            while (result.next()) {
                id = result.getInt(1);
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return id;
    }

    protected static Hashtable<String, String> process_result_to_full_data(ResultSet result) {
        Hashtable<String, String> umessage_data = new Hashtable<String, String>();

        try {
            while (result.next()) {
                umessage_data.put("ID", result.getString(1));
                umessage_data.put("name", result.getString(2));
                umessage_data.put("number_of_users", result.getString(3));
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
