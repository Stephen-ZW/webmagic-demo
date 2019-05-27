/**
 * 文件名：Novel.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：
 */

package com.hand.entity;

import lombok.Data;

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

    private Long noveld;

    //小说名称
    private String novelName;

    //小说地址
    private String novelUrl;

    private List<Chapter> chapters;

}
