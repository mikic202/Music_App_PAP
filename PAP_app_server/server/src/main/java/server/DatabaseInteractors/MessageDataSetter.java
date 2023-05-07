package server.DatabaseInteractors;

import java.util.Hashtable;

import server.ConnectionPool.ConnectionPool;

import java.sql.Connection;

public class MessageDataSetter implements DataSetterInterface {
    static final String TABLENAME = MessagesDatabaseInformation.MESSAGES_TABLE.value();

    static public void setData(int id, Hashtable<String, String> data) {
        Connection connection = ConnectionPool.getConnection();
        String preparedStatement = String.format(
                "update %s set %s=?, %s=?, %s=?, %s=? where %s=?",
                MessagesDatabaseInformation.MESSAGES_TABLE.value(),
                MessagesDatabaseInformation.SENDER_COLUMN.value(),
                MessagesDatabaseInformation.CONVERSATION_COLUMN.value(),

                MessagesDatabaseInformation.DATE_COLUMN.value(),
                MessagesDatabaseInformation.MESSAGE_COLUMN.value(),
                MessagesDatabaseInformation.ID_COLUMN.value());
        try {

            var statement = connection.prepareStatement(preparedStatement);
            statement.setString(1, data.get("sender"));
            statement.setString(2, data.get("conversation"));
            statement.setString(3, data.get("send_date"));
            statement.setString(4, data.get("text"));
            statement.setString(5, data.get("ID"));
            statement.executeUpdate();
            connection.commit();

        } catch (

        Exception e) {
            System.out.println(e);

        } finally {

        }
        ConnectionPool.releaseConnection(connection);
    }

    static public int addData(Hashtable<String, String> data) {
        int added_id = 0;
        String preparedStatement = String.format(
                "insert into %s (%s, %s, %s, %s) values (?, ?, ?, ?)",
                MessagesDatabaseInformation.MESSAGES_TABLE.value(),
                MessagesDatabaseInformation.SENDER_COLUMN.value(),
                MessagesDatabaseInformation.CONVERSATION_COLUMN.value(),
                MessagesDatabaseInformation.DATE_COLUMN.value(),
                MessagesDatabaseInformation.MESSAGE_COLUMN.value());
        Connection connection = ConnectionPool.getConnection();
        try {

            var statement = connection.prepareStatement(preparedStatement);
            statement.setString(1, data.get("sender"));
            statement.setString(2, data.get("conversation"));
            statement.setString(3, data.get("send_date"));
            statement.setString(4, data.get("text"));
            statement.executeUpdate();
            connection.commit();
            added_id = MessageDataAccesor.getLatestMessage(Integer.parseInt(data.get("conversation")));

        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        ConnectionPool.releaseConnection(connection);
        return added_id;
    }

    static public void setIsImage(int messageId) {
        Connection connection = ConnectionPool.getConnection();
        String preparedStatement = String.format(
                "update %s set %s=? where %s=?",
                MessagesDatabaseInformation.MESSAGES_TABLE.value(),
                MessagesDatabaseInformation.IS_IMAGE_COLUMN.value(),
                MessagesDatabaseInformation.ID_COLUMN.value());
        try {

            var statement = connection.prepareStatement(preparedStatement);
            statement.setBoolean(1, true);
            statement.setInt(2, messageId);
            statement.executeUpdate();
            connection.commit();

        } catch (

        Exception e) {
            System.out.println(e);

        } finally {

        }
        ConnectionPool.releaseConnection(connection);
    }

}
