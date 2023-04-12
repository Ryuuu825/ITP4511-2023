<%-- 
   Document   : venueInfo
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page import="ict.bean.GuestList"%>
<%@page import="ict.bean.Guest"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Guest List</title>
        <script src="https://code.jquery.com/jquery-3.6.4.min.js"
        integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.css" rel="stylesheet" />
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.js"></script>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />
    </head>
    <style>
        .nav-hover:hover {
            font-size: 1.125rem;
            line-height: 1.75rem;
        }

    </style>
    <script>
        $(document).ready(function () {
            var params = new window.URLSearchParams(window.location.search);
            var bookingId = params.get('bookingId');
            var venueId = params.get('venueId');

            $("#search-button").click(function () {
                var search = $("#search-input").val();
                window.location.href = "viewGuests?action=search&bookingId=" + bookingId + "&venueId=" + venueId + "&search=" + search;
            });

            //key press enter
            $("#search-input").keypress(function (e) {
                if (e.which == 13) {
                    var search = $("#search-input").val();
                    window.location.href = "viewGuests?search&bookingId=" + bookingId + "&venueId=" + venueId + "&search=" + search;
                }
            });

            $(".form-control").focus(function () {
                $(this).parent().find($(".form-label")).addClass("bg-white");
                $(this).addClass("border-2 border-primary active");
            });

            $(".form-control").blur(function () {
                $(this).parent().find($(".form-label")).removeClass("bg-white");
                if ($(this).val() == "") {
                    $(this).removeClass("border-2 border-primary active");
                } else {
                    $(this).removeClass("border-2 border-primary");
                }
            });
        });
    </script>
    <%
        String role = (String) session.getAttribute("role");
        if (role == null) {
            response.sendRedirect("");
        }
        GuestList guests = (GuestList) request.getAttribute("guests");
        int bookingId = guests.getBookingId();
        int venueId = guests.getVenueId();
    %>

    <body style="background-color: #f2f2f2;">
        <header class="z-3 w-100 d-flex flex-row align-items-center justify-content-between p-3"
                style="background-color: #144272; height: 5rem;">
            <div class="title text-uppercase ms-3 me-auto fw-bold text-white" style="font-size: 1.5rem;line-height: 2rem;">
                <a href="index.jsp" class="text-white text-decoration-none">
                    Event Point Limited
                </a>
            </div>
            <ul class="nav justify-content-end fs-6 fw-semibold flex align-items-center mr-5">
                <li class="nav-item nav-hover">
                    <a class="text-white mx-3 text-decoration-none" href="viewVenue">
                        <!-- Browse Spaces -->
                        Venue
                    </a>
                </li>
                <li class="nav-item border-start nav-hover">
                    <a class="text-white mx-3 text-decoration-none" href="venueInfo.jsp">
                        <!-- Browse Spaces -->
                        Timeslot
                    </a>
                </li>
                <li class="nav-item border-start nav-hover">
                    <a class="text-white mx-3 text-decoration-none" href="searchBookings"> Booking </a>
                </li>
                <li class="nav-item border bg-light rounded-1 py-1">
                    <a class="mx-3 text-dark text-decoration-none" href="logout"> Sign Out </a>
                </li>
            </ul>
        </header>
        <section class="p-5">
            <div class="fw-bold fs-5 my-3"><a href="searchBookings">Bookings </a>> <span
                    class=""><a href="searchBookings?bookingId=<%=bookingId%>&venueId=<%=venueId%>">Details </a></span> > <span
                    class="text-decoration-underline">View Guests</span>
            </div>
            <ict:result />
            <div class="bg-white p-3 border card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="m-2">Guests</h5>
                    <div class="input-group" style="width: 30%;">
                        <div class="form-outline">
                            <input id="search-input" type="search" class="form-control border rounded-start" />
                            <label class="form-label" for="search">Search by guest name or email</label>
                        </div>
                        <button id="search-button" type="button" class="btn btn-primary">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </div>
                <% if ("Member".equalsIgnoreCase(role)) {%>
                <form class="p-3 border-bottom" action="addGuest" method="post">
                    <div class="row d-flex justify-content-between align-items-center ">
                        <div class="col-md-5">
                            <div class="form-outline">
                                <input type="text" name="name" id="name" required
                                       aria-required="true" class="form-control border" />
                                <label class="form-label" for="name">Name</label>
                            </div>
                        </div>
                        <div class="col-md-5">
                            <div class="form-outline">
                                <input type="email" name="email" id="email" class="form-control border" />
                                <label class="form-label" for="email">Email address</label>
                            </div>
                        </div>
                        <div class="col-md-2 my-1">
                            <button type="submit" class="btn btn-primary btn-block">
                                Add Guest
                            </button>
                        </div>
                    </div>
                </form>
                <% }%>
                <div class="table-responsive text-nowrap mt-3 fs-6">
                    <ict:guestTable guests="<%=guests%>" role="<%=role%>"/>
                </div>
            </div>
        </section>
    </body>

</html>