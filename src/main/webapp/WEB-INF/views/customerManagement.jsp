
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Registration</title>

<script src="<c:url value="/static/js/jquery-3.5.1.min.js" />"></script>
<link href="<c:url value="/static/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/static/css/hackermenHMS.css" />"
	rel="stylesheet">
<script src="<c:url value="/static/js/bootstrap.min.js" />"></script>

<script src="<c:url value="/static/js/searchFilters.js" />"></script>


</head>
<body>

	<div class="pageHeader">
		<img src="<c:url value="/static/images/HMS Logo.png" />"
			alt="HMS Hackermen logo" class="hmsLogo" id="hmsLogo" />
		<div class="navBarDiv">
			<nav class="navbar navbar-default">
				<ul class="nav navbar-nav">
					<li><a href="${pageContext.request.contextPath}/registration/">Login/Registration</a></li>
					<li><a href="">Room Search (under development)</a></li>
					<li><a href="">My Reservations (under development)</a></li>
					<li class="active"><a
						href="${pageContext.request.contextPath}/userManagement/customer">Manage
							System Data</a></li>
				</ul>
			</nav>
		</div>
		<div class="navBarUsers">
			<nav class="navbar navbar-light">
				<ul class="nav navbar-nav">
					<li class="dataManagement subNavActive"><a
						href="${pageContext.request.contextPath}/userManagement/customer">Manage
							Users</a></li>
					<li class="userNavActive"><a
						href="${pageContext.request.contextPath}/userManagement/customer">Manage
							Users: Customers</a></li>
					<li class=""><a
						href="${pageContext.request.contextPath}/userManagement/staff">Manage
							Users: Staff</a></li>

					<li class="dataManagement"><a href="">Manage Rooms
							(und.dev.)</a></li>
					<li class="dataManagement"><a href="">Manage Facilities
							(und.dev.)</a></li>
					<li class="dataManagement"><a href="">Manage Amenities
							(und.dev.)</a></li>
				</ul>
			</nav>
		</div>
	</div>

	<div class="container">
		<h1>Manage Customers</h1>
		<hr />
		<div class="form-group searchBar form-horizontal">
			<label for="fName" class="col-md-3 control-label">Search
				Entries:</label>
			<div class="col-md-9">
				<input class="form-control searchInput" id="searchInput"
					required="required" value="" />
			</div>
		</div>


		<c:if test="${ message != null }">
			<div class="alert alert-success" role="alert">${message}</div>
		</c:if>
		<table class="table table-striped table-bordered">

			<tr class="dataHeader">
				<td>Customer Id</td>
				<td>First Name</td>
				<td>Last Name</td>
				<td>Username</td>
				<td>Email</td>
				<td>Phone number</td>
				<td>Address</td>
				<td></td>
				<td></td>
			</tr>
			<c:forEach var="customer" items="${customerList}">
				<tr class="dataRows">
					<td>${customer.id}</td>
					<td>${customer.fName}</td>
					<td>${customer.lName}</td>
					<td>${customer.username}</td>
					<td>${customer.email}</td>
					<td>${customer.phoneNumber}</td>
					<td>${customer.address}</td>
					<td><a
						href="${pageContext.request.contextPath}/editCustomer/?id=${customer.id}">Edit</a></td>
					<td><a
						href="${pageContext.request.contextPath}/deleteCustomer/?id=${customer.id}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>

		<h3>Create Customer</h3>

		<form:form action="${pageContext.request.contextPath}/createCustomer/"
			cssClass="form-horizontal" method="post" modelAttribute="customer">

			<div class="form-group">
				<label for="fName" class="col-md-3 control-label">First Name</label>
				<div class="col-md-9">
					<form:input path="fName" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<label for="lName" class="col-md-3 control-label">Last Name</label>
				<div class="col-md-9">
					<form:input path="lName" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>

			<div class="form-group">
				<label for="username" class="col-md-3 control-label">Username</label>
				<div class="col-md-9">
					<form:input path="username" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-md-3 control-label">Password</label>
				<div class="col-md-9">
					<form:input path="password" type="password" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-md-3 control-label">Email</label>
				<div class="col-md-9">
					<form:input path="email" type="email" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>


			<div class="form-group">
				<label for="phoneNumber" class="col-md-3 control-label">Phone
					number</label>
				<div class="col-md-9">
					<form:input path="phoneNumber" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>


			<div class="form-group">
				<label for="address" class="col-md-3 control-label">Address</label>
				<div class="col-md-9">
					<form:input path="address" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<form:button cssClass="btn btn-primary">Submit</form:button>
				</div>
			</div>

		</form:form>

	</div>
</body>
</html>