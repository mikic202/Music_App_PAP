package server.DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import server.ConnectionPool.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;

public class MessageDataAccesor implements DataAccesorInterface {

    final static String TABLENAME = MessagesDatabaseInformation.MESSAGES_TABLE.value();

    public static Hashtable<String, String> getData(int message_id) {
        return getData(MessagesDatabaseInformation.ID_COLUMN.value(), message_id);
    }

    public static Hashtable<String, String> getData(String columnName, String columnValue) {

        String preparedStatement = String.format("Select * from %s where %s=?", TABLENAME, columnName);

        return getQuerryResult(preparedStatement, columnValue);
    }

    public static Hashtable<String, String> getData(String columnName, int columnValue) {

        String preparedStatement = String.format("Select * from %s where %s=?", TABLENAME, columnName);

        return getQuerryResult(preparedStatement, columnValue);
    }

    public static String getMessageText(int message_id) {
        return getData(message_id).get("text");
    }

    public static int getLatestMessage(int conversationId) {
        ResultSet result = null;
        int id = 0;

        String preparedStatement = String.format("Select MAX(%s) from %s where %s=?",
                MessagesDatabaseInformation.ID_COLUMN.value(),
                TABLENAME,
                MessagesDatabaseInformation.SENDER_COLUMN.value());

        Connection connection = ConnectionPool.getConnection();

        try {

            var statement = connection.prepareStatement(preparedStatement);
            statement.setInt(1, conversationId);
            result = statement.executeQuery();
            while (result.next()) {
                id = result.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        ConnectionPool.releaseConnection(connection);
        return id;
    }

    public static ArrayList<Hashtable<String, String>> getMessagesOlderThanGiven(int latest_message,
            int conversation_id) {
        String preparedStatement = String.format("Select * from %s where %s=? AND %s>? ", TABLENAME,
                MessagesDatabaseInformation.CONVERSATION_COLUMN.value(),
                MessagesDatabaseInformation.ID_COLUMN.value());

        ArrayList<Hashtable<String, String>> messages_data = new ArrayList<>();

        ResultSet result = null;

        try {

            Connection connection = ConnectionPool.getConnection();

            var statement = connection.prepareStatement(preparedStatement);
            connection.commit();
            statement.setInt(1, conversation_id);
            statement.setInt(2, latest_message);
            result = statement.executeQuery();

            while (result.next()) {
                Hashtable<String, String> msg_data = new Hashtable<>();
                msg_data.put("ID", result.getString(1));
                msg_data.put("sender", result.getString(2));
                msg_data.put("conversation", result.getString(3));
                msg_data.put("send_date", result.getString(4));
                msg_data.put("text", result.getString(5));
                msg_data.put("is_image", result.getString(6));
                messages_data.add(msg_data);
            }

            ConnectionPool.releaseConnection(connection);
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
                umessage_data.put("is_image", result.getString(6));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return umessage_data;
    }

    protected static Hashtable<String, String> getQuerryResult(String preparedStatement, String value) {
        Hashtable<String, String> messagesData = new Hashtable<String, String>();

        ResultSet result = null;
        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(preparedStatement);
            connection.commit();
            statement.setString(1, value);
            result = statement.executeQuery();
            messagesData = processResultToFullData(result);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);
        return messagesData;
    }

    protected static Hashtable<String, String> getQuerryResult(String preparedStatement, int value) {
        Hashtable<String, String> messagesData = new Hashtable<String, String>();

        ResultSet result = null;
        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(preparedStatement);
            connection.commit();
            statement.setInt(1, value);
            result = statement.executeQuery();
            messagesData = processResultToFullData(result);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);
        return messagesData;
    }
}
