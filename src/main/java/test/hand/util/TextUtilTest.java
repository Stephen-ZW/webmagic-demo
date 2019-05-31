package test.hand.util;

import com.hand.util.TextUtil;
import org.junit.Test;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class TextUtilTest {

    @Test
    public void getNovel() {
        System.out.println("开始执行......");
        long start = System.currentTimeMillis();
        TextUtil textUtil = new TextUtil();
        textUtil.getNovel();
        long end = System.currentTimeMillis();
        System.out.println("程序执行时间：" + (end - start) / 1000 + "秒");
    }
}