<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insertPage</title>
</head>
<body>
	<form action="/insert" method="post">
		<div>
			<label for="title">제목</label>
			<input type="text" name="title" />
		</div>
		<div>
			<label for="title">내용</label>
			<input type="text" name="content" />
		</div>
		<div>
			<label for="title">작성자</label>
			<input type="text" name="writer" />
		</div>
		<div>
			<button type="submit">등록</button>
			<button type="reset">취소</button>
		</div>
	</form>
</body>
</html>