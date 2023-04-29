package server.DatabaseInteractors;

import java.util.Hashtable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MessageDataSetter implements DataSetterInterface {
    static public void setData(int id, Hashtable<String, String> data) {
        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(
                    "update %s set %s='%s', %s='%s', %s='%s', %s='%s' where %s=%s",
                    MessagesDatabaseInformation.MESSAGES_TABLE.value(),
                    MessagesDatabaseInformation.SENDER_COLUMN.value(),
                    data.get("sender"), MessagesDatabaseInformation.CONVERSATION_COLUMN.value(),
                    data.get("conversation"),
                    MessagesDatabaseInformation.DATE_COLUMN.value(),
                    data.get("send_date"), MessagesDatabaseInformation.MESSAGE_COLUMN.value(),
                    data.get("text"), MessagesDatabaseInformation.ID_COLUMN.value(),
                    data.get("ID"));

            stat.executeUpdate(request);

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
    }

    static public int addData(Hashtable<String, String> data) {
        int added_id = 0;
        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(
                    "insert into %s (%s, %s, %s, %s) values ('%s', '%s', '%s', '%s')",
                    MessagesDatabaseInformation.MESSAGES_TABLE.value(),
                    MessagesDatabaseInformation.SENDER_COLUMN.value(),
                    MessagesDatabaseInformation.CONVERSATION_COLUMN.value(),
                    MessagesDatabaseInformation.DATE_COLUMN.value(),
                    MessagesDatabaseInformation.MESSAGE_COLUMN.value(),
                    data.get("sender"), data.get("conversation"), data.get("send_date"), data.get("text"));

            stat.executeUpdate(request);
            added_id = MessageDataAccesor.get_latest_message();

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return added_id;
    }

}
