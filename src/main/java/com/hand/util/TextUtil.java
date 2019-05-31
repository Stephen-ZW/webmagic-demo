/**
 * 文件名：TextUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：生成text文件
 */

package com.hand.util;

import com.hand.dao.ChapterDao;
import com.hand.dao.NovelDao;
import com.hand.entity.Chapter;
import com.hand.entity.Novel;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 生成text文件
 *
 * @author Wei
 * @version 1.0
 * @date 2019/5/31 10:51
 */
public class TextUtil {

    private NovelDao novelDao = new NovelDao();

    private ChapterDao chapterDao = new ChapterDao();

    /***
     *      ┌─┐       ┌─┐
     *   ┌──┘ ┴───────┘ ┴──┐
     *   │                 │
     *   │       ───       │
     *   │  ─┬┘       └┬─  │
     *   │                 │
     *   │       ─┴─       │
     *   │                 │
     *   └───┐         ┌───┘
     *       │         │
     *       │         │
     *       │         │
     *       │         └──────────────┐
     *       │                        │
     *       │                        ├─┐
     *       │                        ┌─┘
     *       │                        │
     *       └─┐  ┐  ┌───────┬──┐  ┌──┘
     *         │ ─┤ ─┤       │ ─┤ ─┤
     *         └──┴──┘       └──┴──┘
     *                神兽保佑
     *               代码无BUG!
     */
    public void getNovel() {
        List<Novel> novelList = novelDao.queryAll();
        for (Novel novel : novelList) {
            List<Chapter> chapterList = chapterDao.queryByNovelId(novel.getNovelId());
            novel.setChapters(chapterList);
            generateText(novel);
        }
    }

    /**
     * 生成text文件
     *
     * @param novel 小说实体类
     */
    public void generateText(Novel novel) {
        String fileName = novel.getNovelName();
        String filePath = "D:\\" + fileName + ".txt";
        File file = new File(filePath);
        if (!file.exists()) {
            List<Chapter> chapters = novel.getChapters();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fileName).append("\n\t").append(novel.getAuthor()).append("\n");
            for (Chapter chapter : chapters) {
                stringBuilder = stringBuilder.append(chapter.getChapterName()).append("\n\n").append(chapter.getChapterContent());
            }
            byte[] bytes = stringBuilder.toString().getBytes();
            try {
                DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(filePath)));
                for (int i = 0; i < bytes.length; i++) {
                    out.write(bytes[i]);
                }
                out.flush();
                out.close();
                System.out.println("小说 《" + fileName + "》 已经被存储在: " + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
