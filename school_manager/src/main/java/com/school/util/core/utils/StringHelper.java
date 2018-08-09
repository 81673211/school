package com.school.util.core.utils;

public class StringHelper {
    /**
     * 将给定的字节数组用十六进制字符串表示.
     */
    public static String toString(byte[] data) {
        if (data == null) {
            return "null!";
        }
        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return new String(out);
    }

    private static final char[] DIGITS = {
            '0', '1', '2', '3', '4', '5', '6',
            '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

}
