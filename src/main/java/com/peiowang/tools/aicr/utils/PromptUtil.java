package com.peiowang.tools.aicr.utils;

public class PromptUtil {


    public static String removeSpecialToken(String prompt) {

        //删除QWen的space token
        prompt = prompt.replaceAll("<\\|im_start\\|>user\n", "");
        prompt = prompt.replaceAll("\n"
                + "<\\|im_end\\|>\n"
                + "<\\|im_start\\|>assistant", "");

        //删除CodeLlama的space token
        prompt = prompt.replaceAll("<s> \\[INST] ", "");
        prompt = prompt.replaceAll("\\[/INST]", "");

        return prompt;
    }


}
