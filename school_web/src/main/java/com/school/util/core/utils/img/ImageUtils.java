package com.school.util.core.utils.img;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.UUID;

public class ImageUtils {
	private static final String IMAGE_FORM_OF_JPG = "jpg";
	private static final String IMAGE_FORM_OF_PNG = "png";
	public static void cutJPG(InputStream input, OutputStream out, int x,  
			int y, int width, int height) throws IOException {  
		ImageInputStream imageStream = null;  
		try {  
			BufferedImage bin = ImageIO.read(input);
			int srcWidth = bin.getWidth();
			int srcHeight = bin.getHeight();
			System.out.println("------srcWidth:"+srcWidth +" srcHeight:"+srcHeight);
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");  
			ImageReader reader = readers.next();  
			imageStream = ImageIO.createImageInputStream(input);  
			reader.setInput(imageStream, true);  
			ImageReadParam param = reader.getDefaultReadParam();  

			Rectangle rect = new Rectangle(x, y, width, height);  
			param.setSourceRegion(rect);  
			BufferedImage bi = reader.read(0, param);  
			ImageIO.write(bi, "jpg", out);  
		} finally {  
			imageStream.close();  
		}  
	}  

	public static void cutPNG(InputStream input, OutputStream out, int x,  
			int y, int width, int height) throws IOException {  
		ImageInputStream imageStream = null;  
		try { 
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("png");  
			ImageReader reader = readers.next();  
			imageStream = ImageIO.createImageInputStream(input);  
			reader.setInput(imageStream, true);  
			ImageReadParam param = reader.getDefaultReadParam();  

			Rectangle rect = new Rectangle(x, y, width, height);  
			param.setSourceRegion(rect);  
			BufferedImage bi = reader.read(0, param);  
			ImageIO.write(bi, "png", out);  
		} finally {  
			imageStream.close();  
		}  
	}  

	public static void cutImage(InputStream input, OutputStream out, String type,int x,  
			int y, int width, int height) throws IOException {  
		ImageInputStream imageStream = null;  
		try {  
			String imageType=(null==type||"".equals(type))?"jpg":type;  
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imageType);  
			ImageReader reader = readers.next();  
			imageStream = ImageIO.createImageInputStream(input);  
			reader.setInput(imageStream, true);  
			ImageReadParam param = reader.getDefaultReadParam();  
			Rectangle rect = new Rectangle(x, y, width, height);  
			param.setSourceRegion(rect);  
			BufferedImage bi = reader.read(0, param);  
			ImageIO.write(bi, imageType, out);  
		} finally {  
			imageStream.close();  
		}  
	}
	public static void cutTest(InputStream input, OutputStream out,int x,  
			int y, int width, int height) throws IOException {
		/*BufferedImage bi = ImageIO.read(input);
			int srcWidth = bi.getWidth();
			int srcHeight = bi.getHeight();
			Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
			ImageFilter cropFilter = new CropImageFilter(x,y,width,height);
			Image img = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(image.getSource(),cropFilter));
			BufferedImage tag = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			Graphics graphics = tag.getGraphics();
			graphics.drawImage(img, 0,0,null);
			graphics.dispose();
			ImageIO.write(tag, "jpg", out);*/
		ImageInputStream imageStream = null;  
		try {  	        	
			Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");  
			ImageReader reader = readers.next();  
			imageStream = ImageIO.createImageInputStream(input);  
			reader.setInput(imageStream, true);  
			ImageReadParam param = reader.getDefaultReadParam();  

			Rectangle rect = new Rectangle(x, y, width, height);  
			param.setSourceRegion(rect);  
			BufferedImage bi = reader.read(0, param);  
			ImageIO.write(bi, "jpg", out);  
		}catch (Exception e) {
			e.printStackTrace();
		}finally {  
			imageStream.close();  
		} 
	}
	public Iterator<ImageReader> getImageReadersByFormatName(String postFix) {
		if (postFix.equals(IMAGE_FORM_OF_JPG)) {
			return ImageIO.getImageReadersByFormatName(IMAGE_FORM_OF_JPG);
		}else if (postFix.equals(IMAGE_FORM_OF_PNG)) {
			return ImageIO.getImageReadersByFormatName(IMAGE_FORM_OF_PNG);
		}
		return ImageIO.getImageReadersByFormatName(IMAGE_FORM_OF_JPG);
	}
	public static void main(String[] args) throws Exception {
		//http://localhost:8080/huili/assets/images/test.jpg
		String path = "http://192.168.20.213:8172/upload/poster/20151009/10b3bfe3-a379-4966-b3d7-f5c1cfb9c6b1.jpg";
		String timp=UUID.randomUUID().toString(); //文件名
		String cutFilePath = "C:\\Users\\Administrator\\Desktop\\壳牌\\listen_cut_"+timp+".jpg";
		URL url = new URL(path);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		InputStream newFileIn = connection.getInputStream();
		cutTest(newFileIn,  
				new FileOutputStream(cutFilePath),0,0,350,700);
	}
	public static void testRun(String path , String cutFilePath,int x,int y,int width,int height) throws Exception {
		URL url = new URL(path);
		HttpURLConnection connection = (HttpURLConnection)url.openConnection();
		connection.setConnectTimeout(3000);
		connection.setReadTimeout(3000);
		InputStream newFileIn = connection.getInputStream();
		ImageUtils.cutTest(newFileIn,  
				new FileOutputStream(cutFilePath),x,y,width,height);
	}
}
