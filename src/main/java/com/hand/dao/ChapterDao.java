package com.hand.dao;

import com.hand.entity.Chapter;
import com.hand.util.DbUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * 章节数据操作
 *
 * @author Wei
 * @version 1.0
 * @date 2019/5/30 15:32
 */
public class ChapterDao extends AbstractDao<Chapter> {

    private Logger logger = LoggerFactory.getLogger(NovelDao.class);

    DbUtil dbUtil = new DbUtil();


    @Override
    public void insert(Chapter chapter) {
        if (chapter == null) {
            logger.warn("章节为空！");
            return;
        }
        String insertSql = "INSERT INTO `chapter`(`chapter_id`,`novel_id`,`chapter_name`,`chapter_url`,`chapter_content`)VALUES(?,?,?,?,?)";
        QueryRunner run = dbUtil.getRunner();
        Object[] params = {chapter.getChapterId(), chapter.getNovelId(), chapter.getChapterName(), chapter.getChapterUrl(), chapter.getChapterContent()};
        try {
            run.insert(insertSql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
