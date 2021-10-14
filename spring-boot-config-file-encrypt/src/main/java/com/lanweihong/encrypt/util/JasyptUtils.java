package com.lanweihong.encrypt.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * @author lanweihong 986310747@qq.com
 * @date 2021/10/13 16:34
 */
public class JasyptUtils {

    public static void main(String[] args) {
        StandardPBEStringEncryptor stringEncryptor = new StandardPBEStringEncryptor();
        // salt
        stringEncryptor.setPassword("As12mda-02mdAadi123df");
        String userName = stringEncryptor.encrypt("root");
        String password = stringEncryptor.encrypt("123456");
        System.out.println("用户名：" + userName);
        System.out.println("密码：" + password);
    }
}
