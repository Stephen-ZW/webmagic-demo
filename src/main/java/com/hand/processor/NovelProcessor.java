/**
 * 文件名：NovelProcessor.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：
 */

package com.hand.processor;

import com.hand.entity.Chapter;
import com.hand.entity.Novel;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * 实现PageProcessor接口，爬取处理数据
 *
 * @author ZhangWei
 * @version 1.0
 * @date 2019/5/27 23:34
 */
public class NovelProcessor implements PageProcessor {

    private Logger logger = LoggerFactory.getLogger(NovelProcessor.class);

    //开始地址
    private static final String START_URL = "http://www.xbiquge.la";

    //地址限制
    private static final String TARGET_USER_BASE_INFO = "http://www.xbiquge.la/[\\w-]+";

    private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(300).setTimeOut(3 * 60 * 1000);

    /**
     * 爬取页面
     *
     * @param page 页面
     */
    @Override
    public void process(Page page) {
//        List<String> novalList = page.getHtml().links().regex(TARGET_USER_BASE_INFO).all();
//
//        for (String s : novalList) {
//            if (StringUtils.isBlank(s) || s.equals("#") || s.startsWith("javascript:")) {
//                continue;
//            }
//            //page.addTargetRequest(s+"/about");
//            System.err.println(s);
//        }

        String url = page.getUrl().toString();
        if (null == page.getHtml()) {
            try {
                logger.warn("#################  html为空，线程休眠10分钟");
                Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (url.startsWith("http://www.xbiquge.la/xiaoshuodaquan/")) {
            List<String> novalList = page.getHtml().xpath("//*[@id='main']/[@class='novellist']/ul/li/a/@href").all();
            for (String novalUrl : novalList) {
                Novel novel = new Novel();
                novel.setNovelUrl(novalUrl);
                page.addTargetRequest(new Request(novalUrl));
            }
        }

        if (url.startsWith("http://www.xbiquge.la") && !url.contains("xiaoshuodaquan")) {
            List<String> chapterList = page.getHtml().xpath("//*[@id='list']/dl/dd/a/@href").all();
            for (String chapterUrl : chapterList) {
                chapterUrl = "http://www.xbiquge.la" + chapterUrl;
                page.addTargetRequest(new Request(chapterUrl));
            }
        }

        if (url.endsWith("html")) {
            Chapter chapter = new Chapter();
            String chpaterName = page.getHtml().xpath("//div[@class='content_read']/div[@class='box_con']/div[@class='bookname']/h1/text()").toString();
        }


    }

    /**
     * 设置网站相关配置
     *
     * @return 返回站点信息
     */
    @Override
    public Site getSite() {
        return null;
    }
}
