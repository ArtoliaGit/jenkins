package com.bsoft.examination.util.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.WriteHandler;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月11日 18:33:00
 */
public class ExcelWriterFactory extends ExcelWriter {

    private OutputStream outputStream;
    private int sheetNo = 0;

    public ExcelWriterFactory(OutputStream outputStream, ExcelTypeEnum fileType) {
        super(outputStream, fileType);
        this.outputStream = outputStream;
    }

    public ExcelWriterFactory(OutputStream outputStream, ExcelTypeEnum fileType, WriteHandler writeHandler) {
        super(null, outputStream, fileType, true, writeHandler);
        this.outputStream = outputStream;
    }

    public ExcelWriterFactory write(List<? extends BaseRowModel> list, String sheetName,
                                    BaseRowModel rowModel) {
        if (list == null) {
            return this;
        }
        this.sheetNo++;
        try {
            Sheet sheet = new Sheet(sheetNo, 0);
            if (rowModel != null) {
                sheet.setClazz(rowModel.getClass());
            }
            if (sheetName == null) {
                sheetName = "Sheet" + this.sheetNo;
            }
            sheet.setSheetName(sheetName);
            this.write(list, sheet);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                outputStream.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return this;
    }

    @Override
    public void finish() {
        super.finish();
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
