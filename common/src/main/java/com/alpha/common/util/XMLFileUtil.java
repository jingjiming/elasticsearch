package com.alpha.common.util;

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by kids on 2019/5/5.
 */
public class XMLFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(XMLFileUtil.class);

    private static Map<String, JSONObject> map = new HashMap<>();

    static {
        readXMLFile();
    }

    synchronized public static void readXMLFile() {
        logger.info("---------> start loading pdf-setting.xml file...");
        try {
            SAXReader reader = new SAXReader();
            URL url = XMLFileUtil.class.getClassLoader().getResource("config/pdf-settings.xml");
            File file = new File(url.getFile());
            Document document = reader.read(file);
            Element root = document.getRootElement();
            Iterator<Element> iterator = root.elementIterator();
            while (iterator.hasNext()) {
                Element element = iterator.next();
                String id = element.attributeValue("id");
                String name = element.attributeValue("name");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", element.elementText("title"));
                jsonObject.put("content", element.elementText("content"));
                jsonObject.put("name", name);
                map.put(id, jsonObject);
            }

            logger.info("---------> load pdf-setting.xml successfully");
        } catch (DocumentException e) {
            logger.error("---------> load pdf-setting.xml exception ...");
        }
    }

    public static JSONObject getConfig(String id) {
		if (StringUtils.isNotEmpty(id)) {
            return map.get(id);
        }
        return null;
    }

}
