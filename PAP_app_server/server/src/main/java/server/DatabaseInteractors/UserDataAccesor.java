package server.DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import server.ConnectionPool.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;

public class UserDataAccesor implements DataAccesorInterface {

    final static String TABLENAME = DatabseInformation.USER_TABLE.value();

    public static Hashtable<String, String> getData(int userId) {
        return getData(UserDatabaseInformation.ID_COLUMN.value(), userId);
    }

    public static Hashtable<String, String> getData(String columnName, String columnValue) {

        String preparedStatement = String.format("Select * from %s where %s=?", TABLENAME, columnName);

        return getQuerryResult(preparedStatement, columnValue);
    }

    public static Hashtable<String, String> getData(String columnName, int columnValue) {

        String preparedStatement = String.format("Select * from %s where %s=?", TABLENAME, columnName);

        return getQuerryResult(preparedStatement, columnValue);
    }

    public static Hashtable<String, String> getDataWithEmail(String email) {
        return getData(UserDatabaseInformation.EMAIL_COLUMN.value(), email);
    }

    public static Hashtable<String, String> getDataWithName(String username) {
        return getData(UserDatabaseInformation.USERNAME_COLUMN.value(), username);
    }

    public static ArrayList<Integer> getUserConversations(int id) {
        ArrayList<Integer> conversations = new ArrayList<Integer>();

        String preparedStatement = String.format("Select %s from %s where %s=?",
                UserDatabaseInformation.CONVERSATION_ID_COLUMN.value(),
                UserDatabaseInformation.USER_CONVERSATION_TABLE.value(), UserDatabaseInformation.ID_COLUMN.value());

        ResultSet result;
        Connection connection = ConnectionPool.getConnection();
        try {

            var statement = connection.prepareStatement(preparedStatement);
            statement.setInt(1, id);
            result = statement.executeQuery();

            while (result.next()) {
                conversations.add(result.getInt(1));
            }
            connection.commit();

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

        String preparedStatement = String.format("Select MAX(%s) from %s",
                UserDatabaseInformation.ID_COLUMN.value(),
                TABLENAME);

        Connection connection = ConnectionPool.getConnection();

        try {

            var statement = connection.prepareStatement(preparedStatement);
            result = statement.executeQuery();
            while (result.next()) {
                id = result.getInt(1);
            }
            connection.commit();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        ConnectionPool.releaseConnection(connection);
        return id;
    }

    private static Hashtable<String, String> processResultToFullData(ResultSet result) {
        Hashtable<String, String> userData = new Hashtable<String, String>();

        try {
            while (result.next()) {
                userData.put("ID", result.getString(1));
                userData.put("username", result.getString(2));
                userData.put("email", result.getString(3));
                userData.put("password", result.getString(4));
                userData.put("profile_picture", result.getString(5));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return userData;
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
            connection.commit();
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
            connection.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);
        return userData;
    }
}
