package com.bsoft.examination.util.excel;

import com.alibaba.excel.event.WriteHandler;
import org.apache.poi.ss.usermodel.*;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月11日 22:41:00
 */
public class ExcelStyleHandler implements WriteHandler {

    @Override
    public void sheet(int i, Sheet sheet) {

    }

    @Override
    public void row(int i, Row row) {

//        if (i == 0) {
//            row.setHeight();
//        }
    }

    @Override
    public void cell(int i, Cell cell) {

        Workbook workbook = cell.getSheet().getWorkbook();
        CellStyle cellStyle = createStyle(workbook);
        if (cell.getRowIndex() == 0) {
            cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            Font font = workbook.createFont();
            font.setFontName("宋体");
            font.setBold(true);
            font.setFontHeightInPoints((short) 14);
            cellStyle.setFont(font);

            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        }

        cell.setCellStyle(cellStyle);
    }

    private CellStyle createStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);

        return cellStyle;
    }
}
