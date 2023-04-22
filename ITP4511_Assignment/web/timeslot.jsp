<%-- 
   Document   : Timeslot
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

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
        <title>Timeslot</title>
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

        .modal {
            --mdb-modal-width: 1024px;
        }

        .imgbox-hover:hover {
            transform: scale(1.01);
            cursor: pointer;
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


                    });
    </script>
    <%
        String role = (String) session.getAttribute("role");
        if (role == null || "Member".equalsIgnoreCase(role)) {
            response.sendRedirect("");
        }
        ArrayList<Venue> venues = (ArrayList<Venue>) request.getAttribute("venues");
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
                    <a class="text-white mx-3 text-decoration-none" href="searchTimeslots">
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

            <ict:result />
            <div class="card bg-white p-3">
                <div class="card-header d-flex w-100 justify-content-between align-items-center">
                    <h5 class="m-2">Timeslot</h5>
                    <div class="input-group d-flex justify-content-end" style="width:30%">
                        <button id="week-button" type="button" class="btn btn-secondary border-end">
                            Week
                        </button>
                        <button id="day-button" type="button" class="btn btn-secondary">
                            Day
                        </button>
                        <select required class="form-select border form-control h-100" name="staff"
                                id="select-staff" aria-label="select">
                            <%
                                for (Venue v : venues) {
                                    out.print("<option value=\"" + v.getId() + "\">" + v.getName() + "</option>");
                                }
                            %>
                        </select>
                    </div>
                </div>

                <div class="table-responsive text-nowrap mt-3 fs-6">
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col"></th>
                                <th scope="col">Sunday</th>
                                <th scope="col">Monday</th>
                                <th scope="col">Tuesday</th>
                                <th scope="col">Wednesday</th>
                                <th scope="col">Thursday</th>
                                <th scope="col">Friday</th>
                                <th scope="col">Saturday</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr class="table-active">
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">2</th>
                                <td>Jacob</td>
                                <td>Thornton</td>
                                <td>@fat</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td colspan="2" class="table-active">Larry the Bird</td>
                                <td>@twitter</td>
                            </tr>
                        </tbody>
                    </table>
                </div>

            </div>
        </section>
    </body>

</html>