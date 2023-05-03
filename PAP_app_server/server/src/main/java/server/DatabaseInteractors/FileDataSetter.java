package server.DatabaseInteractors;

import java.sql.Connection;
import java.util.Hashtable;

import server.ConnectionPool.ConnectionPool;

public class FileDataSetter implements DataSetterInterface {
    static public void setData(int id, Hashtable<String, String> data) {

    }

    static public int addData(Hashtable<String, String> data) {
        String preparedStatement = String.format(
                "insert into %s (%s, %s, %s) values (?, ?, ?)",
                FileDatabsaeInformation.FILE_TABLE.value(), FileDatabsaeInformation.USER_COLUMN.value(),
                FileDatabsaeInformation.NAME_COLUMN.value(), FileDatabsaeInformation.FILEPATH_COLUMN.value());
        Connection connection = ConnectionPool.getConnection();
        try {

            var statement = connection.prepareStatement(preparedStatement);
            statement.setString(1, data.get("user_id"));
            statement.setString(2, data.get("file_name"));
            statement.setString(3, data.get("file_path"));
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            System.out.println(e);

        } finally {

        }
        ConnectionPool.releaseConnection(connection);
        return 0;
    }

}
