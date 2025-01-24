package cn.yam.mcp.service;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    @Value("${deepseek.api-key}")
    private String apiKey;

    public String generateSQL(String ddl, String question) {
        String url = "https://api.deepseek.com/chat/completions";

        String systemPrompt = "根据以下表结构生成SQL语句：\n" + ddl
                + "\n请将用户的问题转换为SQL，只返回SQL语句，不要解释";

        // 构造请求体
        JSONObject requestBody = new JSONObject();
        requestBody.set("model", "deepseek-chat");
        requestBody.set("stream", false);

        JSONArray messages = new JSONArray();
        messages.add(new JSONObject().set("role", "system").set("content", systemPrompt));
        messages.add(new JSONObject().set("role", "user").set("content", question));
        requestBody.set("messages", messages);

        // 发送HTTP请求
        HttpResponse response = HttpRequest.post(url)
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .execute();

        // 解析响应
        JSONObject responseJson = JSONUtil.parseObj(response.body());
        return responseJson.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getStr("content");
    }
}
