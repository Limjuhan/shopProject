<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<sitemesh:write property="title" />
<sitemesh:write property="head" />
<sitemesh:write property="body" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title><sitemesh:title default="관리자 대시보드" /></title>
  <sitemesh:head />
  <meta name="viewport" content="width=device-width, initial-scale=1" />

  <!-- Bootstrap 5 -->
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />

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
      text-align: center;
      padding: 1rem;
      border-top: 1px solid #dee2e6;
      margin-top: 2rem;
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
          <li class="nav-item"><a class="nav-link" href="#">로그아웃</a></li>
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
    <sitemesh:body />
  </div>

  <!-- 푸터 -->
  <footer class="footer text-muted">
    &copy; 2025 MyAdmin. All rights reserved.
  </footer>

  <!-- JS -->
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
  <script>
    const toggleBtn = document.getElementById("toggleSidebar");
    const sidebar = document.getElementById("sidebar");
    const mainContent = document.getElementById("mainContent");

    toggleBtn.addEventListener("click", () => {
      sidebar.classList.toggle("collapsed");
      mainContent.classList.toggle("collapsed");
    });
  </script>
</body>
</html>
