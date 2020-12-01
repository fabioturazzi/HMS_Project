
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Facilities Management</title>
<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
		<%@ include file="/WEB-INF/views/navBarManage.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">Manage Facilities</h1>
		<hr />

		<h3>Create Facility</h3>
		<form:form action="${pageContext.request.contextPath}/createFacility/"
			cssClass="form-horizontal" method="post" modelAttribute="facility">
			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>
			<div class="form-group">
				<label for="facilityName" class="col-md-3 control-label">Facility
					Name</label>
				<div class="col-md-9">
					<form:input path="facilityName" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<label for="facilityType" class="col-md-3 control-label">Facility
					Type</label>
				<div class="col-md-9">
					<form:select path="facilityType" cssClass="form-control"
						required="required" value="">
						<form:options items="${facilitiesTypesList}"></form:options>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label for="capacity" class="col-md-3 control-label">Capacity</label>
				<div class="col-md-9">
					<form:input type="number" path="capacity" cssClass="form-control"
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

		<hr />
		<div class="form-group searchBar form-horizontal">
			<label for="facilityName" class="col-md-3 control-label">Search
				Entries:</label>
			<div class="col-md-9">
				<input class="form-control searchInput" id="searchInput"
					required="required" value="" />
			</div>
		</div>

		<c:if test="${ message != null }">
			<c:set var="str" value="${message}" />
			<jsp:useBean id="str" type="java.lang.String" />
			<c:if
				test='<%=str.equalsIgnoreCase("Facility created successfully")%>'>
				<div class="alert alert-success" role="alert">${message}</div>
			</c:if>
			<c:if
				test='<%=str.equalsIgnoreCase("Facility deleted successfully")%>'>
				<div class="alert alert-warning" role="alert">${message}</div>
			</c:if>
			<c:if
				test='<%=str.equalsIgnoreCase("Facility name already exists. Please choose a different one")%>'>
				<div class="alert alert-danger" role="alert">${message}</div>
			</c:if>
		</c:if>

		<table class="table table-striped table-bordered">

			<tr class="dataHeader">
				<th>Facility Id</th>
				<th>Facility Name</th>
				<th>Facility Type</th>
				<th>Capacity</th>
				<th colspan="2">Options</th>
			</tr>
			<c:forEach var="facility" items="${facilitiesList}">
				<tr class="dataRows">
					<td>${facility.facilityId}</td>
					<td>${facility.facilityName}</td>
					<td>${facility.facilityType}</td>
					<td>${facility.capacity}</td>
					<td><a
						href="${pageContext.request.contextPath}/updateFacility/?facilityId=${facility.facilityId}">Edit</a></td>
					<td><a
						href="${pageContext.request.contextPath}/deleteFacility/?facilityId=${facility.facilityId}">Delete</a></td>
				</tr>
			</c:forEach>

		</table>

	</div>
</body>
</html>