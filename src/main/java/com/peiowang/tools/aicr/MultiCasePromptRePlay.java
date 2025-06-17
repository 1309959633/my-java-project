package com.peiowang.tools.aicr;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.peiowang.tools.aicr.excuters.V55CodeLlamaExcuter;
import com.peiowang.tools.aicr.excuters.V55QwenExcuter;
import com.peiowang.tools.aicr.utils.ExcelUtil;
import com.peiowang.tools.aicr.utils.PromptUtil;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 多要点case重放实验
 */
public class MultiCasePromptRePlay {


    public static void main(String[] args) throws Exception {
        List<String> filterPoints = List.of("未正常处理异常/错误类型，如不捕获异常，忽略网络处理错误等，且缺乏原因解释",
                "函数嵌套层次过深（超过4层），应该使用卫语句和提前return优化",
                "函数入参过多（超过5个）",
                "解析JSON、调用外部API、文件I/O、访问数据库时，未妥善处理可能返回的错误",
                "Stream流处理lambda表达式中代码块过多（超过3行）",
                "魔法数字/魔法字符串出现次数超过2次");
        String inputExcelFilePath = "/Users/wangpei/Documents/aicr/多要点case/case.xlsx";
        String outputExcelFilePath = "/Users/wangpei/Documents/aicr/多要点case/multiCaseResult.xlsx";

        //从excel中提起出需要重放的prompt、result，要点、代码块。标注状态等
        List<ObjectNode> promptDatas = ExcelUtil.extractDatas(inputExcelFilePath);

        String multiCasePointExcelFilePath = "/Users/wangpei/Documents/aicr/多要点case/casePoint.xlsx";
        List<ObjectNode> pointDatas = ExcelUtil.extractDatas(multiCasePointExcelFilePath);
        for (ObjectNode data : promptDatas) {
            String prompt = data.get("prompt").asText();
            prompt = PromptUtil.removeSpecialToken(prompt);
            String point = data.get("crPointTitle").asText();
            if (Objects.equals(point, "point")) {
                continue;
            }
            Optional<String> containsPoint = filterPoints.stream().filter(filterPoint -> point.contains(filterPoint)).findAny();
            if (containsPoint.isEmpty()) {
                System.out.println("过滤要点：" + point);
                continue;
            }

            String singlePointPrompt = replaceOldPoint(prompt, point, pointDatas);
            String v55QwenResult = V55QwenExcuter.excute(singlePointPrompt);
            String v55LlamaResult = V55CodeLlamaExcuter.excute(singlePointPrompt);
            data.put("singlePointPrompt", singlePointPrompt);
            data.put("v55QwenResult", v55QwenResult);
            data.put("v55LlamaResult", v55LlamaResult);

            //过滤出需要重放的要点，替换新的prompt并解析出新的结果
            String moreCasePrompt = replaceNewPoint(prompt, point, pointDatas);
            String v55QwenMoreCaseResult = V55QwenExcuter.excute(moreCasePrompt);
            String v55LlamanMoreCaseResult = V55CodeLlamaExcuter.excute(moreCasePrompt);
            data.put("moreCasePrompt", moreCasePrompt);
            data.put("v55QwenMoreCaseResult", v55QwenMoreCaseResult);
            data.put("v55LlamanMoreCaseResult", v55LlamanMoreCaseResult);

            //修改prompt对正面案例、反面案例的描述
            String goodBadTitlePrompt = replaceGoodBadTitle(moreCasePrompt);
            String v55QwenGoodBadTitleResult = V55QwenExcuter.excute(goodBadTitlePrompt);
            String v55LlamanGoodBadTitleResult = V55CodeLlamaExcuter.excute(goodBadTitlePrompt);

            data.put("goodBadTitlePrompt", goodBadTitlePrompt);
            data.put("v55QwenGoodBadTitleResult", v55QwenGoodBadTitleResult);
            data.put("v55LlamanGoodBadTitleResult", v55LlamanGoodBadTitleResult);
        }
        ExcelUtil.write2Excel(outputExcelFilePath, promptDatas);

    }

    private static String replaceGoodBadTitle(String moreCasePrompt) {
        moreCasePrompt = moreCasePrompt.replace("正面案例：", "以下例子没有违反要点：");
        moreCasePrompt = moreCasePrompt.replace("反面案例：", "以下情况违反了要点：");
        return moreCasePrompt;
    }


    private static ObjectNode getPointData(List<ObjectNode> pointDatas, String point) {

        for (ObjectNode data : pointDatas) {
            String pointTitle = data.get("point").asText();
            if (Objects.equals(pointTitle, "point")) {
                continue;
            }
            if (!Objects.equals(pointTitle, point)) {
                continue;
            }
            return data;
        }
        throw new RuntimeException("replaceMoreCase error");
    }

    private static String replaceNewPoint(String prompt, String point, List<ObjectNode> pointDatas) {
        ObjectNode pointData = getPointData(pointDatas, point);
        return replacePoint(prompt, pointData.get("moreCaseContent").asText());
    }

    private static String replaceOldPoint(String prompt, String point, List<ObjectNode> pointDatas) {
        ObjectNode pointData = getPointData(pointDatas, point);
        return replacePoint(prompt, pointData.get("singlePointContent").asText());
    }

    private static String replacePoint(String prompt, String content) {
        //
        String regex = "你需要关注下面这些评审要点：\n"
                + ".*?"
                + "\n请将审查结果按下面的JSON格式要求输出：";
        String replacement = "你需要关注下面这些评审要点：\n"
                + content
                + "\n请将审查结果按下面的JSON格式要求输出：";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL);
        Matcher matcher = pattern.matcher(prompt);
        return matcher.replaceAll(replacement);
    }

}
