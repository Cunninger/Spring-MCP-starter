package cn.yam.mcp.controller;

import cn.yam.mcp.core.MCPRequest;
import cn.yam.mcp.core.MCPResponse;
import cn.yam.mcp.server.SQLGenerationServer;
import cn.yam.mcp.server.SQLExecutionServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai-sql")
public class UserController {
    @Autowired
    private SQLGenerationServer sqlGenerationServer;
    @Autowired
    private SQLExecutionServer sqlExecutionServer;

    @PostMapping("/query")
    public MCPResponse<Object> handleQuery(@RequestParam String question) {
        try {
            // First MCP request - SQL Generation
            MCPRequest genRequest = new MCPRequest();
            genRequest.setType("SQL_GENERATION");
            genRequest.setContent(question);
            
            String sql = sqlGenerationServer.processRequest(genRequest);
            
            // Second MCP request - SQL Execution
            MCPRequest execRequest = new MCPRequest();
            execRequest.setType("SQL_EXECUTION");
            execRequest.setContent(sql);
            
            Object result = sqlExecutionServer.processRequest(execRequest);
            return MCPResponse.success(result);
            
        } catch (Exception e) {
            return MCPResponse.error(e.getMessage());
        }
    }
}