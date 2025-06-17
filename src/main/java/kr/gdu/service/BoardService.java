package kr.gdu.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kr.gdu.dao.BoardDao;
import kr.gdu.dao.CommDao;
import kr.gdu.logic.Board;
import kr.gdu.logic.Comment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardService {
	
	private final BoardDao boardDao;
	private final CommDao commDao;

	public BoardService(BoardDao boardDao, CommDao commDao) {
		super();
		this.boardDao = boardDao;
		this.commDao = commDao;
	}
	
	public int boardcount(String boardid, String searchtype, String searchcontent) {
		return boardDao.count(boardid,searchtype,searchcontent);
	}
	
	public List<Board> boardlist(Integer pageNum, int limit, String boardid, String searchtype, String searchcontent) {
		List<Board> result = boardDao.list(pageNum, limit, boardid,searchtype,searchcontent);
		
		return result;
	}

	public Board getBoard(String num) {
		return boardDao.selectOne(num);
		
	}

	public void addReadCnt(String num) {
		boardDao.addReadCnt(num);
		
	}

	public void boardWrite(Board board, HttpServletRequest request) {
		
		int maxNum = boardDao.maxNum();
		board.setNum(++maxNum);
		board.setGrp(maxNum);
		
		if (board.getFile1() != null && !board.getFile1().isEmpty()) {
			String path = request.getServletContext().getRealPath("/") + "board/file/";
			uploadFileCreate(board.getFile1(), path);
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		
		boardDao.insert(board);
	}

	public void updateBoard(@Valid Board board, HttpServletRequest request) {
		
		if (board.getFile1() != null && !board.getFile1().isEmpty()) {
			String path = request.getServletContext().getRealPath("/") + "board/file/";
			uploadFileCreate(board.getFile1(), path);
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		
		boardDao.update(board);
	}
	
	private void uploadFileCreate(MultipartFile file, String path) {
		
		String orgFile = file.getOriginalFilename();
		File f = new File(path);
		
		if (!f.exists()) {
			f.mkdirs();
		}
		
		try {
			file.transferTo(new File(path + orgFile));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public String getPass(int num) {
		String pwd = "";
		try {
			 pwd = boardDao.getPass(num);
		} catch(Exception e) {
			e.printStackTrace();
			log.warn("비밀번호 찾기 오류. 게시글번호:{}", num);
		}
		return pwd;
	}
	
	@Transactional
	public void deleteBoard(int num) {
		try {
			boardDao.deleteComment(num);
			if (boardDao.deleteBoard(num) < 1) {
				throw new RuntimeException("게시글 삭제 실패");
			}
		} catch(Exception e) {
			e.printStackTrace();
			log.warn("게시글 삭제 오류. 게시글 번호: {}", num);
		}
	}

	public void boardReply(Board board, HttpServletRequest request) {
		
		if (board.getFile1() != null && !board.getFile1().isEmpty()) {
			String path = request.getServletContext().getRealPath("/") + "board/file/";
			uploadFileCreate(board.getFile1(), path);
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		
		boardDao.grpStepAdd(board); // 이미 등록된 답글들 grpstep값 1씩 증가
		int max = boardDao.maxNum();
		board.setNum(++max);
		
		board.setGrplevel(board.getGrplevel() + 1);
		board.setGrpstep(board.getGrpstep() + 1);
		boardDao.insert(board);
		
	}

	public List<Comment> commentlist(int num) {
		return commDao.list(num);
	}

	public int commmaxseq(int num) {
		return commDao.maxseq(num);
	}

	public void cominsert(Comment comm) {
		commDao.insert(comm);
		
	}

	public Comment commSelectOne(int num, int seq) {
		return commDao.selectOne(num, seq);
	}

	public void commdel(int num, int seq) {
		commDao.delete(num, seq);
		
	}

	public String sidoSelect1(String si, String gu) {
		BufferedReader fr = null;
		String path = "C:/upload/data/sido.txt";
		
		try {
			fr = new BufferedReader(new FileReader(path));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Set<String> set = new LinkedHashSet<>();
		String data = null;
		
		if (si==null && gu==null) {
			try {
				while ((data=fr.readLine()) != null) {
					String[] arr = data.split("\\s+");
					if (arr.length >= 3) {
						set.add(arr[0].trim());
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		List<String> list = new ArrayList<String>(set);
		
		return list.toString();
	}

	public List<String> sidoSelect2(String si, String gu) {
		BufferedReader fr = null;
		String path = "C:/upload/data/sido.txt";
		
		try {
			fr = new BufferedReader(new FileReader(path));
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Set<String> set = new LinkedHashSet<>();
		String data = null;
		
		if (si==null && gu==null) {
			try {
				while ((data=fr.readLine()) != null) {
					String[] arr = data.split("\\s+");
					if (arr.length >= 3) {
						set.add(arr[0].trim());
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else if (gu == null) {
			si = si.trim();
			try {
				while ((data=fr.readLine()) != null) {
					String[] arr = data.split("\\s+");
					if (arr.length >= 3 && arr[0].equals(si) && !arr[1].contains(arr[0])) {
						set.add(arr[1].trim());
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			si = si.trim();
			gu = gu.trim();
			try {
				while ((data=fr.readLine()) != null) {
					String[] arr = data.split("\\s+");
					if (arr.length >= 3 && arr[0].equals(si) && arr[1].equals(gu) && !arr[0].equals(arr[1]) && !arr[2].contains(arr[1])) {
						if (arr.length > 3) {
							if (arr[3].contains(arr[1])) {
								continue;
							}
						}
						set.add(arr[2].trim());
					}
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		List<String> list = new ArrayList<String>(set);
		
		return list;
	}

	public String exchange1() {
		Document doc = null;
		List<List<String>> trlist = new ArrayList<List<String>>();
		String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
		String exdate = null;
		
		try {
			doc = Jsoup.connect(url).get();
			Elements trs = doc.select("tr");
			exdate = doc.select("p.table-unit").html();
			
			for (Element tr : trs) {
				List<String> tdlist = new ArrayList<String>();
				Elements tds = tr.select("td");
				for (Element td : tds) {
					tdlist.add(td.html());
				}
				if (tdlist.size() > 0) {
					if (tdlist.get(0).equals("USD") || tdlist.get(0).equals("CNH") || 
						tdlist.get(0).equals("JPY(100)") || tdlist.get(0).equals("EUR")) {
							trlist.add(tdlist);
						}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("<h4 class='title'> 수출입은행<br>" + exdate + "</h4>");
		sb.append("<table class='table'>");
		sb.append(
				"<tr>"
				+ "<th>통화</th>"
				+ "<th>기준율</th>"
				+ "<th>받으실때</th>"
				+ "<th>보내실때</th>"
				+ "</tr>"
				);
		
		for (List<String> tds : trlist) {
			sb.append(
					"<tr> "
					+ "<td>" + tds.get(0) + "<br>" + tds.get(1) + "</td>"
					+ "<td>" + tds.get(4) + "</td>");
			sb.append(
					"<td>" + tds.get(2) + "</td>"
					+ "<td>" + tds.get(3) + "</td>"
					);
		}
		sb.append("</table>");
		
		return sb.toString();
	}

	public Map<String, Object> exchange2() {
		Document doc = null;
		List<List<String>> trlist = new ArrayList<List<String>>();
		String url = "https://www.koreaexim.go.kr/wg/HPHKWG057M01";
		String exdate = null;
		
		try {
			doc = Jsoup.connect(url).get();
			Elements trs = doc.select("tr");
			exdate = doc.select("p.table-unit").html();
			
			for (Element tr : trs) {
				List<String> tdlist = new ArrayList<String>();
				Elements tds = tr.select("td");
				for (Element td : tds) {
					tdlist.add(td.html());
				}
				if (tdlist.size() > 0) {
					if (tdlist.get(0).equals("USD") || tdlist.get(0).equals("CNH") || 
						tdlist.get(0).equals("JPY(100)") || tdlist.get(0).equals("EUR")) {
							trlist.add(tdlist);
						}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("exdate", exdate);
		map.put("trlist", trlist);
		
		return map;
	}

	public Map<String, Integer> graph1(String id) {
		
		List<Map<String, Object>> list = boardDao.graph1(id);
		Map<String, Integer> map = new HashMap<>();
		
		for (Map<String, Object> m : list) {
			String writer = (String) m.get("writer");
			long cnt = (Long) m.get("cnt");
			map.put(writer, (int) cnt);
		}
		return map;
	}

	public Map<String, Object> getLogo() {
		Document doc = null;
		String url = "https://gudi.kr";
		String src = null;
		
		try {
			doc = Jsoup.connect(url).get();
			Elements imgElements = doc.select("img.normal_logo._front_img");
			
			// 선택된 요소가 있는지 확인
            if (!imgElements.isEmpty()) {
                Element img = imgElements.first();
                src = img.attr("src"); 
            }
        } catch(IOException e) {
			e.printStackTrace();
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("logo", src);
		
		return map;
	}
	
}


















