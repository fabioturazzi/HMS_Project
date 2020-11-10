
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
		<h1 id="pageTitle">Edit Customer</h1>
		<hr />
		<form:form action="${pageContext.request.contextPath}/editCustomer/"
			cssClass="form-horizontal" method="post" modelAttribute="customer">

			<div class="form-group">
				<label for="id" class="col-md-3 control-label">Booking ID</label>
				<div class="col-md-9">
					<form:input path="id" value="${customer.id}"
						cssClass="form-control" readonly="true" />
				</div>
			</div>
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