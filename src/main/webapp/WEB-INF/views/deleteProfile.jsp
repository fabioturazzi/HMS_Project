<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete User</title>

<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>
	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
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
							<h1 id="pageTitle">Delete Account<span hidden>${user.id}</span></h1>
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
