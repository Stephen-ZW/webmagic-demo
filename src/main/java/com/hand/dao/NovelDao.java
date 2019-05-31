package com.hand.dao;

import com.hand.entity.Novel;
import com.hand.util.DbUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        String insertSql = "INSERT INTO `novel`(novel_id,`novel_name`,`author`,`novel_url`,`insert_time`)VALUES(?,?,?,?,?)";
        QueryRunner run = dbUtil.getRunner();
        Object[] params = {novel.getNovelId(), novel.getNovelName(), novel.getAuthor(), novel.getNovelUrl(), new Date()};
        try {
            run.insert(insertSql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Novel> queryAll() {
        String querySql = "select novel_id as novelId, novel_name as novelName, author,novel_url as novelUrl, insert_time as insertTime from novel";
        QueryRunner run = dbUtil.getRunner();
        List<Novel> novelList = new ArrayList<>();
        try {
            novelList = run.query(querySql, new BeanListHandler<>(Novel.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return novelList;
    }
}
