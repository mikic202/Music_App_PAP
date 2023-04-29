package server.DatabaseInteractors;

import java.util.Hashtable;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConversationDataSetter implements DataSetterInterface {
    static public void setData(int id, Hashtable<String, String> data) {
        try {

            Connection connection = DriverManager.getConnection(DatabseInformation.URL.value(),
                    DatabseInformation.USER.value(), DatabseInformation.PASSWORD.value());

            Statement stat = connection.createStatement();
            String request = String.format(
                    "update %s set %s='%s', %s='%s' where %s=%s",
                    ConversationDatabsaeInformation.CONVERSATION_TABLE.value(),
                    ConversationDatabsaeInformation.NAME_COLUMN.value(),
                    data.get("name"), ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value(),
                    data.get("number_of_users"),
                    ConversationDatabsaeInformation.ID_COLUMN.value(),
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
                    "insert into %s (%s, %s) values ('%s', '%s')",
                    ConversationDatabsaeInformation.CONVERSATION_TABLE.value(),
                    ConversationDatabsaeInformation.NAME_COLUMN.value(),
                    ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value(),
                    data.get("name"), data.get("number_of_users"));

            stat.executeUpdate(request);
            added_id = ConversationDataAccesor.getLatestConversation();

            connection.close();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        return added_id;
    }

}
