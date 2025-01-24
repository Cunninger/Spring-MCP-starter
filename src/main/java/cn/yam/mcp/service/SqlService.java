package cn.yam.mcp.service;

import cn.yam.mcp.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SqlService {
    @Autowired
    private UserMapper userMapper;

    public Object executeSQL(String sql) {
        sql = sql.trim();
        if (sql.toLowerCase().startsWith("select")) {
            return userMapper.executeQuery(sql);
        } else {
            return userMapper.executeUpdate(sql);
        }
    }
}