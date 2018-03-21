<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

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
            <a class="navbar-brand" href="ComputerDatabase"> Application - Computer Database </a>
        </div>
    </header>
    
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <div class="label label-default pull-right">
                        id: ${dtoComputerBase.id}
                    </div>
                    <h1>Edit Computer</h1>
                    <c:if test="${not empty error}">
                    	<p>${error}</p>
                    </c:if>
                    <form:form action="editComputer" method="POST" modelAttribute="DTOComputer">
                        <form:input type="hidden" path = "id" name="id" id="id" value="${dtoComputerBase.id}" />
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <form:input type="text" class="form-control" path = "name" name="computerName" id="computerName" value="${dtoComputerBase.name}" placeholder="computerBase"/>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <form:input type="date" class="form-control" path = "introduced" name="introduced" id="introduced" value="${dtoComputerBase.introduced}" placeholder="Introduced date"/>
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <form:input type="date" class="form-control" path = "discontinued" name="discontinued" id="discontinued" value="${dtoComputerBase.discontinued}" placeholder="Discontinued date"/>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <form:select class="form-control" path = "companyID" name="companyId" id="companyId" >
                                	<option selected="selected" value="${dtoComputerBase.companyID}">${dtoComputerBase.companyName}</option>
                                    <c:forEach items="${allCompanies}" var="company">
                                    	<option value="${company.id}">${company.name}</option>
                                    </c:forEach>
                                </form:select>
                            </div>            
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="Edit" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>