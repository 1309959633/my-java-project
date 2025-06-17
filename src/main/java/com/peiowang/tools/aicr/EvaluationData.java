package com.peiowang.tools.aicr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.peiowang.tools.aicr.excuters.V55CodeLlamaExcuter;
import com.peiowang.tools.aicr.excuters.V55QwenExcuter;
import com.peiowang.tools.aicr.utils.ExcelUtil;
import com.peiowang.tools.aicr.utils.PromptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 多要点case重放实验
 * 用于处理评估数据，从Excel文件中提取代码块并生成评估数据
 */
public class EvaluationData {
    private static final Logger logger = LoggerFactory.getLogger(EvaluationData.class);
    
    // 配置常量
    private static final String DEFAULT_INPUT_FILE = "/Users/wangpei/Documents/aicr/evaluationData/case.xlsx";
    private static final int BASE_ID = 10000;
    private static final String SKIP_POINT = "配置/常量/错误码分散在代码中，未集中管理";
    private static final String HEADER_POINT = "crPointTitle";
    private static final String REQUIRED_FIELD_CODE_BLOCK = "codeBlock";
    private static final String REQUIRED_FIELD_CR_POINT = "crPointTitle";
    private static final int MAX_CODE_BLOCK_LENGTH = 10000; // 防止过大的代码块

    /**
     * 主方法
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        try {
            String inputFilePath = validateAndGetInputPath(args);
            processEvaluationData(inputFilePath);
        } catch (IllegalArgumentException e) {
            logger.error("参数验证失败: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("处理评估数据时发生错误", e);
        }
    }

    /**
     * 验证并获取输入文件路径
     * @param args 命令行参数
     * @return 验证后的文件路径
     * @throws IllegalArgumentException 如果参数无效
     */
    private static String validateAndGetInputPath(String[] args) {
        String inputFilePath = args.length > 0 ? args[0] : DEFAULT_INPUT_FILE;
        Path path = Paths.get(inputFilePath);
        
        if (!Files.exists(path)) {
            throw new IllegalArgumentException("输入文件不存在: " + inputFilePath);
        }
        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("输入路径不是文件: " + inputFilePath);
        }
        if (!Files.isReadable(path)) {
            throw new IllegalArgumentException("输入文件不可读: " + inputFilePath);
        }
        
        return inputFilePath;
    }

    /**
     * 处理评估数据
     * @param inputFilePath Excel文件路径
     * @throws Exception 处理过程中的异常
     */
    private static void processEvaluationData(String inputFilePath) throws Exception {
        List<ObjectNode> promptDatas = ExcelUtil.extractDatas(inputFilePath);
        if (promptDatas == null || promptDatas.isEmpty()) {
            logger.warn("未从Excel文件中提取到数据");
            return;
        }

        Set<String> processedCodeBlocks = new HashSet<>();
        ObjectMapper objectMapper = new ObjectMapper();
        int sequenceNumber = 1;
        int processedCount = 0;
        int skippedCount = 0;

        for (ObjectNode data : promptDatas) {
            try {
                if (shouldSkipData(data)) {
                    skippedCount++;
                    continue;
                }

                String codeBlock = validateAndGetCodeBlock(data);
                if (processedCodeBlocks.contains(codeBlock)) {
                    skippedCount++;
                    continue;
                }

                processedCodeBlocks.add(codeBlock);
                ObjectNode evaluationData = createEvaluationData(objectMapper, codeBlock, sequenceNumber++);
                logger.info("生成的评估数据: {}", evaluationData);
                processedCount++;
            } catch (Exception e) {
                logger.error("处理数据时发生错误: {}", e.getMessage());
                skippedCount++;
            }
        }

        logger.info("处理完成 - 成功处理: {}, 跳过: {}", processedCount, skippedCount);
    }

    /**
     * 验证并获取代码块
     * @param data 数据节点
     * @return 验证后的代码块
     * @throws IllegalArgumentException 如果代码块无效
     */
    private static String validateAndGetCodeBlock(ObjectNode data) {
        if (!data.has(REQUIRED_FIELD_CODE_BLOCK)) {
            throw new IllegalArgumentException("缺少必要字段: " + REQUIRED_FIELD_CODE_BLOCK);
        }

        String codeBlock = data.get(REQUIRED_FIELD_CODE_BLOCK).asText().trim();
        if (codeBlock.isEmpty()) {
            throw new IllegalArgumentException("代码块为空");
        }
        if (codeBlock.length() > MAX_CODE_BLOCK_LENGTH) {
            throw new IllegalArgumentException("代码块长度超过限制: " + MAX_CODE_BLOCK_LENGTH);
        }

        return codeBlock;
    }

    /**
     * 判断是否应该跳过当前数据
     * @param data 数据节点
     * @return 是否跳过
     */
    private static boolean shouldSkipData(ObjectNode data) {
        if (!data.has(REQUIRED_FIELD_CR_POINT)) {
            logger.warn("数据缺少必要字段: {}", REQUIRED_FIELD_CR_POINT);
            return true;
        }

        String point = data.get(REQUIRED_FIELD_CR_POINT).asText();
        return Objects.equals(point, HEADER_POINT) || Objects.equals(point, SKIP_POINT);
    }

    /**
     * 创建评估数据对象
     * @param objectMapper JSON对象映射器
     * @param codeBlock 代码块
     * @param sequenceNumber 序列号
     * @return 评估数据对象
     */
    private static ObjectNode createEvaluationData(ObjectMapper objectMapper, String codeBlock, int sequenceNumber) {
        ObjectNode evaluationData = objectMapper.createObjectNode();
        evaluationData.put("id", BASE_ID + sequenceNumber);
        evaluationData.put("lang", "Java");
        evaluationData.put("lang_ext", ".java");
        evaluationData.put("code_block", codeBlock);
        evaluationData.put("auto_id", sequenceNumber);
        return evaluationData;
    }
}
