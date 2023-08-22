<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>BoardList</title>
</head>
<body>
	<table class="table table-hover">
	  <thead>
	    <tr>
	      <th scope="col">번호</th>
	      <th scope="col">제목</th>
	      <th scope="col">내용</th>
	      <th scope="col">작성자</th>
	      <th scope="col">등록일</th>
	      <th scope="col">수정일</th>
	    </tr>
	  </thead>
	  <tbody>
	    <c:forEach items="${list}" var="i">
		    <tr>
		      <td>${i.bno}</td>
		      <td>${i.title}</td>
		      <td>${i.content}</td>
		      <td>${i.writer}</td>
		      <td>${i.regDate}</td>
		      <td>${i.updateDate}</td>
		    </tr>
		</c:forEach>
	  </tbody>
	</table>
</body>
</html>