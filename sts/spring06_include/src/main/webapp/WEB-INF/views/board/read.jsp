<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>getOne</title>
</head>
<body>
	<form action="/board/update" method="post">
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
			<input type="text" name="regDate" id="regDate" 
					value='<fmt:formatDate pattern='yyyy/MM/dd' value="${one.regDate}"/>'
			 		readonly />
		</div>
		<div> 
			<label for="updateDate">수정일</label>
			<input type="text" name="updateDate" id="updateDate" 
					value='<fmt:formatDate pattern='yyyy/MM/dd' value="${one.updateDate}"/>'
			 		readonly />
		</div>
		<div>
			<button type="submit">수정</button>
		</div>
	</form>
</body>
</html>