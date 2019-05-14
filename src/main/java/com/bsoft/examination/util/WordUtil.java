package com.bsoft.examination.util;

import freemarker.core.ParseException;
import freemarker.template.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月12日 12:00:00
 */
public class WordUtil {

    /**
     * 将word写到磁盘
     * @param dataMap 模板数据
     * @param templateName 模板名
     * @param fileName 文件名
     */
    public static void createWord(Map<String, Object> dataMap, String templateName, String fileName) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassForTemplateLoading(WordUtil.class, "/templates");
            Template template = configuration.getTemplate(templateName);
            File outFile = new File("D:/temp" + File.separator + fileName);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
            template.process(dataMap, out);
            out.flush();
            out.close();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将word写到响应流
     * @param response HttpServletResponse
     * @param dataMap 模板数据
     * @param templateName 模板名
     * @param fileName 文件名
     */
    public static void createWord(HttpServletResponse response, Map<String, Object> dataMap, String templateName, String fileName) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassForTemplateLoading(WordUtil.class, "/templates");
            Template template = configuration.getTemplate(templateName);
            fileName = new String(fileName.getBytes(), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Content-Type", "application/msword");

            OutputStream outputStream = response.getOutputStream();
            Writer out = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            template.process(dataMap, out);
            out.flush();
            out.close();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
