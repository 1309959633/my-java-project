package com.peiowang.tools.aicr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.FileInputStream;
import java.util.Objects;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class SftAnalyzer {

    public static void main(String[] args) {
        extractSftFromExcel2jsonl();
    }

    /**
     * 将excel中的训练数据读取出来，转化为jsonl文件
     */
    public static void extractSftFromExcel2jsonl() {
        String inputFilePath = "/Users/wangpei/Downloads/v_d_list_2024-12-19_15-41-05_1.xlsx";
        String outputFilePath = "/Users/wangpei/Downloads/v_d_list_2024-12-19_15-41-05.jsonl";

        try (FileInputStream fis = new FileInputStream(inputFilePath);
                Workbook workbook = WorkbookFactory.create(fis);
                FileOutputStream fos = new FileOutputStream(outputFilePath)) {

            Sheet sheet = workbook.getSheetAt(0);
            ObjectMapper objectMapper = new ObjectMapper();

            for (Row row : sheet) {
                ObjectNode jsonNode = objectMapper.createObjectNode();
                for (Cell cell : row) {
                    String columnName = sheet.getRow(0).getCell(cell.getColumnIndex()).getStringCellValue();
                    switch (cell.getCellType()) {
                        case STRING:
                            jsonNode.put(columnName, cell.getStringCellValue());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                jsonNode.put(columnName, cell.getDateCellValue().toString());
                            } else {
                                jsonNode.put(columnName, cell.getNumericCellValue());
                            }
                            break;
                        case BOOLEAN:
                            jsonNode.put(columnName, cell.getBooleanCellValue());
                            break;
                        default:
                            jsonNode.put(columnName, "");
                    }
                }
                fos.write((jsonNode.toString() + System.lineSeparator()).getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 从训练数据集的json文件中解析出sft数据，并输出到EXcel中
     */
    private static void extractSftFromJsonFile2Excel() {
        String inputFilePath = "/Users/wangpei/Downloads/v5_3_update_cr_point_to_v5_5_java.jsonl";
        String outputFilePath = "/Users/wangpei/Downloads/sft1217.xlsx";

        ObjectMapper objectMapper = new ObjectMapper();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        try (BufferedReader br = Files.newBufferedReader(Paths.get(inputFilePath))) {
            String line;
            int rowNum = 0;

            while ((line = br.readLine()) != null) {
                JsonNode jsonNode = objectMapper.readTree(line);
                Row row = sheet.createRow(rowNum++);

                int cellNum = 0;
                Iterator<String> fieldNames = jsonNode.fieldNames();
                while (fieldNames.hasNext()) {
                    String fieldName = fieldNames.next();
                    if (Objects.equals("code_block", fieldName) || Objects.equals("cr_points", fieldName) || Objects.equals("prompt", fieldName) || Objects.equals("cr_result_json",
                            fieldName)) {

                        JsonNode fieldValue = jsonNode.get(fieldName);

                        Cell cell = row.createCell(cellNum++);
                        cell.setCellValue(fieldValue.asText());
                    }
                }
            }

            try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
                workbook.write(fileOut);
            }

            System.out.println("Data has been written to " + outputFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
