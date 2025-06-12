package kr.gdu.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.gdu.logic.Board;

@Mapper
public interface BoardMapper {
	
	String select = "select num, "
						 + "writer, "
						 + "pass, "
						 + "title, "
						 + "content, "
						 + "file1, "
						 + "fileurl, "
						 + " regdate, "
						 + "readcnt, "
						 + "grp, "
						 + "grplevel, "
						 + "grpstep, "
						 + "boardid "
				    + "from board";

	@Select({"<script>",
		"select count(*) "
		+ "from board "
		+ "where boardid = #{boardid} ",
		"<if test='searchtype != null'> "
		+ " and ${searchtype} like '%${searchcontent}% </if>",
		"</script>"})
	int count(Map<String, Object> param);
	
	@Select({"<script>",
        select,
        "<where>", 
        "   <if test='num != null'>",
        "       num = #{num}",
        "   </if>",
        "   <if test='boardid != null'>",
        "       AND boardid = #{boardid}", // 앞선 조건이 있다면 AND로 연결
        "   </if>",
        "   <if test='searchtype != null'>", 
        "       AND ${searchtype} LIKE CONCAT('%', #{searchcontent}, '%')", // CONCAT 함수 사용 (SQL Injection 방지 및 가독성)
        "   </if>",
        "</where>",
        "<if test='limit != null'>",
        "   ORDER BY grp DESC, grpstep ASC LIMIT #{startrow}, #{limit}",
        "</if>",
        "</script>"})
	List<Board> select(Map<String, Object> param);

}










