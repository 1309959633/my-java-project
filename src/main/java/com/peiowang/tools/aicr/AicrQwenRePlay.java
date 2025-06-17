package com.peiowang.tools.aicr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.HashMap;
import java.util.Map;
import okhttp3.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class AicrQwenRePlay {

    private static final String QWEN_URL = "http://30.163.16.136:80/generate";
    private static final String V53_URL = "https://sh-copilot.git.woa.com/server/code-intell-chat-cr-model-server-exp-f5-3-rag-step2-x2/v1/completions";
    private static final String V54_URL = "https://sh-copilot.git.woa.com/server/code-intell-chat-cr-model-server-exp-f5-4-rag-step2-x2/v1/completions";
    private static final String QWEN_TOKEN = "U1dLR0ZLdVJEQkZQTG5zcG1iVHo=";
    private static final String QWEN_COOKIE = "x-client-ssid=9610b00b:0192fa5e237f:07ee81; x_host_key_access=f917c78160124bfd25622f04dbfb3ed821846d44_s";
    private static final String CR_POINT1 = "## ";
    //
    //

    private static final String ROOT_PATH = "/Users/peiowang/Downloads/aicr";


    public static void main(String[] args) throws Exception {
        doInfer("未处理可能出现访问空对象的方法或变量", "rag54_要点_空指针.csv");
        doInfer("未处理可能出现的数组越界问题", "rag54_要点_数组越界.csv");
        doInfer("配置/常量/错误码分散在代码中，未集中管理", "rag54_要点_常量管理.csv");
    }

    private static void doInfer(String crPoint, String fileName) throws IOException {
        List<CSVRecord> dataList = new ArrayList<>();
        try {
            dataList = getData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        dataList = filterData(dataList, crPoint);

        System.out.println(dataList.size());

        List<Map<String, String>> newDataList = new ArrayList<>();
        int i = 0;
        for (CSVRecord data : dataList) {
            i++;
            if (i > 30) {
                break;
            }
            String newPrompt = data.get("prompt");
//            String newPrompt = data.get("prompt")
//                    .replace(CR_POINT1,
//                            "\n1. 对于某些相似的代码片段，在超过3行或以上相似的情况下才算作重复代码\n"
//                            + "2. 如果有超过3行的相似代码，但是只是某个函数的重复调用，不属于重复代码，不需要抽取优化");
            newPrompt = "<|im_start|>user\n" + newPrompt + "\n"
                    + "<|im_end|>\n"
                    + "<|im_start|>assistant";
            String res = "";
            try {
                res = generateV54Result(newPrompt);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("-----prompt-----");
            System.out.println(newPrompt);
            System.out.println("-----result-----");
            System.out.println(res);

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("prompt", newPrompt);
            resultMap.put("result", res);
            newDataList.add(resultMap);
        }

        writeData(newDataList, fileName);
    }

    private static List<CSVRecord> getData() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(ROOT_PATH + "/java-raw.csv"));
        return CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader).getRecords();
    }

    private static List<CSVRecord> filterData(List<CSVRecord> dataList, String crPoint) {
        List<CSVRecord> filteredData = new ArrayList<>();
        for (CSVRecord record : dataList) {
            if (record.get("cr_point").contains(crPoint)) {
                filteredData.add(record);
            }
        }
        return filteredData;
    }

    private static String generateQwenResult(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode requestBody = objectMapper.createObjectNode()
                .put("prompt", prompt)
                .put("token", QWEN_TOKEN);

        Request request = new Request.Builder()
                .url(QWEN_URL)
                .post(RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", QWEN_COOKIE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();
            JsonNode responseJson = objectMapper.readTree(responseBody);
            return responseJson.get("raw_answer").asText();
        } catch (Exception e) {

            return "infer error.";
        }

    }

    private static String generateV53Result(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode stopAN = objectMapper.createArrayNode().add("<|assistant|>").add("<|end|>").add("</s>").add("<|endoftext|>");
        JsonNode requestBody = objectMapper.createObjectNode()
                .put("prompt", prompt)
                .put("top_p", 0.95)
                .put("stream", false)
                .put("max_tokens", 4096)
                .put("top_k", 50)
                .put("temperature", 0.2)
                .put("model", "/data/code/CodeLLM-f5-3-RAG-STEP2")
                .put("repetition_penalty", 1.0)
                .put("n", 1)
                .set("stop", stopAN);

        Request request = new Request.Builder()
                .url(V53_URL)
                .post(RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer U1dLR0ZLdVJEQkZQTG5zcG1iVHo=")
                .addHeader("Cookie", QWEN_COOKIE)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            return response.body().string();
        } catch (Exception e) {
            return "infer error.";
        }
    }

    private static String generateV54Result(String prompt) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode stopAN = objectMapper.createArrayNode().add("<|assistant|>").add("<|end|>").add("</s>").add("<|endoftext|>");
        JsonNode requestBody = objectMapper.createObjectNode()
                .put("prompt", prompt)
                .put("top_p", 0.95)
                .put("stream", false)
                .put("max_tokens", 4096)
                .put("top_k", 50)
                .put("temperature", 0.2)
                .put("model", "/data/code/CodeLLM-f5-4-RAG-STEP2")
                .put("repetition_penalty", 1.0)
                .put("n", 1)
                .set("stop", stopAN);

        Request request = new Request.Builder()
                .url(V54_URL)
                .post(RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer U1dLR0ZLdVJEQkZQTG5zcG1iVHo=")
                .addHeader("Cookie", QWEN_COOKIE)
                .build();

        StringBuilder result = new StringBuilder();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            String responseBody = response.body().string();

            try {
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode choicesNode = rootNode.get("choices");
                for (JsonNode choice : choicesNode) {
                    String text = choice.get("text").asText();
                    result.append(text);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }

    private static void writeData(List<Map<String, String>> dataList, String fileName) throws IOException {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(ROOT_PATH + "/" + fileName));
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("prompt", "result"));
            for (Map<String, String> record : dataList) {
                csvPrinter.printRecord(record.get("prompt"), record.get("result"));
            }
            csvPrinter.flush();
            csvPrinter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
