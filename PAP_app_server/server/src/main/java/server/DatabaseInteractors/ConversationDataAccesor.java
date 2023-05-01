package server.DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import server.ConnectionPool.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;

public class ConversationDataAccesor implements DataAccesorInterface {
    final static String TABLENAME = ConversationDatabsaeInformation.CONVERSATION_TABLE.value();

    public static Hashtable<String, String> getData(int id) {
        return getData(ConversationDatabsaeInformation.ID_COLUMN.value(), id);
    }

    public static Hashtable<String, String> getData(String column_name, String column_value) {
        String preparedStatement = String.format("Select * from %s where %s=?", TABLENAME, column_name);
        return getQuerryResult(preparedStatement, column_value);
    }

    public static Hashtable<String, String> getData(String column_name, int column_value) {
        String preparedStatement = String.format("Select * from %s where %s=?", TABLENAME, column_name);
        return getQuerryResult(preparedStatement, column_value);
    }

    public static ArrayList<Integer> getUsersInConversation(int conversation_id) {
        ArrayList<Integer> users = new ArrayList<Integer>();
        ResultSet result = null;

        String preparedStatement = String.format("Select %s from %s where %s = ?",
                ConversationDatabsaeInformation.USER_COLUMN.value(),
                UserDatabaseInformation.USER_CONVERSATION_TABLE.value(),
                UserDatabaseInformation.CONVERSATION_ID_COLUMN.value());
        Connection connection = ConnectionPool.getConnection();
        try {

            var statement = connection.prepareStatement(preparedStatement);
            statement.setInt(1, conversation_id);
            result = statement.executeQuery();
            while (result.next()) {
                users.add(result.getInt(1));
            }
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        ConnectionPool.releaseConnection(connection);
        return users;
    }

    public static ArrayList<Integer> getMessagesInConversation(int conversation_id) {
        ArrayList<Integer> messages = new ArrayList<Integer>();
        ResultSet result = null;

        String preparedStatement = String.format("Select %s from %s where %s = ?",
                MessagesDatabaseInformation.ID_COLUMN.value(),
                MessagesDatabaseInformation.MESSAGES_TABLE.value(),
                MessagesDatabaseInformation.CONVERSATION_COLUMN.value());
        Connection connection = ConnectionPool.getConnection();
        try {

            var statement = connection.prepareStatement(preparedStatement);
            statement.setInt(1, conversation_id);
            result = statement.executeQuery();
            while (result.next()) {
                messages.add(result.getInt(1));
            }
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        ConnectionPool.releaseConnection(connection);
        return messages;
    }

    public static int getLatestConversation() {
        ResultSet result = null;
        int id = 0;

        String preparedStatement = String.format("Select MAX(%s) from %s",
                ConversationDatabsaeInformation.ID_COLUMN.value(),
                TABLENAME);
        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(preparedStatement);
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

    protected static Hashtable<String, String> processResultToFullData(ResultSet result) {
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

    private static Hashtable<String, String> getQuerryResult(String preparedStatement, int value) {
        Hashtable<String, String> userData = new Hashtable<String, String>();

        ResultSet result = null;
        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(preparedStatement);
            connection.commit();
            statement.setInt(1, value);
            result = statement.executeQuery();
            userData = processResultToFullData(result);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);
        return userData;
    }

    private static Hashtable<String, String> getQuerryResult(String preparedStatement, String value) {
        Hashtable<String, String> userData = new Hashtable<String, String>();

        ResultSet result = null;
        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(preparedStatement);
            connection.commit();
            statement.setString(1, value);
            result = statement.executeQuery();
            userData = processResultToFullData(result);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);
        return userData;
    }
}
