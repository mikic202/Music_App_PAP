package DatabaseInteractors;

import java.util.Hashtable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class UserDataSetter implements DataSetterInterface {
    static public void set_data(int id, Hashtable<String, String> data) {
        String db_url = "jdbc:mysql://localhost:3306/PAP_app";
        String db_username = "root";
        String password = "";

        try {

            Connection connection = DriverManager.getConnection(db_url, db_username, password);

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

    }

}
