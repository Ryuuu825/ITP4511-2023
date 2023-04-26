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
        <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.css" rel="stylesheet" />

        <script src="https://cdn.tailwindcss.com"></script>

        <script src="https://code.jquery.com/jquery-3.6.4.min.js"
        integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
        <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"
    ></script>

        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />
        

        <style>
            .btn-primary {
                background-color: #3b71ca !important;
            }

            input {
                border : 1px solid #e0e0e0;
                border-radius: 0.375rem;
            }
        </style>
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

            // show the modal
            if (params.get('action') == "edit") {
                $("#editModal").modal("show");
            }
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

    <jsp:useBean id="guest" class="ict.bean.Guest" scope="request" />

    <body style="background-color: #f2f2f2;">
        <jsp:include page="header.jsp" />


        <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="errorModalLabel">
            <div class="modal-dialog modal-dialog-centered"  style="width:20rem;height: 20rem; ">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="errorModalLabel">
                            Edit Guest
                        </h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                            onclick=""></button>
                    </div>
                    <% if (guest != null) { %>

                    <form action="<%=request.getContextPath()%>/editGuest" method="post">
                        <div class="modal-body">
                            <input type="hidden" name="id" value="<%=guest.getId()%>">
                            <input type="hidden" name="action" value="update">

                            <input type="hidden" name="bookingId" value="<%=request.getParameter("bookingId")%>">
                            <input type="hidden" name="venueId" value="<%=request.getParameter("venueId")%>">

                            <div class="form-outline my-3">
                                <input id="editGuestName" name="editGuestName" type="text" class="active form-control border rounded-start" value="<%=guest.getName()%>" />
                                <label class="form-label" for="search">Guest Name</label>
                            </div>

                            <div class="form-outline mb-3">
                                <input id="editGuestEmail" name="editGuestEmail" type="text" class="active form-control border rounded-start" value="<%=guest.getEmail()%>" />
                                <label class="form-label" for="search">Guest Email</label>
                            </div>
                                
                        </div>
                        <div class="modal-footer">
                            <a href="<%=request.getContextPath()%>/viewGuests?action=search&bookingId=<%=request.getParameter("bookingId")%>&venueId=<%=request.getParameter("venueId")%>" class="btn btn-secondary" >Cancel</a>
                            <button type="submit" class="btn btn-primary" data-bs-dismiss="modal">Save</button>
                            
                        </div>
                    </form>
                    <% } %>
                </div>
            </div>
        </div>

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
                    <input type="hidden" name="bookingId" value="<%=bookingId%>" />
                    <input type="hidden" name="venueId" value="<%=venueId%>" />
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