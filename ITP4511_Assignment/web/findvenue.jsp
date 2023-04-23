<%-- 
   Document   : venueInfo
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page import="ict.bean.Venue"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"> -->
    <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"
            integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e" crossorigin="anonymous"> -->
    <!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"> -->
    <!-- </script> -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"
        integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.css" rel="stylesheet" />
    <!-- <script src="https://cdn.tailwindcss.com"></script> -->
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />
</head>
<style>
    .nav-hover:hover {
        font-size: 1.125rem;
        line-height: 1.75rem;
    }

    .card .bg-image {
        border-top-left-radius: var(--mdb-card-border-radius);
        border-bottom-left-radius: var(--mdb-card-border-radius);
        border-top-right-radius: 0;
    }

</style>
<script>
    $(document).ready(function () {
        const searchFocus = document.getElementById('search-focus');
        const keys = [{
                keyCode: 'AltLeft',
                isTriggered: false
            },
            {
                keyCode: 'ControlLeft',
                isTriggered: false
            },
        ];

        window.addEventListener('keydown', (e) => {
            keys.forEach((obj) => {
                if (obj.keyCode === e.code) {
                    obj.isTriggered = true;
                }
            });

            const shortcutTriggered = keys.filter((obj) => obj.isTriggered).length === keys.length;

            if (shortcutTriggered) {
                searchFocus.focus();
            }
        });

        window.addEventListener('keyup', (e) => {
            keys.forEach((obj) => {
                if (obj.keyCode === e.code) {
                    obj.isTriggered = false;
                }
            });
        });

        $('#search-focus').on('focus', function () {
            $(this).addClass('border-primary');
        });

        $('#search-focus').on('blur', function () {
            $(this).removeClass('border-primary');
        });
    });
</script>
<%
    ArrayList<Venue> venueList = (ArrayList<Venue>) request.getAttribute("venueList");
%>

<body style="min-height: 100vh;background-color: #eee;">
    <header class="z-3 w-100 d-flex flex-row align-items-center justify-content-between p-3"
        style="background-color: #144272;">
        <div class="title text-uppercase ms-5 me-auto fw-bold text-white"
            style="font-size: 1.875rem;line-height: 2.25rem;">
            <a href="index.jsp" class="text-white">
                Event Point Limited
            </a>
        </div>
        <ul class="nav justify-content-end fs-6 fw-semibold flex align-items-center mr-5">
            <li class="nav-item nav-hover">
                <a class="text-white mx-3" href="#">
                    <!-- Browse Spaces -->
                    Browse Spaces
                </a>
            </li>
            <li class="nav-item border-start nav-hover">
                <a class="text-white mx-3" href="findVenue">
                    <!-- Browse Spaces -->
                    Find venues
                </a>
            </li>
            <li class="nav-item border-start nav-hover">
                <a class="text-white mx-3 px-3" href="login.jsp"> Login </a>
            </li>
            <li class="nav-item border bg-light rounded-1 py-1">
                <a class="mx-3 text-dark" href="register.jsp"> Sign Up </a>
            </li>
        </ul>
    </header>
    <section>
        <div class="text-start container-xl py-5">
            <div class="row">
                <div class="col-3 card">
                    <div class="card-header">
                        <label class="fs-4"><strong>Find venues</strong></label>
                    </div>
                    <div class="row card-body">
                        <p class="text-lg">Find the perfect venue for your event</p>
                    </div>
                </div>
                <div class="col card ms-4">
                    <div class="card-header d-flex flex-row justify-content-between align-items-center">
                        <label class="fs-4"><strong>Displaying 0 matching results</strong></label>
                        <form class="input-group w-25" action="searchVenue" method="GET">
                            <div class="form-outline">
                                <input id="search-focus" name="keyword" type="search" id="keyword"
                                    class="form-control border" />
                                <label class="form-label" for="keyword">Search</label>
                            </div>
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-search"></i>
                            </button>
                        </form>
                    </div>
                    <div class="card-body">
                        <% for (Venue venue : venueList) { %>
                        <div class="row">
                            <div class="card mb-3 ps-0" style="max-width: 100%;">
                                <div class="row g-0">
                                    <div class="col-md-4">
                                        <div class="bg-image hover-zoom w-100 h-100">
                                            <img src="assets/<%=venue.getImg() %>" alt="Venue Image"
                                                class="img-fluid w-100 h-100" />
                                        </div>
                                    </div>
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <h5 class="card-title"><%=venue.getName() %></h5>
                                            <p class="card-text">
                                                <%=venue.getDescription() %>
                                            </p>
                                            <p class="card-text">
                                                <table class="table table-sm table-borderless">
                                                    <tr>
                                                        <td>
                                                            <middle class="text-muted">Capacity:
                                                                <%=venue.getCapacity() %></middle>
                                                        </td>
                                                        <td>
                                                            <middle class="text-muted">Type: <%=venue.getTypeString() %>
                                                            </middle>
                                                        </td>
                                                    </tr>
                                                    <td>
                                                        <middle class="text-muted">Location:
                                                            <%=venue.getDistrict() %></middle>
                                                    </td>
                                                    <td>
                                                        <middle class="text-muted">Capacity: <%=venue.getCapacity() %>
                                                    </td>
                                                    </middle>
                                                </table>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>

</html>