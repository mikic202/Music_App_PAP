package server.DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import server.ConnectionPool.ConnectionPool;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

public class UserDataAccesor implements DataAccesorInterface {

    final static String TABLENAME = DatabseInformation.USER_TABLE.value();

    public static Hashtable<String, String> getData(int user_id) {
        return getData(UserDatabaseInformation.ID_COLUMN.value(), user_id);
    }

    public static Hashtable<String, String> getData(String column_name, String column_value) {

        String prepared_statement = String.format("Select * from %s where %s=?", TABLENAME, column_name);

        return getQuerryResult(prepared_statement, column_value);
    }

    public static Hashtable<String, String> getData(String column_name, int column_value) {

        String prepared_statement = String.format("Select * from %s where %s=?", TABLENAME, column_name);

        return getQuerryResult(prepared_statement, column_value);
    }

    public static Hashtable<String, String> getDataWithEmail(String email) {
        return getData(UserDatabaseInformation.EMAIL_COLUMN.value(), email);
    }

    public static Hashtable<String, String> getDataWithName(String username) {
        return getData(UserDatabaseInformation.USERNAME_COLUMN.value(), username);
    }

    public static ArrayList<Integer> getUserConversations(int id) {
        ArrayList<Integer> conversations = new ArrayList<Integer>();

        String querry = String.format("Select %s from %s where %s='%s'",
                UserDatabaseInformation.CONVERSATION_ID_COLUMN.value(),
                UserDatabaseInformation.USER_CONVERSATION_TABLE.value(), UserDatabaseInformation.ID_COLUMN.value(), id);

        ResultSet result;
        Connection connection = ConnectionPool.getConnection();
        try {

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);

            while (result.next()) {
                conversations.add(result.getInt(1));
            }

        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        ConnectionPool.releaseConnection(connection);

        return conversations;
    }

    public static int getLatestUser() {
        ResultSet result = null;
        int id = 0;

        String querry = String.format("Select MAX(%s) from %s",
                UserDatabaseInformation.ID_COLUMN.value(),
                TABLENAME);

        Connection connection = ConnectionPool.getConnection();

        try {

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);
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

    private static Hashtable<String, String> processResultToFullData(ResultSet result) {
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

    private static Hashtable<String, String> getQuerryResult(String prepared_statement, int value) {
        Hashtable<String, String> user_data = new Hashtable<String, String>();

        ResultSet result = null;
        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(prepared_statement);
            connection.commit();
            statement.setInt(1, value);
            result = statement.executeQuery();
            user_data = processResultToFullData(result);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);
        return user_data;
    }

    private static Hashtable<String, String> getQuerryResult(String prepared_statement, String value) {
        Hashtable<String, String> user_data = new Hashtable<String, String>();

        ResultSet result = null;
        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(prepared_statement);
            // connection.commit();
            statement.setString(1, value);
            result = statement.executeQuery();
            user_data = processResultToFullData(result);

        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);
        return user_data;
    }
}
