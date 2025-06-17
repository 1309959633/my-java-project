package com.peiowang.tools.aicr.v55;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.peiowang.tools.aicr.utils.ExcelUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MultCasePromptDataAnalyzer {

    public static void main(String[] args) {

        String inputExcelFilePath = "/Users/wangpei/Documents/aicr/multicase/multiCaseResult2.xlsx";
        List<ObjectNode> datas = ExcelUtil.extractDatas(inputExcelFilePath);
        pointAnalyze(datas);

    }

    /**
     * 按要点分析
     *
     * @param datas
     * @return
     */
    public static void pointAnalyze(List<ObjectNode> datas) {
        //要点名，样本总数量，模型输出结果数，good,bad,好评率。qwen/codeLlama优化、劣化、持平，优化率，劣化率

        Map<String, Integer> pointCountMap = new HashMap<>();
        for (ObjectNode data : datas) {
            String crPointTitle = data.get("crPointTitle").asText();
            //  排除表头
            if (crPointTitle.equals("crPointTitle")) {
                continue;
            }
            updatePointCountMap(pointCountMap, crPointTitle);
        }

        Map<String, Integer> v55QwenPointResultCountMap = getModelResultData(datas, "v55Qwen");
        Map<String, Integer> v55LlamaPointResultCountMap = getModelResultData(datas, "v55Llama");
        Map<String, Integer> v55QwenMoreCasePointResultCountMap = getModelResultData(datas, "v55QwenMoreCase");
        Map<String, Integer> v55LlamanMoreCasePointResultCountMap = getModelResultData(datas, "v55LlamanMoreCase");
        Map<String, Integer> v55QwenGoodBadTitlePointResultCountMap = getModelResultData(datas, "v55QwenGoodBadTitle");
        Map<String, Integer> v55LlamanGoodBadTitlePointResultCountMap = getModelResultData(datas, "v55LlamanGoodBadTitle");

        Map<String, Integer> v55QwenPointGoodResultCountMap = getGoodResultData(datas, "v55Qwen");
        Map<String, Integer> v55LlamaPointGoodResultCountMap = getGoodResultData(datas, "v55Llama");
        Map<String, Integer> v55QwenMoreCasePointGoodResultCountMap = getGoodResultData(datas, "v55QwenMoreCase");
        Map<String, Integer> v55LlamanMoreCasePointGoodResultCountMap = getGoodResultData(datas, "v55LlamanMoreCase");
        Map<String, Integer> v55QwenGoodBadTitlePointGoodResultCountMap = getGoodResultData(datas, "v55QwenGoodBadTitle");
        Map<String, Integer> v55LlamanGoodBadTitlePointGoodResultCountMap = getGoodResultData(datas, "v55LlamanGoodBadTitle");

        Map<String, Integer> v55QwenPointBadResultCountMap = getBadResultData(datas, "v55Qwen");
        Map<String, Integer> v55LlamaPointBadResultCountMap = getBadResultData(datas, "v55Llama");
        Map<String, Integer> v55QwenMoreCasePointBadResultCountMap = getBadResultData(datas, "v55QwenMoreCase");
        Map<String, Integer> v55LlamanMoreCasePointBadResultCountMap = getBadResultData(datas, "v55LlamanMoreCase");
        Map<String, Integer> v55QwenGoodBadTitlePointBadResultCountMap = getBadResultData(datas, "v55QwenGoodBadTitle");
        Map<String, Integer> v55LlamanGoodBadTitlePointBadResultCountMap = getBadResultData(datas, "v55LlamanGoodBadTitle");

        Map<String, Integer> qwenMoreCasePointOptimizationCountMap = getPointOptimizationCountData(datas, "qwenMoreCase");
        Map<String, Integer> qwenGoodBadTitlePointOptimizationCountMap = getPointOptimizationCountData(datas, "qwenGoodBadTitle");
        Map<String, Integer> codeLlamaMoreCasePointOptimizationCountMap = getPointOptimizationCountData(datas, "codeLlamaMoreCase");
        Map<String, Integer> codeLlamaGoodBadTitlePointOptimizationCountMap = getPointOptimizationCountData(datas, "codeLlamaGoodBadTitle");

        Map<String, Integer> qwenMoreCasePointDegradationCountMap = getPointDegradationCountData(datas, "qwenMoreCase");
        Map<String, Integer> qwenGoodBadTitlePointDegradationCountMap = getPointDegradationCountData(datas, "qwenGoodBadTitle");
        Map<String, Integer> codeLlamaMoreCasePointDegradationCountMap = getPointDegradationCountData(datas, "codeLlamaMoreCase");
        Map<String, Integer> codeLlamaGoodBadTitlePointDegradationCountMap = getPointDegradationCountData(datas, "codeLlamaGoodBadTitle");

        System.out.println("==========qwen point=======");
        System.out.println("要点" + "\t" + "样本数" + "\t" + "输出结果数" + "\t" + "good"
                + "\t" + "bad" + "\t" + "多case prompt输出结果数" + "\t" + "多case prompt good" + "\t" + "多case prompt bad"
                + "\t" + "多case prompt优化数" + "\t" + "多case prompt劣化数" + "\t" + "替换正反例描述prompt输出结果数" + "\t" + "替换正反例描述prompt good" + "\t"
                + "替换正反例描述prompt bad" + "\t" + "替换正反例描述prompt优化数" + "\t" + "替换正反例描述prompt劣化数");
        pointCountMap.keySet().forEach(point -> {
            Integer count = pointCountMap.getOrDefault(point, 0);
            Integer v54PointResultCount = v55QwenPointResultCountMap.getOrDefault(point, 0);
            Integer v54PointGoodResultCount = v55QwenPointGoodResultCountMap.getOrDefault(point, 0);
            Integer v54PointBadResultCount = v55QwenPointBadResultCountMap.getOrDefault(point, 0);

            Integer qwenMoreCasePointResultCount = v55QwenMoreCasePointResultCountMap.getOrDefault(point, 0);
            Integer qwenMoreCasePointGoodResultCount = v55QwenMoreCasePointGoodResultCountMap.getOrDefault(point, 0);
            Integer qwenMoreCasePointBadResultCount = v55QwenMoreCasePointBadResultCountMap.getOrDefault(point, 0);
            Integer qwenMoreCasePointOptimizationCount = qwenMoreCasePointOptimizationCountMap.getOrDefault(point, 0);
            Integer qwenMoreCasePointDegradationCount = qwenMoreCasePointDegradationCountMap.getOrDefault(point, 0);

            Integer v55QwenGoodBadTitlePointResultCount = v55QwenGoodBadTitlePointResultCountMap.getOrDefault(point, 0);
            Integer v55QwenGoodBadTitlePointGoodResultCount = v55QwenGoodBadTitlePointGoodResultCountMap.getOrDefault(point, 0);
            Integer v55QwenGoodBadTitlePointBadResultCount = v55QwenGoodBadTitlePointBadResultCountMap.getOrDefault(point, 0);
            Integer qwenGoodBadTitlePointOptimizationCount = qwenGoodBadTitlePointOptimizationCountMap.getOrDefault(point, 0);
            Integer qwenGoodBadTitlePointDegradationCount = qwenGoodBadTitlePointDegradationCountMap.getOrDefault(point, 0);

            System.out.println(point + "\t" + count + "\t" + v54PointResultCount + "\t" + v54PointGoodResultCount
                    + "\t" + v54PointBadResultCount + "\t" + qwenMoreCasePointResultCount + "\t" + qwenMoreCasePointGoodResultCount + "\t" + qwenMoreCasePointBadResultCount
                    + "\t" + qwenMoreCasePointOptimizationCount + "\t" + qwenMoreCasePointDegradationCount + "\t" + v55QwenGoodBadTitlePointResultCount + "\t"
                    + v55QwenGoodBadTitlePointGoodResultCount
                    + "\t" + v55QwenGoodBadTitlePointBadResultCount + "\t" + qwenGoodBadTitlePointOptimizationCount + "\t" + qwenGoodBadTitlePointDegradationCount);

        });
        System.out.println("==========qwen point=======");

        System.out.println("==========codellama point=======");
        System.out.println("要点" + "\t" + "样本数" + "\t" + "输出结果数" + "\t" + "good"
                + "\t" + "bad" + "\t" + "多case prompt输出结果数" + "\t" + "多case prompt good" + "\t" + "多case prompt bad"
                + "\t" + "多case prompt优化数" + "\t" + "多case prompt劣化数" + "\t" + "替换正反例描述prompt输出结果数" + "\t" + "替换正反例描述prompt good" + "\t"
                + "替换正反例描述prompt bad" + "\t" + "替换正反例描述prompt优化数" + "\t" + "替换正反例描述prompt劣化数");
        pointCountMap.keySet().forEach(point -> {
            Integer count = pointCountMap.getOrDefault(point, 0);
            Integer v54PointResultCount = v55LlamaPointResultCountMap.getOrDefault(point, 0);
            Integer v54PointGoodResultCount = v55LlamaPointGoodResultCountMap.getOrDefault(point, 0);
            Integer v54PointBadResultCount = v55LlamaPointBadResultCountMap.getOrDefault(point, 0);

            Integer qwenMoreCasePointResultCount = v55LlamanMoreCasePointResultCountMap.getOrDefault(point, 0);
            Integer qwenMoreCasePointGoodResultCount = v55LlamanMoreCasePointGoodResultCountMap.getOrDefault(point, 0);
            Integer qwenMoreCasePointBadResultCount = v55LlamanMoreCasePointBadResultCountMap.getOrDefault(point, 0);
            Integer qwenMoreCasePointOptimizationCount = codeLlamaMoreCasePointOptimizationCountMap.getOrDefault(point, 0);
            Integer qwenMoreCasePointDegradationCount = codeLlamaMoreCasePointDegradationCountMap.getOrDefault(point, 0);

            Integer v55QwenGoodBadTitlePointResultCount = v55LlamanGoodBadTitlePointResultCountMap.getOrDefault(point, 0);
            Integer v55QwenGoodBadTitlePointGoodResultCount = v55LlamanGoodBadTitlePointGoodResultCountMap.getOrDefault(point, 0);
            Integer v55QwenGoodBadTitlePointBadResultCount = v55LlamanGoodBadTitlePointBadResultCountMap.getOrDefault(point, 0);
            Integer qwenGoodBadTitlePointOptimizationCount = codeLlamaGoodBadTitlePointOptimizationCountMap.getOrDefault(point, 0);
            Integer qwenGoodBadTitlePointDegradationCount = codeLlamaGoodBadTitlePointDegradationCountMap.getOrDefault(point, 0);

            System.out.println(point + "\t" + count + "\t" + v54PointResultCount + "\t" + v54PointGoodResultCount
                    + "\t" + v54PointBadResultCount + "\t" + qwenMoreCasePointResultCount + "\t" + qwenMoreCasePointGoodResultCount + "\t" + qwenMoreCasePointBadResultCount
                    + "\t" + qwenMoreCasePointOptimizationCount + "\t" + qwenMoreCasePointDegradationCount + "\t" + v55QwenGoodBadTitlePointResultCount + "\t"
                    + v55QwenGoodBadTitlePointGoodResultCount
                    + "\t" + v55QwenGoodBadTitlePointBadResultCount + "\t" + qwenGoodBadTitlePointOptimizationCount + "\t" + qwenGoodBadTitlePointDegradationCount);

        });
        System.out.println("==========codellama point=======");
    }


    /**
     * 获取优化的数据
     *
     * @param datas
     * @param model
     * @return
     */
    private static Map<String, Integer> getPointOptimizationCountData(List<ObjectNode> datas, String model) {
        //Map<String, Integer> qwenMoreCasePointOptimizationCountMap = getPointOptimizationCountData(datas, "qwenMoreCase");
        //        Map<String, Integer> qwenGoodBadTitlePointOptimizationCountMap = getPointOptimizationCountData(datas, "qwenGoodBadTitle");
        //        Map<String, Integer> codeLlamaMoreCasePointOptimizationCountMap = getPointOptimizationCountData(datas, "codeLlamaMoreCase");
        //        Map<String, Integer> codeLlamaGoodBadTitlePointOptimizationCountMap = getPointOptimizationCountData(datas, "codeLlamaGoodBadTitle");

        Map<String, Integer> map = new HashMap<>();
        for (ObjectNode data : datas) {
            String crPointTitle = data.get("crPointTitle").asText();
            //  排除表头
            if (crPointTitle.equals("crPointTitle")) {
                continue;
            }

            //原来异常的，现在正常了
            String oldName = model.startsWith("qwen") ? "v55QwenResultCategory" : "v55LlamaResultCategory";
            String oldValue = data.get(oldName).asText();
            String newName = "";
            switch (model) {
                case "qwenMoreCase":
                    newName = "v55QwenMoreCaseResultCategory";
                    break;
                case "qwenGoodBadTitle":
                    newName = "v55QwenGoodBadTitleResultCategory";
                    break;
                case "codeLlamaMoreCase":
                    newName = "v55LlamanMoreCaseResultCategory";
                    break;
                case "codeLlamaGoodBadTitle":
                    newName = "v55LlamanGoodBadTitleResultCategory";
                    break;
                default:
                    break;
            }
            String newValue = data.get(newName).asText();
            //优化:原结果错误，新结果正确
            if (!Objects.equals(oldValue, "否") && !Objects.equals(oldValue, "有效建议") && (Objects.equals(newValue, "否") || Objects.equals(newValue, "有效建议"))) {
                updatePointCountMap(map, crPointTitle);
            }

        }
        return map;
    }

    /**
     * 获取优化的数据
     *
     * @param datas
     * @param model
     * @return
     */
    private static Map<String, Integer> getPointDegradationCountData(List<ObjectNode> datas, String model) {
        Map<String, Integer> map = new HashMap<>();
        for (ObjectNode data : datas) {
            String crPointTitle = data.get("crPointTitle").asText();
            //  排除表头
            if (crPointTitle.equals("crPointTitle")) {
                continue;
            }
            //原来异常的，现在正常了
            String oldName = model.startsWith("qwen") ? "v55QwenResultCategory" : "v55LlamaResultCategory";
            String oldValue = data.get(oldName).asText();
            String newName = "";
            switch (model) {
                case "qwenMoreCase":
                    newName = "v55QwenMoreCaseResultCategory";
                    break;
                case "qwenGoodBadTitle":
                    newName = "v55QwenGoodBadTitleResultCategory";
                    break;
                case "codeLlamaMoreCase":
                    newName = "v55LlamanMoreCaseResultCategory";
                    break;
                case "codeLlamaGoodBadTitle":
                    newName = "v55LlamanGoodBadTitleResultCategory";
                    break;
                default:
                    break;
            }
            String newValue = data.get(newName).asText();
            //劣化:原结果正确，新结果错误
            if ((Objects.equals(oldValue, "否") || Objects.equals(oldValue, "有效建议")) && !Objects.equals(newValue, "否") && !Objects.equals(newValue, "有效建议")) {
                updatePointCountMap(map, crPointTitle);
            }
        }
        return map;
    }

    /**
     * 统计要点-模型输出good结果数
     *
     * @param datas
     * @param model
     * @return
     */
    private static Map<String, Integer> getGoodResultData(List<ObjectNode> datas, String model) {
        Map<String, Integer> map = new HashMap<>();
        for (ObjectNode data : datas) {
            String crPointTitle = data.get("crPointTitle").asText();
            //  排除表头
            if (crPointTitle.equals("crPointTitle")) {
                continue;
            }

            //
            if (Objects.equals(model, "v55Qwen")) {
                String a = data.get("v55QwenResultCategory").asText();
                if (Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55Llama")) {
                String a = data.get("v55LlamaResultCategory").asText();
                if (Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55QwenMoreCase")) {
                String a = data.get("v55QwenMoreCaseResultCategory").asText();
                if (Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55LlamanMoreCase")) {
                String a = data.get("v55LlamanMoreCaseResultCategory").asText();
                if (Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55QwenGoodBadTitle")) {
                String a = data.get("v55QwenGoodBadTitleResultCategory").asText();
                if (Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55LlamanGoodBadTitle")) {
                String a = data.get("v55LlamanGoodBadTitleResultCategory").asText();
                if (Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
        }
        return map;
    }

    /**
     * 统计要点-模型输出bad结果数
     *
     * @param datas
     * @param model
     * @return
     */
    private static Map<String, Integer> getBadResultData(List<ObjectNode> datas, String model) {
        Map<String, Integer> map = new HashMap<>();
        for (ObjectNode data : datas) {
            String crPointTitle = data.get("crPointTitle").asText();
            //  排除表头
            if (crPointTitle.equals("crPointTitle")) {
                continue;
            }

            if (Objects.equals(model, "v55Qwen")) {
                String a = data.get("v55QwenResultCategory").asText();
                if (!Objects.equals("否", a) && !Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55Llama")) {
                String a = data.get("v55LlamaResultCategory").asText();
                if (!Objects.equals("否", a) && !Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55QwenMoreCase")) {
                String a = data.get("v55QwenMoreCaseResultCategory").asText();
                if (!Objects.equals("否", a) && !Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55LlamanMoreCase")) {
                String a = data.get("v55LlamanMoreCaseResultCategory").asText();
                if (!Objects.equals("否", a) && !Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55QwenGoodBadTitle")) {
                String a = data.get("v55QwenGoodBadTitleResultCategory").asText();
                if (!Objects.equals("否", a) && !Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55LlamanGoodBadTitle")) {
                String a = data.get("v55LlamanGoodBadTitleResultCategory").asText();
                if (!Objects.equals("否", a) && !Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
        }
        return map;
    }

    /**
     * 统计要点-模型输出结果数
     *
     * @param datas
     * @param model
     * @return
     */
    private static Map<String, Integer> getModelResultData(List<ObjectNode> datas, String model) {
        Map<String, Integer> map = new HashMap<>();
        for (ObjectNode data : datas) {
            String crPointTitle = data.get("crPointTitle").asText();
            //  排除表头
            if (crPointTitle.equals("crPointTitle")) {
                continue;
            }

            if (Objects.equals(model, "v55Qwen")) {
                String a = data.get("v55QwenResultCategory").asText();
                if (!Objects.equals("否", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55Llama")) {
                String a = data.get("v55LlamaResultCategory").asText();
                if (!Objects.equals("否", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55QwenMoreCase")) {
                String a = data.get("v55QwenMoreCaseResultCategory").asText();
                if (!Objects.equals("否", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55LlamanMoreCase")) {
                String a = data.get("v55LlamanMoreCaseResultCategory").asText();
                if (!Objects.equals("否", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55QwenGoodBadTitle")) {
                String a = data.get("v55QwenGoodBadTitleResultCategory").asText();
                if (!Objects.equals("否", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "v55LlamanGoodBadTitle")) {
                String a = data.get("v55LlamanGoodBadTitleResultCategory").asText();
                if (!Objects.equals("否", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }


        }
        return map;
    }

    private static void updatePointCountMap(Map<String, Integer> pointCountMap, String point) {
        if (pointCountMap.containsKey(point)) {
            pointCountMap.put(point, pointCountMap.get(point) + 1);
        } else {
            pointCountMap.put(point, 1);
        }
    }

}
