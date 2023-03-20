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
                    "update users set username='%s', email='%s', password='%s' where user_id=%s",
                    data.get("username"), data.get("email"), data.get("password"),
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
                    "insert into users (username, email, password) values ('%s', '%s', '%s')",
                    data.get("username"), data.get("email"), data.get("password"));

            stat.executeUpdate(request);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }

    }
}
