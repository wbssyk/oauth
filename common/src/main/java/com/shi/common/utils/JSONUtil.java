package com.shi.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shiyakun
 */
public class JSONUtil {
    static final Logger LOG = LoggerFactory.getLogger(JSONUtil.class );

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        // 对象的所有字段全部列入，还是其他的选项，可以忽略null等
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        // 设置Date类型的序列化及反序列化格式
//        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // 忽略空Bean转json的错误
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略未知属性，防止json字符串中存在，java对象中不存在对应属性的情况出现错误
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 注册一个时间序列化及反序列化的处理模块，用于解决jdk8中localDateTime等的序列化问题
        mapper.registerModule(new JavaTimeModule());
    }

    private JSONUtil() {
    }

    public static <T> String objToJson(T entity) {
        String json = null;

        try {
            json = mapper.writeValueAsString(entity);
        } catch (JsonProcessingException var3) {
            var3.printStackTrace();
        }

        return json;
    }

    public static <T> String objToStr(T entity) {
        return objToJson(entity);
    }

    public static <T> byte[] objToJsonBytes(T entity) throws Exception {
        return mapper.writeValueAsBytes(entity);
    }

    public static <T> JsonNode objToNode(T entity) throws Exception {
        return mapper.valueToTree(entity);
    }

    public static <T> boolean writeToJsonFile(String filepath, T entity) throws Exception {
        File file = new File(filepath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException var4) {
                var4.printStackTrace();
                return false;
            }
        }

        return writeToJsonFile(new File(filepath), entity);
    }

    public static <T> boolean writeToJsonFile(File file, T entity) throws Exception {
        try {
            mapper.writeValue(file, entity);
            return true;
        } catch (FileNotFoundException var3) {
            LOG.error("File not exists");
            var3.printStackTrace();
            return false;
        }
    }

    public static <T> T jsonToObj(String json, Class<T> type) throws Exception {
        return mapper.readValue(json, type);
    }

    public static <T> T strToObj(String json, Class<T> type) throws Exception {
        return jsonToObj(json, type);
    }

    public static Map<String, Object> jsonToMap(String json) throws Exception {
        return (Map) mapper.readValue(json, Map.class);
    }

    public static <T> Map<String, T> jsonToMap(String json, Class<T> type) throws Exception {
        return (Map) mapper.readValue(json, new TypeReference<Map<String, T>>() {
        });
    }

    public static <T> T mapToObj(Map map, Class<T> type) throws Exception {
        return mapper.convertValue(map, type);
    }

    public static <T> T parseJSON(File json, Class<T> type) throws Exception {
        return mapper.readValue(json, type);
    }

    public static <T> T parseJSON(URL url, Class<T> type) throws Exception {
        return mapper.readValue(url, type);
    }

    public static <T> List<T> jsonToList(String json, Class<T> T) throws Exception {
        CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, T);
        return (List) mapper.readValue(json, type);
    }

    public static <T> List<T> strToList(String json, Class<T> T) throws Exception {
        return jsonToList(json, T);
    }

    public static JsonNode jsonToNode(String json) throws Exception {
        return mapper.readTree(json);
    }

    public static JsonNode strToNode(String json) throws Exception {
        return jsonToNode(json);
    }

    public static ObjectNode objectNode() {
        return JsonNodeFactory.instance.objectNode();
    }

    public static boolean isJsonString(String json) {
        try {
            mapper.readTree(json);
            return true;
        } catch (Exception varTo) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("check input string is json format;json : " + json + " ; exception;" + varTo.getMessage());
            }

            return false;
        }
    }

    public static ArrayNode arrayNode() {
        return JsonNodeFactory.instance.arrayNode();
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> map = new HashMap();
        map.put("role", "1001");
        map.put("time", "2016-07-07");
        map.put("date",new Date());
        Map<String, String> rtnMap = jsonToMap(objToJson(map), String.class);
        System.out.println(rtnMap);
    }
}
