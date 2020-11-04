
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Staff Registration</title>

<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
		<%@ include file="/WEB-INF/views/navBarManage.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">Manage Staff</h1>
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
				<th>Staff Id</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th>Username</th>
				<th>Position</th>
				<th>Recovery Question</th>
				<th>Recovery Answer</th>
				<th></th>
				<th></th>
			</tr>
			<c:forEach var="staff" items="${staffList}">
				<tr class="dataRows">
					<td>${staff.id}</td>
					<td>${staff.fName}</td>
					<td>${staff.lName}</td>
					<td>${staff.username}</td>
					<td>${staff.position}</td>
					<td>${staff.passQuestion}</td>
					<td>${staff.passAnswer}</td>
					<td><a
						href="${pageContext.request.contextPath}/editStaff/?id=${staff.id}">Edit</a></td>
					<td><a
						href="${pageContext.request.contextPath}/deleteStaff/?id=${staff.id}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>

		<h3>Create Staff</h3>

		<form:form action="${pageContext.request.contextPath}/createStaff/"
			cssClass="form-horizontal" method="post" modelAttribute="staff">

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
				<label for="position" class="col-md-3 control-label">Position</label>
				<div class="col-md-9">
					<form:input path="position" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="passQuestion" class="col-md-3 control-label">Password
					Recovery Question</label>
				<div class="col-md-9">
					<form:input path="passQuestion" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="passAnswer" class="col-md-3 control-label">Password
					Recovery Answer</label>
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