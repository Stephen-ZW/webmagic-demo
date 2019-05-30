package com.hand.pipeline;

import com.hand.dao.ChapterDao;
import com.hand.dao.NovelDao;
import com.hand.entity.Chapter;
import com.hand.entity.Novel;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * 插入数据
 *
 * @author Wei
 * @version 1.0
 * @date 2019/5/30 16:56
 */
public class JdbcPipeline implements Pipeline {

    private NovelDao novelDao = new NovelDao();

    private ChapterDao chapterDao = new ChapterDao();

    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.getRequest().getUrl();
        Map<String, Object> map = resultItems.getAll();

        if (url.startsWith("http://www.xbiquge.la")
                && !url.contains("xiaoshuodaquan")
                && !url.endsWith("html")) {
            Novel novel = (Novel) map.get("novel");
            novelDao.insert(novel);
        }

        if (url.endsWith("html")) {
            Chapter chapter = (Chapter) map.get("chapter");
            chapterDao.insert(chapter);
        }
    }
}
