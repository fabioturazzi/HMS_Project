
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Room Type Registration</title>
<%@ include file="/WEB-INF/views/imports.jspf"%>

</head>
<body>

	<div class="pageHeader">
		<%@ include file="/WEB-INF/views/navBar.jspf"%>
		<%@ include file="/WEB-INF/views/navBarManage.jspf"%>
	</div>

	<div class="container">
		<h1 id="pageTitle">Manage Room Types</h1>
		<hr />


		<h3>Create Room Type</h3>

		<form:form action="${pageContext.request.contextPath}/createRoomType/"
			cssClass="form-horizontal" method="post" modelAttribute="roomType">

			<c:if test="${ errorMessage !=null }">
				<div class="alert alert-danger">${errorMessage}</div>
			</c:if>
			<div class="form-group">
				<label for="roomType" class="col-md-3 control-label">Room
					Type</label>
				<div class="col-md-9">
					<form:input path="roomType" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group" hidden>
				<label for="photos" class="col-md-3 control-label">Photos</label>
				<div class="col-md-9">
					<form:input path="photos" cssClass="form-control" value="" />
				</div>
			</div>

			<div class="form-group">
				<label for="dailyPrice" class="col-md-3 control-label">Daily
					Price</label>
				<div class="col-md-9">
					<form:input path="dailyPrice" cssClass="form-control"
						required="required" value="" />
				</div>
			</div>

			<div class="form-group">
				<label for="amenities" class="col-md-3 control-label">Amenities</label>
				<div class="checkboxItems">
					<form:checkboxes element="span class='checkboxItems'"
						cssClass="checkboxItems" checkedItems="${roomType.amenities}"
						items="${amenitiesList}" path="amenities" />
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
				<th>Photos</th>
				<th>Id</th>
				<th>Room Type</th>
				<th>Photos (Links)</th>
				<th>Daily Price</th>
				<th>Amenities</th>
				<th>Capacity</th>
				<th colspan="3">Options</th>
			</tr>

			<c:forEach var="roomType" items="${roomTypeList}">
				<tr class="dataRows">
					<td id="photosCell"><c:if
							test="${not empty roomType.photos[0]}">
							<div id="carouselControls${roomType.roomTypeId}"
								class="carousel slide" data-ride="carousel">
								<div class="carousel-inner">
									<c:forEach var="photo" items="${roomType.photos}"
										varStatus="carouselLoop">
										<div
											class="item<c:if test="${carouselLoop.index==0}"> active</c:if>">
											<img src="<c:url value="${photo}" />"
												alt="${carouselLoop.index}" class="d-block w-100 roomPhoto" />
										</div>
									</c:forEach>
								</div>
								<a class="left carousel-control"
									href="#carouselControls${roomType.roomTypeId}" role="button"
									data-slide="prev"> <span class="icon-prev"></span> <span
									class="sr-only">Previous</span>
								</a> <a class="right carousel-control"
									href="#carouselControls${roomType.roomTypeId}" role="button"
									data-slide="next"> <span class="icon-next"></span> <span
									class="sr-only">Next</span>
								</a>
							</div>
						</c:if> <c:if test="${empty roomType.photos[0]}">(No photos available)</c:if>
					</td>
					<td>${roomType.roomTypeId}</td>
					<td>${roomType.roomType}</td>
					<td><c:if test="${empty roomType.photos[0]}">(No photos available)</c:if>
						<c:if test="${not empty roomType.photos[0]}">
							<c:forEach var="photo" items="${roomType.photos}"
								varStatus="loop">
								${photo}<c:if test="${!loop.last}">
								</c:if>
							</c:forEach>
						</c:if></td>
					<td><fmt:formatNumber type="currency" currencySymbol="$"
							value="${roomType.dailyPrice}" /></td>
					<td><c:forEach var="amenity" items="${roomType.amenities}"
							varStatus="loop">
						${amenity}<c:if test="${!loop.last}"> | </c:if>
						</c:forEach></td>
					<td>${roomType.capacity}</td>
					<td><a
						href="${pageContext.request.contextPath}/editRoomType/?id=${roomType.roomTypeId}">Edit</a></td>
					<td><a
						href="${pageContext.request.contextPath}/deleteRoomType/?id=${roomType.roomTypeId}">Delete</a></td>
					<td><a
						href="${pageContext.request.contextPath}/uploadPhotos/?selectedRoomType=${roomType.roomType}">Upload
							Photos</a></td>
				</tr>
			</c:forEach>

		</table>

	</div>
</body>
</html>