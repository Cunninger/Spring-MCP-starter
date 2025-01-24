package cn.yam.mcp.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("SHOW CREATE TABLE user")
    Map<String, String> getDDL();
    
    @Select("${sql}")
    List<Map<String, Object>> executeQuery(@Param("sql") String sql);

    @Update("${sql}")
    int executeUpdate(@Param("sql") String sql);

}