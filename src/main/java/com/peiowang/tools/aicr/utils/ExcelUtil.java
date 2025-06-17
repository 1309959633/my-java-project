package com.peiowang.tools.aicr.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {


    public static List<ObjectNode> extractDatas(String excelFilePath) {
        List<ObjectNode> list = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(excelFilePath);
                Workbook workbook = WorkbookFactory.create(fis)) {

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
                list.add(jsonNode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void write2Excel(String excelFilePath, List<ObjectNode> datas) {
        try {

            // 创建 Excel 工作簿
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Sheet1");

            // 创建表头
            Row headerRow = sheet.createRow(0);
            Iterator<String> fieldNames = datas.get(0).fieldNames();
            int headerIndex = 0;
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                Cell cell = headerRow.createCell(headerIndex++);
                cell.setCellValue(fieldName);
            }

            // 填充数据
            int rowIndex = 1;
            for (JsonNode node : datas) {
                Row row = sheet.createRow(rowIndex++);
                int cellIndex = 0;
                Iterator<String> fields = node.fieldNames();
                while (fields.hasNext()) {
                    String field = fields.next();
                    Cell cell = row.createCell(cellIndex++);
                    cell.setCellValue(node.get(field).asText());
                }
            }

            // 写入 Excel 文件
            try (FileOutputStream fileOut = new FileOutputStream(excelFilePath)) {
                workbook.write(fileOut);
            }

            // 关闭工作簿
            workbook.close();

            System.out.println("Excel 文件已成功生成！");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
