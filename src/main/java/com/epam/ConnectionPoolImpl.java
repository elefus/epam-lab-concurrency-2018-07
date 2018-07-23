package com.epam;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;

public class ConnectionPoolImpl implements IConnectionPool {

    private static final Logger logger = LogManager.getLogger("ConnectionPool.Logger");
    private Set<Connection> connections = new CopyOnWriteArraySet<>();
    private BlockingQueue<Connection> availableConnections;
    private int poolSize;

    private String driver;
    private String dbUrl;
    private String dbKeys;
    private String dbName;
    private String dbUser;
    private String dbPassword;
    private int delay = 100;

    public ConnectionPoolImpl(int poolSize, String dbConfigPropertiesFilename) {

        if (poolSize < 1) throw new IllegalArgumentException("Pool size can't be less then 1.");
        this.poolSize = poolSize;
        this.availableConnections = new ArrayBlockingQueue<>(poolSize);

        if (dbConfigPropertiesFilename == null) {
            logger.fatal("Null string as a connection pool configuration filename.");
            return;
        }

        initDBConfigPropertiesFromFile(dbConfigPropertiesFilename);

    }

    private void initDBConfigPropertiesFromFile(String dbConfigPropertiesFilename) {
        Properties dbProp = new Properties();
        URL url = this.getClass().getResource("/" + dbConfigPropertiesFilename);
        if (url == null) {
            logger.fatal("Connection pool configuration file '{}' not found.", dbConfigPropertiesFilename);
            return;
        }
        String path = url.getPath().replaceAll("%20", " ");
        File dbPropFile = new File(path);
        if (!dbPropFile.exists() || !dbPropFile.canRead()) {
            logger.fatal("Connection pool configuration file '{}' not found.", dbConfigPropertiesFilename);
            return;
        }

        InputStream input = null;
        try {
            input = new FileInputStream(dbPropFile);
            dbProp.load(input);

            this.driver = dbProp.getProperty("db.config.driver");
            this.dbUrl = dbProp.getProperty("db.config.url");
            this.dbKeys = dbProp.getProperty("db.config.keys");
            this.dbName = dbProp.getProperty("db.config.name");
            this.dbUser = dbProp.getProperty("db.config.user");
            this.dbPassword = dbProp.getProperty("db.config.password");
            this.delay = Integer.parseInt(dbProp.getProperty("cp.config.delay"));

        } catch (IOException e) {
            logger.error(e);
        } finally {
            if (input != null) try {
                input.close();
            } catch (IOException ex) {
                logger.error(ex);
            }
        }
    }

    private Optional<Connection> createConnection() {
        Connection connection = null;
        if (connections.size() < poolSize) {
            try {
                connection = DriverManager
                        .getConnection(dbUrl + dbName + dbKeys, dbUser, dbPassword);
                connections.add(connection);
                availableConnections.offer(connection);
                logger.info("Create connection #{}: {}", connections.size(), connection);
            } catch (SQLException e) {
                logger.error("Can not create connection because SQLException: ", e);
            }
        }
        return Optional.ofNullable(connection);
    }

    public synchronized Connection getConnection() {
        logger.trace("Thread entered.");
        //System.out.println("Thread " + Thread.currentThread().getName() + " entered getConnection()");
        try {
            if (availableConnections.isEmpty())
                createConnection().orElseThrow(NoConnectionAvailable::new);
            logger.trace("There are {} available connections and {} created ones.", availableConnections.size(), connections.size());
            //System.out.println(Thread.currentThread().getName() + " There are: " + availableConnections.size() + " available connections, " +
            //        connections.size() + " created.");
            logger.trace("Thread left.");
            //System.out.println("Thread " + Thread.currentThread().getName() + " leave getConnection()");
            return availableConnections.poll();
        } catch (NoConnectionAvailable e) {
            logger.info("There are no available connections. Thread is waiting to obtain.");
            //System.out.println(Thread.currentThread().getName() + " There no available connections. Waiting fo obtain.");
            try {
                wait(delay);
            } catch (InterruptedException e1) {
                logger.warn(e1);
            }
            Connection connection = availableConnections.poll();
            logger.trace("Thread left.");
            //System.out.println("Thread " + Thread.currentThread().getName() + " leave getConnection()");
            if (connection == null) throw new NoConnectionAvailable();
            return connection;
        }
    }


    public synchronized void retrieveConnection(Connection connection) {
        availableConnections.offer(connection);
        logger.trace("There are {} available connections and {} created ones.", availableConnections.size(), connections.size());
        //System.out.println(Thread.currentThread().getName() + " There are: " + availableConnections.size() + " available connections, " +
        //        connections.size() + " created.");
        notifyAll();
    }

    public void close() {
        for (Connection connection : connections) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.warn(e);
            }
        }
        availableConnections.clear();
        connections.clear();
    }

}


