package server.DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MessageDataAccesor implements DataAccesorInterface {

    final static String TABLENAME = MessagesDatabaseInformation.MESSAGES_TABLE.value();

    public static Hashtable<String, String> getData(int message_id) {
        String querry = String.format("Select * from %s where %s='%s'",
                TABLENAME, MessagesDatabaseInformation.ID_COLUMN.value(),
                message_id);
        return getQuerryResult(querry);
    }

    public static Hashtable<String, String> getData(String column_name, String column_value) {

        String query = String.format("Select * from %s where %s='%s'", TABLENAME, column_name, column_value);

        return getQuerryResult(query);
    }

    public static Hashtable<String, String> getData(String column_name, int column_value) {

        String query = String.format("Select * from %s where %s='%d'", TABLENAME, column_name, column_value);

        return getQuerryResult(query);
    }

    public static String getMessageText(int message_id) {
        return getData(message_id).get("text");
    }

    public static int getLatestMessage() {
        ResultSet result = null;
        int id = 0;

        String querry = String.format("Select MAX(%s) from %s",
                MessagesDatabaseInformation.ID_COLUMN.value(),
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

    public static ArrayList<Hashtable<String, String>> getMessagesOlderThanGiven(int latest_message,
            int conversation_id) {
        String querry = String.format("Select * from %s where %s='%d' AND %s>'%d' ", TABLENAME,
                MessagesDatabaseInformation.CONVERSATION_COLUMN.value(), conversation_id,
                MessagesDatabaseInformation.ID_COLUMN.value(),
                latest_message);

        ArrayList<Hashtable<String, String>> messages_data = new ArrayList<>();

        ResultSet result = null;

        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();

            result = stat.executeQuery(querry);

            while (result.next()) {
                Hashtable<String, String> msg_data = new Hashtable<>();
                msg_data.put("ID", result.getString(1));
                msg_data.put("sender", result.getString(2));
                msg_data.put("conversation", result.getString(3));
                msg_data.put("send_date", result.getString(4));
                msg_data.put("text", result.getString(5));
                messages_data.add(msg_data);
            }

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return messages_data;
    }

    protected static Hashtable<String, String> processResultToFullData(ResultSet result) {
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

    protected static Hashtable<String, String> getQuerryResult(String querry) {
        Hashtable<String, String> umessage_data = new Hashtable<String, String>();

        ResultSet result = null;

        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);
            umessage_data = processResultToFullData(result);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return umessage_data;
    }
}
