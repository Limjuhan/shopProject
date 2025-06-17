package kr.gdu.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.gdu.logic.Board;

@Mapper
public interface BoardMapper {
	
	String select = "select num, "
						 + "writer, "
						 + "pass, "
						 + "title, "
						 + "content, "
						 + "file1 as fileurl, "
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

	@Select(select + 
			" where num = #{num}")
	Board selectOne(String num);
	
	@Update("update board "
			+ "set readcnt = readcnt + 1 "
			+ "where num = #{num}")
	int addReadCnt(String num);
	
	@Insert("insert into board "
			+ "(num, boardid, writer, pass, title, content, file1, "
			+ "regdate, readcnt, grp, grplevel, grpstep) "
			+ "values(#{num}, #{boardid}, #{writer}, #{pass}, "
			+ "#{title}, #{content}, #{fileurl}, now(), 0, #{grp}, #{grplevel}, #{grpstep})")
	
	int insert(Board board);
	
	@Select("select ifnull(max(num), 0) "
			+ "from board")
	int maxNum();
	
	@Update("update board "
			+ "set writer = #{writer}, "
			+ "title = #{title}, "
			+ "content = #{content}, "
			+ "file1 = #{fileurl} "
			+ "where num = #{num}")
	int update(Board board);
	
	@Select("select pass "
			+ "from board "
			+ "where num = #{num}")
	String getPass(int num);
	
	@Delete("delete "
			+ "from board "
			+ "where num = #{num}")
	int deleteBoard(int num);
	
	@Delete("delete "
			+ "from comment "
			+ "where num = #{num}")
	int deleteComment(int num);
	
	@Update("update board "
			+ "set grpstep = grpstep+1 "
			+ "where grp = #{grp} "
			+ "and grpstep > #{grpstep}")
	void grpStepAdd(Board board);
	
	@Select("select writer, count(*) cnt "
			+ "from board "
			+ "where boardid = #{value} "
			+ "group by writer order by 2 desc limit 0, 7")
	List<Map<String, Object>> graph1(String id);


}










