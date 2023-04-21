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

        .modal{
            --mdb-modal-width: 900px;
        }
    </style>
    <script>
                    $(document).ready(function () {
                        var params = new window.URLSearchParams(window.location.search);
                        var action = params.get('action');
                        action == "edit" ? editVenue() : "";
                        $(".form-control").focus(function () {
                            $(".form-label").addClass("bg-white");
                            $(this).addClass("border-2 border-primary active");
                        });

                        $(".form-control").blur(function () {
                            $(".form-label").removeClass("bg-white");
                            $(this).addClass("border-2 border-primary active");
                            if ($(this).val() === "") {
                                $(this).removeClass("border-2 border-primary active");
                            } else {
                                $(this).removeClass("border-2 border-primary");
                            }
                        });

                        $("#search-button").click(function () {
                            var search = $("#search-input").val();
                            location.href = "searchVenues?action=search&search=" + search;
                        });
                        //key press enter
                        $("#search-input").keypress(function (e) {
                            if (e.which === 13) {
                                var search = $("#search-input").val();
                                location.href = "searchVenues?action=search&search=" + search;
                            }
                        });

                        function editVenue() {
                            $('#venueModal').modal('show');
                        }
                        ;

                        $("#addvenue").click(function () {
                            $('#venueModal').modal('show');
                            $('.modal-title').html("Add Venue");
                            $('.form-control').val("");
                            $('.form-control').removeClass("active");
                        });

                        $("#venueModal").on('hidden.bs.modal', () => {
                            location.href = 'searchVenues';
                        })

                        $(".form-control").val() !== "" ? $(".form-control").addClass("active") : "";
                        var staff = $('#selected-staff').val();
                        $("#select-staff").val(staff).change();
                    });
    </script>
    <%
        String role = (String) session.getAttribute("role");
        if (role == null || "Member".equalsIgnoreCase(role)) {
            response.sendRedirect("");
        }
        ArrayList<VenueDTO> venueDTOs = (ArrayList<VenueDTO>) request.getAttribute("venueDTOs");
        VenueDTO venueDTO = (VenueDTO) request.getAttribute("venueDTO");
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
            <!-- Modal -->
            <div class="modal fade" id="venueModal" tabindex="-1" aria-labelledby="venueLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel"><%=venueDTO != null ? "Edit" : "Add"%> Venue
                            </h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="handleVenue" method="post">
                                <input type="hidden" id="action" name="action"
                                       value="<%=venueDTO != null ? "edit" : "add"%>">
                                <input type="hidden" id="venueId" name="id">
                                <div>
                                    <div class="row">
                                        <div class="col">
                                            <div class="form-outline card h-100 bg-gray text-center">
                                                <%
                                                    if (venueDTO != null && !venueDTO.getVenue().getImg().equals("")) {
                                                        out.print("<img src=\"assets/" + venueDTO.getVenue().getImg() + "\" class=\"card-img w-100 h-100\" alt=\"Venue image\">");
                                                    } else {
                                                %>
                                                <div class="card-img d-flex bg-secondary bg-opacity-25 w-100 h-100 justify-content-center align-items-center">
                                                    Click to upload image
                                                </div>
                                                <% }%>
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="form-outline mb-4">
                                                <input type="text" id="venueName" class="form-control border"
                                                       value="<%=venueDTO != null ? venueDTO.getVenue().getName() : ""%>" />
                                                <label class="form-label" for="venueName">Name</label>
                                            </div>
                                            <div class="row">

                                                <div class="col">
                                                    <div class="form-outline mb-4">
                                                        <input type="text" id="capacity" class="form-control border"
                                                               value="<%=venueDTO != null ? venueDTO.getVenue().getCapacity() : ""%>" />
                                                        <label class="form-label" for="capacity">Capacity</label>
                                                    </div>
                                                </div>
                                                <div class="col">
                                                    <div class="form-outline mb-4">
                                                        <select class="form-control border" name="type" id="select-type" aria-label="select"
                                                                value="<%=venueDTO != null ? venueDTO.getVenue().getType() : ""%>">
                                                            <%
                                                                String[] types = ict.bean.Venue.typeStrings;
                                                                for (int i = 1; i <= types.length; i++) {
                                                                    if (venueDTO != null && venueDTO.getVenue().getType() == i) {
                                                                        out.print("<option selected value=\"" + i + "\">" + types[i - 1] + "</option>");
                                                                    } else {
                                                                        out.print("<option value=\"" + i + "\">" + types[i - 1] + "</option>");
                                                                    }
                                                                }
                                                            %>
                                                        </select>
                                                        <label class="form-label" for="select-type">Type</label>
                                                    </div>
                                                </div>
                                                <div class="col">
                                                    <div class="form-outline mb-4">
                                                        <input type="text" id="hourlyRate" class="form-control border"
                                                               value="<%=venueDTO != null ? venueDTO.getVenue().getHourlyRate() : ""%>" />
                                                        <label class="form-label" for="hourlyRate">HourlyRate</label>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- Message input -->
                                            <div class="form-outline mb-4">
                                                <input type="hidden" id="selected-staff" value="<%=venueDTO != null ? venueDTO.getUser().getId() : "0"%>">
                                                <select class="form-select border form-control" name="staff" id="select-staff" aria-label="select">
                                                    <option value="0">None</option>
                                                    <%
                                                        ArrayList<User> staff = (ArrayList<User>) request.getAttribute("staff");
                                                        for (int i = 1; i <= staff.size(); i++) {
//                                                            if (venueDTO != null && venueDTO.getUser().getId() == i) {
//                                                                out.print("<option selected value=\"" + staff.get(i - 1).getId()+ "\">" + staff.get(i - 1).getFirstName() + " " + staff.get(i - 1).getLastName() + "</option>");
//                                                            } else {
                                                            out.print("<option value=\"" + staff.get(i - 1).getId() + "\">" + staff.get(i - 1).getFirstName() + " " + staff.get(i - 1).getLastName() + "</option>");
//                                                            }
                                                        }
                                                    %>
                                                </select>
                                                <label class="form-label" for="select-staff">Person in charge</label>
                                            </div>
                                            <!-- Message input -->
                                            <div class="form-outline">
                                                <textarea class="form-control border" id="address"
                                                          rows="4"><%=venueDTO != null ? venueDTO.getVenue().getDescription() : ""%></textarea>
                                                <label class="form-label" for="address">Address</label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary"><%=venueDTO != null ? "Save changes" : "Add Venue"%></button>
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
                    <ict:venueTable venues="<%=venueDTOs%>" />
                </div>

            </div>
        </section>
    </body>

</html>