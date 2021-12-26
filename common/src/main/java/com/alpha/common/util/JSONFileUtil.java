package com.alpha.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * Created by kids on 2019/5/5.
 */
public class JSONFileUtil {

    private static final Logger logger = LoggerFactory.getLogger(JSONFileUtil.class);

    private static JSONObject jsonObject = new JSONObject();

    private static JSONArray jsonArray = new JSONArray();

    static {
        readDeptJsonFile("config/dept.json");
        readKnowledgePointJsonFile("config/knowledge_point.json");
    }

    // 读取单位JSON配置文件
    synchronized public static void readDeptJsonFile(String fileName) {
        logger.info("---------> start loading" + fileName);
        StringBuilder sb = new StringBuilder();
        try (InputStream is = JSONFileUtil.class.getClassLoader().getResourceAsStream(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(is, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String str = null;
            while((str = bufferedReader.readLine()) != null){
                sb.append(str);
            }
            bufferedReader.close();
            jsonObject = JSONObject.parseObject(sb.toString());
            logger.info("---------> load " + fileName + " successfully");
        } catch (FileNotFoundException e) {
            logger.error("---------> " + fileName + " not found!!!");
        } catch (IOException e) {
            logger.error("load " + fileName + " IOException");
        }
    }

    // 读取知识点JSON配置文件
    synchronized public static void readKnowledgePointJsonFile(String fileName) {
        logger.info("---------> start loading" + fileName);
        StringBuilder sb = new StringBuilder();
        try (InputStream is = JSONFileUtil.class.getClassLoader().getResourceAsStream(fileName);
             InputStreamReader inputStreamReader = new InputStreamReader(is, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String str = null;
            while((str = bufferedReader.readLine()) != null){
                sb.append(str);
            }
            bufferedReader.close();
            jsonArray = JSONObject.parseArray(sb.toString());
            logger.info("---------> load " + fileName + " successfully");
        } catch (FileNotFoundException e) {
            logger.error("---------> " + fileName + " not found!!!");
        } catch (IOException e) {
            logger.error("load " + fileName + " IOException");
        }
    }

    public static String getDeptNameByPid(int pid) {
		JSONArray jsonArray = jsonObject.getJSONArray("dept");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (pid == jsonObject.getIntValue("pid")) {
                return jsonObject.getString("name");
            }
        }
        return null;
    }

    public static String getDeptNameByCid(int pid, int cid) {
        JSONArray jsonArray = jsonObject.getJSONArray("dept");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (pid == jsonObject.getIntValue("pid")) {
                JSONArray array = jsonObject.getJSONArray("child");
                for (int j = 0; j < array.size(); j++) {
                    JSONObject obj = array.getJSONObject(j);
                    if (cid == obj.getIntValue("cid")) {
                        return obj.getString("name");
                    }
                }
            }
        }
        return null;
    }

    public static String getDeptName(int pid, int cid) {
        StringBuilder stringBuilder = new StringBuilder();
        JSONArray jsonArray = jsonObject.getJSONArray("dept");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (pid == jsonObject.getIntValue("pid")) {
                stringBuilder.append(jsonObject.getString("name"));
                JSONArray array = jsonObject.getJSONArray("child");
                if (array != null && array.size() > 0) {
                    for (int j = 0; j < array.size(); j++) {
                        JSONObject obj = array.getJSONObject(j);
                        if (cid == obj.getIntValue("cid")) {
                            stringBuilder.append(" | ");
                            stringBuilder.append(obj.getString("name"));
                        }
                    }
                }
            }
        }
        return stringBuilder.toString();
    }

    public static JSONArray getKnowledgePointList() {
        return jsonArray;
    }

    public static String getKnowledgePointById(int id) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getIntValue("id") == id) {
                return jsonObject.getString("name");
            }
        }
        return null;
    }

    public static int getKnowledgePointByName(String name) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject.getString("name").equals(name)) {
                return jsonObject.getIntValue("id");
            }
        }
        return 0;
    }
}
