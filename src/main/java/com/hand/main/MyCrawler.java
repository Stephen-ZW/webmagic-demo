package com.hand.main;

import com.hand.pipeline.JdbcPipeline;
import com.hand.processor.NovelProcessor;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * 爬虫测试
 *
 * @author Wei
 * @version 1.0
 * @date 2019/5/30 15:37
 */
public class MyCrawler {

    public static void main(String[] args) {
        List<Pipeline> pipelines = new ArrayList<>();
        pipelines.add(new ConsolePipeline());
        pipelines.add(new JdbcPipeline());

        Spider spider = Spider.create(new NovelProcessor()).setPipelines(pipelines);
        spider.addUrl("http://www.xbiquge.la/xiaoshuodaquan/");
        spider.thread(6);
        spider.run();
    }
}
