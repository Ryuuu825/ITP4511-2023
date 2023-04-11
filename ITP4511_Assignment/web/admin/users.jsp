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

        <script src="https://cdn.tailwindcss.com"></script>
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

        <script src="https://code.jquery.com/jquery-3.6.4.min.js"
        integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>

        <style>
            body {
                background-color: #f3f3f3;
            }

            .btn-primary {
                background-color: #3b71ca !important;
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

            .btn-success {
                background-color: #14a44d !important;
            }

            td {
                vertical-align: middle;
            }

        </style>

        <script>
            // setInterval(() => {
            //     document.documentElement.style.setProperty('--mutate-width-1', '35%');
            // }, 1);

            // setInterval(() => {
            //     document.documentElement.style.setProperty('--mutate-width-2', '65%');
            // }, 1);

            let formDisabled = true;


        </script>
    </head>

    <body class="h-screen w-screen">
        <div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="errorModalLabel">
                            Confirm
                        </h1>
                        <div class="modal-body">
                        </div>
                        
                    </div>
                    <div class="modal-body">
                        <p class="">Are you sure you want to delete this user?</p>

                    </div>
                    <div class="modal-footer flex flex-row items-center">
                        <button type="button" class="btn-danger btn" data-bs-dismiss="modal" aria-label="Close"
                        onclick="$('#deleteForm').submit(); ">
                            Delete
                        </button>
                        <button type="button" class="mx-3" data-bs-dismiss="modal" aria-label="Close"
                        onclick="$('#errorModal').modal('hide');">
                            Close
                        </button>
                    </div>
                </div>
            </div>
        </div>
        
        <header class="z-3 w-100 d-flex flex-row align-items-center justify-content-between p-3"
        style="background-color: #144272; height: 5rem;">
        <div class="title text-uppercase ms-3 me-auto fw-bold text-white" style="font-size: 1.5rem;line-height: 2rem;">
            <a href="index.jsp" class="text-white text-decoration-none">
                Event Point Limited
            </a>
        </div>
        <ul class="nav justify-content-end fs-6 fw-semibold flex align-items-center mr-5">
            <li class="nav-item nav-hover">
                <a class="text-white mx-3 text-decoration-none" href="<%=request.getContextPath()%>/admin/users.jsp">
                    <!-- Browse Spaces -->
                    Users
                </a>
            </li>

            <li class="nav-item nav-hover">
                <a class="text-white mx-3 text-decoration-none" href="<%=request.getContextPath()%>/admin/users.jsp">
                    <!-- Browse Spaces -->
                    Users
                </a>
            </li>

            <li class="nav-item border bg-light rounded-1 py-1">
                <a class="mx-3 text-dark text-decoration-none" href="login.jsp"> Sign Out </a>
            </li>
        </ul>
    </header>

        <div class="content p-10 flex flex-col  mb-3 h-full" style="background-color: #f3f3f3">
            <div class="title uppercase text-xl font-bold my-3">Users</div>
            <%@ taglib uri="/WEB-INF/tlds/ict-taglib" prefix="ict" %>

            <ict:result />

            <div class="flex flex-row h-[75%]">
                <div class="usertable border w-full bg-white p-3 card"
                    style="<% if (ua.getAccount() == null) { %> width: 100%  <%  } else { %> margin-right: 2rem;  <% } %> ">
                    <div class="search">
                        <form class="searchbar flex flex-row items-center" action="#">
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
                                    value="<% if (request.getParameter("search") != null) { %><%=request.getParameter("search")%><% } %>"
                                    name="search"
                                    placeholder="Search" />
                                </div>

                                <% if (request.getParameter("id") != null) { %>
                                    <input type="hidden" name="id" value="<% if (request.getParameter("id") != null) { %><%=request.getParameter("id")%><% } %>">
                                <% } %>

                            <button class="btn btn-primary ml-2" type="submit">
                                Search
                            </button>
                        </form>
                    </div>
    
                    <div class="mt-3 h-[90%] flex flex-col relative">
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
    
                                <tr class="items-center justify-center ">
                                    <th><%= account.getId() %></th>
                                    <td><%= account.getUsername() %></td>
                                    <td><%= user.getEmail() %></td>
                                    <td><%= account.getRoleString() %></td>
                                    <td>
                                        <a href="users.jsp?id=<%= user.getId() %><% if (request.getParameter("search") != null) { %>&search=<%=request.getParameter("search")%><% } %>" class="btn btn-primary btn-rounded btn-sm rounded-pill">VIEW</a>
                                    </td>
                                    <td>
                                        <form action="<%= request.getContextPath() %>/api/admin/users" method="POST" id="deleteForm">
                                            <input type="hidden" name="id" value="<%= account.getId() %>" />
                                            <input type="hidden" name="action" value="delete" />
                                        </form>
                                        <button class="btn btn-danger btn-rounded btn-sm rounded-pill" onclick="$('#errorModal').modal('show')">Delete</button>
                                    </td>
                                </tr>
    
                                <% } %>

    
                            </tbody>
                        </table>
   
                        <ict:paginationpagenumber queryString="<%= request.getQueryString() %>" />
                    </div>
                </div>
    
                <% if (ua.getAccount() != null) { %>

                <div class="h-full box overflow-hidden relative card" >
                    <div class="card-header text-xl mb-0 flex items-center justify-center relative py-[1rem]" style="color:#4f4f4f">
                        <div class="absolute left-3 btn-secondary btn text-dark"
                            onclick="(function() {
                                // enable all the inputs
                                $('.userform input').prop('disabled', !formDisabled);
                                $('.userform select').prop('disabled', !formDisabled);
                                $('.userform button').prop('disabled', !formDisabled);
                                formDisabled = !formDisabled;
                            })()
                            ">
                            Edit 
                        </div>
                        <div>
                            <%= ua.getUser().getFirstName() %> <%= ua.getUser().getLastName() %>
                        </div>
                        <a class="absolute right-3 text-dark" href="<%=request.getContextPath()%>/admin/users.jsp">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
                            </svg>
                        </a>
                    </div>

                    <div class="userform px-3 h-max card-body">
                        <form action="<%= request.getContextPath() %>/api/admin/users" method="post" id="updateForm">

                            <input type="hidden" name="id" value="<%= ua.getAccount().getId() %>" />
                            <input type="hidden" name="action" value="update" />

                            <div class="row mb-4">
                                <div class="col">
                                    <div class="form-outline">
                                        <input type="text" name="fname" id="fname" required aria-required="true" class=" active form-control border" value="<%= ua.getUser().getFirstName() %>" disabled />
                                        <label class="form-label" for="fname">First Name</label>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="form-outline">
                                        <input type="text" name="lname" id="lname" required aria-required="true" class=" active form-control border" value="<%= ua.getUser().getLastName() %>" disabled />
                                        <label class="form-label" for="lname">Last Name</label>
                                    </div>
                                </div>
                              </div>
                                                        
                            <div class="form-outline mb-4">
                                <input type="text" name="phone" id="phone" required aria-required="true" class=" active form-control border" value="<%= ua.getUser().getPhone() %>" disabled />
                                <label class="form-label" for="phone">Phone</label>
                            </div>

                            <div class="border-bottom mb-4">

                            </div>
                            
                            <div class="form-outline mb-4">
                                <input type="text" name="username" id="username" required aria-required="true" class=" active form-control border" value="<%= ua.getAccount().getUsername() %>"  disabled />
                                <label class="form-label" for="username">Username</label>
                            </div>
                                                        
                            <div class="form-outline mb-4">
                                <input type="email" name="email" id="email" required aria-required="true" class=" active form-control border" value="<%= ua.getUser().getEmail() %>"  disabled />
                                <label class="form-label" for="email">Email</label>
                            </div>

                            <div class="form-outline mb-4">
                                <input type="password" name="password" id="password-input" required value=<%= ua.getAccount().getPassword() %>
                                    aria-required="true" class="form-control border active"  disabled />
                                <label class="form-label" for="password-input">Password</label>
                            </div>  

                            <div class="form-outline mb-3">
                                <select name="role" id="role" class="form-control border active" disabled >

                                    <% 
                                        for (ict.bean.Account.roleEnum role : ict.bean.Account.roleEnum.values()) {
                                                                            %>
                                        <option value="<%= role.ordinal() + 1 %>" <% if (ua.getAccount().getRole() - 1  == role.ordinal()) { %> selected <% } %>>
                                            <%= role.toString() %>
                                        </option>
                                    <% } %>
                                </select>
                                <label class="form-label" for="role">Role</label>
                            </div>

                         
                            <div class="flex flex-row absolute bottom-8 w-full">
                                <a class="btn btn-primary ml-auto mr-2 " type="button" href="<%= request.getContextPath() %>/admin/users.jsp?id=<%= ua.getAccount().getId() %>">
                                    Reset
                                </a>
                                
                                <button class="btn btn-success mr-12 w-75" type="submit" disabled>
                                    Save 
                                </button>
                            </div>
                        </form>

                    </div>
                </div>
                <% } %>
            </div>
    </body>
</html>