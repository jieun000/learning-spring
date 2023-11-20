<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://kit.fontawesome.com/8a075d4b91.js" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
</head>
<body id="translateBody">

	<i class="fa-solid fa-earth-americas" onclick="translateText()"></i>

	<div>
		<p>여기는</p>
		<p>첫째줄</p>
		<p>둘째줄</p>
	</div>
	<div>
		<a href="https://www.naver.com/">어린이</a>
	</div>
	
<script>
function translateText() {
    // body 엘리먼트 내의 모든 텍스트 노드 선택
    $("#translateBody").find("*").contents().filter(function() {
        return this.nodeType === 3; // 텍스트 노드만 선택
    }).each(function () {
        var originalText = $(this).text();

        // AJAX 요청으로 텍스트 번역
        var self = this; // 'this'의 참조를 캡처
        $.ajax({
            type: "POST",
            url: "/translate2",
            data: { original: originalText },
            success: function (data) {
                // 번역된 텍스트로 업데이트
                var translatedText = data.translated;
                $(self).replaceWith(document.createTextNode(translatedText));
            },
            error: function (error) {
                console.error("텍스트 번역 오류: ", error);
            }
        });
    });
}

</script>
</body>
</html>