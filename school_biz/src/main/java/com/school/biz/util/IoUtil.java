package com.school.biz.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * IO工具类
 *
 */
public final class IoUtil {

    private IoUtil() {
    }

    /**
     * 从流中读取内容
     *
     * @param in 输入流
     * @param charset 字符集
     * @return 内容
     * @throws IOException
     */
    public static String getString(InputStream in, String charset) throws IOException {
        final long len = in.available();
        if (len >= Integer.MAX_VALUE) {
            throw new IOException("File is larger then max array size");
        }

        byte[] bytes = new byte[(int) len];
        in.read(bytes);
        return new String(bytes, charset);
    }

    /**
     * 将多部分内容写到流中，自动转换为字符串
     * @param out 输出流
     * @param charset 写出的内容的字符集
     * @param isCloseOut 写入完毕是否关闭输出流
     * @param contents 写入的内容，调用toString()方法，不包括不会自动换行
     * @throws IOException
     */
    public static void write(OutputStream out, String charset, boolean isCloseOut, Object... contents)
            throws IOException {
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(out, charset);
            for (Object content : contents) {
                if (content != null) {
                    osw.write(content.toString());
                }
            }
        } catch (Exception e) {
            throw new IOException("Write content to OutputStream error!", e);
        } finally {
            if (isCloseOut) {
                if (osw == null) { return; }
                try {
                    osw.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
