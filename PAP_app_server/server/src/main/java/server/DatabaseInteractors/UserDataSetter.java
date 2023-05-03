package server.DatabaseInteractors;

import java.util.Hashtable;

import server.ConnectionPool.ConnectionPool;

import java.sql.Connection;

public class UserDataSetter implements DataSetterInterface {
    static final String TABLENAME = UserDatabaseInformation.USER_TABLE.value();

    static public void setData(int id, Hashtable<String, String> data) {
        String preparedStatement = String.format(
                "update %s set %s=?, %s=?, %s=? where %s=?",
                TABLENAME, UserDatabaseInformation.USERNAME_COLUMN.value(),
                UserDatabaseInformation.EMAIL_COLUMN.value(),
                UserDatabaseInformation.PASSWORD_COLUMN.value(),
                UserDatabaseInformation.ID_COLUMN.value());

        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(preparedStatement);
            // connection.commit();
            statement.setString(1, data.get("username"));
            statement.setString(2, data.get("email"));
            statement.setString(3, data.get("password"));
            statement.setString(4, data.get("ID"));
            statement.executeUpdate();
            connection.commit();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);
    }

    static public void addData(Hashtable<String, String> data) {

        String preparedStatement = String.format(
                "insert into %s (%s, %s, %s) values (?, ?, ?)",
                TABLENAME, UserDatabaseInformation.USERNAME_COLUMN.value(),
                UserDatabaseInformation.EMAIL_COLUMN.value(),
                UserDatabaseInformation.PASSWORD_COLUMN.value());

        Connection connection = ConnectionPool.getConnection();
        try {
            var statement = connection.prepareStatement(preparedStatement);
            statement.setString(1, data.get("username"));
            statement.setString(2, data.get("email"));
            statement.setString(3, data.get("password"));
            statement.execute();
            connection.commit();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);

    }

    public static int addUserToConversation(int conversationId, int userId) {
        String preparedStatement = String.format(
                "insert into %s (%s, %s) values (?, ?)",
                UserDatabaseInformation.USER_CONVERSATION_TABLE.value(),
                ConversationDatabsaeInformation.ID_COLUMN.value(),
                UserDatabaseInformation.ID_COLUMN.value());

        Connection connection = ConnectionPool.getConnection();
        int addedId = 0;
        try {
            var statement = connection.prepareStatement(preparedStatement);
            statement.setInt(1, conversationId);
            statement.setInt(2, userId);
            statement.execute();
            connection.commit();
            addedId = UserDataAccesor.getLatestUser();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
        ConnectionPool.releaseConnection(connection);
        return addedId;
    }
}
