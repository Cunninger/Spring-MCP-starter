package cn.yam.mcp.service;

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
        requestBody.put("model", "deepseek-chat");
        requestBody.put("stream", false);
        
        JSONArray messages = new JSONArray();
        messages.add(new JSONObject().put("role", "system").put("content", systemPrompt));
        messages.add(new JSONObject().put("role", "user").put("content", question));
        requestBody.put("messages", messages);

        // 发送HTTP请求
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        
        // 解析响应
        JSONObject responseJson = new JSONObject(response.getBody());
        return responseJson.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }
}