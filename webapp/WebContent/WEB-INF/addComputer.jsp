<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="lib/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="lib/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="lib/css/main.css" rel="stylesheet" media="screen">
</head>

<c:url value="/logout" var="logoutUrl" />
<form action="${logoutUrl}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<script>
	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}
</script>

<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
            <a href="?lang=fr">FR</a>
           	<a href="?lang=en">EN</a>
            <div class="pull-right">
            	<a href="updateUser?username=${username}">${username}</a>
            	<a href="javascript:formSubmit()"><spring:message code="logout"/></a>
			</div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="AddComputer"/></h1>
                    <c:if test="${not empty error}">
                    	<p><spring:message code="${error}"/></p>
                    </c:if>
                    <form:form action="addComputer" method="POST" modelAttribute="DTOComputer">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="ComputerName"/></label>
                                <form:input type="text" class="form-control" path="name" name="computerName" id="computerName" placeholder="Computer name"/>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="IntroducedDate"/></label>
                                <form:input type="date" class="form-control" path="introduced" name="introduced" id="introduced" min="1970-01-01" placeholder="Introduced date"/>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="DiscontinuedDate"/></label>
                                <form:input type="date" class="form-control" path="discontinued" name="discontinued" id="discontinued" placeholder="Discontinued date"/>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="Company"/></label>
                                <form:select class="form-control" path="companyID" name="companyId" id="companyId">
                                	 <c:forEach items="${allCompanies}" var="company">
                                    	<option value="${company.id}">${company.name}</option>
                                    </c:forEach> 
                                </form:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value=<spring:message code="Add"/> class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default"><spring:message code="Cancel"/></a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>