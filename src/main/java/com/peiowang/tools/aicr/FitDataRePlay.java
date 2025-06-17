package com.peiowang.tools.aicr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.peiowang.tools.aicr.excuters.V53Excuter;
import com.peiowang.tools.aicr.excuters.V55CodeLlamaExcuter;
import com.peiowang.tools.aicr.excuters.V55QwenExcuter;
import com.peiowang.tools.aicr.utils.ExcelUtil;
import com.peiowang.tools.aicr.utils.PromptUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * 多要点case重放实验
 */
public class FitDataRePlay {


    public static void main(String[] args) throws Exception {
        String inputExcelFilePath = "/Users/wangpei/Documents/aicr/rag_54_fit.xlsx";
        String outputExcelFilePath = "/Users/wangpei/Documents/aicr/rag_55_fit_replay.xlsx";

        //从excel中提起出需要重放的prompt、result，要点、代码块。标注状态等
        List<ObjectNode> promptDatas = ExcelUtil.extractDatas(inputExcelFilePath);

        for (ObjectNode data : promptDatas) {
            //替换新的prompt并解析出新的结果
            String prompt = data.get("prompt").asText();
            if (prompt.equals("prompt")) {
                continue;
            }
            prompt = PromptUtil.removeSpecialToken(prompt);
            String v55QwenResult = V55QwenExcuter.excute(prompt);
            data.put("v55QwenResult", v55QwenResult);
            String v55CodeLlamaResult = V55CodeLlamaExcuter.excute(prompt);
            data.put("v55CodeLlamaResult", v55CodeLlamaResult);
        }
        ExcelUtil.write2Excel(outputExcelFilePath, promptDatas);
    }


}
