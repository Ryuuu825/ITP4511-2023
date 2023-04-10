<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>
        <% 
            // if userAccounts is null, redirect to /api/admin/users 
            if (request.getAttribute("userAccounts") == null) {
                getServletContext().getRequestDispatcher("/api/admin/users").forward(request, response); 
            } 
        %>
        <%@ page import="java.util.ArrayList" %>
        <%@ page import="ict.bean.view.UserAccount" %>
        <%@ page import="ict.bean.User" %>
        <%@ page import="ict.bean.Account" %>
        <%@ page import="java.util.Enumeration" %>

        <jsp:useBean id="userAccounts" class="java.util.ArrayList<UserAccount>" scope="request" />
        <jsp:useBean id="ua" class="ict.bean.view.UserAccount" scope="request" />

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>JSP Page</title>


        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
            crossorigin="anonymous" />
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"></script>

        <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.css" rel="stylesheet" />
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.js"></script>

        <script src="https://cdn.tailwindcss.com"></script>

        <style>
            body {
                background-color: #f3f3f3;
            }

            .btn-primary {
                background-color: #0d6efd !important;
            }

            .btn-primary:hover {
                background-color: #0a58ca !important;
            }

            .btn-danger {
                background-color: #dc3444 !important;
            }

            .btn-danger:hover {
                background-color: #b92c3a !important;
            }   

            .box {
                width: var(--mutate-width-1, 35%);
                transition: width 1s ease-in-out;
            }

            .usertable {
                width: var(--mutate-width-2, 65%);
                transition: width 1s ease-in-out;
            }

        </style>

        <script>
            // setInterval(() => {
            //     document.documentElement.style.setProperty('--mutate-width-1', '35%');
            // }, 1);

            // setInterval(() => {
            //     document.documentElement.style.setProperty('--mutate-width-2', '65%');
            // }, 1);


        </script>
    </head>

    <body class="h-screen w-screen">
        <nav class="w-full h-[5rem] flex flex-row items-center"
            style="background-color: #144272">
            <div class="title">
                <h1 class="text-2xl uppercase ml-12 mr-auto font-extrabold text-white">
                    Event Point Limited
                </h1>
            </div>
            <ul
                class="nav justify-content-end font-medium flex align-items-center mr-5 ml-auto">
                <li class="nav-item mx-3">
                    <a class="text-white" href="#"> Users </a>
                </li>
                <li class="nav-item mx-3">
                    <a class="text-white" href="login.jsp"> Report </a>
                </li>
                <li class="nav-item px-3 border bg-light rounded-sm py-1 mx-2">
                    <a class="text-dark" href="register.jsp"> Sign Out </a>
                </li>
            </ul>
        </nav>

        <div class="content p-12 flex flex-col h-full" style="background-color: #f3f3f3">
            <div class="title uppercase text-xl font-bold my-3">Users</div>
            <%@ taglib uri="/WEB-INF/tlds/ict-taglib" prefix="ict" %>

            <ict:result />

            <div class="flex flex-row h-75">
                <div class="usertable border w-full bg-white p-3 "
                    style="<% if (ua.getAccount() == null) { %> width: 100%  <%  } else { %> margin-right: 2rem;  <% } %> ">
                    <div class="search">
                        <div class="searchbar flex flex-row items-center">
                            <div class="w-full border rounded-sm flex flex-row p-2 ">
                                <svg fill="none" stroke="currentColor" stroke-width="1.5"
                                viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"
                                aria-hidden="true" class="w-6 h-6">
                                <path stroke-linecap="round" stroke-linejoin="round"
                                    d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z">
                                </path>
                                </svg>
                                <input class="w-full focus:outline-none ml-5"
                                    type="text"
                                    placeholder="Search" />
                                </div>
                            <button class="btn btn-primary text-white rounded-sm p-2 ml-2 h-full" type="button">
                                Search
                            </button>
                        </div>
                    </div>
    
                    <div class="mt-3">
                        <table class="table table-striped">
                            <thead>
                                <tr>
                                    <th scope="col">ID</th>
                                    <th scope="col">Username</th>
                                    <th scope="col">Email</th>
                                    <th scope="col">Role</th>
                                    <th scope="col" >Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                    for (UserAccount userAccount : userAccounts) {
                                        User user = userAccount.getUser();
                                        Account account = userAccount.getAccount();
                                %>
    
                                <tr>
                                    <th><%= account.getId() %></th>
                                    <td><%= account.getUsername() %></td>
                                    <td><%= user.getEmail() %></td>
                                    <td><%= account.getRoleString() %></td>
                                    <td >
                                        <a href="users.jsp?id=<%= user.getId() %>" class="underline">View</a>
                                    </td>
                                    <td>
                                        <form action="<%= request.getContextPath() %>/api/admin/users" method="POST"
                                                onsubmit="return confirm('Are you sure you want to delete this user?')"
                                            >
                                            <input type="hidden" name="id" value="<%= account.getId() %>" />
                                            <input type="hidden" name="action" value="delete" />
                                            <button type="submit" class="mx-3 border btn btn-danger px-3 py-1 text-white">Delete</button>
                                        </form>
                                    </td>
                                </tr>
    
                                <% } %>
    
                            </tbody>
                        </table>
                    </div>
                </div>
    
                <% if (ua.getAccount() != null) { %>

                <div class="h-full border bg-white box overflow-hidden" >
                    <div class="title text-center text-2xl font-semibold mt-4 pb-3 border-b mb-2 mx-2">
                        User - 
                        <span class="font-extrabold text-3xl text-black">
                            <%= ua.getAccount().getUsername() %>
                        </span>
                    </div>

                    <div class="userform px-3 py-2">
                        <form action="/api/admin/user" method="post">
                            <input type="hidden" name="id" value="<%= ua.getAccount().getId() %>" />
                            <input type="hidden" name="action" value="update" />
                        </form>

                    </div>
                </div>
                <% } %>
            </div>
    </body>
</html>