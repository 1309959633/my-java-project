package com.peiowang.tools.aicr.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.nd4j.common.io.ResourceUtils;

public class CodellamaBPE {

    private Map<String, Integer> bpeRanks;
    private Map<String, String> encoder;

    public CodellamaBPE(String tokenizerFile) throws IOException {

        JsonNode tokenizerJsonNode;
        ObjectMapper mapper = new ObjectMapper();
        try {
            URL source = ResourceUtils.getURL(tokenizerFile);
            tokenizerJsonNode = mapper.readTree(source);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        JsonNode modelJsonNode = tokenizerJsonNode.get("model");
        if (modelJsonNode != null) {
            JsonNode vocabNode = modelJsonNode.get("vocab");
            ArrayNode mergesArray = (ArrayNode) modelJsonNode.get("merges");
            this.bpeRanks = loadBpeRanks(mergesArray);
            this.encoder = loadEncoder(vocabNode);
        }
    }


    private Map<String, Integer> loadBpeRanks(ArrayNode mergesArray) {
        ObjectMapper mapper = new ObjectMapper();
        // 将 ArrayNode 转换为 List
        List<String> list = mapper.convertValue(mergesArray, List.class);
        Map<String, Integer> bpeRanks = new HashMap<>();
        int rank = 0;
        for (String m : list) {
            bpeRanks.put(m, rank++);
        }
        return bpeRanks;
    }

    private Map<String, String> loadEncoder(JsonNode vocabNode) {
        ObjectMapper mapper = new ObjectMapper();
        // 将 JsonNode 转换为 Map
        return mapper.convertValue(vocabNode, Map.class);
    }

    public List<String> encode(String text) {
        List<String> tokens = new ArrayList<>();
        for (String word : text.split(" ")) {
            tokens.addAll(bpe(word));
        }
        return tokens;
    }

    private List<String> bpe(String word) {
        List<String> chars = new ArrayList<>(Arrays.asList(word.split("")));
        while (true) {
            int minRank = Integer.MAX_VALUE;
            String minPair = null;
            for (int i = 0; i < chars.size() - 1; i++) {
                String pair = chars.get(i) + " " + chars.get(i + 1);
                if (bpeRanks.containsKey(pair) && bpeRanks.get(pair) < minRank) {
                    minRank = bpeRanks.get(pair);
                    minPair = pair;
                }
            }
            if (minPair == null) {
                break;
            }
            String[] parts = minPair.split(" ");
            List<String> newChars = new ArrayList<>();
            boolean merged = false;
            for (int i = 0; i < chars.size(); i++) {
                if (i < chars.size() - 1 && chars.get(i).equals(parts[0]) && chars.get(i + 1).equals(parts[1]) && !merged) {
                    newChars.add(parts[0] + parts[1]);
                    i++;
                    merged = true;
                } else {
                    newChars.add(chars.get(i));
                }
            }
            chars = newChars;
        }
        List<String> tokens = new ArrayList<>();
        for (String c : chars) {
            String v = encoder.getOrDefault(c, c);
            tokens.add(v);
        }
        return tokens;
    }

    public static void main(String[] args) {
        try {
            CodellamaBPE bpe = new CodellamaBPE("classpath:codellama/tokenizer.json");
            String text = "Your prompt text here";
            List<String> tokens = bpe.encode(text);
            System.out.println("Token count: " + tokens.size());
            System.out.println("Tokens: " + tokens);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}