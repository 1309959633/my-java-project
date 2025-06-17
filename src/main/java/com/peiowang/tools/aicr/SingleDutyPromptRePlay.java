package com.peiowang.tools.aicr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.peiowang.tools.aicr.excuters.V55CodeLlamaExcuter;
import com.peiowang.tools.aicr.excuters.V55QwenExcuter;
import com.peiowang.tools.aicr.utils.ExcelUtil;
import com.peiowang.tools.aicr.utils.PromptUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 结果清晰度提升重放实验
 */
public class SingleDutyPromptRePlay {


    public static void main(String[] args) throws Exception {
        String filterPoint = "类/函数实现未遵守单一职责原则";
        String inputExcelFilePath = "/Users/wangpei/Documents/aicr/result_clarity/case.xlsx";
        String outputExcelFilePath = "/Users/wangpei/Documents/aicr/result_clarity/singleDutyResult.xlsx";

        //从excel中提起出需要重放的prompt、result，要点、代码块。标注状态等
        List<ObjectNode> promptDatas = ExcelUtil.extractDatas(inputExcelFilePath);
        List<ObjectNode> newDatas = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (ObjectNode data : promptDatas) {
            String prompt = data.get("prompt").asText();
            prompt = PromptUtil.removeSpecialToken(prompt);
            String point = data.get("crPointTitle").asText();
            if (!Objects.equals(point, filterPoint)) {
                continue;
            }

            String codeBlock = data.get("codeBlock").asText();
            ObjectNode newData = objectMapper.createObjectNode();
            newData.put("point", point);
            newData.put("codeBlock", codeBlock);

            newData.put("prompt", prompt);
            String oldResult = V55CodeLlamaExcuter.excute(prompt);
            newData.put("oldResult", oldResult);

            //修改prompt对正面案例、反面案例的描述
            String resultClarityPrompt = replacePoint(prompt);
            String newResult = V55CodeLlamaExcuter.excute(resultClarityPrompt);
            newData.put("resultClarityPrompt", resultClarityPrompt);
            newData.put("newResult", newResult);
            newDatas.add(newData);
        }
        ExcelUtil.write2Excel(outputExcelFilePath, newDatas);

    }

    private static String replacePoint(String moreCasePrompt) {
        moreCasePrompt = moreCasePrompt.replace("## 类/函数实现未遵守单一职责原则", "## 类/函数实现未遵守单一职责原则，当函数体总行数超过80的时候需要考虑拆分。");
        return moreCasePrompt;
    }

}
