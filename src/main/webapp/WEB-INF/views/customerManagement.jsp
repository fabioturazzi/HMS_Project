
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Registration</title>
<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
		<%@ include file="/WEB-INF/views/navBarManage.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">Manage Customers</h1>
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
				<th>Customer Id</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Username</th>
				<th>Email</th>
				<th>Phone number</th>
				<th>Address</th>
				<th>Recovery Question</th>
				<th>Recovery Answer</th>
				<th></th>
				<th></th>
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
					<td>${customer.passQuestion}</td>
					<td>${customer.passAnswer}</td>
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
						required="required" value="" pattern=".{8,}"
						title="Password must have 8 characters" />
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
				<label for="passQuestion" class="col-md-3 control-label">Password Recovery Question</label>
				<div class="col-md-9">
					<form:input path="passQuestion" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="passAnswer" class="col-md-3 control-label">Password Recovery Answer</label>
				<div class="col-md-9">
					<form:input path="passAnswer" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<!-- Button -->
				<div class="col-md-offset-3 col-md-9">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>

		</form:form>

	</div>
</body>
</html>