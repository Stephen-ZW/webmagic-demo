package com.hand.util;

import org.apache.commons.dbutils.QueryRunner;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 获取查询工具
 *
 * @author Wei
 * @version 1.0
 * @date 2019/5/30 16:56
 */
public class DbUtil {

    private DataSource ds = null;
    private QueryRunner runner = null;


    public DbUtil() {
        try {
            this.ds = DbPoolConnection.getInstance().getDataSource();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (this.ds != null) {
            this.runner = new QueryRunner(this.ds);
        }
    }

    public DbUtil(DataSource ds) {
        this.ds = ds;
        this.runner = new QueryRunner(this.ds);
    }

    public QueryRunner getRunner() {
        return this.runner;
    }
}
