<%-- 
   Document   : venueInfo
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page import="java.util.HashMap"%>
<%@page import="ict.bean.User"%>
<%@page import="java.time.temporal.TemporalAdjusters"%>
<%@page import="java.time.LocalDate"%>
<%@page import="ict.bean.Timeslot"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="ict.bean.view.CalendarTimeslot"%>
<%@page import="ict.bean.Venue"%>
<%@page import="java.lang.Integer"%>
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
            --mdb-modal-width: 60%;
        }

        .date-td {
            width: 10%;
        }

        .date-td,
        .date-td label {
            cursor: pointer;
        }

        .ts-btn:hover {
            background-color: var(--mdb-primary);
            color: white;
        }

        .ts-btn:disabled:hover {
            background-color: var(--mdb-secondary);
            color: gray;
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
                            location.href = 'findVenue';
                        })

                        function showCalendar() {
                            $("#calendarModal").modal('show');
                        }
                        ;

                        $(".ts-btn").click(function () {
                            $(this).toggleClass("btn-primary");
                            $(this).toggleClass("btn-outline-primary");
                            $(this).find($("input[type=checkbox]")).prop("checked", function (i, v) {
                                return !v;
                            });
                        });

                        $(".date-td").click(function () {
                            $(this).toggleClass("table-success");
                            $(this).find($("input[type=checkbox]")).prop("checked", function (i, v) {
                                return !v;
                            });

                            for (var i = 0; i < $("input[name='selectedDate']").length; i++) {
                                if ($(`input[name='selectedDate']:eq(` + i + `)`).val() === $(this).find($(
                                        "input[name='dateOption']")).val()) {
                                    $(`input[name='selectedDate']:eq(` + i + `)`).parent().toggleClass("d-none");
                                    break;
                                }
                            }
                        });

                    });
    </script>
    <%
        ArrayList<Venue> venueList = (ArrayList<Venue>) request.getAttribute("venueList");
        ArrayList<ArrayList<CalendarTimeslot>> selectedDateTimeslot = (ArrayList<ArrayList<CalendarTimeslot>>) request.getAttribute("selectedDateTimeslot");
        ArrayList<ArrayList<CalendarTimeslot>> monthlyDateTimeslot = (ArrayList<ArrayList<CalendarTimeslot>>) request.getAttribute("monthlyDateTimeslot");
        String[] selectedDate = (String[]) session.getAttribute("selectedDate");
        String selectedVenue = (Integer) request.getAttribute("selectedVenue") + "";
        HashMap<String, int[]> bookingVenues = (HashMap<String, int[]>) session.getAttribute("bookingVenues");
    %>

    <body style="min-height: 100vh;background-color: #eee;">
        <jsp:include page="header.jsp" />

        <div class="position-fixed" style="bottom:1.5rem; right: 1.5rem; z-index: 10;">
            <button type="button" style="width: 4.5rem; height: 4.5rem;" class="p-0 btn btn-primary rounded-pill position-relative" data-mdb-ripple-unbound="true">
                <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor" class="bi bi-cart-fill" viewBox="0 0 16 16">
                <path d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z"/>
                </svg>
                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary">
                    <%=bookingVenues != null ? "+" + bookingVenues.size() : 0 %> <span class="visually-hidden">unread messages</span>
                </span>
            </button>
        </div>
        <section>
            <!-- Modal -->
            <div class="modal fade" id="calendarModal" tabindex="-1" aria-labelledby="calendarLabel" aria-hidden="true">
                <form class="modal-dialog modal-dialog-centered" action="handleBooking" method="post">
                    <input type="hidden" id="action" name="action" value="addBookingVenue">
                    <div class="modal-content">
                        <div class="modal-header">
                            <input type="hidden" id="venueId" name="venueId"
                                   value="<%=selectedVenue != null || selectedVenue != "" ? selectedVenue : null%>">
                            <h1 class="modal-title fs-5" id="exampleModalLabel">
                                Calendar
                            </h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <table class="table table-bordered text-center shadow-3 rounded">
                                <thead>
                                    <tr class="table-primary">
                                        <th class="col">Sun</th>
                                        <th class="col">Mon</th>
                                        <th class="col">Tue</th>
                                        <th class="col">Wed</th>
                                        <th class="col">Thu</th>
                                        <th class="col">Fri</th>
                                        <th class="col">Sat</th>
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
                                        int idx = 0;
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
                                                    String chk = "";
                                                    String style = "";
                                                    if (selectedDate != null) {
                                                        for (String d : selectedDate) {
                                                            if (d.equals(date.toString())) {
                                                                chk = "checked";
                                                                style = "table-success";
                                                            }
                                                        }
                                                    }

                                                    out.print("<td class=\"date-td " + style + "\"><label>" + formatter.format(date) + "</label><input type=\"checkbox\"" + chk + " class=\"d-none\" name=\"dateOption\" value=\"" + date + "\">" + "</td>");
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
                            <% if (monthlyDateTimeslot != null) {
                                for (ArrayList<CalendarTimeslot> ctss : monthlyDateTimeslot) {%>
                            <div class="card mb-3 rounded-0 d-none">
                                <input type="hidden" name="selectedDate" value="<%=ctss.get(0).getDate()%>">
                                <div class="card-header d-flex align-items-center justify-content-between">
                                    <label class="fs-5"><%=ctss.get(0).getDate()%></label>
                                    <button type="button" class="btn-close"></button>
                                </div>
                                <div class="card-body">
                                    <% for (CalendarTimeslot cts : ctss) {
                                    %>
                                    <button type="button" onclick="event.preventDefault();
                                            " <%=cts.isBooked() ? "disabled" : ""%>
                                            class="ts-btn btn btn-outline-<%=cts.isBooked() ? "secondary" : "primary"%> btn-rounded m-1">
                                        <%=cts.getTimeslot().getStartTime()%> - <%=cts.getTimeslot().getEndTime()%>
                                        <input type="checkbox" class="d-none" name="timeOption"
                                               value="<%=cts.getVenuetimeslotId()%>">
                                    </button>
                                    <%
                                        }
                                    %> </div>
                            </div> <%
                                    }
                                }
                            %>
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
                            <label class="fs-4"><strong>Displaying <%=venueList != null ? venueList.size() : 0%> matching
                                    results</strong></label>
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
                            <% if (venueList != null) {
                                for (Venue venue : venueList) {%>
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
                            <%
                                } 
                            } %>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>

</html>