<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns:th="http://www.w3.org/1999/xhtml">
<head>
    <!-- Meta -->
    <meta charset="utf-8" />
    <title>DirectID Java Example</title>

    <!-- Bootstrap and Fonts -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" />
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet" />
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,400,600,700,800,300' rel='stylesheet' type='text/css' />

    <!-- Custom Style -->
    <style>
        html,
        body {
        font-family: 'Open Sans', sans-serif;
        font-weight: 300;
        color: #222;
        height: 100%;
        margin: 0;
        padding: 0;
        }

        #page-wrapper {
        position: relative;
        height: auto!important;
        min-height: 100%;
        }

        #main {
        padding-bottom: 50px;
        }

        #footer {
        width: 100%;
        height: 50px;
        position: absolute;
        bottom: 0;
        font-size: 0.75em;
        text-align: center;
        }
        }
    </style>

    <link href="http://direct.id/Media/Default/Images/favicon.png" rel="shortcut icon" type="image/x-icon" />
</head>

<body>
<div id="page-wrapper">

    <header id="header" class="container">
        <div class="row">
            <div class="col-xs-12">
                <a href="http://direct.id"><img src="http://direct.id/Media/Default/Images/DirectID.png" />
                </a>
            </div>
        </div>
    </header>

    <main id="main" class="container">
        <header class="row">
            <div id="widget-header" class="col-xs-10 col-xs-offset-1 col-sm-10 col-sm-offset-1">
                <h1>Well Done!</h1>
                <legend>Individuals Summary</legend>
            </div>
        </header>
        <div class="row">
            <div class="col-xs-10 col-xs-offset-1 col-sm-8 col-sm-offset-1">

                <table class="table table-hover">
                    <thead>
                    <tr>
                        <td>Reference</td>
                        <td>Email Address</td>
                        <td>Name</td>
                        <td>Timestamp</td>
                        <td>UserID</td>
                    </tr>
                    </thead>
                    <tr>
                        <td><span th:text="${individualmodel.Reference}"></span></td>
                        <td><span th:text="${individualmodel.Timestamp}"></span></td>
                        <td><span th:text="${individualmodel.Name}"></span></td>
                        <td><span th:text="${individualmodel.EmailAddress}"></span></td>
                        <td><span th:text="${individualmodel.UserID}"></span></td>

                    <c:forEach items="${individualmodels}" var="individual">
                        <tr>
                            <td>${individual.Reference}</td>
                            <td>${individual.Timestamp}</td>
                            <td>${individual.Name}</td>
                            <td>${individual.EmailAddress}</td>
                            <td>${individual.UserID}</td>
                        </tr>
                    </c:forEach>

                </table>

            </div>
        </div>
    </main>

    <footer id="footer" class="container">
        <div class="row">
            <div class="col-xs-10 col-xs-offset-1 col-sm-10 col-sm-offset-1">
                DirectID is provided by miiCard Limited | Edinburgh | London | New York
                <br/> US +1 302 760 9130 | UK +44 (0)845 119 3333 | e: info@directid.co | © miiCard® | All rights reserved 2014 | Registered in Scotland SC400459
            </div>
        </div>
    </footer>
</div>
</body>

</html>