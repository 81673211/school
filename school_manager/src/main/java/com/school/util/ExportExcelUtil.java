package com.school.util;

import com.school.constant.ConstantMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author 作者：方梁
 * @date 创建时间：2016年9月2日 下午2:06:12
 * @description
 */
public class ExportExcelUtil {

	public static void main(String[] args) {
		
	}

	/**
	 * 
	 * @param response
	 *            请求
	 * @param fileName
	 *            文件名 如："学生表"
	 * @param excelHeader
	 *            excel表头数组，存放"姓名#name"格式字符串，"姓名"为excel标题行， "name"为对象字段名
	 * @param dataList
	 *            数据集合，需与表头数组中的字段名一致，并且符合javabean规范
	 * @return 返回一个HSSFWorkbook
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> HSSFWorkbook export(String fileName, String[] excelHeader,
			Collection<T> dataList) throws Exception {
		// 创建一个Workbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 设置标题样式
		HSSFCellStyle titleStyle = wb.createCellStyle();
		// 设置单元格边框样式
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框 细边线
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);// 下边框 细边线
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框 细边线
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框 细边线
		// 设置单元格对齐方式
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		// 设置字体样式
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 15); // 字体高度
//		titleFont.setFontName("黑体"); // 字体样式
		titleStyle.setFont(titleFont);
		// 在Workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(fileName);
		// 标题数组
		String[] titleArray = new String[excelHeader.length];
		// 字段名数组
		String[] fieldArray = new String[excelHeader.length];
		for (int i = 0; i < excelHeader.length; i++) {
			String[] tempArray = excelHeader[i].split("#");// 临时数组 分割#
			titleArray[i] = tempArray[0];
			fieldArray[i] = tempArray[1];
		}
		// 在sheet中添加标题行
		HSSFRow row = sheet.createRow((int) 0);// 行数从0开始
		row.setHeight((short) 555);
		HSSFCell sequenceCell = row.createCell(0);// cell列 从0开始 第一列添加序号
		sequenceCell.setCellValue("序号");
		sequenceCell.setCellStyle(titleStyle);
		sheet.setColumnWidth(0, (short) (35.7 * 150));
//		sheet.autoSizeColumn(0);// 自动设置宽度
		// 为标题行赋值
		for (int i = 0; i < titleArray.length; i++) {
			HSSFCell titleCell = row.createCell(i + 1);// 0号位被序号占用，所以需+1
			titleCell.setCellValue(titleArray[i]);
			titleCell.setCellStyle(titleStyle);
			sheet.setColumnWidth(i + 1, (short) (35.7 * 150));
//			sheet.autoSizeColumn(i + 1);// 0号位被序号占用，所以需+1
		}
		// 数据样式 因为标题和数据样式不同 需要分开设置 不然会覆盖
		HSSFCellStyle dataStyle = wb.createCellStyle();
		// 设置数据边框
		dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dataStyle.setWrapText(true);
		// 设置居中样式
		dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
		// 设置数据字体
		Font dataFont = wb.createFont();
		dataFont.setFontHeightInPoints((short) 12); // 字体高度
		dataFont.setFontName("宋体"); // 字体
		dataStyle.setFont(dataFont);
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataList.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;// 0号位被占用 所以+1
			row = sheet.createRow(index);
//			row.setHeight((short) 400);
			// 为序号赋值
			HSSFCell sequenceCellValue = row.createCell(0);// 序号值永远是第0列
			sequenceCellValue.setCellValue(index);
			sequenceCellValue.setCellStyle(dataStyle);
//			sheet.autoSizeColumn(0);
			sheet.setColumnWidth(0, (short) (20 * 150));
			T t = (T) it.next();
			// 利用反射，根据传过来的字段名数组，动态调用对应的getXxx()方法得到属性值
			for (int i = 0; i < fieldArray.length; i++) {
				HSSFCell dataCell = row.createCell(i + 1);
				dataCell.setCellStyle(dataStyle);
				sheet.setColumnWidth(i + 1, (short) (35.7 * 150));
//				sheet.autoSizeColumn(i + 1);
				String fieldName = fieldArray[i];
				String curName = "";
				
				String[] nameArray = fieldName.split("-");
				if(nameArray.length>1){
					Object obj = t;
					for(String name : nameArray){
						String getMethodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);// 取得对应getXxx()方法

						Class<? extends Object> tCls = obj.getClass();// 泛型为Object以及所有Object的子类
						Method getMethod = tCls.getMethod(getMethodName, new Class[] {});// 通过方法名得到对应的方法
						obj = getMethod.invoke(obj, new Object[] {});// 动态调用方,得到属性值
						if(obj instanceof List){
							curName = name;
							break;
						}
					}
					
					//如果反射出的对象是个集合，处理方式如下
					if(obj instanceof List){
						List<T> list = ((List<T>) obj);
						String name = fieldName.substring(curName.length()+1);
						String content = "";
						Object subObj = null;
						for(T sut : list){
							String getMethodName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);// 取得对应getXxx()方法
							Class<? extends Object> tCls = sut.getClass();// 泛型为Object以及所有Object的子类
							Method getMethod = tCls.getMethod(getMethodName, new Class[] {});// 通过方法名得到对应的方法
							subObj = getMethod.invoke(sut, new Object[] {});// 动态调用方,得到属性值
							if(null==subObj)
								content += "\r\n";
							else{
								String value = subObj.toString();
								if(name.equals("evidenceType"))
									value = ConstantMap.evidenceTypeMap.get(value);
								else if(name.equals("applyOrgans"))
									value = ConstantMap.applyOrgansMap.get(value);
								content += value + "\r\n";
							}
						}
						dataCell.setCellValue(content);
					}else{
						if(null==obj)
							dataCell.setCellValue("");// 为当前列赋值
						else
							dataCell.setCellValue(obj.toString());// 为当前列赋值
					}
				}else{
					String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);// 取得对应getXxx()方法
					Class<? extends Object> tCls = t.getClass();// 泛型为Object以及所有Object的子类
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});// 通过方法名得到对应的方法
					Object value = getMethod.invoke(t, new Object[] {});// 动态调用方,得到属性值
					if (value != null) {
						dataCell.setCellValue(value.toString());// 为当前列赋值
					}
				}
				
				sheet.autoSizeColumn(i + 1);
			}
		}
		return wb;
	}

}
