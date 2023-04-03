package DatabaseInteractors;

import java.util.Hashtable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class FileDataSetter implements DataSetterInterface {
    static public void set_data(int id, Hashtable<String, String> data) {
        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(
                    "update %s set %s='%s', %s='%s', %s='%s', %s='%s' where %s=%s",
                    FileDatabsaeInformation.FILE_TABLE.value(),
                    FileDatabsaeInformation.NAME_COLUMN.value(),
                    data.get("name"), FileDatabsaeInformation.USER_COLUMN.value(),
                    data.get("user_id"),
                    FileDatabsaeInformation.FILEPATH_COLUMN.value(),
                    data.get("filepath"));

            stat.executeUpdate(request);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
    }

    static public int add_data(Hashtable<String, String> data) {
        int added_id = 0;
        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(
                    "insert into %s (%s, %s, %s) values ('%s', '%s', '%s')",
                    FileDatabsaeInformation.FILE_TABLE.value(),
                    FileDatabsaeInformation.NAME_COLUMN.value(),
                    FileDatabsaeInformation.USER_COLUMN.value(),
                    FileDatabsaeInformation.FILEPATH_COLUMN.value(),
                    data.get("name"), data.get("user_id"), data.get("filepath"));

            stat.executeUpdate(request);
            added_id = UserDataAccesor.get_latest_user();

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return added_id;
    }

}
