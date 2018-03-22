<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="lib/css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="lib/css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="lib/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
		</div>
	</header>

	<section id="main">
		<div class="container">	
			<div class="alert alert-danger">
				Error 500: An error has occured!
				<br/>
				<c:if test="${not empty error}">
                 	<p>${error}</p>
                </c:if>
			</div>
		</div>
	</section>

	<script src="lib/js/jquery.min.js"></script>
	<script src="lib/js/bootstrap.min.js"></script>
	<script src="lib/js/dashboard.js"></script>

</body>
</html>