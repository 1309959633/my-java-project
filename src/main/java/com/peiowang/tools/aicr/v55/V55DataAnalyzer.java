package com.peiowang.tools.aicr.v55;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.peiowang.tools.aicr.utils.ExcelUtil;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class V55DataAnalyzer {

    public static void main(String[] args) {

        String inputExcelFilePath = "/Users/wangpei/Documents/aicr/V55（codellama和qwen）回放流水分析.xlsx";
        List<ObjectNode> datas = ExcelUtil.extractDatas(inputExcelFilePath);
        categoryAnalyze(datas);
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

        Map<String, Integer> v54PointResultCountMap = getModelResultData(datas, "v54");
        Map<String, Integer> qwenPointResultCountMap = getModelResultData(datas, "qwen");
        Map<String, Integer> codellamaPointResultCountMap = getModelResultData(datas, "codellama");

        Map<String, Integer> v54PointGoodResultCountMap = getGoodResultData(datas, "v54");
        Map<String, Integer> qwenPointGoodResultCountMap = getGoodResultData(datas, "qwen");
        Map<String, Integer> codellamaPointGoodResultCountMap = getGoodResultData(datas, "codellama");

        Map<String, Integer> v54PointBadResultCountMap = getBadResultData(datas, "v54");
        Map<String, Integer> qwenPointBadResultCountMap = getBadResultData(datas, "qwen");
        Map<String, Integer> codellamaPointBadResultCountMap = getBadResultData(datas, "codellama");

        //5.5-codellama
        //5.5-qwen
        Map<String, Integer> qwenPointOptimizationCountMap = getPointOptimizationCountData(datas, "5.5-qwen");
        Map<String, Integer> codellamaPointOptimizationCountMap = getPointOptimizationCountData(datas, "5.5-codellama");

        Map<String, Integer> qwenPointDegradationCountMap = getPointDegradationCountData(datas, "5.5-qwen");
        Map<String, Integer> codellamaPointDegradationCountMap = getPointDegradationCountData(datas, "5.5-codellama");

        System.out.println("==========point=======");
        System.out.println("要点" + "\t" + "样本数" + "\t" + "v5.4输出结果数" + "\t" + "v5.4 good"
                + "\t" + "v5.4 bad" + "\t" + "qwen输出结果数" + "\t" + "qwen good" + "\t" + "qwen bad"
                + "\t" + "qwen优化数" + "\t" + "qwen劣化数" + "\t" + "codellama输出结果数" + "\t" + "codellama good"
                + "\t" + "codellama bad" + "\t" + "codellama 优化数" + "\t" + "codellama 劣化数");
        pointCountMap.keySet().forEach(point -> {
            Integer count = pointCountMap.getOrDefault(point, 0);
            Integer v54PointResultCount = v54PointResultCountMap.getOrDefault(point, 0);
            Integer v54PointGoodResultCount = v54PointGoodResultCountMap.getOrDefault(point, 0);
            Integer v54PointBadResultCount = v54PointBadResultCountMap.getOrDefault(point, 0);

            Integer qwenPointResultCount = qwenPointResultCountMap.getOrDefault(point, 0);
            Integer qwenPointGoodResultCount = qwenPointGoodResultCountMap.getOrDefault(point, 0);
            Integer qwenPointBadResultCount = qwenPointBadResultCountMap.getOrDefault(point, 0);
            Integer qwenPointOptimizationCount = qwenPointOptimizationCountMap.getOrDefault(point, 0);
            Integer qwenPointDegradationCount = qwenPointDegradationCountMap.getOrDefault(point, 0);

            Integer codellamaPointResultCount = codellamaPointResultCountMap.getOrDefault(point, 0);
            Integer codellamaPointGoodResultCount = codellamaPointGoodResultCountMap.getOrDefault(point, 0);
            Integer codellamaPointBadResultCount = codellamaPointBadResultCountMap.getOrDefault(point, 0);
            Integer codellamaPointOptimizationCount = codellamaPointOptimizationCountMap.getOrDefault(point, 0);
            Integer codellamaPointDegradationCount = codellamaPointDegradationCountMap.getOrDefault(point, 0);

            System.out.println(point + "\t" + count + "\t" + v54PointResultCount + "\t" + v54PointGoodResultCount
                    + "\t" + v54PointBadResultCount + "\t" + qwenPointResultCount + "\t" + qwenPointGoodResultCount + "\t" + qwenPointBadResultCount
                    + "\t" + qwenPointOptimizationCount + "\t" + qwenPointDegradationCount + "\t" + codellamaPointResultCount + "\t" + codellamaPointGoodResultCount
                    + "\t" + codellamaPointBadResultCount + "\t" + codellamaPointOptimizationCount + "\t" + codellamaPointDegradationCount);

        });
        System.out.println("==========point=======");
    }


    /**
     * 获取优化的数据
     *
     * @param datas
     * @param model
     * @return
     */
    private static Map<String, Integer> getPointOptimizationCountData(List<ObjectNode> datas, String model) {
        Map<String, Integer> map = new HashMap<>();
        for (ObjectNode data : datas) {
            String crPointTitle = data.get("crPointTitle").asText();
            //  排除表头
            if (crPointTitle.equals("crPointTitle")) {
                continue;
            }
//5.5-codellama

            String pickModels = data.get("满意模型").asText();
            if (Objects.equals(pickModels, "")) {

            }
            //优化
            if (pickModels.contains(model) && !pickModels.contains("5.4")) {
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
            String pickModels = data.get("满意模型").asText();
            if (Objects.equals(pickModels, "")) {

            }
            //劣化
            if (!pickModels.contains(model) && pickModels.contains("5.4")) {
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

            if (Objects.equals(model, "v54")) {
                String a = data.get("labelCategory").asText();
                if (Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "qwen")) {
                String a = data.get("QwenCategory").asText();
                if (Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "codellama")) {
                String a = data.get("CodeLlamaCategory").asText();
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

            if (Objects.equals(model, "v54")) {
                String a = data.get("labelCategory").asText();
                if (!Objects.equals("否", a) && !Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "qwen")) {
                String a = data.get("QwenCategory").asText();
                if (!Objects.equals("否", a) && !Objects.equals("有效建议", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "codellama")) {
                String a = data.get("CodeLlamaCategory").asText();
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

            if (Objects.equals(model, "v54")) {
                String a = data.get("labelCategory").asText();
                if (!Objects.equals("否", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "qwen")) {
                String a = data.get("QwenCategory").asText();
                if (!Objects.equals("否", a)) {
                    updatePointCountMap(map, crPointTitle);
                }
            }
            if (Objects.equals(model, "codellama")) {
                String a = data.get("CodeLlamaCategory").asText();
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

    /**
     * 按分类整体做分析
     *
     * @param datas
     * @return
     */
    public static Map<String, Map<String, Integer>> categoryAnalyze(List<ObjectNode> datas) {
        //统计整体好评率
        Map<String, Integer> v54CategoryCountMap = new HashMap<>();
        Map<String, Integer> qwenCategoryCountMap = new HashMap<>();
        Map<String, Integer> codellamaCategoryCountMap = new HashMap<>();
        for (ObjectNode data : datas) {
            String crPointTitle = data.get("crPointTitle").asText();
            //  排除表头
            if (crPointTitle.equals("crPointTitle")) {
                continue;
            }
            updateCategoryCountMap(data, "labelCategory", v54CategoryCountMap);
            updateCategoryCountMap(data, "QwenCategory", qwenCategoryCountMap);
            updateCategoryCountMap(data, "CodeLlamaCategory", codellamaCategoryCountMap);
        }

        Map<String, Map<String, Integer>> categoryCountMap = new HashMap<>();
        Set<String> categorys = new HashSet<>();
        categorys.addAll(v54CategoryCountMap.keySet());
        categorys.addAll(qwenCategoryCountMap.keySet());
        categorys.addAll(codellamaCategoryCountMap.keySet());
        System.out.println("==========category=======");
        categorys.forEach(category -> {
            Map<String, Integer> m = new HashMap<>();
            m.put("v54CategotyCount", v54CategoryCountMap.getOrDefault(category, 0));
            m.put("qwenCategotyCount", qwenCategoryCountMap.getOrDefault(category, 0));
            m.put("codellamaCategotyCount", codellamaCategoryCountMap.getOrDefault(category, 0));
            categoryCountMap.put(category, m);
            System.out.println(category + "\t" + m.get("v54CategotyCount") + "\t" + m.get("qwenCategotyCount") + "\t" + m.get("codellamaCategotyCount"));
//            System.out.println("v54CategotyCount->" + m.get("v54CategotyCount"));
//            System.out.println("qwenCategotyCount->" + m.get("qwenCategotyCount"));
//            System.out.println("codellamaCategotyCount->" + m.get("codellamaCategotyCount"));
        });
        System.out.println("==========category=======");
        return categoryCountMap;
    }

    private static void updateCategoryCountMap(ObjectNode data, String label, Map<String, Integer> categoryCountMap) {
        String category = data.get(label).asText();
        if (!Objects.equals(category, "否")) {
            if (categoryCountMap.containsKey(category)) {
                categoryCountMap.put(category, categoryCountMap.get(category) + 1);
            } else {
                categoryCountMap.put(category, 1);
            }
        }
    }


}
