package com.peiowang.tools.aicr.excuters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VllmBaseExcuter {


    /**
     * 重放
     *
     * @param prompt
     * @return
     * @throws IOException
     */
    public String doInfer(String prompt, String url, String modelPath) throws IOException {

        String res = "";
        try {
            res = generateResult(prompt, url, modelPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("-----prompt-----");
        System.out.println(prompt);
        System.out.println("-----result-----");
        System.out.println(res);
        return res;
    }


    private String generateResult(String prompt, String url, String modelPath) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS) // 连接超时
                .readTimeout(300, TimeUnit.SECONDS)    // 读取超时
                .writeTimeout(150, TimeUnit.SECONDS)   // 写入超时
                .build();
        ObjectMapper objectMapper = new ObjectMapper();

        ArrayNode stopAN = objectMapper.createArrayNode().add("<|assistant|>").add("<|end|>").add("</s>").add("<|endoftext|>");
        JsonNode requestBody = objectMapper.createObjectNode()
                .put("prompt", prompt)
                .put("top_p", 0.95)
                .put("stream", false)
                .put("max_tokens", 2048)
                .put("top_k", 50)
                .put("temperature", 0)
                .put("model", modelPath)
                .put("repetition_penalty", 1.0)
                .put("n", 1)
                .set("stop", stopAN);

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer U1dLR0ZLdVJEQkZQTG5zcG1iVHo=")
//                .addHeader("Cookie", QWEN_COOKIE)
                .build();

        StringBuilder result = new StringBuilder();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response.body().toString());
            }

            String responseBody = response.body().string();

            try {
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode choicesNode = rootNode.get("choices");
                for (JsonNode choice : choicesNode) {
                    String text = choice.get("text").asText();
                    result.append(text);
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result.toString();
    }
}
