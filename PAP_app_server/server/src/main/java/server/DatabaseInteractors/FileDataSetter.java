package server.DatabaseInteractors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Hashtable;

public class FileDataSetter implements DataSetterInterface {
    static public void set_data(int id, Hashtable<String, String> data) {
    	
    }

    static public int add_data(Hashtable<String, String> data) {
    	try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(
                    "insert into %s (%s, %s, %s) values (%s, '%s', '%s')",
                    FileDatabsaeInformation.FILE_TABLE.value(), FileDatabsaeInformation.USER_COLUMN.value(),
                    FileDatabsaeInformation.NAME_COLUMN.value(), FileDatabsaeInformation.FILEPATH_COLUMN.value(),
                    data.get("user_id"), data.get("file_name"), data.get("file_path"));

            stat.executeUpdate(request);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return 0;
    }

}
