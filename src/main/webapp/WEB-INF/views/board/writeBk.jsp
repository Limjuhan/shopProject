<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>게시글 작성</title>
	<script src="https://cdn.ckeditor.com/ckeditor5/12.4.0/classic/ckeditor.js"></script>
	<script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>
	<script src="/ckeditor/ckeditor.js"></script>
</head>
<body class="bg-light">

	<div class="container mt-5">
		<div class="card shadow">
			<div class="card-header bg-success text-white">
				<h4 class="mb-0">게시글 작성</h4>
			</div>
			<div class="card-body">
				<form:form modelAttribute="board" action="write" enctype="multipart/form-data" name="f" class="needs-validation">
					<input type="hidden" name="boardid" value="${param.boardid}">
					<div class="mb-3">
						<label class="form-label fw-bold">글쓴이</label>
						<form:input path="writer" cssClass="form-control"/>
						<form:errors path="writer" cssClass="text-danger"/>
					</div>

					<div class="mb-3">
						<label class="form-label fw-bold">비밀번호</label>
						<form:input path="pass" cssClass="form-control" type="password"/>
						<form:errors path="pass" cssClass="text-danger"/>
					</div>

					<div class="mb-3">
						<label class="form-label fw-bold">제목</label>
						<form:input path="title" cssClass="form-control"/>
						<form:errors path="title" cssClass="text-danger"/>
					</div>

					<div class="mb-3">
						<label class="form-label fw-bold">내용</label>
						<form:textarea path="content" id="editor" cssClass="form-control" rows="5"/>
						<form:errors path="content" cssClass="text-danger"/>
					</div>

					<div class="mb-3">
						<label class="form-label fw-bold">첨부파일</label>
						<input type="file" name="file1" class="form-control">
					</div>

					<div class="d-flex justify-content-between">
						<button type="submit" class="btn btn-primary">게시글 등록</button>
						<a href="list?boardid=${param.boardid}" class="btn btn-secondary">게시글 목록</a>
					</div>

				</form:form>
			</div>
		</div>
	</div>
	
<script>
	
	ClassicEditor
	    .create(document.querySelector("#editor"),{
	        ckfinder: {
	            uploadUrl : '/image/upload'
	        }
	    })
	    .then(editor => {
	    })
	    .catch(error => {
	        console.log(error);
	    });
</script>
</body>
</html>
