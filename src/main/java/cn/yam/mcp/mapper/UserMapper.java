package cn.yam.mcp.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Select("SHOW CREATE TABLE user")
    String getDDL();
    
    @Select("${sql}")
    List<Map<String, Object>> executeQuery(@Param("sql") String sql);

    @Update("${sql}")
    int executeUpdate(@Param("sql") String sql);
}