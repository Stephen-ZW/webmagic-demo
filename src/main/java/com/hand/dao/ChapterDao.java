package com.hand.dao;

import com.hand.entity.Chapter;
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
        String insertSql = "INSERT INTO `chapter`(chapter_id,novel_id,`chapter_name`,`chapter_url`,`chapter_content`,`insert_time`)VALUES(?,?,?,?,?,?)";
        QueryRunner run = dbUtil.getRunner();
        Object[] params = {chapter.getChapterId(), chapter.getNovelId(), chapter.getChapterName(),
                chapter.getChapterUrl(), chapter.getChapterContent(), new Date()};
        try {
            run.insert(insertSql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Chapter> queryByNovelId(int novelId) {
        String querySql = "select chapter_id as chapterId,novel_id as novelId,chapter_name as chapterName,chapter_url as chapterUrl,chapter_content as chapterContent,insert_time as insertTime from chapter where novel_id = ?";
        Object[] params = {novelId};
        QueryRunner run = dbUtil.getRunner();
        List<Chapter> chapterList = new ArrayList<>();
        try {
            chapterList = run.query(querySql, new BeanListHandler<>(Chapter.class), params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chapterList;
    }
}
