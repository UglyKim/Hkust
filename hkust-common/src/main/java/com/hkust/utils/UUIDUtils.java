package com.hkust.utils;

import java.util.UUID;

public class UUIDUtils {
    /**
     * 生成随机 UUID
     * @return 随机 UUID
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成没有连字符的随机 UUID
     * @return 没有连字符的随机 UUID
     */
    public static String generateUUIDWithoutHyphens() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 基于名字空间和名字生成 UUID
     * @param namespace 名字空间
     * @param name 名字
     * @return 生成的 UUID
     */
    public static String generateNameUUID(String namespace, String name) {
        UUID nsUUID = UUID.fromString(namespace);
        UUID uuid = UUID.nameUUIDFromBytes((nsUUID.toString() + name).getBytes());
        return uuid.toString();
    }

    public static void main(String[] args) {
        String s = UUIDUtils.generateUUIDWithoutHyphens();
        System.out.println(s);
    }
}
