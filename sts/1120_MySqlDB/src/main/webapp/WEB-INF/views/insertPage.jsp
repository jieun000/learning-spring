<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/insert" method="post">
		<div>
			<label for="NowUser">등록할 유저</label>
			<input type="text" name="NowUser" />
		</div>
		<div>
			<button type="submit">등록</button>
			<button type="reset">취소</button>
		</div>
	</form>
</body>
</html>