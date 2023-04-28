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
        <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
        crossorigin="anonymous"
        ></script>  
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
                window.location.href = "<%=request.getContextPath()%>/member/booking?search=" + search;
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
        ArrayList<BookingDTO> upcomingBookings = (ArrayList<BookingDTO>) request.getAttribute("upcomingBookings");
    %>

    <body style="background-color: #f2f2f2;">
        <jsp:include page="header.jsp" />

        <section class="p-5">
            <ict:result />
            <% 
                // upcomingBookings 
                if ( upcomingBookings != null && upcomingBookings.size() > 0 ) {
            %>
                <div class="alert alert-info fade show" role="alert">
                    <div class="d-flex flex-row justify-content-between">
                       <div>
                            You have <span class="fw-bold" style="font-size: 1.1rem;"><%= upcomingBookings.size() %></span> upcoming bookings.
                            <br>
                            You can click <a href="<%=request.getContextPath()%>/member/booking?action=upcoming" class="link-primary fw-semibold">here</a> view them.
                       </div>
                        <div onclick="$('.alert').alert('close')" style="cursor: pointer;">
                            &times;
                        </div>
                    </div>
                </div>
            <% } %>     
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