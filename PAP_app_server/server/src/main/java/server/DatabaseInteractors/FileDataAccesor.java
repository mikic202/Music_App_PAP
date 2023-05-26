package server.DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import server.ConnectionPool.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;

public class FileDataAccesor implements DataAccesorInterface {

    static final String TABLENAME = FileDatabsaeInformation.FILE_TABLE.value();

    public static Hashtable<String, String> getData(int id) {
        return getData(FileDatabsaeInformation.ID_COLUMN.value(), id);
    }

    public static Hashtable<String, String> getData(String column_name, String column_value) {
        String preparedStatements = String.format("Select * from %s where %s=?", TABLENAME, column_name);
        return getQuerryResult(preparedStatements, column_value);
    }

    public static Hashtable<String, String> getData(String column_name, int column_value) {
        String preparedStatements = String.format("Select * from %s where %s=?", TABLENAME, column_name);
        return getQuerryResult(preparedStatements, column_value);
    }

    public static ArrayList<Integer> getUserFiles(int user_id) {
        ArrayList<Integer> files = new ArrayList<>();
        ResultSet result = null;
        Connection connection = ConnectionPool.getConnection();

        String preparedStatement = String.format("Select %s from %s where %s=?",
                FileDatabsaeInformation.ID_COLUMN.value(),
                TABLENAME, FileDatabsaeInformation.USER_COLUMN.value());

        try {

            var statement = connection.prepareStatement(preparedStatement);
            connection.commit();
            statement.setInt(1, user_id);
            result = statement.executeQuery();
            while (result.next()) {
                files.add(result.getInt(1));
            }
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return files;
    }

    private static Hashtable<String, String> processResultToFullData(ResultSet result) {
        Hashtable<String, String> user_data = new Hashtable<String, String>();

        try {
            while (result.next()) {
                user_data.put("ID", result.getString(1));
                user_data.put("file_name", result.getString(2));
                user_data.put("user", result.getString(3));
                user_data.put("file_path", result.getString(4));
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return user_data;
    }

    private static Hashtable<String, String> getQuerryResult(String preparedStatement, int value) {
        Hashtable<String, String> fileData = new Hashtable<String, String>();

        ResultSet result = null;
        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(preparedStatement);
            connection.commit();
            statement.setInt(1, value);
            result = statement.executeQuery();
            fileData = processResultToFullData(result);
            connection.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);
        return fileData;
    }

    private static Hashtable<String, String> getQuerryResult(String preparedStatement, String value) {
        Hashtable<String, String> fileData = new Hashtable<String, String>();

        ResultSet result = null;
        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(preparedStatement);
            connection.commit();
            statement.setString(1, value);
            result = statement.executeQuery();
            fileData = processResultToFullData(result);
            connection.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);
        return fileData;
    }
}
