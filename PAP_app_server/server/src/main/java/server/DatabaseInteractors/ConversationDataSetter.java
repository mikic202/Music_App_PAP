package server.DatabaseInteractors;

import java.util.Hashtable;

import server.ConnectionPool.ConnectionPool;

import java.sql.Connection;

public class ConversationDataSetter implements DataSetterInterface {
    static public void setData(int id, Hashtable<String, String> data) {
        String preparedStatement = String.format(
                "update %s set %s=?, %s=? where %s=?",
                ConversationDatabsaeInformation.CONVERSATION_TABLE.value(),
                ConversationDatabsaeInformation.NAME_COLUMN.value(),
                ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value(),
                ConversationDatabsaeInformation.ID_COLUMN.value());

        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(preparedStatement);
            statement.setString(1, data.get(ConversationDatabsaeInformation.NAME_COLUMN.value()));
            statement.setString(2, data.get(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value()));
            statement.setString(3, data.get(ConversationDatabsaeInformation.ID_COLUMN.value()));
            connection.commit();

            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        ConnectionPool.releaseConnection(connection);
    }

    static public int addData(Hashtable<String, String> data) {
        int added_id = 0;
        String preparedStatement = String.format(
                "insert into %s (%s, %s) values (?, ?)",
                ConversationDatabsaeInformation.CONVERSATION_TABLE.value(),
                ConversationDatabsaeInformation.NAME_COLUMN.value(),
                ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value());
        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(preparedStatement);
            statement.setString(1, data.get(ConversationDatabsaeInformation.NAME_COLUMN.value()));
            statement.setString(2, data.get(ConversationDatabsaeInformation.NUMBER_OF_USERS_COLUMN.value()));
            connection.commit();
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        ConnectionPool.releaseConnection(connection);
        added_id = ConversationDataAccesor.getLatestConversation();
        return added_id;
    }

}
