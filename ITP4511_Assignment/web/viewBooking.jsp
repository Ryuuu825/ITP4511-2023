<%-- 
   Document   : venueInfo
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page import="ict.bean.GuestList"%>
<%@page import="ict.bean.view.DateTimeslots"%>
<%@page import="ict.bean.view.VenueTimeslots"%>
<%@page import="ict.bean.view.BookingDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="ict.bean.Venue"%>
<%@page import="ict.bean.Timeslot"%>
<%@page import="ict.bean.Guest"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>View Booking</title>
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
        
        .summary-width {
            width: calc(33.33% - 3rem);
        }
    </style>
    <script>
        $(document).ready(function () {
            $(window).scroll(function () {
                if ($(this).scrollTop() >= $('#summary').height()) {
                    $('#summary').parent().addClass("me-5");
                    $('#summary').addClass("position-fixed end-0 top-0 mx-5 summary-width");
                }else {
                    $('#summary').parent().removeClass("me-5");
                    $('#summary').removeClass("position-fixed end-0 top-0 mx-5 summary-width");
                }
            });
        });
    </script>

    <jsp:useBean scope="request" id="bookingDTO" class="ict.bean.view.BookingDTO" />
    <%
        String role = (String)session.getAttribute("role");
        if (role == null) {
            response.sendRedirect("");
        }
        int bookingId = bookingDTO.getBooking().getId();
        String memberName = bookingDTO.getMember().getFirstName() + " " + bookingDTO.getMember().getLastName();
        String memberPhone = bookingDTO.getMember().getPhone();
        String memberEmail = bookingDTO.getMember().getEmail();

        ArrayList<GuestList> guests = bookingDTO.getVenueGuestlists();
        ArrayList<VenueTimeslots> venueTimeslotses = bookingDTO.getVenueTimeslotses();
        double[] subTotals = new double[venueTimeslotses.size()];
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
                    <a class="text-white mx-3 text-decoration-none" href="searchVenues">
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
            <div class="fw-bold fs-5 my-3"><a href="searchBookings">Bookings </a>/ <span
                    class="text-decoration-underline">Details</span></div>
            <div class="row">
                <div class="col-md-8 mb-4">
                    <div class="card mb-4">
                        <div class="card-header py-3">
                            <h5 class="mb-0">Member Information</h5>
                        </div>
                        <div class="card-body">
                            <div>
                                <div class="row mb-4">
                                    <div class="col">
                                        <div class="form-outline">
                                            <input type="text" id="firstName" disabled class="form-control border active"
                                                   value="<%=memberName%>" />
                                            <label class="form-label" for="firstName">Name</label>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-outline">
                                            <input type="text" id="phone" disabled class="form-control border active"
                                                   value="<%=memberPhone%>" />
                                            <label class="form-label" for="phone">Phone</label>
                                        </div>
                                    </div>

                                </div>
                                <div class="form-outline mb-4">
                                    <input type="text" id="email" disabled class="form-control border active"
                                           value="<%=memberEmail%>" />
                                    <label class="form-label" for="email">Email</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                        //subTotal for each venueTimeslots
                        double subTotal = 0;
                        for (int i = 1; i <= venueTimeslotses.size(); i++) {
                            Venue v = venueTimeslotses.get(i - 1).getVenue();
                            int venueId = v.getId();
                            String venueName = v.getName();
                            int capacity = v.getCapacity();
                            String venueType = v.getTypeString();
                            String venueAddress = v.getAddress();
                            String hourlyRate = "HK$ " + v.getHourlyRate() + " per hour";
                    %>
                    <div class="card mb-4">
                        <div class="card-header py-3">
                            <h5 class="mb-0">Booked Venue <%=i%></h5>
                        </div>
                        <div class="card-body">
                            <div>
                                <div class="form-outline mb-4">
                                    <input type="text" id="venueName" disabled class="form-control border active"
                                           value="<%=venueName%>" />
                                    <label class="form-label" for="venueName">Name</label>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="form-outline mb-4">
                                            <input type="text" id="capacity" disabled class="form-control border active"
                                                   value="<%=capacity%>" />
                                            <label class="form-label" for="capacity">Capacity</label>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-outline mb-4">
                                            <input type="text" id="type" disabled class="form-control border active"
                                                   value="<%=venueType%>" />
                                            <label class="form-label" for="type">Type</label>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-outline mb-4">
                                            <input type="text" id="hourlyRate" disabled class="form-control border active"
                                                   value="<%=hourlyRate%>" />
                                            <label class="form-label" for="hourlyRate">HourlyRate</label>
                                        </div>
                                    </div>
                                </div>
                                <!-- Message input -->
                                <div class="form-outline">
                                    <textarea class="form-control border active" disabled id="address"
                                              rows="2"><%=venueAddress%></textarea>
                                    <label class="form-label" for="address">Address</label>
                                </div>
                            </div>
                        </div>
                        <div class="card-header">
                            <h5 class="mb-0">Timeslot</h5>
                        </div>
                        <div class="card-body">
                            <%
                                ArrayList<DateTimeslots> dtss = venueTimeslotses.get(i - 1).getDateTimeslots();
                                for (DateTimeslots dts : dtss) {
                                    String date = dts.getDate().toString();
                            %>
                            <div class="card-title">
                                <h6 class="mb-0"><%=date%></h6>
                            </div>
                            <div class="d-flex flex-row justify-content-start mb-2 px-4 py-2">
                                <%
                                    ArrayList<Timeslot> tss = dts.getTimeslots();
                                    subTotal += tss.size() * v.getHourlyRate();
                                    for (Timeslot ts : tss) {
                                        String startTime = ts.getStartTime().toString();
                                        String endTime = ts.getEndTime().toString();
                                %>
                                <label class="border px-2 me-2 border-2 border-primary text-primary rounded-pill">
                                    <%=startTime%> - <%=endTime%>
                                </label>
                                <%
                                    }
                                %>
                            </div>
                            <% }%>
                        </div>
                        <div class="card-header border-top d-flex justify-content-between align-items-center">
                            <h5 class="mb-0">Guest List</h5>
                            <a type="button" href="viewGuests?action=search&bookingId=<%=bookingId%>&venueId=<%=venueId%>" class="btn btn-link fs-7 rounded-pill">
                                View Guests
                            </a>
                        </div>
                    </div>
                    <%
                            subTotals[i - 1] = subTotal;
                        }
                    %>
                </div>
                <div class="col-md-4 mb-4">
                    <div class="card mb-4" id="summary">
                        <div class="card-header py-3">
                            <h5 class="mb-0">Summary</h5>
                        </div>
                        <div class="card-body">
                            <ul class="list-group list-group-flush">
                                <%
                                    for (int i = 0; i < venueTimeslotses.size(); i++) {
                                        Venue venue = venueTimeslotses.get(i).getVenue();
                                %>
                                <li
                                    class="list-group-item d-flex justify-content-between align-items-center border-0 px-0 pb-0">
                                    <%=venue.getName()%>
                                    <span>HK$ <%=subTotals[i]%></span>
                                </li>
                                <%
                                    }
                                %>
                                <li
                                    class="border-top list-group-item d-flex justify-content-between align-items-center px-0 my-3">
                                    <div>
                                        <strong>Total amount</strong>
                                    </div>
                                    <span class="fs-5"><strong>HK$ <%=Arrays.stream(subTotals).sum()%></strong></span>
                                </li>
                            </ul>
                            <ict:statusButton status="<%=bookingDTO.getBooking().getStatus()%>" role="<%=role%>"/>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>

</html>