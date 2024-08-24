package com.hkust.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumToJsonUtils {

    /**
     * example ：
     * [
     *  {
     *      "code": "1",
     *      "name": "开柜门"
     *  }
     * ]
     *
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> List<ObjectNode> convertEnumToJsonList(Class<T> enumClass) {
        ObjectMapper mapper = new ObjectMapper();
        return Arrays.stream(enumClass.getEnumConstants())
                .map(enumConstant -> {
                    ObjectNode node = mapper.createObjectNode();
                    try {
                        String code = (String) enumClass.getMethod("getCode").invoke(enumConstant);
                        String name = (String) enumClass.getMethod("getName").invoke(enumConstant);
                        node.put("code", code);
                        node.put("name", name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return node;
                })
                .collect(Collectors.toList());
    }

    /**
     * example ：
     * {
     *      "1": "正常",
     *      "2": "故障"
     * }
     *
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> ObjectNode convertEnumToJson(Class<T> enumClass) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode objectNode = mapper.createObjectNode();

        try {
            T[] enums = enumClass.getEnumConstants();
            for (T e : enums) {
                Method getCodeMethod = enumClass.getMethod("getCode");
                Method getNameMethod = enumClass.getMethod("getName");

                Object code = getCodeMethod.invoke(e);
                Object name = getNameMethod.invoke(e);

                objectNode.put(code.toString(), name.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return objectNode;
    }

    /**
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends Enum<T>> ArrayNode convertEnumToJsonArray(Class<T> enumClass) {
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode arrayNode = mapper.createArrayNode();

        try {
            T[] enums = enumClass.getEnumConstants();
            for (T e : enums) {
                ObjectNode objectNode = mapper.createObjectNode();
                Method getNameMethod = enumClass.getMethod("getName");

                Object code = e.name();
                Object name = getNameMethod.invoke(e);

                objectNode.put("code", code.toString());
                objectNode.put("name", name.toString());
                arrayNode.add(objectNode);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return arrayNode;
    }
}
