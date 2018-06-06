package com.school.util.core.utils.excel.entity;

import java.util.Comparator;

/**
 * 按照升序排序
 * 
 *
 */
public class ComparatorExcelField implements Comparator<ExcelExportEntity> {

    @Override
    public int compare(ExcelExportEntity prev, ExcelExportEntity next) {
        return prev.getOrderNum() - next.getOrderNum();
    }

}
