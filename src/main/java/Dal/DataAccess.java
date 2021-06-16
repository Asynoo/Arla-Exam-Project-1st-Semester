package Dal;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

/**
 * Project is finished
 * */

public class DataAccess {
    private final SQLServerDataSource dataSource;

    public DataAccess() {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setUser("CSe20B_3");
        dataSource.setPassword("CSe20B_3");
        dataSource.setDatabaseName("ArlaCMS");
    }

    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }
}