package kr.gdu.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.gdu.dao.mapper.BoardMapper;
import kr.gdu.logic.Board;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSessionTemplate template;
	private Class<BoardMapper> cls = BoardMapper.class;
	private Map<String, Object> param = new HashMap<>();
	
	public int count(String boardid, String searchtype, String searchcontent) {
		
		param.clear();
		param.put("boardid", boardid);
		param.put("searchtype", searchtype);
		param.put("searchcontent", searchcontent);
		
		return template.getMapper(cls).count(param);
	}
	
	public List<Board> list(Integer pageNum, 
			int limit, 
			String boardid, 
			String searchtype, 
			String searchcontent) {
		
		param.clear();
		param.put("startrow", (pageNum - 1) * limit);
		param.put("limit", limit);
		param.put("boardid", boardid);
		param.put("searchtype", searchtype);
		param.put("searchcontent", searchcontent);
		
		return template.getMapper(cls).select(param);
	}

	public Board selectOne(String num) {

		return template.getMapper(cls).selectOne(num);
	}

	public int addReadCnt(String num) {

		return template.getMapper(cls).addReadCnt(num);
	}

	public int insert(Board board) {
		return template.getMapper(cls).insert(board);
		
	}

	public int maxNum() {
		return template.getMapper(cls).maxNum();
	}

	public int update(Board board) {
		return template.getMapper(cls).update(board);
	}

	public String getPass(int num) {
		return template.getMapper(cls).getPass(num);
	}

	public int deleteBoard(int num) {
		return template.getMapper(cls).deleteBoard(num);
		
	}

	public int deleteComment(int num) {
		return template.getMapper(cls).deleteComment(num);
		
	}

	public void grpStepAdd(Board board) {
		template.getMapper(cls).grpStepAdd(board);
	}

	public List<Map<String, Object>> graph1(String id) {
		return template.getMapper(cls).graph1(id);
	}
	
	
}
