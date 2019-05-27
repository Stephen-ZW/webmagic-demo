/**
 * 文件名：NovelProcessor.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：
 */

package com.hand.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
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
    private static final String START_URL = "";

    private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(300).setTimeOut(3 * 60 * 1000);

    /**
     * 爬取页面
     *
     * @param page 页面
     */
    @Override
    public void process(Page page) {
        List<String> novalList = page.getHtml().links().regex("").all();
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
