<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Menu</title>
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/purecss@3.0.0/build/pure-min.css">
	</head>
	<body>
		<form method="post" action="http://localhost:8080/OIDCTest/oidctest/service/delete">
			${oidcUser.username} 登入成功 !<br>
			id: ${oidcUser.id}<br>
			email: ${oidcUser.email }<br>
			<button type="submit">刪除帳戶</button>
		</form>
	
	</body>
</html>