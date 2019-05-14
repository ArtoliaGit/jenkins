package com.bsoft.examination.util.excel;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.WriteHandler;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月11日 15:15:00
 */
public class ExcelUtil {

    /**
     * 读取excel文件
     * @param excel 文件
     * @return
     */
    public static List<Object> readExcel(MultipartFile excel) {
        return readExcel(excel, null, 1, 1);
    }

    /**
     * 读取excel文件
     * @param excel 文件
     * @param rowModel 实体类映射，继承BaseRowModel类
     * @return
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel) {
        return readExcel(excel, rowModel, 1, 1);
    }

    /**
     * 读取第一个sheet的数据
     * @param excel 文件
     * @param rowModel 实体类映射，继承BaseRowModel类
     * @param sheetNo sheet的序号，从1开始
     * @return
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel, Integer sheetNo) {
        return readExcel(excel, rowModel, sheetNo, 1);
    }

    /**
     * 读取指定sheet的数据
     * @param excel 文件
     * @param rowModel 实体类映射，继承BaseRowModel类
     * @param sheetNo sheet的序号，从1开始
     * @param headLineNum 表头行数，默认为1
     * @return 数据list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel, Integer sheetNo, Integer headLineNum) {
        ExcelListener listener = new ExcelListener();
        ExcelReader reader = getReader(excel, listener);
        if (reader == null) {
            return null;
        }
        Sheet sheet = new Sheet(1, 1);
        if (rowModel != null) {
            sheet.setClazz(rowModel.getClass());
        }
        if (sheetNo != null) {
            sheet.setSheetNo(sheetNo);
        }
        if (headLineNum != null) {
            sheet.setHeadLineMun(headLineNum);
        }
        reader.read(sheet);
        return listener.getDatas();
    }

    /**
     * 导出excel
     * @param response HttpServletResponse
     * @param list 数据 list，每个元素为一个 BaseRowModel
     * @param filename 导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param rowModel 映射实体类，Excel 模型
     * @param fileType 导出文件格式
     */
    public static void writeExcel(HttpServletResponse response, List<? extends BaseRowModel> list,
                                  String filename, String sheetName, BaseRowModel rowModel, ExcelTypeEnum fileType) {
        if (list == null) {
            throw new RuntimeException("数据为空！");
        }
        if (filename == null) {
            String time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
            filename = "导出数据（" + time + ")";
        }
        if (sheetName == null) {
            sheetName = "Sheet1";
        }
        if (fileType == null) {
            fileType = ExcelTypeEnum.XLSX;
        }

        String suffix = "." + fileType.toString().toLowerCase();
        OutputStream outputStream = getOutputStream(response, filename, suffix);
        ExcelWriter writer = new ExcelWriter(outputStream, fileType);
        Sheet sheet = new Sheet(1, 0);
        if (rowModel != null) {
            sheet.setClazz(rowModel.getClass());
        }
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        writer.finish();
        try {
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出excel：多个sheet
     * @param response HttpServletResponse
     * @param filename 导出的文件名
     * @param fileType 导出文件格式
     * @return
     */
    public static ExcelWriterFactory writeExcel(HttpServletResponse response, String filename, ExcelTypeEnum fileType) {

        return writeExcel(response, filename, fileType, null);
    }

    /**
     * 导出excel：多个sheet
     * @param response HttpServletResponse
     * @param filename 导出的文件名
     * @param fileType 导出文件格式
     * @param writeHandler 样式
     * @return
     */
    public static ExcelWriterFactory writeExcel(HttpServletResponse response, String filename,
                                                ExcelTypeEnum fileType, WriteHandler writeHandler) {
        if (filename == null) {
            String time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
            filename = "导出数据（" + time + ")";
        }
        if (fileType == null) {
            fileType = ExcelTypeEnum.XLSX;
        }
        String suffix = "." + fileType.toString().toLowerCase();
        ExcelWriterFactory writer;
        if (writeHandler == null) {
            writer = new ExcelWriterFactory(getOutputStream(response, filename, suffix), fileType);
        } else {
            writer = new ExcelWriterFactory(getOutputStream(response, filename, suffix), fileType, writeHandler);
        }
        return writer;
    }

    /**
     * 导出excel到磁盘
     * @param filename 导出的文件名
     * @param fileType 导出文件格式
     * @return
     */
    public static ExcelWriterFactory writeExcel(String filename, ExcelTypeEnum fileType) {

        return writeExcel(filename, fileType, null);
    }

    /**
     * 导出excel到磁盘
     * @param filename 导出的文件名
     * @param fileType 导出文件格式
     * @param writeHandler 样式
     * @return
     */
    public static ExcelWriterFactory writeExcel(String filename, ExcelTypeEnum fileType, WriteHandler writeHandler) {
        if (filename == null) {
            String time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
            filename = "导出数据（" + time + ")";
        }
        if (fileType == null) {
            fileType = ExcelTypeEnum.XLSX;
        }
        OutputStream outputStream = null;
        try {
            String suffix = "." + fileType.toString().toLowerCase();
            String filePath = "D:/temp/" + filename + suffix;
            File file = new File(filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            outputStream = new FileOutputStream(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ExcelWriterFactory writer;
        if (writeHandler == null) {
            writer = new ExcelWriterFactory(outputStream, fileType);
        } else {
            writer = new ExcelWriterFactory(outputStream, fileType, writeHandler);
        }
        return writer;
    }

    /**
     * 生成outputstream
     * @param filename
     * @param response
     * @return
     */
    private static OutputStream getOutputStream(HttpServletResponse response, String filename, String fileType) {
        String filePath = filename + fileType;
        try {
            filename = new String(filePath.getBytes(), "ISO-8859-1");
            response.addHeader("Content-Disposition", "filename=" + filename);
            response.setHeader("Content-Type", "application/msexcel");
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("创建文件失败！");
        }
    }

    /**
     * 获取ExcelReader
     * @param excel
     * @param listener
     * @return
     */
    private static ExcelReader getReader(MultipartFile excel, ExcelListener listener) {
        String filename = excel.getOriginalFilename();
        if (filename == null || !StringUtils.endsWithAny(filename.toLowerCase(), ".xls", ".xlsx")) {
            throw new RuntimeException("文件类型错误！");
        }

        InputStream inputStream;
        try {
            inputStream = new BufferedInputStream(excel.getInputStream());
            return new ExcelReader(inputStream, null, listener);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
