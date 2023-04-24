<%-- 
   Document   : venueInfo
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page import="ict.bean.User"%>
<%@page import="java.time.temporal.TemporalAdjusters"%>
<%@page import="java.time.LocalDate"%>
<%@page import="ict.bean.Timeslot"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="ict.bean.view.CalendarTimeslot"%>
<%@page import="ict.bean.Venue"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Page</title>
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

        .card .bg-image {
            border-top-left-radius: var(--mdb-card-border-radius);
            border-bottom-left-radius: var(--mdb-card-border-radius);
            border-top-right-radius: 0;
        }

        .venue-table td {
            padding-bottom: 0;
        }

        .modal {
            --mdb-modal-width: 75%;
        }
    </style>
    <script>
                    $(document).ready(function () {
                        var params = new window.URLSearchParams(window.location.search);
                        var action = params.get('action');
                        action == "calendar" ? showCalendar() : "";

                        const searchFocus = document.getElementById('search-focus');
                        const keys = [{
                                keyCode: 'AltLeft',
                                isTriggered: false
                            },
                            {
                                keyCode: 'ControlLeft',
                                isTriggered: false
                            }
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

                        $("#calendarModal").on('hidden.bs.modal', () => {
                            location.href = 'searchVenues';
                        })

                        function showCalendar() {
                            $("#calendarModal").modal('show');
                        }
                        ;
                    });
    </script>
    <%
        User user = (User) session.getAttribute("userInfo");
        ArrayList<Venue> venueList = (ArrayList<Venue>) request.getAttribute("venueList");
        //VenueTimeslots calendar = (VenueTimeslots) request.getAttribute("calendar");
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
                <%
                    if (user == null) {

                %>
                <li class="nav-item border-start nav-hover">
                    <a class="text-white mx-3 px-3" href="login.jsp"> Login </a>
                </li>
                <li class="nav-item border bg-light rounded-1 py-1">
                    <a class="mx-3 text-dark" href="register.jsp"> Sign Up </a>
                </li>
                <%            } else {
                %>
                <li class="nav-item border bg-light rounded-1 py-1">
                    <a class="mx-3 text-dark" href="logout"> Sign Out </a>
                </li>
                <%
                    }
                %>
            </ul>
        </header>
        <div></div>
        <section>
            <!-- Modal -->
            <div class="modal fade" id="calendarModal" tabindex="-1" aria-labelledby="calendarLabel" aria-hidden="true">
                <form class="modal-dialog modal-dialog-centered" action="handleBooking" method="post">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">
                                Calendar
                            </h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <table class="table table-bordered text-center">
                                <thead>
                                    <tr class="table-primary">
                                        <th class="col">Sunday</th>
                                        <th class="col">Monday</th>
                                        <th class="col">Tuesday</th>
                                        <th class="col">Wednesday</th>
                                        <th class="col">Thursday</th>
                                        <th class="col">Friday</th>
                                        <th class="col">Saturday</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <%
                                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
                                        LocalDate date = LocalDate.now().plusDays(1);
                                        int day = date.getDayOfWeek().getValue();
                                        int month = date.getMonth().getValue();
                                        LocalDate max = date.plusMonths(1);
                                        int rowOfMonth = 1;
                                        boolean newMonth = false;
                                        out.print("<tr><td class=\"table-secondary\" colspan=\"7\">" + date.getMonth() + "</td></tr>");
                                        while (date.isBefore(max)) {
                                            if (newMonth) {
                                                out.print("<tr><td class=\"table-secondary\" colspan=\"7\">" + date.getMonth() + "</td></tr>");
                                                month = date.getMonth().getValue();
                                                day = date.getDayOfWeek().getValue();
                                                rowOfMonth = 1;
                                            }
                                            out.print("<tr>");

                                            for (int j = 0; j <= 6; j++) {
                                                if (j < day && rowOfMonth == 1) {
                                                    out.print("<td></td>");
                                                } else if (month < date.getMonth().getValue() && newMonth == false) {
                                                    out.print("<td></td>");
                                                } else if (date.isBefore(max)) {
                                                    out.print("<td>" + formatter.format(date) + "</td>");
                                                    date = date.plusDays(1);
                                                }
                                            }
                                            newMonth = month < date.getMonth().getValue();
                                            out.print("</tr>");
                                            rowOfMonth++;
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">ADD TO BOOK</button>
                        </div>
                    </div>
                </form>
            </div>
            <!-- Modal -->
            <div class="text-start container-xl py-5">
                <div class="row">
                    <div class="col-md-3 card">
                        <div class="card-header">
                            <label class="fs-4"><strong>Find venues</strong></label>
                        </div>
                        <div class="row card-body">
                            <p class="text-lg">Find the perfect venue for your event</p>
                        </div>
                    </div>
                    <div class="col card ms-4">
                        <div class="card-header d-flex flex-row justify-content-between align-items-center">
                            <label class="fs-4"><strong>Displaying <%=venueList.size()%> matching results</strong></label>
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
                            <% for (Venue venue : venueList) {%>
                            <div class="row">
                                <div class="card mb-3 ps-0" style="max-width: 100%;">
                                    <div class="row g-0">
                                        <div class="col-md-5">
                                            <div class="bg-image hover-zoom w-100 h-100">
                                                <img src="assets/<%=venue.getImg()%>" alt="Venue Image"
                                                     class="img-fluid w-100 h-100" />
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="card-body pb-0">
                                                <h5 class="card-title"><%=venue.getName()%></h5>
                                                <p class="card-text">
                                                    <%=venue.getDescription()%>
                                                </p>
                                                <table class="venue-table table table-sm table-borderless">
                                                    <tr>
                                                        <td>
                                                    <middle class="text-muted">Capacity:
                                                        <%=venue.getCapacity()%></middle>
                                                    </td>
                                                    <td>
                                                    <middle class="text-muted">Type: <%=venue.getTypeString()%>
                                                    </middle>
                                                    </td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="2">
                                                    <middle class="text-muted">Address:
                                                        <%=venue.getAddress()%></middle>
                                                    </td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="2">
                                                    <middle class="text-muted">Location:
                                                        <%=venue.getDistrict()%></middle>
                                                    </td>
                                                    </tr>
                                                </table>
                                                <div
                                                    class="card-footer px-0 d-flex justify-content-between align-items-center">
                                                    <div class="note note-primary">
                                                        <strong>HourlyRate(HK$):
                                                            <%=venue.getHourlyRate()%>
                                                        </strong>
                                                    </div>
                                                    <a href="getCalendar?action=calendar&venueId=<%=venue.getId()%>"
                                                       onclick="showCalendar()" class="
                                                       book-btn btn btn-primary">Book Venue</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <% }%>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>

</html>