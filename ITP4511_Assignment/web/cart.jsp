<%@page import="java.util.Map"%>
<%@page import="ict.bean.User"%>
<%@page import="java.util.HashMap"%>
<%@page import="ict.bean.view.VenueTimeslots"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="ict.bean.Venue"%>
<%@page import="ict.bean.Timeslot"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>

<ict:checkRole roleStr="Member,SeniorManager,Staff"
               redirectFrom="/getCart?action=cart" />

<% String role = (String) session.getAttribute("role"); %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>View Cart</title>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js"
                integrity="sha384-zYPOMqeu1DAVkHiLqWBUTcbYfZ8osu1Nd6Z89ify25QV9guujx43ITvfi12/QExE" crossorigin="anonymous">
        </script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.min.js"
                integrity="sha384-Y4oOpwW3duJdCWv5ly8SCFYWqFDsfob/3GkgExXKV4idmbt98QcxXYs9UoXAB7BZ" crossorigin="anonymous">
        </script>
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
                    function removeVenue(id, size) {
                        if (size == 1) {
                            if (confirm("Only one venue was selected!\n If you confirm to remove this venue, the page will be returned!") == true) {
                                $.ajax({url: "delCartVenue", type: "post", data: {action: "delCartVenue", venueId: id}, async: false, success: function (result) {
                                        location.href = "findVenue";
                                    }});
                            }
                        } else {
                            $.ajax({url: "delCartVenue", type: "post", data: {action: "delCartVenue", venueId: id}, async: false, success: function (result) {
                                    location.reload();
                                }});
                        }

                    }
                    $(document).ready(function () {
                        var params = new window.URLSearchParams(window.location.search);
                        var venue = params.get('venueId');
                        venue !== null ? showCalendar() : "";
                        function showCalendar() {
                            $("#calendarModal").modal('show');
                        }
                        $(window).scroll(function () {
                            if ($(this).scrollTop() >= $('#summary').height()) {
                                $('#summary').parent().addClass("me-5");
                                $('#summary').addClass("position-fixed end-0 top-0 mx-5 summary-width");
                            } else {
                                $('#summary').parent().removeClass("me-5");
                                $('#summary').removeClass("position-fixed end-0 top-0 mx-5 summary-width");
                            }
                        });

                        $("#bookVenueForm button[type=submit]").click(function () {
                            var submit = false;
                            for (var i = 0; i < $("input[name='timeOption']").length; i++) {
                                if ($(`input[name='timeOption']:eq(` + i + `)`).is(":checked")) {
                                    submit = true;
                                }
                            }
                            if (submit) {
                                $("#bookVenueForm").submit();
                            } else {
                                alert("You have to select a timeslot");
                            }
                        });
                    });
    </script>

    <%
        HashMap<String, Venue> cartVenues = (HashMap<String, Venue>) request.getAttribute("cartVenues");
        HashMap<String, HashMap<String, ArrayList<Timeslot>>> venueDateTimes = (HashMap<String, HashMap<String, ArrayList<Timeslot>>>) request.getAttribute("venueDateTimes");
        User user = (User) session.getAttribute("userInfo");
        String selectedVenue = (String) request.getAttribute("selectedVenue");
    %>

    <body style="background-color: #f2f2f2;">
        <jsp:include page="header.jsp" />
        <!-- Modal -->
        <div class="modal fade" id="calendarModal" tabindex="-1" aria-labelledby="calendarLabel" aria-hidden="true">
            <form class="modal-dialog modal-dialog-centered" id="bookVenueForm" action="getCart" method="post">
                <input type="hidden" id="action" name="action" value="addBookingVenue">
                <div class="modal-content">
                    <div class="modal-header">
                        <input type="hidden" id="venueId" name="venueId"
                               value="<%=selectedVenue != null || selectedVenue != "" ? selectedVenue : ""%>">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">
                            Calendar
                        </h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <jsp:include page="calendar.jsp" />
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit" onclick="event.preventDefault();" class="btn btn-primary">ADD TO
                            BOOK</button>
                    </div>
                </div>
            </form>
        </div>
        <!-- Modal -->

        <section class="p-5">
            <div class="fw-bold fs-5 my-3"><a href="findVenue">Find venues </a>/ <span
                    class="text-decoration-underline">Cart</span></div>
            <div class="row">
                <div class="col-md-8 mb-4">
                    <div class="card mb-4">
                        <div class="card-header py-3">
                            <h5 class="mb-0">Personal Information</h5>
                        </div>
                        <div class="card-body">
                            <div>
                                <div class="row mb-4">
                                    <div class="col">
                                        <div class="form-outline">
                                            <input type="text" id="firstName" disabled class="form-control border active"
                                                   value="<%=user.getFirstName()%>" />
                                            <label class="form-label" for="firstName">Name</label>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-outline">
                                            <input type="text" id="phone" disabled class="form-control border active"
                                                   value="<%=user.getPhone()%>" />
                                            <label class="form-label" for="phone">Phone</label>
                                        </div>
                                    </div>

                                </div>
                                <div class="form-outline mb-4">
                                    <input type="text" id="email" disabled class="form-control border active"
                                           value="<%=user.getEmail()%>" />
                                    <label class="form-label" for="email">Email</label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
                        //subTotal for each venueTimeslots
                        double[] subTotals = new double[cartVenues.size()];
                        double subTotal = 0;
                        int idx = 0;
                        for (Map.Entry<String, Venue> vs : cartVenues.entrySet()) {
                            Venue v = vs.getValue();
                            int venueId = v.getId();
                            String venueName = v.getName();
                            int capacity = v.getCapacity();
                            String venueType = v.getTypeString();
                            String venueAddress = v.getAddress();
                            String hourlyRate = "HK$ " + v.getHourlyRate() + " per hour";
                    %>
                    <div class="card mb-4">
                        <div class="card-header py-3 d-flex align-items-center justify-content-between">
                            <h5 class="mb-0">Booking Venue</h5>
                            <button type="button" onclick="removeVenue(<%=venueId%>, <%=cartVenues.size()%>);" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
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
                        <div class="card-header d-flex justify-content-between align-items-center">
                            <h5 class="mb-0">Timeslot</h5>
                            <a onclick="showCalendar()" href="<%=request.getContextPath()%>/getCart?action=cart&venueId=<%=venueId%>"
                               class="btn btn-link fs-7 rounded-pill">
                                Change Timeslots
                            </a>
                        </div>
                        <div class="card-body">
                            <%
                                HashMap<String, ArrayList<Timeslot>> dts = venueDateTimes.get("vid" + venueId);
                                if (dts != null && !dts.isEmpty()) {
                                    for (Map.Entry<String, ArrayList<Timeslot>> dt : dts.entrySet()) {
                                        String date = dt.getKey();
                                        ArrayList<Timeslot> tss = dt.getValue();
                            %>
                            <div class="card-title">
                                <h6 class="mb-0"><%=date%></h6>
                            </div>
                            <div class="d-flex flex-row justify-content-start mb-2 px-4 py-2">
                                <%
                                    subTotal = 0;
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
                            <%
                                    }
                                    subTotals[idx] = subTotal;
                                    idx++;
                                }%>
                        </div>
                        <div class="card-header border-top d-flex justify-content-between align-items-center">
                            <h5 class="mb-0">Guest List</h5>
                            <a type="button" href="<%=request.getContextPath()%>/createGuests?action=addlist"
                               class="btn btn-link fs-7 rounded-pill">
                                Add Guests
                            </a>
                        </div>
                    </div>
                    <%

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
                                    int i = 0;
                                    for (Map.Entry<String, Venue> venues : cartVenues.entrySet()) {
                                        String vid = venues.getKey();
                                        Venue venue = venues.getValue();
                                %>
                                <li
                                    class="list-group-item d-flex justify-content-between align-items-center border-0 px-0 pb-0">
                                    <%=venue.getName()%>
                                    <span>HK$ <%=subTotals[i]%></span>
                                </li>
                                <%
                                        i++;
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
                            <form method="post" action="addBooking">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="total" value="<%=Arrays.stream(subTotals).sum()%>">
                                <button type="submit" class="btn btn-primary w-100">ADD BOOKING</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>

</html>