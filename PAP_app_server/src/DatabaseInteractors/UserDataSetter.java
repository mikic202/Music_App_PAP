package DatabaseInteractors;

import java.util.Hashtable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UserDataSetter implements DataSetterInterface {
    static public void set_data(int id, Hashtable<String, String> data) {
        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(
                    "update %s set %s='%s', %s='%s', %s='%s' where %s=%s",
                    UserDatabaseInformation.USER_TABLE.value(), UserDatabaseInformation.USERNAME_COLUMN.value(),
                    data.get("username"), UserDatabaseInformation.EMAIL_COLUMN.value(), data.get("email"),
                    UserDatabaseInformation.PASSWORD_COLUMN.value(),
                    data.get("password"), UserDatabaseInformation.ID_COLUMN.value(),
                    data.get("ID"));

            stat.executeUpdate(request);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
    }

    static public void add_data(Hashtable<String, String> data) {

        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(
                    "insert into %s (%s, %s, %s) values ('%s', '%s', '%s')",
                    UserDatabaseInformation.USER_TABLE.value(), UserDatabaseInformation.USERNAME_COLUMN.value(),
                    UserDatabaseInformation.EMAIL_COLUMN.value(), UserDatabaseInformation.PASSWORD_COLUMN.value(),
                    data.get("username"), data.get("email"), data.get("password"));

            stat.executeUpdate(request);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }

    }
}
