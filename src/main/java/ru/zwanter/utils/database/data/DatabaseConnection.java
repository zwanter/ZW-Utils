package ru.zwanter.utils.database.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DatabaseConnection {

    private final String ipAddress;
    private final String userName;
    private final String userPassword;
    private final String database;

    private final int port;

    private final boolean useSSL;

    private DatabaseType databaseType;

}
