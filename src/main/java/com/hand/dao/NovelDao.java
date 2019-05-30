package com.hand.dao;

import com.hand.entity.Novel;
import com.hand.util.DbUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * 小说数据操作
 *
 * @author Wei
 * @version 1.0
 * @date 2019/5/30 15:31
 */
public class NovelDao extends AbstractDao<Novel> {

    private Logger logger = LoggerFactory.getLogger(NovelDao.class);

    DbUtil dbUtil = new DbUtil();


    @Override
    public void insert(Novel novel) {
        if (novel == null) {
            logger.warn("小说为空！");
            return;
        }
        String insertSql = "INSERT INTO `novel`('novel_id',`novel_name`,`novel_url`)VALUES(?,?,?)";
        QueryRunner run = dbUtil.getRunner();
        Object[] params = {novel.getNoveld(), novel.getNovelName(), novel.getNovelUrl()};
        try {
            run.insert(insertSql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
