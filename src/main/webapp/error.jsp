<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <center>
        <h1>Error</h1>
        <h2>
            <%
                if (exception != null) {
                    out.print(exception.getMessage());
                } else {
                    out.print("Unknown error occurred.");
                }
            %>
            <br/>
        </h2>
    </center>
</body>
</html>
