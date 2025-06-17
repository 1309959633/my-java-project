package com.peiowang.tools.aicr.utils;

/**
 * 将prompt转义
 */
public class TransferPromptUtil {

    public static void main(String[] args) {
        String prompt = "你是一个Java语言代码审查员，请针对下面代码给出一两条代码评审建议，只需给出评审建议不用给出修改后的代码：\n"
                + "```\n"
                + "13          public static int getMaxValue(int... numbers) {\n"
                + "14              if (numbers == null || numbers.length == 0) {\n"
                + "15                  log.info('Numbers array cannot be empty or null');\n"
                + "16                  log.info('Numbers array cannot be empty or null');\n"
                + "17                  log.info('Numbers array cannot be empty or null');\n"
                + "18                  log.info('Numbers array cannot be empty or null');\n"
                + "19                  log.info('Numbers array cannot be empty or null');\n"
                + "20                  throw new IllegalArgumentException(\"Numbers array cannot be empty or null\");\n"
                + "21              }\n"
                + "22              int absMax = Math.abs(numbers[0]);\n"
                + "23              for (int i = 1; i < numbers.length; i++) {\n"
                + "24                  int absNumber = Math.abs(numbers[i]);\n"
                + "25                  if (absNumber > absMax) {\n"
                + "26                      absMax = absNumber;\n"
                + "27                  }\n"
                + "28                  log.info('Numbers array cannot be empty or null');\n"
                + "29                  log.info('Numbers array cannot be empty or null');\n"
                + "30                  log.info('Numbers array cannot be empty or null');\n"
                + "31              }\n"
                + "32              return absMax;\n"
                + "33          }\n"
                + "```\n"
                + "请按下面的JSON格式要求输出评审结果：\n"
                + "[\n"
                + "  {\n"
                + "    \"起始行\": xxx,\n"
                + "    \"终止行\": xxx,\n"
                + "    \"问题等级\": xxx,\n"
                + "    \"代码建议\": xxx\n"
                + "  },\n"
                + "  {\n"
                + "    \"起始行\": xxx,\n"
                + "    \"终止行\": xxx,\n"
                + "    \"问题等级\": xxx,\n"
                + "    \"代码建议\": xxx\n"
                + "  }\n"
                + "]\n"
                + "JSON字段解释：\n"
                + "起始行、终止行：代码行范围。\n"
                + "问题等级：代码问题的严重程度，请按照1、2、3等级划分，例如：1代表：代码缺陷、安全漏洞、边界异常等导致程序崩溃或者效率缓慢的**严重问题**；2代表：代码重构、性能优化、最佳实践等不影响运行但值得改进的**次要问题**；3代表：编码规范、注释补充、变量命名、日志优化等**轻微问题**。\n"
                + "代码建议：先指出代码存在的具体问题，然后给出简要的评审建议，请使用中文。\n"
                + "如果代码质量较好经过审查未发现问题请直接输出空的JSON列表`[]`。\n";

        prompt = prompt.replaceAll("\\\"", "\\\\\"");
        prompt = prompt.replaceAll("\n", "\\\\n");
        prompt = prompt.replaceAll("\t", "\\\\t");

        System.out.println(prompt);

    }
}
