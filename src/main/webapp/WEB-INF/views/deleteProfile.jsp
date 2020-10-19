<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete User</title>

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
	</div>
	<div class="container">
		<div class="container bootstrap snippet">
			<br>
			<div class="row">
				<form:form
					action="${pageContext.request.contextPath}/deleteProfile"
					cssClass="form-horizontal" method="post" modelAttribute="user">

					<div class="text-center">

						<div class="form-group">
							<h1>Delete Account<span hidden>${user.id}</span></h1>
							<p>
								Are you sure you want to delete <strong>${user.username}</strong>'s
								account?
							</p>

							<div class="row">
								<a href="${pageContext.request.contextPath}/deleteProfileCompletely/?id=${user.id}">
								<label class="btn btn-danger">Delete</label></a>
								<a href="${pageContext.request.contextPath}/profile"><label class="btn btn-primary">Discard</label></a>
							</div>
						</div>
					</div>
					
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
