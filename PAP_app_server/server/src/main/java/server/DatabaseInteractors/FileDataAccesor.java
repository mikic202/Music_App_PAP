package server.DatabaseInteractors;

import java.util.ArrayList;
import java.util.Hashtable;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class FileDataAccesor implements DataAccesorInterface {

    static final String TABLENAME = FileDatabsaeInformation.FILE_TABLE.value();

    public static Hashtable<String, String> get_data(int id) {
        return get_data(FileDatabsaeInformation.ID_COLUMN.value(), id);
    }

    public static Hashtable<String, String> get_data(String column_name, String column_value) {
        String querry = String.format("Select * from %s where %s='%s'", TABLENAME, column_name, column_value);
        return get_querry_result(querry);
    }

    public static Hashtable<String, String> get_data(String column_name, int column_value) {
        return get_data(column_name, String.format("%d", column_value));
    }

    public static ArrayList<Integer> get_user_files(int user_id) {
        ArrayList<Integer> files = new ArrayList<>();
        ResultSet result = null;

        String querry = String.format("Select %s from %s where %s='%s'", FileDatabsaeInformation.ID_COLUMN.value(),
                TABLENAME, FileDatabsaeInformation.USER_COLUMN.value(), user_id);
        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);
            while (result.next()) {
                files.add(result.getInt(1));
            }
            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return files;
    }

    private static Hashtable<String, String> process_result_to_full_data(ResultSet result) {
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

    private static Hashtable<String, String> get_querry_result(String querry) {
        Hashtable<String, String> user_data = new Hashtable<String, String>();

        ResultSet result = null;

        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(querry);

            result = stat.executeQuery(request);
            user_data = process_result_to_full_data(result);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return user_data;
    }
}
