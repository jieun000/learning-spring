<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>getOne</title>
</head>
<body>
	<form action="/update" method="post">
		<div>
			<label for="bno">번호</label>
			<input type="text" name="bno" id="bno" value="${one.bno}" readonly />
		</div>
		<div>
			<label for="title">제목</label>
			<input type="text" name="title" id="title" value="${one.title}" />
		</div>
		<div>
			<label for="content">내용</label>
			<input type="text" name="content" id="content" value="${one.content}" />
		</div>
		<div>
			<label for="writer">작성일</label>
			<input type="text" name="writer" id="writer" value="${one.writer}" />
		</div>
		<div>
			<label for="regDate">등록일</label>
			<input type="text" name="regDate" id="regDate" value="${one.regDate}" readonly />
		</div>
		<div>
			<label for="updateDate">수정일</label>
			<input type="text" name="updateDate" id="updateDate" value="${one.updateDate}" readonly />
		</div>
		<input type="submit" value="수정" />
	</form>
</body>
</html>