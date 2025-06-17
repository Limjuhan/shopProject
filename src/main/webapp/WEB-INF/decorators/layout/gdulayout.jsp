<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<c:set var="path" value="${pageContext.request.contextPath}" scope="application" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8" />
<title><sitemesh:write property="title" /></title>

<meta name="viewport" content="width=device-width, initial-scale=1" />

<!-- Bootstrap 5 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<sitemesh:write property="head" />
<style>
body {
	overflow-x: hidden;
	padding-top: 56px; /* fixed top navbar 높이 */
}

/* 사이드바 */
.sidebar {
	width: 250px;
	position: fixed;
	top: 56px; /* navbar 높이만큼 띄움 */
	bottom: 0;
	left: 0;
	background-color: #f8f9fa;
	overflow-y: auto;
	z-index: 1000;
	transition: all 0.3s ease;
	padding-top: 1rem;
}

.sidebar.collapsed {
	width: 80px;
}

/* 사이드바 링크 텍스트 넘침 처리 */
.sidebar .list-group-item {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

/* 메인 콘텐츠 */
.main-content {
	margin-left: 250px;
	padding: 1.5rem;
	transition: all 0.3s ease;
}

.main-content.collapsed {
	margin-left: 80px;
}

/* 푸터 */
.footer {
  background: linear-gradient(135deg, #343a40, #212529); /* 그라디언트 배경 */
  border-top: none; /* 기존 테두리 제거 */
  padding: 2rem 0;
}

.footer .form-select {
  max-width: 180px; /* 드롭다운 너비 제한 */
  background-color: #495057; /* 어두운 배경 */
  color: white;
  border-color: #6c757d;
}

.footer .form-select:focus {
  background-color: #495057;
  border-color: #adb5bd;
  box-shadow: 0 0 0 0.25rem rgba(173, 181, 189, 0.25);
}

.footer a {
  transition: transform 0.2s; /* 호버 애니메이션 */
}

.footer a:hover {
  transform: scale(1.2); /* 호버 시 확대 */
}

#footer-info {
    display: flex; /* flexbox 사용 */
    align-items: center; /* 세로 중앙 정렬 */
    /* justify-content: center; 가로 중앙 정렬이 필요 없으므로 제거 또는 주석 처리 */
    gap: 10px; /* 요소들 간 간격 */
}

#footer-info img {
    height: 20px; /* 로고 이미지 크기 조정 (예시) */
    vertical-align: middle; /* 텍스트와 세로 정렬 */
}
@media (max-width: 576px) {
  .footer .d-flex {
    flex-direction: column; /* 모바일에서 세로 배치 */
    gap: 0.5rem;
  }

  .footer .form-select {
    max-width: 100%; /* 모바일에서 드롭다운 너비 100% */
  }
}

/* 환율 정보 컨테이너 */
.exchange-container {
  width: 100%;
  padding: 8px; /* 적당한 여백 */
  box-sizing: border-box;
}

#exchange {
  width: 100%;
  font-size: 1rem; /* 폰트 크기 정상화 */
  line-height: 1.4; /* 읽기 편한 줄 간격 */
  word-wrap: break-word; /* 긴 텍스트 줄바꿈 */
  overflow-x: hidden; /* 좌우 스크롤 방지 */
  box-sizing: border-box;
}

/* 환율 정보 테이블 */
#exchange table {
  width: 100%;
  table-layout: fixed; /* 셀 너비 고정 */
  border-collapse: collapse;
}

#exchange table td, #exchange table th {
  padding: 4px;
  font-size: 0.9rem; /* 테이블 텍스트 적당히 */
  overflow: hidden;
  text-overflow: ellipsis; /* 긴 텍스트 생략 */
  white-space: nowrap; /* 테이블 셀에서 줄바꿈 방지 */
}

/* 사이드바 접힌 상태 */
.sidebar.collapsed .exchange-container {
  padding: 4px;
}

.sidebar.collapsed #exchange {
  font-size: 0.85rem; /* 접힌 상태에서 약간 작게 */
}

.sidebar.collapsed #exchange table td, 
.sidebar.collapsed #exchange table th {
  font-size: 0.8rem;
}

/* 사이드바 전체 스크롤 관리 */
.sidebar {
  overflow-y: auto; /* 기존 설정 유지 */
}
</style>
</head>
<body>

	<!-- 상단 네비게이션 -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container-fluid">
			<button class="btn btn-outline-light me-2" id="toggleSidebar">☰</button>
			<a class="navbar-brand" href="#">하나도너츠</a>
			<div class="collapse navbar-collapse">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link" href="/user/login">홈</a></li>
					<li class="nav-item"><a class="nav-link" href="#">설정</a></li>
					<li class="nav-item"><a class="nav-link" href="/user/logout">로그아웃</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- 왼쪽 사이드바 -->
	<div id="sidebar" class="sidebar">
		<div class="list-group">
			<a href="/admin/dashboard" class="list-group-item list-group-item-action">📊 대시보드</a>
			<a href="/admin/users" class="list-group-item list-group-item-action">👥 사용자 관리</a>
			<a href="/board/list?boardid=1" class="list-group-item list-group-item-action">📌 공지사항</a>
			<a href="/board/list?boardid=2" class="list-group-item list-group-item-action">💬 자유게시판</a>
			<a href="/board/list?boardid=3" class="list-group-item list-group-item-action">❓ Q&A</a>
			<a href="#" class="list-group-item list-group-item-action">⚙️ 설정</a>
		</div>
		
	</div>
	
	<!-- 메인 콘텐츠 -->
	<div id="mainContent" class="main-content">
		<sitemesh:write property="body" />
	</div>

	<!-- 푸터 -->
	<footer class="footer bg-dark text-white py-4">
		<div class="container">
			<!-- 드롭다운 영역 -->
			<div class="mb-3 text-center">
				<div class="d-flex flex-wrap gap-2 justify-content-center">
					<select name="si" class="form-select form-select-sm" onchange="getText('si')">
						<option value="">시도를 선택하세요</option>
					</select>
					<select name="gu" class="form-select form-select-sm" onchange="getText('gu')">
						<option value="">구군을 선택하세요</option>
					</select>
					<select name="dong" class="form-select form-select-sm">
						<option value="">동리를 선택하세요</option>
					</select>
				</div>
			</div>
			<!-- 회사 정보 -->
			<div class="text-center mb-3">
				<p class="mb-0">
					<i class="fas fa-donut-bite me-2"></i>하나도너츠 | 대표:  | 고객센터: 1234-5678
				</p>
			</div>
			<!-- 구분선 -->
			<hr class="bg-secondary my-3">
			<!-- 하단: 저작권 및 링크 -->
			<div class="row align-items-center">
				<div class="col-md-6 text-center text-md-start" id="footer-info">
					<p class="mb-0">© 2025 하나도너츠. 석범아 도넛 사와.</p>
				</div>
				<div class="col-md-6 text-center text-md-end">
					<a href="#" class="text-white mx-2"><i class="fab fa-facebook-f"></i></a>
					<a href="#" class="text-white mx-2"><i class="fab fa-twitter"></i></a>
					<a href="#" class="text-white mx-2"><i class="fab fa-instagram"></i></a>
				</div>
			</div>
		</div>
	</footer>
		
	<script>	
		const toggleBtn = document.getElementById("toggleSidebar");
		const sidebar = document.getElementById("sidebar");
		const mainContent = document.getElementById("mainContent");

		toggleBtn.addEventListener("click", () => {
			sidebar.classList.toggle("collapsed");
			mainContent.classList.toggle("collapsed");
		});

		$(function() {
			getSido1();
			getLogo();
		});

		function getSido1() {
			$.ajax({
				url: "${path}/ajax/select1",
				success: function(data) {
					console.log(data);
					let arr = data.substring(data.indexOf('[')+1, data.indexOf(']')).split(",");
					$.each(arr, function(i, item) {
						$("select[name=si]").append(function() {
							return "<option>" + item + "</option>";
						});
					});
				}	
			});
		}
		
		function getText(name) {
			let city = $("select[name='si']").val();
			let gu = $("select[name='gu']").val();
			let disname;
			let toptext = "구군을 선택하세요.";
			let params = "";

			if (name == "si") {
				params = "si=" + city.trim();
				disname = "gu";
			} else if (name == "gu") {
				params = "si=" + city.trim() + "&gu=" + gu.trim();
				disname = "dong";
				toptext = "동리를 선택하세요.";
			} else {
				return;
			}
			console.log('params: ', params);
			$.ajax({
				url: "/ajax/select2",
				type: "post",
				data: params,
				success: function(arr) {
					console.log(arr);
					$("select[name=" + disname + "] option").remove();
					$("select[name=" + disname + "]").append(function() {
						return "<option value=''>" + toptext + "</option>";
					});
					$.each(arr, function(i, item) {
						$("select[name=" + disname + "]").append(function() {
							return "<option>" + item + "</option>";
						});
					});
				}	
			});
		}
		function getLogo() {
		    $.ajax({
		        url: "/ajax/logo",
		        success: function(json) {
		            console.log(json);
		            var html = '<img src=' + json.logo + ' />';
		            $("#footer-info").prepend(html);
		        },
		        error: function(e) {
		            alert("로고 조회 오류: " + e.status);
		        }
		    });
		}
	</script>
</body>
</html>