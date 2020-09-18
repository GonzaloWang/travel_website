package com.itheima.travel.utils;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;

/**
 * @Description MD5摘要加密算法
 */
public class MD5Coder {

    /** 加密算法 */
    private static final String ALGORITHM = "MD5";

    /** 字符编码 */
    private static final String CHARSET   = "UTF-8";

    /**
     * 将原文加密生成摘要字符串，默认编码utf-8
     */
    public static String md5Encode(String src) {
        return md5Encode(src, CHARSET);
    }

    /**
     * 将原文加密生成摘要字符串，自定义编码
     */
    public static String md5Encode(String src, String charset) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            return Hex.encodeHexString(md.digest(src.getBytes(charset)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(MD5Coder.md5Encode("pass"));
    }


}
