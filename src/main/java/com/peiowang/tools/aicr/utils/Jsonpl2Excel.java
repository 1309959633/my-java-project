package com.peiowang.tools.aicr.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Jsonpl2Excel {

    public static void main(String[] args) {

        //读取jsonl文件

        //我需要将一个jsonl文件读取出来，然后把每一行解析为json对象
        String filePath = "D:\\data\\jsonl\\test.jsonl";
        List<JsonNode> list = readJsonl(filePath);

    }

    private static List<JsonNode> readJsonl(String filePath) {
        List<JsonNode> list = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    // 将每行解析为JsonNode对象
                    JsonNode jsonNode = mapper.readTree(line);
                    list.add(jsonNode);
                    // 这里可以对jsonNode进行进一步处理
                    System.out.println(jsonNode.toString());
                } catch (IOException e) {
                    System.err.println("Error parsing JSON line: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
        }
        return list;
    }
}
