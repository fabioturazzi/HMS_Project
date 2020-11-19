
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Facility Booking Registration</title>

<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
		<%@ include file="/WEB-INF/views/navBarManage.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">Edit Facility Booking</h1>
		<hr />
		<form:form
			action="${pageContext.request.contextPath}/editFacilityBooking/"
			cssClass="form-horizontal" method="post"
			modelAttribute="facilityBooking">

			<div class="form-group">
				<label for="facilityBookingId" class="col-md-3 control-label">Facility
					Booking Id</label>
				<div class="col-md-9">
					<form:input path="facilityBookingId" cssClass="form-control"
						required="required" value="" readonly="true" />
				</div>
			</div>
			<div class="form-group">
				<label for="facilityName" class="col-md-3 control-label">Facility</label>
				<div class="col-md-9">
					<form:input path="facilityName" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<label for="numberOfPeople" class="col-md-3 control-label">Number
					of People</label>
				<div class="col-md-9">
					<form:input type="number" path="numberOfPeople"
						cssClass="form-control" required="required" value="" />
				</div>
			</div>
			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>
			<div class="form-group">
				<label for="customerUsername" class="col-md-3 control-label">Customer</label>
				<div class="col-md-9">
					<form:select path="customerUsername" cssClass="form-control"
						required="required" value="">
						<form:options items="${customerListItems}"></form:options>
					</form:select>
				</div>
			</div>
			<div class="form-group">
				<label for="correspBookingId" class="col-md-3 control-label">Booking
					Id</label>
				<div class="col-md-9">
					<form:input path="correspBookingId" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="date" class="col-md-3 control-label">Date</label>
				<div class="col-md-9">
					<form:input path="date" type="date" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="timeStart" class="col-md-3 control-label">Start
					Time</label>
				<div class="col-md-9">
					<form:input path="timeStart" type="time" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>
			<div class="form-group">
				<label for="timeEnd" class="col-md-3 control-label">End Time</label>
				<div class="col-md-9">
					<form:input path="timeEnd" type="time" cssClass="form-control"
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