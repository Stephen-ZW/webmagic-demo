/**
 * 文件名：NovelProcessor.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：
 */

package com.hand.processor;

import com.hand.entity.Chapter;
import com.hand.entity.Novel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    private static final String P_MATCH_REGEX = "<p[^>]*?>.*?</p>";

    //private static final String TAG_MATCH_REGEX = "(?!<(br).*?>)<.*?>";

    private static final String TAG_MATCH_REGEX = "<[^>]*>";

    private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(300).setTimeOut(3 * 60 * 1000)
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");

    /**
     * 爬取页面
     *
     * @param page 页面
     */
    @Override
    public void process(Page page) {

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
            processNovelHomePage(page);
        } else if (url.startsWith("http://www.xbiquge.la")
                && !url.contains("xiaoshuodaquan")
                && !url.endsWith("html")) {
            processNovelDetail(page, url);
        } else if (url.endsWith("html")) {
            processChapterDetail(page, url);
        }
    }

    /**
     * 处理小说首页
     *
     * @param page 页面
     */
    public void processNovelHomePage(Page page) {
        List<String> novalList = page.getHtml().xpath("//*[@id='main']/*[@class='novellist']/ul/li/a/@href").all();
        for (String novalUrl : novalList) {
            if (novalUrl.equals("http://www.xbiquge.la/1/1516/")) {
                page.addTargetRequest(new Request(novalUrl));
            }
        }
    }

    /**
     * 处理小说页
     *
     * @param page 页面
     * @param url  地址信息
     */
    public void processNovelDetail(Page page, String url) {
        List<String> chapterList = page.getHtml().xpath("//*[@id='list']/dl/dd/a/@href").all();
        String novelName = page.getHtml()
                .xpath("//div[@id='wrapper']/div[@class='box_con']/div[@id='maininfo']/div[@id='info']/h1/text()").toString();
        String author = page.getHtml().xpath("//div[@id='wrapper']/div[@class='box_con']/div[@id='maininfo']/div[@id='info']/p[1]/text()").toString();
        Novel novel = new Novel();
        int novelId = Integer.parseInt(url.substring(url.indexOf("a/") + 2).replace("/", ""));
        novel.setNovelId(novelId);
        novel.setNovelUrl(url);
        novel.setAuthor(author);
        novel.setNovelName(novelName);
        page.putField("novel", novel);
        List<String> chapterUrls = new ArrayList<>();
        for (String chapterUrl : chapterList) {
            chapterUrl = "http://www.xbiquge.la" + chapterUrl;
            chapterUrls.add(chapterUrl);
        }
        page.addTargetRequests(chapterUrls);

    }

    /**
     * 处理章节页
     *
     * @param page 页面
     * @param url  地址信息
     */
    public void processChapterDetail(Page page, String url) {
        String chpaterName = page.getHtml()
                .xpath("//div[@class='content_read']/div[@class='box_con']/div[@class='bookname']/h1/text()")
                .toString().replace("正文卷", "").replace("正文", "");
        String chapterContent = page.getHtml()
                .xpath("//div[@class='content_read']/div[@class='box_con']/div[@id='content']").toString();

        Pattern p_pattern = Pattern.compile(P_MATCH_REGEX, Pattern.CASE_INSENSITIVE);
        Pattern tag_pattern = Pattern.compile(TAG_MATCH_REGEX, Pattern.CASE_INSENSITIVE);
        chapterContent = tag_pattern.matcher(p_pattern.matcher(chapterContent).replaceAll("")).replaceAll("").replace("&nbsp;", " ");
        //.replace("<br>","\n");
        int novelId = Integer.parseInt(url.substring(url.indexOf("a/") + 2, url.lastIndexOf("/")).replace("/", ""));
        int chapterId = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
        Chapter chapter = new Chapter();
        chapter.setChapterId(chapterId);
        chapter.setNovelId(novelId);
        chapter.setChapterName(chpaterName);
        chapter.setChapterContent(chapterContent);
        chapter.setChapterUrl(url);
        page.putField("chapter", chapter);
    }


    /**
     * 设置网站相关配置
     *
     * @return 返回站点信息
     */
    @Override
    public Site getSite() {
        return site;
    }
}
