<%--
  Created by IntelliJ IDEA.
  User: thuannd.dev
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en" xml:lang="en">
<head>
    <title>Room Status Management</title>
    <style>
        table { width: 80%; margin: 30px auto; border-collapse: collapse; }
        th, td { padding: 12px; border: 1px solid #ccc; text-align: center; }
        th { background-color: #f2f2f2; }
        .btn { padding: 6px 12px; border: none; border-radius: 4px; cursor: pointer; }
        .status-label { padding: 4px 8px; border-radius: 12px; display: inline-block; }
        .status-label.available { background-color: #4CAF50; color: white; }
        .status-label.dirty { background-color: #e67e22; color: white; }
        .status-label.maintenance { background-color: #c0392b; color: white; }
        .status-label.occupied { background-color: #2980b9; color: white; }
        .status-links { text-align:center; margin:12px 0; font-size:14px; }
        .status-links a { color:#0366d6; text-decoration:none; margin:0 6px; }
        .status-links a:hover { text-decoration:underline; }
        .status-links .sep { color:#666; }
    </style>
</head>
<body>
<h2 style="text-align:center;">Housekeeping - Room Status</h2>

<c:if test="${param.msg == 'updated'}">
    <p style="text-align:center; color:green;"> Room status updated successfully!</p>
</c:if>
<c:if test="${param.msg == 'invalid_transition'}">
    <p style="text-align:center; color:red;"> Invalid status transition: ${param.error}</p>
</c:if>

<!-- Status quick-links UI (plain anchors) -->
<div class="status-links">[
    <c:choose>
        <c:when test="${param.status == '' || param.status == 'all' || param.status == null}">
            <strong>All</strong>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/housekeeping">All</a>
        </c:otherwise>
    </c:choose>
    <span class="sep">|</span>
    <c:choose>
        <c:when test="${param.status == 'available'}">
            <strong>Available</strong>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/housekeeping?status=available">Available</a>
        </c:otherwise>
    </c:choose>
    <span class="sep">|</span>
    <c:choose>
        <c:when test="${param.status == 'occupied'}">
            <strong>Occupied</strong>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/housekeeping?status=occupied">Occupied</a>
        </c:otherwise>
    </c:choose>
    <span class="sep">|</span>
    <c:choose>
        <c:when test="${param.status == 'dirty'}">
            <strong>Dirty</strong>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/housekeeping?status=dirty">Dirty</a>
        </c:otherwise>
    </c:choose>
    <span class="sep">|</span>
    <c:choose>
        <c:when test="${param.status == 'maintenance'}">
            <strong>Maintenance</strong>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/housekeeping?status=maintenance">Maintenance</a>
        </c:otherwise>
    </c:choose>
]</div>

<table>
    <tr>
        <th>Room No</th>
        <th>Type</th>
        <th>Status</th>
        <th>Capacity</th>
        <th>Action</th>
    </tr>
    <%--@elvariable id="r" type="com.hotel_management.domain.dto.room.RoomDetailViewModel"--%>
    <c:forEach var="r" items="${rooms}">
        <tr>
            <td>${r.roomNumber}</td>
            <td>${r.typeName}</td>
            <td><span class="status-label ${fn:toLowerCase(r.status)}">${r.status}</span></td>
            <td>${r.capacity}</td>
            <td>
                <form action="housekeeping/room/status" method="post">
                    <input type="hidden" name="roomId" value="${r.roomId}" />
                    <c:choose>
                        <c:when test="${r.status == 'Occupied'}">
                            <select name="status" aria-label="Room status" disabled>
                                <option value="Occupied" selected>Occupied</option>
                            </select>
                            <button type="submit" class="btn" disabled>Update</button>
                        </c:when>
                        <c:when test="${r.status == 'Available'}">
                            <select name="status" aria-label="Room status">
                                <option value="Available" selected>Available</option>
                                <option value="Dirty">Dirty</option>
                                <option value="Maintenance">Maintenance</option>
                            </select>
                            <button type="submit" class="btn">Update</button>
                        </c:when>
                        <c:when test="${r.status == 'Dirty'}">
                            <select name="status" aria-label="Room status">
                                <option value="Dirty" selected>Dirty</option>
                                <option value="Available">Available</option>
                                <option value="Maintenance">Maintenance</option>
                            </select>
                            <button type="submit" class="btn">Update</button>
                        </c:when>
                        <c:when test="${r.status == 'Maintenance'}">
                            <select name="status" aria-label="Room status">
                                <option value="Maintenance" selected>Maintenance</option>
                                <option value="Available">Available</option>
                            </select>
                            <button type="submit" class="btn">Update</button>
                        </c:when>
                    </c:choose>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>