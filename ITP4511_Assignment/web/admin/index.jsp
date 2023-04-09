<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>JSP Page</title>

        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
            crossorigin="anonymous"
        />
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"
        ></script>

        <script src="https://cdn.tailwindcss.com"></script>
    </head>
    <body class="h-screen w-screen">
        <nav
            class="w-full h-[5rem] flex flex-row items-center"
            style="background-color: #144272"
        >
            <div class="title">
                <h1
                    class="text-2xl uppercase ml-12 mr-auto font-extrabold text-white"
                >
                    Event Point Limited
                </h1>
            </div>
            <ul
                class="nav justify-content-end font-semibold flex align-items-center mr-5 ml-auto"
            >
                <li class="nav-item mx-3">
                    <a class="text-white" href="users.jsp"> Users </a>
                </li>
                <li class="nav-item mx-3">
                    <a class="text-white " href="login.jsp"> Report </a>
                </li>
                <li class="nav-item border bg-light rounded-sm py-1 mx-3 px-2">
                    <a class="text-dark" href="register.jsp"> Sign Out </a>
                </li>
            </ul>
        </nav>

        <div class="welcome message text-xl border bg-gray-100 px-3 py-1 m-4 mx-5">
            Welcome back, <%= session.getAttribute("username") %> 🕺
        </div>

        <div class="content px-5 flex flex-col">

            <div class="title uppercase text-xl font-bold">
                Dashborad
            </div>
            
        </div>
    </body>
</html>
