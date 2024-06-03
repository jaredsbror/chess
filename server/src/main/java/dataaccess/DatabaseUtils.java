package dataaccess;


import java.sql.*;

public class DatabaseUtils {

    // Generic method to set parameters in a PreparedStatement
    public static void setParameter(PreparedStatement preparedStatement, int index, Object param, int sqlType) throws SQLException {
        if (param == null) {
            preparedStatement.setNull(index, sqlType);
        } else if (param instanceof String) {
            preparedStatement.setString(index, (String) param);
        } else if (param instanceof Integer) {
            preparedStatement.setInt(index, (Integer) param);
        } else if (param instanceof Long) {
            preparedStatement.setLong(index, (Long) param);
        } else if (param instanceof Double) {
            preparedStatement.setDouble(index, (Double) param);
        } else if (param instanceof Float) {
            preparedStatement.setFloat(index, (Float) param);
        } else if (param instanceof Boolean) {
            preparedStatement.setBoolean(index, (Boolean) param);
        } else if (param instanceof Date) {
            preparedStatement.setDate(index, (Date) param);
        } else if (param instanceof Timestamp) {
            preparedStatement.setTimestamp(index, (Timestamp) param);
        } else {
            throw new IllegalArgumentException("Unexpected parameter type: " + param.getClass().getName());
        }
    }
}
