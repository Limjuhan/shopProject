package kr.gdu.logic;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {
	
	private int num;
	private String boardid;
	@NotEmpty(message="작성자 입력하세요")
	private String writer;
	@NotEmpty(message="비밀번호 입력하세요")
	private String pass;
	@NotEmpty(message="제목 입력하세요")
	private String title;
	@NotEmpty(message="내용 입력하세요")
	private String content;
	private MultipartFile file1;
	private String fileurl;
	private Date regdate;
	private int readcnt;
	private int grp;
	private int grplevel;
}
