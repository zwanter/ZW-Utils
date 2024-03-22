package ru.zwanter.utils.database;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import ru.zwanter.utils.AnnotationProcessor;
import ru.zwanter.utils.database.data.DatabaseConnection;
import ru.zwanter.utils.database.data.DatabaseType;
import ru.zwanter.utils.database.data.ISQLRequests;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Logger;

@Getter
public class Database {

    private Connection connection;
    private final DatabaseConnection databaseConnection;
    private Logger logger;
    private final File file;

    public Database(JavaPlugin plugin, DatabaseConnection databaseConnection) {
        this.file = plugin.getDataFolder();
        this.databaseConnection = databaseConnection;
        initializeDatabase();
    }

    public Database(File file, DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        this.file = file;
        initializeDatabase();
    }
    public Database(File file, Logger logger, DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
        this.file = file;
        this.logger = logger;
        initializeDatabase();
    }

    public void sqlRequest(ISQLRequests... isqlRequests) {
        if (connection != null) {
            Arrays.stream(isqlRequests).forEach(requests -> {
                AnnotationProcessor.sqlRequestProcess(requests, connection);
            });
        }
    }

    private void initializeDatabase() {
        DatabaseType databaseType = databaseConnection.getDatabaseType();
        if (databaseType == DatabaseType.MYSQL) {
            connectToMySQL();
        } else if (databaseType == DatabaseType.SQLITE) {
            connectToSQLite();
        } else {
            if (logger != null) {
                logger.severe("Unsupported database type specified in config!");
            }
        }
    }

    private void connectToMySQL() {
        String url = "jdbc:mysql://" + databaseConnection.getIpAddress() + ":" + databaseConnection.getPort() + "/" + databaseConnection.getDatabase() + "?useSSL=" + databaseConnection.isUseSSL();

        Properties properties = new Properties();
        properties.setProperty("user", databaseConnection.getUserName());
        properties.setProperty("password", databaseConnection.getUserPassword());

        try {
            connection = DriverManager.getConnection(url, properties);
            if (logger != null) {
                logger.info("Connected to MySQL database successfully!");
            }
        } catch (SQLException e) {
            if (logger != null) {
                logger.severe("Failed to connect to MySQL database: " + e.getMessage());
            }
        }
    }

    private void connectToSQLite() {
        String databaseFile = file.getPath() + "/" + databaseConnection.getDatabase() + ".db";

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile);
            if (logger != null) {
                logger.info("Connected to SQLite database successfully!");
            }
        } catch (SQLException e) {
            if (logger != null) {
                logger.severe("Failed to connect to SQLite database: " + e.getMessage());
            }
        }
    }



}
