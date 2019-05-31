package com.hand.util;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 连接池工具类
 *
 * @author Wei
 * @version 1.0
 * @date 2019/5/30 16:41
 */
public class DbPoolConnection {

    private static Logger logger = Logger.getLogger(DbPoolConnection.class);

    private static DbPoolConnection databasePool = null;
    private static DruidDataSource dds = null;

    static {
        Properties properties = loadPropertyFile("conf.properties");
        try {
            dds = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DbPoolConnection() {
    }

    public static synchronized DbPoolConnection getInstance() {
        if (null == databasePool) {
            databasePool = new DbPoolConnection();
        }
        return databasePool;
    }

    public DruidDataSource getDataSource() throws SQLException {
        return dds;
    }

    public DruidPooledConnection getConnection() throws SQLException {
        return dds.getConnection();
    }

    public static Properties loadPropertyFile(String configFile) {
        String webRootPath = null;
        if (null == configFile || configFile.equals(""))
            throw new IllegalArgumentException("Properties file path can not be null : " + configFile);
        ClassLoader classLoader = DbPoolConnection.class.getClassLoader();
        URL resource = classLoader.getResource(configFile);
        String path = resource.getPath();
        logger.info("加载配置文件..." + path);
        File file = new File(path);
        InputStream inputStream = null;
        Properties p = null;
        try {
            inputStream = new FileInputStream(file);
            p = new Properties();
            p.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Properties file not found: "
                    + configFile);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "Properties file can not be loading: " + configFile);
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

}