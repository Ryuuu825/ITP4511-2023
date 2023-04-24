<%-- 
   Document   : venueInfo
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page import="ict.bean.view.BookingDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>

<ict:checkRole roleStr="Member,SeniorManager,Staff" redirectFrom="/member/booking" />

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Bookings</title>
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
            $(".form-control").focus(function () {
                $(".form-label").addClass("bg-white");
            });

            $(".form-control").blur(function () {
                $(".form-label").removeClass("bg-white");
            });

            $("#search-button").click(function () {
                var search = $("#search-input").val();
                window.location.href = "searchBookings?search=" + search;
            });

            //key press enter
            $("#search-input").keypress(function (e) {
                if (e.which == 13) {
                    var search = $("#search-input").val();
                    window.location.href = "searchBookings?search=" + search;
                }
            });

            $("#search-input").focus(function () {
                $(this).addClass("border-2 border-primary active");
            });

            $("#search-input").blur(function () {
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
        ArrayList<BookingDTO> bookings = (ArrayList<BookingDTO>) request.getAttribute("bookingDTOs");
    %>

    <body style="background-color: #f2f2f2;">
        <jsp:include page="header.jsp" />

        <section class="p-5">
            <ict:result />
            <div class="card bg-white p-3">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="m-2">Bookings</h5>
                    <div class="input-group" style="width: 30%;">
                        <div class="form-outline">
                            <input id="search-input" type="search" class="form-control border rounded-start" />
                            <label class="form-label" for="search-input">
                                <% if (role.equals("Member")) { %>
                                    Search by booking id
                                <% } else { %>
                                    Search by booking id or member name
                                <% } %>
                            </label>
                        </div>
                        <button id="search-button" type="button" class="btn btn-primary">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </div>

                <div class="table-responsive text-nowrap mt-3 fs-6">
                    <ict:bookingTable bookings="<%=bookings%>" role="<%=role%>"/>
                </div>
            </div>
        </section>
    </body>

</html>