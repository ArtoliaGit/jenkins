package com.bsoft.examination.web;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.bsoft.examination.domain.ExcelDemo;
import com.bsoft.examination.util.WordUtil;
import com.bsoft.examination.util.excel.ExcelStyleHandler;
import com.bsoft.examination.util.excel.ExcelUtil;
import com.bsoft.examination.util.excel.ExcelWriterFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月12日 11:11:00
 */
@RestController
@RequestMapping("file")
public class FileController {

    @PostMapping
    public String upload(MultipartFile file) throws Exception {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        List<Object> data = ExcelUtil.readExcel(file, new ExcelDemo());
        System.out.println(data);

        return "success";
    }

    @GetMapping("/excel")
    public void downloadExcel(HttpServletResponse response) {
        List<ExcelDemo> list = getList();
        String fileName = "一个 Excel 文件";
        String sheetName = "第一个 sheet";

        ExcelWriterFactory writer = ExcelUtil.writeExcel(response, fileName, ExcelTypeEnum.XLS, new ExcelStyleHandler());
        writer.write(list, "测试", new ExcelDemo());
        writer.write(list, "测试2", new ExcelDemo());
        writer.finish();
    }

    @GetMapping("/word")
    public void downloadWord(HttpServletResponse response) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("username", "张三");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        dataMap.put("currDate", sdf.format(new Date()));

        dataMap.put("content", "这是正文");

        try {
            FileInputStream fis = new FileInputStream(new File("D:\\下载\\33.jpg"));
            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();

            dataMap.put("image", Base64.getEncoder().encodeToString(data));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> newList = new ArrayList<>();
        for (int i = 1; i <= 10; i ++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", "标题" + i);
            map.put("content", "内容" + i);
            map.put("author", "作者" + i);
            newList.add(map);
        }
        dataMap.put("newList", newList);

        Random r = new Random();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
        StringBuffer sb = new StringBuffer();
        sb.append(sdf1.format(new Date()));
        sb.append("_");
        sb.append(r.nextInt(100));

        String filePath = "./upload";

        String fileOnlyName = "导出word" + sb + ".doc";
//		String fileName = "导出word.doc";

        WordUtil.createWord(response, dataMap, "freemarker模板.ftl", fileOnlyName);
    }

    public List<ExcelDemo> getList() {
        List<ExcelDemo> list = new ArrayList<>();
        ExcelDemo model1 = new ExcelDemo();
        model1.setName("howie");
        model1.setAge("19");
        model1.setAddress("123456789");
        model1.setEmail("123456789@gmail.com");
        list.add(model1);
        ExcelDemo model2 = new ExcelDemo();
        model2.setName("harry");
        model2.setAge("20");
        model2.setAddress("198752233");
        model2.setEmail("198752233@gmail.com");
        list.add(model2);
        return list;
    }
}
