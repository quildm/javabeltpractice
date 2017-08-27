<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Welcome Page</title>
</head>
<body>

    <c:if test="${logoutMessage != null}">
    		<span style="color:green">
        <c:out value="${logoutMessage}"></c:out>
        </span>
    </c:if>

    <c:if test="${errorMessage != null}">
	    	<span style="color:red">
	        <c:out value="${errorMessage}"></c:out>
	    </span>
    </c:if>
    

    <form id="logoutForm" method="POST" action="/logout">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        
    <c:if test="${isAdmin}">
    		<a href="/admin">Admin Dashboard</a>
    </c:if>
        
        <input type="submit" value="Logout!" />
    </form>
    
    <h1>Welcome  <c:out value="${currentUser.email}"></c:out></h1>
    <div style="padding:10px; margin:10px; border: 1px solid black">
		<p>
		Current package: ${chosedPack.name}
		</p>
		<p>
		Next Due date: <fmt:formatDate pattern="MMMM dd, yyyy" value="${currentUser.dueDate}" />
		</p>

		<p>
		Amaunt Due: &#x24; ${chosedPack.cost}
		</p>
		<p>
		User since: <fmt:formatDate pattern="MMMM dd, yyyy" value="${currentUser.createdAt}" />
		</p>
</div>
    
    

</body>
</html>