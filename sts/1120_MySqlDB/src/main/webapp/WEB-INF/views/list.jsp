<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List</title>
</head>
<body>
	<table class="table table-hover">
	  <thead>
	    <tr>
	      <th scope="col">번호</th>
	      <th scope="col">유저</th>
	      <th scope="col">등록일</th>
	    </tr>
	  </thead>
	  <tbody>
	    <c:forEach items="${list}" var="i">
		    <tr>
		      <td>${i.num}</td>
		      <td>${i.person}</td>
		      <td>${i.regDate}</td>
		    </tr>
		</c:forEach>
	  </tbody>
	</table>
</body>
</html>