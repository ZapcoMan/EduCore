package com.example.common.utils;

import cn.hutool.core.codec.Base32;
import cn.hutool.core.codec.Base64;

public class MixUtils {

    //base64字符串加密方法
    public static String encode(String str) {
        str = Base64.encode(str);
        str = Base32.encode(str);
        return str;
    }

    //解密 方法
    public static String decode(String str) {
        str = Base32.decodeStr(str);
        str = Base64.decodeStr(str);
        return str;
    }

}
