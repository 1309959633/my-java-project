package com.peiowang.tools.aicr.excuters;

import java.io.IOException;

public class V54Excuter extends VllmBaseExcuter {

    private static final String URL = "https://sh-copilot.git.woa.com/server/code-intell-chat-cr-model-server-exp-f5-4-rag-step2-x2/v1/completions";
    private static final String MODEL_PATH = "/data/code/CodeLLM-f5-4-RAG-STEP2";


    /**
     * 重放
     *
     * @param prompt
     * @return
     * @throws IOException
     */
    public String doInfer(String prompt) throws IOException {
        return super.doInfer(prompt, URL, MODEL_PATH);
    }
}
