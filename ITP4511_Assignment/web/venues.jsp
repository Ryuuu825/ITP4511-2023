<%-- 
   Document   : venueInfo
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page import="ict.bean.view.VenueDTO"%>
<%@page import="ict.bean.Venue"%>
<%@page import="ict.bean.User"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Venues</title>
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
</style>
<script>
    $(document).ready(function () {
        var params = new window.URLSearchParams(window.location.search);
        var action = params.get('action');
        console.log(action);
        action == "edit" ? editVenue() : "";

        $(".form-control").focus(function () {
            $(".form-label").addClass("bg-white");
        });

        $(".form-control").blur(function () {
            $(".form-label").removeClass("bg-white");
        });

        $("#search-button").click(function () {
            var search = $("#search-input").val();
            window.location.href = "searchVenues?action=search&search=" + search;
        });

        //key press enter
        $("#search-input").keypress(function (e) {
            if (e.which === 13) {
                var search = $("#search-input").val();
                window.location.href = "searchVenues?action=search&search=" + search;
            }
        });

        $("#search-input").focus(function () {
            $(this).addClass("border-2 border-primary active");
        });

        $("#search-input").blur(function () {
            if ($(this).val() === "") {
                $(this).removeClass("border-2 border-primary active");
            } else {
                $(this).removeClass("border-2 border-primary");
            }
        });

        function editVenue() {
            $('#venueModal').modal('show');
        };

        $("#addvenue").click(function () {
            $('#venueModal').modal('show');
            $('.modal-title').html("Add Venue");
        });
    });
</script>
<%
        String role = (String) session.getAttribute("role");
        if (role == null || "Member".equalsIgnoreCase(role)) {
            response.sendRedirect("");
        }

    %>
<jsp:useBean id="venueDTOs" class="java.util.ArrayList<VenueDTO>" scope="request" />
<jsp:useBean id="venueDTO" class="ict.bean.view.VenueDTO" scope="request" />

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
        <!-- Modal -->
        <div class="modal fade" id="venueModal" tabindex="-1" aria-labelledby="venueLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel"><%=venueDTO != null ? "Edit" : "Add"%> Venue
                        </h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <form action="handleVenue">
                            <input type="hidden" id="action" name="action" value="<%=venueDTO != null ? "edit" : "add"%>">
                            <input type="hidden" id="venueId" name="id">
                            <div>
                                <div class="form-outline mb-4">
                                    <input type="text" id="venueName" disabled class="form-control border active"
                                        value="<%=venueDTO != null ? venueDTO.getVenue().getName() : ""%>" />
                                    <label class="form-label" for="venueName">Name</label>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <div class="form-outline mb-4">
                                            <input type="text" id="capacity" disabled class="form-control border active"
                                                value="<%=venueDTO != null ? venueDTO.getVenue().getCapacity(): ""%>" />
                                            <label class="form-label" for="capacity">Capacity</label>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-outline mb-4">
                                            <input type="text" id="type" disabled class="form-control border active"
                                                value="<%=venueDTO != null ? venueDTO.getVenue().getTypeString(): ""%>" />
                                            <label class="form-label" for="type">Type</label>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="form-outline mb-4">
                                            <input type="text" id="hourlyRate" disabled
                                                class="form-control border active"
                                                value="<%=venueDTO != null ? venueDTO.getVenue().getHourlyRate(): ""%>" />
                                            <label class="form-label" for="hourlyRate">HourlyRate</label>
                                        </div>
                                    </div>
                                </div>
                                <!-- Message input -->
                                <div class="form-outline">
                                    <textarea class="form-control border active" disabled id="address"
                                        rows="2"><%=venueDTO != null ? venueDTO.getVenue().getDescription(): ""%></textarea>
                                    <label class="form-label" for="address">Address</label>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal -->
        <ict:result />
        <div class="card bg-white p-3">
            <div class="card-header d-flex justify-content-between align-items-center">
                <h5 class="m-2">Venues</h5>
                <div class="input-group" style="width: 35%;">
                    <div class="form-outline">
                        <input id="search-input" type="search" class="form-control border rounded-start" />
                        <label class="form-label" for="search-input">Search by venue name, location or person in
                            charge</label>
                    </div>
                    <button id="search-button" type="button" class="btn btn-primary">
                        <i class="fas fa-search"></i>
                    </button>
                </div>
            </div>

            <div class="table-responsive text-nowrap mt-3 fs-6">
                <ict:venueTable venues="<%=venueDTOs %>" />
            </div>

        </div>
    </section>
</body>

</html>