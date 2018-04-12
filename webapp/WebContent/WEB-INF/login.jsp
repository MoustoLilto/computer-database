<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/cdb.tld" prefix="cdbTagLib" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<link href="lib/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="lib/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="lib/css/main.css" rel="stylesheet" media="screen">
</head>

<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
            <div class="pull-right">
            	<a href="?lang=fr">FR</a>
            	<a href="?lang=en">EN</a>
			</div>
        </div>
    </header>
    
	<c:url value="/login" var="loginUrl"/>
	<form action="${loginUrl}" method="post">       
	    <c:if test="${param.error != null}">        
	        <p>
	            Invalid username and password.
	        </p>
	    </c:if>
	    <c:if test="${param.logout != null}">       
	        <p>
	            You have been logged out.
	        </p>
	    </c:if>
	    <p>
	        <label for="username">Username</label>
	        <input type="text" id="username" name="username"/>	
	    </p>
	    <p>
	        <label for="password">Password</label>
	        <input type="password" id="password" name="password"/>	
	    </p>
	    <input type="hidden"                        
	        name="${_csrf.parameterName}"
	        value="${_csrf.token}"/>
	    <button type="submit" class="btn btn-primary">Log in</button>
	</form>
	
<script src="lib/js/jquery.min.js"></script>
<script src="lib/js/bootstrap.min.js"></script>
<script src="lib/js/dashboard.js"></script>

</body>
</html>