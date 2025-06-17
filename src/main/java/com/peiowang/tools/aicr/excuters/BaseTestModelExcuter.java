package com.peiowang.tools.aicr.excuters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

//测试用的基础模型请求
//服务器:30.163.16.136:80
public class BaseTestModelExcuter {

    private static final String V55_TEST_URL = "http://30.163.16.136:80/generate";
    private static final String COOKIE = "x-client-ssid=a910b00b:01934266b54b:19a58a; x_host_key_access=f917c78160124bfd25622f04dbfb3ed821846d44_s";

    /**
     * 重放
     *
     * @param prompt
     * @return
     */
    protected static String doInfer(String prompt, String model, String template, String llmEndpoint) {

        String res = "";
        try {
            res = generateResult(prompt, model, template, llmEndpoint);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("-----prompt-----");
        System.out.println(prompt);
        System.out.println("-----result-----");
        System.out.println(res);
        return res;
    }


    private static String generateResult(String prompt, String model, String template, String llmEndpoint) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS) // 连接超时
                .readTimeout(300, TimeUnit.SECONDS)    // 读取超时
                .writeTimeout(150, TimeUnit.SECONDS)   // 写入超时
                .build();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode requestBody = objectMapper.createObjectNode()
                .put("prompt", prompt)
                .put("model", model)
                .put("template", template)
                .put("llm_endpoint", llmEndpoint);

        Request request = new Request.Builder()
                .url(V55_TEST_URL)
                .post(RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", COOKIE)
                .build();

        StringBuilder result = new StringBuilder();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.body().toString());
            }

            String responseBody = response.body().string();

            System.out.println(responseBody);
            try {
                JsonNode rootNode = objectMapper.readTree(responseBody);
                String formattedJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode.get("json_repair_answer"));
                result.append(formattedJson);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}
