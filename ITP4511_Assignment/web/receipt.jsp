<%-- 
   Document   : register
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Receipt</title>
	<script src="https://code.jquery.com/jquery-3.6.4.min.js"
		integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
</head>
<script>
	$(document).ready(function () {
		var params = new window.URLSearchParams(window.location.search);
		var file = params.get('file');
		if (file.split(".")[1] === "pdf") {
			$("#filePdf").attr("data", file);
			$("#filePdf").css("display", "block");
		} else {
			$("#fileImg").attr("src", file);
			$("#fileImg").css("display", "block");
		}
	});
</script>

<body style="width:100%; height:100vh; overflow: hidden; margin: 0;">
	<object id="filePdf" width="100%" height="100%" style="display: none;">
	</object>
	<div style="width:100%; height:100vh;">
		<img id="fileImg" height="100%" style="display: none; margin: auto;">
		</img>
	</div>

</body>

</html>