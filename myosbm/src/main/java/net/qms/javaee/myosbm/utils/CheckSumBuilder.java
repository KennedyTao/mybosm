package net.qms.javaee.myosbm.utils;

import java.security.MessageDigest;

/**
 *   调用网易云通信服务端接口的请求都需要按此规则校验
 *   CheckSum有效期：出于安全性考虑，每个checkSum的有效期为5分钟(用CurTime计算)，建议每次请求都生成新的checkSum，
 *   同时请确认发起请求的服务器是与标准时间同步的，比如有NTP服务。
 *   CheckSum检验失败时会返回414错误码，具体参看code状态表。
 *   用于计算CheckSum的AppSecret
 */
public class CheckSumBuilder {
    // 计算并获取CheckSum
    public static String getCheckSum(String appSecret, String nonce, String curTime) {
        return encode("sha1", appSecret + nonce + curTime);
    }

    // 计算并获取md5值
    public static String getMD5(String requestBody) {
        return encode("md5", requestBody);
    }

    private static String encode(String algorithm, String value) {
        if (value == null) {
            return null;
        }
        try {
            MessageDigest messageDigest
                    = MessageDigest.getInstance(algorithm);
            messageDigest.update(value.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
}