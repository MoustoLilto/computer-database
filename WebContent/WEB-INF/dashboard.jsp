<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/WEB-INF/tld/cdb.tld" prefix="cdbTagLib" %>

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
        </div>
    </header>
    
    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
            	${attribute.numberOfRows} Computers found
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">
                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name"/>
                        <input type="hidden" name="tuples" value="${attribute.nbreTuples}">
                        <input type="hidden" name="page" value="1">
                        <input type="hidden" name="orderBy" value="${attribute.orderBy}">
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <a href="dashboard?tuples=${attribute.nbreTuples}&search=${attribute.recherche}&page=${attribute.numeroPage}&beforeOrderBy=${attribute.orderBy}&orderBy=computer.name">Computer name</a>
                        </th>
                        <th>
                            <a href="dashboard?tuples=${attribute.nbreTuples}&search=${attribute.recherche}&page=${attribute.numeroPage}&beforeOrderBy=${attribute.orderBy}&orderBy=introduced">Introduced date</a>
                        </th>
                        <th>
                            <a href="dashboard?tuples=${attribute.nbreTuples}&search=${attribute.recherche}&page=${attribute.numeroPage}&beforeOrderBy=${attribute.orderBy}&orderBy=discontinued">Discontinued date</a>
                        </th>
                        <th>
                            <a href="dashboard?tuples=${attribute.nbreTuples}&search=${attribute.recherche}&page=${attribute.numeroPage}&beforeOrderBy=${attribute.orderBy}&orderBy=company.name">Company</a>
                        </th>

                    </tr>
                </thead>
                <tbody id="results">
	                <c:forEach items="${attribute.allComputers}" var="computer">
	                	<tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${computer.id}">
	                        </td>
	                        <td>
	                            <a href="editComputer?id=${computer.id}" onclick="">${computer.name}</a>
	                        </td>
	                        <td>${computer.introduced}</td>
	                        <td>${computer.discontinued}</td>
	                        <td>${computer.companyName}</td>
	                    </tr>
	                
	                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
              <cdbTagLib:pagination attribute="${attribute}"/>
        	</ul>

	        <div class="btn-group btn-group-sm pull-right" role="group" >
	            <input type="button" class="btn btn-default" onclick="location.href='dashboard?tuples=10&search=${attribute.recherche}&page=1&orderBy=${attribute.orderBy}'" value="10"/>
	            <input type="button" class="btn btn-default" onclick="location.href='dashboard?tuples=50&search=${attribute.recherche}&page=1&orderBy=${attribute.orderBy}'" value="50"/>
	            <input type="button" class="btn btn-default" onclick="location.href='dashboard?tuples=100&search=${attribute.recherche}&page=1&orderBy=${attribute.orderBy}'" value="100"/>
	        </div>
        </div>

    </footer>
<script src="lib/js/jquery.min.js"></script>
<script src="lib/js/bootstrap.min.js"></script>
<script src="lib/js/dashboard.js"></script>

</body>
</html>