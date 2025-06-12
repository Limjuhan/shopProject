package kr.gdu.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import kr.gdu.logic.Board;
import kr.gdu.service.BoardService;

@Controller
@RequestMapping("board")
public class BoardController {
	
	private final BoardService boardService;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("*")
	public ModelAndView write() {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject(new Board());
		
		return mav;
	}
	
	/*
	 * Spring에서 파라미터 전달 방식
	 * 	1. 파라미터이름과 매개변수의 이름이 같은 경우 매핑
	 * 	2. Bean 클래스의 프로퍼티명과 파라미터이름이 같은 경우 매핑
	 * 	3. Map객체에 RequestParam 어노테이션을 이용한 매핑
	 * 		
	 * 
	 */
	@RequestMapping("list")
	public ModelAndView list(@RequestParam Map<String, String> param,
			HttpSession session) {
		
		Integer pageNum = null;
		
		for (String key : param.keySet()) {
			if (!StringUtils.hasText(param.get(key))) {
				param.put(key, null);
			}
		}
		
		if (StringUtils.hasText(param.get("pageNum"))) {
		    try {
		        pageNum = Integer.parseInt(param.get("pageNum"));
		    } catch (NumberFormatException e) {
		        // 숫자로 변환할 수 없는 경우, 기본값 1로 설정하거나 에러 처리
		        pageNum = 1;
		    }
		} else {
		    pageNum = 1;
		}
		
		String boardid = param.get("boardid");
		String searchtype = param.get("searchtype");
		String searchcontent = param.get("searchcontent");
		
		ModelAndView mav = new ModelAndView();
		String boardName = null;
		
		switch(boardid) {
		
		case "1" : boardName = "공지사항";
			break;
			
		case "2" : boardName = "자유게시판";
			break;
		
		case "3" : boardName = "QnA";
			break;
		default : boardName = "공지사항";
			boardid = "1";
		}
		
		// 게시판 조회 처리
		int limit = 10;
		int listcount = boardService.boardcount(boardid, searchtype, searchcontent);
		List<Board> boardlist = boardService.boardlist(pageNum, limit, boardid, searchtype, searchcontent);
		int maxpage = (int) ((double) listcount / limit + 0.95);
		int startpage = (int) ((pageNum / 10.0 + 0.9) - 1) * 10 + 1;
		int endpage = startpage + 9;
		if (endpage > maxpage) endpage = maxpage;
		int boardno = listcount - (pageNum - 1) * limit;
		
		mav.addObject("boardid", boardid);
		mav.addObject("boardName", boardName);
		mav.addObject("pageNum", pageNum);
		mav.addObject("maxpage", maxpage);
		mav.addObject("startpage", startpage);
		mav.addObject("endpage", endpage);
		mav.addObject("listcount", listcount);
		mav.addObject("boardlist", boardlist);
		mav.addObject("boardno", boardno);
		
		return mav;
	}
	
}


















