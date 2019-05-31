/**
 * 文件名：Novel.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：
 */

package com.hand.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 小说
 *
 * @author ZhangWei
 * @version 1.0
 * @date 2019/5/27 23:02
 */

@Data
public class Novel {

    private int novelId;

    //小说名称
    private String novelName;

    //作者
    private String author;

    //小说地址
    private String novelUrl;

    //插入时间
    private Date insertTime;

    private List<Chapter> chapters;

}
