package server.DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class UserDataAccesor implements DataAccesorInterface {

    final static String TABLENAME = DatabseInformation.USER_TABLE.value();

    public static Hashtable<String, String> getData(int user_id) {

        String query = String.format("Select * from %s where user_id='%d'", TABLENAME, user_id);

        return getQuerryResult(query);
    }

    public static Hashtable<String, String> getData(String column_name, String column_value) {

        String query = String.format("Select * from %s where %s='%s'", TABLENAME, column_name, column_value);

        return getQuerryResult(query);
    }

    public static Hashtable<String, String> getData(String column_name, int column_value) {

        String query = String.format("Select * from %s where %s='%d'", TABLENAME, column_name, column_value);

        return getQuerryResult(query);
    }

    public static Hashtable<String, String> getDataWithEmail(String email) {
        String query = String.format("Select * from %s where email='%s'", TABLENAME, email);
        return getQuerryResult(query);
    }

    public static Hashtable<String, String> getDataWithName(String nickname) {
        String query = String.format("Select * from %s where email='%s'", TABLENAME, nickname);
        return getQuerryResult(query);
    }

    public static ArrayList<Integer> getUserConversations(int id) {
        ArrayList<Integer> conversations = new ArrayList<Integer>();

        String querry = String.format("Select %s from %s where %s='%s'",
                UserDatabaseInformation.CONVERSATION_ID_COLUMN.value(),
                UserDatabaseInformation.USER_CONVERSATION_TABLE.value(), UserDatabaseInformation.ID_COLUMN.value(), id);

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

    public static int getLatestUser() {
        ResultSet result = null;
        int id = 0;

        String querry = String.format("Select MAX(%s) from %s",
                UserDatabaseInformation.ID_COLUMN.value(),
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

    private static Hashtable<String, String> getQuerryResult(String querry) {
        Hashtable<String, String> user_data = new Hashtable<String, String>();

        ResultSet result = null;

        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);
            user_data = processResultToFullData(result);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return user_data;
    }
}
