package com.bsoft.examination.util;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;

import java.util.UUID;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019年05月11日 12:15:00
 */
public class UUIDUtil {

    /**
     * 随机生成uuid
     * @return
     */
    public static String generateRandomUUID() {
        UUID uuid = Generators.randomBasedGenerator().generate();
        return uuid.toString().replaceAll("-", "");
    }

    /**
     * 生成基于时间戳的uuid
     * @return
     */
    public static String generateTimeUUID() {
        UUID uuid = Generators.timeBasedGenerator(EthernetAddress.fromInterface()).generate();
        return uuid.toString().replaceAll("-", "");
    }
}
