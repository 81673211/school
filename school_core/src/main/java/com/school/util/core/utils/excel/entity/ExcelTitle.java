package com.school.util.core.utils.excel.entity;

import java.util.Date;

import org.apache.poi.hssf.util.HSSFColor;

import com.school.util.core.utils.DateUtil;


/**
 * 表格属性
 * 
 */
public class ExcelTitle {

    public ExcelTitle() {

    }

    public ExcelTitle(String title, String secondTitle, String sheetName) {
        this.title = title;
        this.secondTitle = "";
        this.sheetName = sheetName;
    }
    
    public ExcelTitle(String title) {
        this.title = title;
        this.secondTitle = "导出日期：" + DateUtil.formatDate(new Date());
        this.sheetName = title;
    }
    
    /**
     * 表格名称
     */
    private String title;
    /**
     * 第二行名称
     */
    private String secondTitle;
    /**
     * sheetName
     */
    private String sheetName;
    /**
     * 表头颜色
     */
    private short color = HSSFColor.WHITE.index;

    /**
     * 属性说明行的颜色 例如:HSSFColor.SKY_BLUE.index 默认
     */
    private short headerColor = HSSFColor.SKY_BLUE.index;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public short getColor() {
        return color;
    }

    public void setColor(short color) {
        this.color = color;
    }

    public String getSecondTitle() {
        return secondTitle;
    }

    public void setSecondTitle(String secondTitle) {
        this.secondTitle = secondTitle;
    }

    public short getHeaderColor() {
        return headerColor;
    }

    public void setHeaderColor(short headerColor) {
        this.headerColor = headerColor;
    }
}
