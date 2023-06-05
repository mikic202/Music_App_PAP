package server.DatabaseInteractors;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ListIterator;

import server.ClientHandlers.Client;
import server.ConnectionPool.ConnectionPool;

import java.sql.Connection;
import java.sql.ResultSet;

public class FileDataAccesor implements DataAccesorInterface {

    static final String TABLENAME = FileDatabsaeInformation.FILE_TABLE.value();

    public static ArrayList<Hashtable<String, String>> getData(int id) {
        return getData(FileDatabsaeInformation.ID_COLUMN.value(), id);
    }

    public static ArrayList<Hashtable<String, String>> getData(String column_name, String column_value) {
        String preparedStatements = String.format("Select * from %s where %s=?", TABLENAME, column_name);
        return getQuerryResult(preparedStatements, column_value);
    }

    public static ArrayList<Hashtable<String, String>> getData(String column_name, int column_value) {
        String preparedStatements = String.format("Select * from %s where %s=?", TABLENAME, column_name);
        return getQuerryResult(preparedStatements, column_value);
    }

    public static Hashtable<String, Integer> getUserFiles(int userId) {
        String songIdStatement = String.format("Select * from %s where %s=?",
                TABLENAME, FileDatabsaeInformation.USER_COLUMN.value());

        ArrayList<Hashtable<String, String>> songIdData = getQuerryResult(songIdStatement, userId);
        Hashtable<String, Integer> files = new Hashtable<String, Integer>();
        System.out.println(songIdData);

        ListIterator<Hashtable<String, String>> it = songIdData.listIterator();
        
        while(it.hasNext()) {
        	Hashtable<String, String> file = it.next();

        	System.out.println(file.get("file_name"));
        	System.out.println(file.get("is_image"));
        	if(file.get("is_image").equals("1")) {
        		continue;
        	}
        	files.put(file.get("file_name"), Integer.parseInt(file.get("ID")));
        }

        if(files.isEmpty())
        {
            files.put("none", -1);
        }

        return files;
    }

    public static ArrayList<Integer> getMusicUserLitened(int userId) {
        ArrayList<Integer> files = new ArrayList<>();
        ResultSet result = null;
        Connection connection = ConnectionPool.getConnection();

        String preparedStatement = String.format("Select %s from %s where %s=?",
                FileDatabsaeInformation.ID_COLUMN.value(),
                FileDatabsaeInformation.USER_FILE_RELATION_TABLE.value(), FileDatabsaeInformation.USER_COLUMN.value());

        try {

            var statement = connection.prepareStatement(preparedStatement);
            connection.commit();
            statement.setInt(1, userId);
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

    private static ArrayList<Hashtable<String, String>> processResultToFullData(ResultSet result) {
        ArrayList<Hashtable<String, String>> ret = new ArrayList<Hashtable<String, String>>();

        try {
            while (result.next()) {
                Hashtable<String, String> user_data = new Hashtable<String, String>();
                user_data.put("ID", result.getString(1));
                user_data.put("file_name", result.getString(2));
                user_data.put("user", result.getString(3));
                user_data.put("file_path", result.getString(4));
                user_data.put("is_image", result.getString(5));
                ret.add(user_data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return ret;
    }

    private static ArrayList<Hashtable<String, String>> getQuerryResult(String preparedStatement, int value) {
    	ArrayList<Hashtable<String, String>> fileData = new ArrayList<Hashtable<String, String>>();

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

    private static ArrayList<Hashtable<String, String>> getQuerryResult(String preparedStatement, String value) {
    	ArrayList<Hashtable<String, String>> fileData = new ArrayList<Hashtable<String, String>>();

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
