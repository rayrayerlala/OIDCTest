<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Registor</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
	</head>
	<body>
		<form method="post" action="http://localhost:8080/OIDCTest/oidctest/service/registor">
			<input type="hidden" name="idToken" value="${idToken }"><p />
			此帳號未註冊<p />
			<a href='http://localhost:8080/OIDCTest/index.jsp'>重新登入</a><p />
			<button type="submit">註冊</button>
		</form>
	
	</body>
</html>