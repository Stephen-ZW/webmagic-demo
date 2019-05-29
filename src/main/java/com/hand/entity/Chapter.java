/**
 * 文件名：Chapter.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：
 */

package com.hand.entity;

import lombok.Data;


/**
 * 章节
 *
 * @author ZhangWei
 * @version 1.0
 * @date 2019/5/27 23:03
 */

@Data
public class Chapter {

    private int chapterId;

    private int novelId;

    //章节名称
    private String chapterName;

    //章节地址
    private String chapterUrl;

    //章节内容
    private String chapterContent;
}
