package com.school.util.core.utils;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Queue;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class ZipUtils {

	private static final int BUFFER_SIZE = 4096;

	private ZipUtils() {
	}

	/**
	 * 压缩字节数组
	 */
	public static byte[] zip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(bos);
			ZipEntry entry = new ZipEntry("json");
			entry.setSize(data.length);
			zip.putNextEntry(entry);
			zip.write(data);
			zip.closeEntry();
			zip.close();
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/**
	 * GZip 压缩字节数组
	 * @param data UTF-8格式的字节数组
	 * @return
	 */
	public static byte[] gzip(byte[] data) {
		byte[] b = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();  
			GZIPOutputStream gop = new GZIPOutputStream(bos);  
			
			gop.write(data);
			
			gop.finish(); //这个在写入arrayOutputStream时一定要有，否则不能完全写入  
			gop.close();
			
			b = bos.toByteArray();
			bos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/**
	 * GZip解压缩
	 * @param data
	 * @return
	 */
	public static byte[] unGZip(byte[] data) {
		byte[] b = new byte[0];
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			GZIPInputStream gzip = new GZIPInputStream(bis);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			  
	        // Transfer bytes from the compressed stream to the output stream  
	        byte[] buf = new byte[1024];  
	        int len;  
	        while ((len = gzip.read(buf)) > 0) {  
	        	baos.write(buf, 0, len);  
	        }  
	  
	        // Close the file and stream  
	        b = baos.toByteArray();
	        
	        baos.flush();
	        baos.close();
	        gzip.close();
	        bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/**
	 * 解压字节数组
	 */
	public static byte[] unZip(byte[] data) {
		byte[] b = new byte[0];
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ZipInputStream zip = new ZipInputStream(bis);
			while (zip.getNextEntry() != null) {
				byte[] buf = new byte[1024];
				int num = -1;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				while ((num = zip.read(buf, 0, buf.length)) != -1) {
					baos.write(buf, 0, num);
				}
				b = baos.toByteArray();
				baos.flush();
				baos.close();
			}
			zip.close();
			bis.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return b;
	}

	/**
	 * 解压zip中的单个文件
	 */
	public static boolean unzipSingleFile(String zipFilePath, String fileName,
			String targetFilePath) {

		File tFile = new File(targetFilePath);
		BufferedOutputStream dest = null;
		ZipInputStream zis = null;
		try {
			ZipEntry entry = null; // 每个zip条目的实例
			FileInputStream fis = new FileInputStream(zipFilePath);
			zis = new ZipInputStream(new BufferedInputStream(fis));

			// 循环处理ZipEntry
			while ((entry = zis.getNextEntry()) != null) {
				try {
					if (!fileName.equals(entry.getName())) {
						continue;
					}

					int count;
					byte data[] = new byte[BUFFER_SIZE];
					FileOutputStream fos = new FileOutputStream(tFile);
					dest = new BufferedOutputStream(fos, BUFFER_SIZE);
					while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
						extracted(dest).write(data, 0, count);
					}

					break;
				} catch (Exception ex) {
					return false;
				} finally {
					if (null != dest) {
						dest.flush();
						dest.close();
					}
				}
			}
		} catch (IOException ex) {
			return false;
		} finally {
			if (zis != null) {
				try {
					zis.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	private static BufferedOutputStream extracted(BufferedOutputStream dest) {
		return dest;
	}

	private static void writeFileToZip(File file, ZipOutputStream outZip) {
		ZipEntry zipEntry = new ZipEntry(file.getName());
		FileInputStream inputStream = null;
		try {
			outZip.putNextEntry(zipEntry);
			int len;
			byte[] buffer = new byte[BUFFER_SIZE];
			inputStream = new FileInputStream(file);
			while ((len = inputStream.read(buffer)) != -1) {
				outZip.write(buffer, 0, len);
			}
		} catch (IOException e) {
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}

			if (outZip != null) {
				try {
					outZip.closeEntry();
				} catch (IOException e) {
				}
			}
		}
	}

	public static boolean zipSingelFile(String originalFile, String desFile) {

		if (originalFile == null) {
			return false;
		}

		// 检查目标目录是否存在
		File desDir = new File(desFile).getParentFile();
		if (!desDir.exists() || !desDir.isDirectory()) {
			if (!desDir.mkdirs()) {
				return false;
			}
		}

		ZipOutputStream outZip = null;

		try {
			// 创建Zip包
			outZip = new ZipOutputStream(new FileOutputStream(desFile));
			// 只归档不压缩, 会导致 CRC mismatch 错误
			// outZip.setMethod(ZipOutputStream.STORED);

			File file = new File(originalFile);
			writeFileToZip(file, outZip);
			outZip.finish();
			outZip.close();
			return true;
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 指定文件和文件夹列表压缩为一个文件
	 */
	public static boolean zipFiles(Queue<String> fileList, String desFile) {

		if (fileList == null || fileList.size() == 0) {
			return false;
		}

		// 找出父文件夹的路径
		File tempFile = new File(fileList.peek());
		if (!tempFile.exists()) {
			return false;
		}

		// 检查目标目录是否存在
		File desDir = new File(desFile).getParentFile();
		if (!desDir.exists() || !desDir.isDirectory()) {
			if (!desDir.mkdirs()) {
				return false;
			}
		}

		String parentFolderPath = tempFile.getParent() + File.separator;
		

		// 新的队列用来容纳文件树
		Queue<String> allFiles = new LinkedList<String>(fileList);
		ZipOutputStream outZip = null;

		try {
			// 创建Zip包
			outZip = new ZipOutputStream(new FileOutputStream(desFile));
			// 只归档不压缩, 会导致 CRC mismatch 错误
			// outZip.setMethod(ZipOutputStream.STORED);

			while (!allFiles.isEmpty()) {
				File file = new File(allFiles.poll());
				// 判断是不是文件
				if (file.isFile()) {
					writeFileToZip(parentFolderPath, file, outZip);
				
				} else if (file.isDirectory()) {
					handleDir(parentFolderPath, file, allFiles, outZip);
					
				}
			}

			outZip.finish();
			try {
				outZip.close();
			} catch (IOException e) {
			}
			return true;
		} catch (IOException e) {
		} catch (Exception e) {
		}

		// 失败之后删除已经创建的文件
		if (!new File(desFile).delete()) {
		}
		return false;
	}

	private static void handleDir(String parentFolderPath, File file,
			Queue<String> allFiles, ZipOutputStream outZip) {
		File[] list = file.listFiles();
		// 如果没有子文件, 则添加进去即可
		if (list.length <= 0) {
			try {
				String path = getRelativePath(parentFolderPath,
						file.getAbsolutePath())
						+ File.separator;
				ZipEntry zipEntry = new ZipEntry(path);
				outZip.putNextEntry(zipEntry);
				outZip.closeEntry();
			} catch (IOException e) {
			}
		}

		// 添加到待处理列表
		for (File f : list) {
			allFiles.add(f.getAbsolutePath());
		}
	}

	/**
	 * 获取相对路径
	 */
	private static String getRelativePath(String parentFolderPath,
			String desFile) {
		return desFile.replaceAll(parentFolderPath, "");
	}

	private static void writeFileToZip(String parentFolderPath, File file,
			ZipOutputStream outZip) {
		ZipEntry zipEntry = new ZipEntry(getRelativePath(parentFolderPath,
				file.getAbsolutePath()));
		FileInputStream inputStream = null;
		try {
			outZip.putNextEntry(zipEntry);
			int len;
			byte[] buffer = new byte[BUFFER_SIZE];
			inputStream = new FileInputStream(file);
			while ((len = inputStream.read(buffer)) != -1) {
				outZip.write(buffer, 0, len);
			}
			outZip.closeEntry();

		} catch (IOException e) {
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
	/** 
     * 压缩文件 
     *  
     * @param srcfile File[] 需要压缩的文件列表 
     * @param zipfile File 压缩后的文件 
     */  
    public static void zipFiles(String sourceFilePath, File zipfile) {  
    	File sourceFile = new File(sourceFilePath);
        byte[] buf = new byte[1024];  
        try {  
        	File[] srcfile = sourceFile.listFiles();  
            // Create the ZIP file  
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipfile));  
            // Compress the files  
            for (int i = 0; i < srcfile.length; i++) {  
                File file = srcfile[i];  
                FileInputStream in = new FileInputStream(file);  
                // Add ZIP entry to output stream.  
                out.putNextEntry(new ZipEntry(file.getName()));  
                // Transfer bytes from the file to the ZIP file  
                int len;  
                while ((len = in.read(buf)) > 0) {  
                    out.write(buf, 0, len);  
                }  
                // Complete the entry  
                out.closeEntry();  
                in.close();  
            }  
            // Complete the ZIP file  
            out.close();  
        } catch (IOException e) {  
        }  
    }
    
    /** 
     * 解压缩 
     *  
     * @param zipfile File 需要解压缩的文件 
     * @param descDir String 解压后的目标目录 
     */  
    public static void unZipFiles(File zipfile, String descDir) {  
        try {  
            // Open the ZIP file  
            ZipFile zf = new ZipFile(zipfile);  
            for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {  
                // Get the entry name  
                ZipEntry entry = ((ZipEntry) entries.nextElement());  
                String zipEntryName = entry.getName();  
                InputStream in = zf.getInputStream(entry);  
                // System.out.println(zipEntryName);  
                OutputStream out = new FileOutputStream(descDir + zipEntryName);  
                byte[] buf1 = new byte[1024];  
                int len;  
                while ((len = in.read(buf1)) > 0) {  
                    out.write(buf1, 0, len);  
                }  
                // Close the file and stream  
                in.close();  
                out.close();  
            }  
        } catch (IOException e) {  
        }  
    }  
  
    
	public static void deleteFile(String targetPath) throws IOException {  
		File targetFile = new File(targetPath);  
		if (targetFile.isDirectory()) {  
			FileUtils.deleteDirectory(targetFile);  
		} else if (targetFile.isFile()) {  
			targetFile.delete();  
		}  
	}
    
	//test
		public static void main(String[] args){
			String value = "{\"header\":{\"sequence\":null,\"cmdVer\":\"1.0\",\"resultCode\":2001,\"msg\":\"用户ID不存在\"},\"body\":{\"token\":null,\"deviceID\":null,\"serverUrl\":null,\"serverTime\":null,\"upType\":null,\"upUrl\":null,\"mapKey\":null}}";
			try {
				byte[] bytes = zip(value.getBytes("utf-8"));
				System.out.println(bytes.length+";"+bytes);
				
				bytes = unZip(bytes);
				try {
					String requestString = new String(bytes, "UTF-8");
					System.out.println("\n请求数据(解析后):\n" + requestString + "\n");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
}
