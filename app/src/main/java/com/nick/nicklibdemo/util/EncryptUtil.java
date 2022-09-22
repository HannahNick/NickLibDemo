package com.nick.nicklibdemo.util;

import android.text.TextUtils;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Nick on 2017/9/8.
 * 加密工具
 */

public class EncryptUtil {

    private static StringBuilder mSb = new StringBuilder();

    private EncryptUtil(){
    }

    private static String bytes2Hex(byte[] src) {
        char[] res = new char[src.length * 2];
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >>> 4 & 0x0f];
            res[j++] = hexDigits[src[i] & 0x0f];
        }

        return new String(res);
    }

    private static String getMd5ByFile(File file) {
        String value = null;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);

            MessageDigest digester = MessageDigest.getInstance("MD5");
            byte[] bytes = new byte[8192];
            int byteCount;
            while ((byteCount = in.read(bytes)) > 0) {
                digester.update(bytes, 0, byteCount);
            }
            value = bytes2Hex(digester.digest());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 判断文件的MD5是否为指定值
     *
     * @param file
     * @param md5
     * @return
     */
    public static boolean checkMd5(File file, String md5) {
        if (TextUtils.isEmpty(md5)) {
            throw new RuntimeException("md5 cannot be empty");
        }
        String fileMd5 = getMd5ByFile(file);
        if (md5.equals(fileMd5)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断文件的MD5是否为指定值
     *
     * @param filePath
     * @param md5
     * @return
     */
    public static boolean checkMd5(String filePath, String md5) {
        return checkMd5(new File(filePath), md5);
    }

    /**
     * MD5字符串加密
     *
     * @param str
     * @return
     * @throws NoSuchAlgorithmException
     */
    public final static String md5(String str) throws NoSuchAlgorithmException {
        final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
                '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        byte[] btInput = str.getBytes();
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest md5Inst = MessageDigest.getInstance("MD5");
        // 使用指定的字节更新摘要
        md5Inst.update(btInput);
        // 获得密文
        byte[] bytes = md5Inst.digest();

        StringBuffer strResult = new StringBuffer();
        // 把密文转换成十六进制的字符串形式
        for (int i = 0; i < bytes.length; i++) {
            strResult.append(hexDigits[(bytes[i] >> 4) & 0x0f]);
            strResult.append(hexDigits[bytes[i] & 0x0f]);
        }
        return strResult.toString();
    }

    /**
     * SHA加密
     *
     * @param strSrc
     *            明文
     * @return 加密之后的密文
     */
    public static String shaEncrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");// 将此换成SHA-1、SHA-512、SHA-384等参数
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return "";
        }
        return strDes;
    }

    /**
     * token加密
     *
     * @param originToken 原始token
     * @return 加密后的token
     */
    public static String createToken(String originToken) {

        long timeSecond = System.currentTimeMillis();

        String tokenHead = originToken + "." + timeSecond;
        String base64TokenHead = Base64.encodeToString(tokenHead.getBytes(), Base64.NO_WRAP);

        String tokenBody = "secret=APEC2017&time=" + timeSecond +
                "&token=" + originToken;

        String shaTokenBody = shaEncrypt(tokenBody);


        return base64TokenHead + "." + shaTokenBody;
    }

    /**
     * 密码加密
     * @param pwd
     * @return
     */
    public static String pwdEncrypt(String pwd, String timeSecond){
        String sha_pwd = shaEncrypt(pwd);
        String pwd_head = getBase64(sha_pwd.concat(".").concat(timeSecond));
        String pwd_body_string = "secret=APEC2017&time=" + timeSecond +
                "&userpassword=" + sha_pwd;
        String pwd_body = shaEncrypt(pwd_body_string);
        return pwd_head.concat(".").concat(pwd_body);
    }

    /**
     * base64加密
     * @param str
     * @return
     */
    public static String getBase64(String str) {
        String result = "";
        if( str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 给电话号码中间部分打*
     * @param phone
     * @return
     */
    public static String phoneEncrypt(String phone){
        StringBuilder sb = new StringBuilder(phone);
        sb.replace(3,7,"*****");
        return sb.toString();
    }

    /**
     * 除了第一位，后面都替换成*
     * @param content
     * @return
     */
    public static String commonEncrypt(String content){
        if (mSb.length()>0){
            mSb.delete(0,mSb.length());
        }
        int length = content.length();
        mSb.append(content.substring(0, 1));
        for (int i = 0; i < length; i++) {
            mSb.append("*");
        }
        return mSb.toString();
    }
}
