<%-- 
   Document   : venueInfo
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page import="java.util.Map"%>
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
    </style>
    <script>
                    function hideCart() {
                        $("#cartBox").hide();
                    }

                    function showCart() {
                        $("#cartBox").show();
                    }

                    function goCart() {
                        if ($(`#total`).text() !== "HK$0.0") {
                            $("#cartForm").submit();
                        } else {
                            alert("You have not selected any venues");
                        }
                    }

                    function removeVenue(id) {
                        $.ajax({url: "delCartVenue", type: "post", data: {action: "delCartVenue", venueId: id}, async: false, success: function (result) {
                                location.reload();
                                showCart();
                            }});
                    }

                    $(document).ready(function () {
                        hideCart();
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
                        });

                        function showCalendar() {
                            $("#calendarModal").modal('show');
                        }

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
        ArrayList<Venue> venueList = (ArrayList<Venue>) request.getAttribute("venueList");
        String selectedVenue = (String) request.getAttribute("selectedVenue");
        HashMap<String, ArrayList<Integer>> bookingVenues = (HashMap<String, ArrayList<Integer>>) session.getAttribute("bookingVenues");
    %>

    <body style="min-height: 100vh;background-color: #eee;">
        <jsp:include page="header.jsp" />

        <div class="position-fixed" style="bottom:1.5rem; right: 1.5rem; z-index: 10;">
            <button type="button" onclick="showCart()" style="width: 4.5rem; height: 4.5rem;"
                    class="p-0 btn btn-primary rounded-pill position-relative" data-mdb-ripple-unbound="true">
                <svg xmlns="http://www.w3.org/2000/svg" width="36" height="36" fill="currentColor" class="bi bi-cart-fill"
                     viewBox="0 0 16 16">
                <path
                    d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2zm7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2z" />
                </svg>
                <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary">
                    <%=bookingVenues != null ? "+" + bookingVenues.size() : 0%> <span class="visually-hidden">booking venues</span>
                </span>
            </button>
        </div>
        <section>
            <!-- Modal -->
            <div class="modal fade" id="calendarModal" tabindex="-1" aria-labelledby="calendarLabel" aria-hidden="true">
                <form class="modal-dialog modal-dialog-centered" id="bookVenueForm" action="handleBooking" method="post">
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

            <!-- Cart -->
            <div class="card position-fixed w-25" style="bottom:1rem; right: 0.5rem; z-index: 20;" id="cartBox"
                 tabindex="-1" aria-labelledby="cartLabel" aria-hidden="true">
                <form class="" id="cartForm" action="getCart" method="get">
                    <input type="hidden" id="action" name="action" value="cart">
                    <div class="card-header d-flex align-items-center justify-content-between">
                        <h1 class="modal-title fs-5">
                            Cart
                        </h1>
                        <button type="button" onclick="hideCart()" class="btn-close" data-bs-dismiss="card"
                                aria-label="Close"></button>
                    </div>
                    <div class="card-body p-0">
                        <ol class="list-group list-group-flush list-group-numbered">
                            <%
                                double total = 0;
                                if (bookingVenues != null && !bookingVenues.isEmpty()) {
                                    total = 0;
                                    for (Map.Entry<String, ArrayList<Integer>> en : bookingVenues.entrySet()) {
                                        String key = en.getKey();
                                        ArrayList<Integer> val = en.getValue();
                                        for (Venue v : venueList) {
                                            String venueId = v.getId() + "";
                                            if (venueId.equalsIgnoreCase(key)) {
                                                total += v.getHourlyRate() * val.size();
                            %>
                            <li class="list-group-item d-flex align-items-start justify-content-between">
                                <div class="ms-2 me-auto">
                                    <div class="fw-semibold"><%=v.getName()%></div>
                                    Content for list item
                                </div>
                                <div class="d-flex align-items-center" style="width:30%;"><label
                                        class="me-auto">$<%=v.getHourlyRate()%></label><label class="me-1">x</label>
                                    <label class="me-1"><%=val.size()%></label>
                                    <button type="button" onclick="removeVenue(<%=venueId%>);" class="btn-close" style="width:0.3rem;height: 0.3rem;" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                            </li>
                            <%                           }
                                        }
                                    }
                                }
                            %>
                        </ol>
                        <div class="mt-2 d-flex align-items-start justify-content-between p-4 border-top">
                            <div class="fs-5 fw-semibold">Total</div>
                            <strong id="total" class="fs-4">HK$<%=total%></strong>
                        </div>
                    </div>
                    <div class="card-footer d-flex justify-content-end">
                        <button type="button" onclick="hideCart()" class="btn btn-secondary me-2"
                                data-bs-dismiss="offcanvas">Close</button>
                        <button type="submit" onclick="event.preventDefault();
                                goCart();" class ="btn btn-primary">GO TO CART</button>
                    </div>
                </form>
            </div>
            <!-- Cart -->

            <div class="text-start container-xl py-5">
                <div class="row">
<!--                    <div class="col-md-3 card">
                        <div class="card-header">
                            <label class="fs-4"><strong>Find venues</strong></label>
                        </div>
                        <div class="row card-body">
                            <p class="text-lg">Find the perfect venue for your event</p>
                        </div>
                    </div>-->
                    <div class="col card ms-4">
                        <div class="card-header d-flex flex-row justify-content-between align-items-center">
                            <%
                                ArrayList<Venue> shownVenues = new ArrayList<Venue>();
                                if (venueList != null && !venueList.isEmpty()) {
                                    for (int i = 0; i < venueList.size(); i++) {
                                        if (venueList.get(i) != null && venueList.get(i).getUserId() != 0 && venueList.get(i).getEnable()) {
                                            Venue venue = venueList.get(i);
                                            shownVenues.add(venue);
                                        }
                                    }
                                }
                            %>
                            <label class="fs-4"><strong>Displaying <%=shownVenues != null ? shownVenues.size() : 0%>
                                    matching
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
                            <% if (shownVenues != null && !shownVenues.isEmpty()) {
                                    for (int i = 0; i < shownVenues.size(); i++) {
                                        if (shownVenues.get(i) != null && shownVenues.get(i).getUserId() != 0) {
                                            Venue venue = shownVenues.get(i);
                            %>
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
                                    }
                                } else {
                                    out.print("<div class=\"row\">No such record found</div>");
                                }
                            %>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>

</html>