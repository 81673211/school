package com.school.util.core.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageTools {
	/**
	 * ��ȡͼƬ���
	 * @param file
	 * @return
	 */
	public static int getImgWidth(InputStream is) throws IOException{
		BufferedImage src = null;
		int ret = -1;
		src = ImageIO.read(is);
		ret = src.getWidth();
		is.close();
		return ret;
	}
	/**
	 * ��ȡͼƬ�߶�
	 * @param file
	 * @return
	 * @throws IOException 
	 */
	public static int getImgHeight(InputStream is) throws IOException {
		BufferedImage src = null;
		int ret = -1;
		src = ImageIO.read(is);
		ret = src.getHeight();
		is.close();
		return ret;
	}
}
