package com.hy.sys.common.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName BCryptUtil
 * description:
 * yao create 2023年07月31日
 * version: 1.0
 */
public class BCryptUtil {

    public static String passwordEncoder(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
