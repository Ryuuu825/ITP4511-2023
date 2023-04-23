<%-- 
   Document   : venueInfo
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page import="ict.bean.Venue"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"> -->
    <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"
            integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e" crossorigin="anonymous"> -->
    <!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"> -->
    <!-- </script> -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"
        integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.css" rel="stylesheet" />
    <!-- <script src="https://cdn.tailwindcss.com"></script> -->
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

    .table td {
        padding-bottom: 0;
    }
</style>
<script>
    $(document).ready(function () {
        const searchFocus = document.getElementById('search-focus');
        const keys = [{
                keyCode: 'AltLeft',
                isTriggered: false
            },
            {
                keyCode: 'ControlLeft',
                isTriggered: false
            },
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

        $(".book-btn").click(function () {
            $('#venueModal').modal('show');
        });
    });
</script>
<%
    ArrayList<Venue> venueList = (ArrayList<Venue>) request.getAttribute("venueList");
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
            <li class="nav-item border-start nav-hover">
                <a class="text-white mx-3 px-3" href="login.jsp"> Login </a>
            </li>
            <li class="nav-item border bg-light rounded-1 py-1">
                <a class="mx-3 text-dark" href="register.jsp"> Sign Up </a>
            </li>
        </ul>
    </header>
    <div></div>
    <section>
        <!-- Modal -->
        <div class="modal fade" id="venueModal" tabindex="-1" aria-labelledby="venueLabel" aria-hidden="true">
            <form class="modal-dialog modal-dialog-centered" action="handleVenue" method="post"
                enctype="multipart/form-data">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">
                            Venue
                        </h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <input type="hidden" id="action" name="action" value="">
                        <input type="hidden" id="venueId" name="id"
                            value="<%=venueDTO != null ? venueDTO.getVenue().getId() : "0"%>">
                        <div>
                            <div class="row">
                                <div class="col-7">
                                    <input type="file" class="d-none" id="fileinput" name="img" accept="image/*">
                                    <input type="hidden" id="changeImage" name="changeImage" value="false">
                                    <div class="imgbox-hover form-outline card h-100 bg-gray text-center"
                                        title="Click to upload image">
                                        <div id="upload-img"
                                            class="card-img d-flex bg-secondary bg-opacity-25 w-100 h-100 justify-content-center align-items-center
                                                 <%=venueDTO != null && venueDTO.getVenue().getImg() != "" ? "d-none" : "d-block"%>">
                                            Click to upload image
                                        </div>
                                        <img id="venue-img"
                                            src="<%=venueDTO != null ? "assets/" + venueDTO.getVenue().getImg() : ""%>"
                                            class="card-img w-100 h-100 <%=venueDTO != null && venueDTO.getVenue().getImg() != "" ? "d-block" : "d-none"%>"
                                            alt="Venue image">
                                    </div>
                                </div>
                                <div class="col-5">
                                    <div class="form-outline mb-4">
                                        <input required type="text" id="venueName" name="name"
                                            class="form-control border"
                                            value="<%=venueDTO != null ? venueDTO.getVenue().getName() : ""%>" />
                                        <label class="form-label" for="venueName">Name</label>
                                    </div>
                                    <div class="row">

                                        <div class="col">
                                            <div class="form-outline mb-4">
                                                <input required type="number" id="capacity" name="capacity"
                                                    class="form-control border"
                                                    value="<%=venueDTO != null ? venueDTO.getVenue().getCapacity() : ""%>" />
                                                <label class="form-label" for="capacity">Capacity</label>
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="form-outline mb-4 position-relative">
                                                <select required class="form-control border" name="type"
                                                    id="select-type" aria-label="select"
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
                                                <span class="select-arrow position-absolute"
                                                    style="top:25%; right: 1rem;"></span>
                                            </div>
                                        </div>
                                        <div class="col">
                                            <div class="form-outline mb-4">
                                                <input required type="number" id="hourlyRate" name="hourlyRate"
                                                    class="form-control border"
                                                    value="<%=venueDTO != null ? venueDTO.getVenue().getHourlyRate() : ""%>" />
                                                <label class="form-label" for="hourlyRate">HourlyRate</label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-outline mb-4 position-relative">
                                        <input type="hidden" id="selected-staff"
                                            value="<%=venueDTO != null ? venueDTO.getUser().getId() : "0"%>">
                                        <select required class="form-select border form-control" name="staff"
                                            id="select-staff" aria-label="select">
                                            <option value="0">None</option>
                                            <%
                                                    ArrayList<User> staff = (ArrayList<User>) request.getAttribute("staff");
                                                    for (int i = 1; i <= staff.size(); i++) {
                                                        out.print("<option value=\"" + staff.get(i - 1).getId() + "\">" + staff.get(i - 1).getFirstName() + " " + staff.get(i - 1).getLastName() + "</option>");
                                                    }
                                                %>
                                        </select>
                                        <label class="form-label" for="select-staff">Person in charge</label>
                                        <span class="select-arrow position-absolute"
                                            style="top:25%; right: 1rem;"></span>
                                    </div>
                                    <div class="form-outline mb-4 position-relative">
                                        <input type="hidden" id="selected-district"
                                            value="<%=venueDTO != null ? venueDTO.getVenue().getDistrict() : ""%>">
                                        <select required class="border form-control select-input" size="1"
                                            name="district" id="select-district" aria-label="select">
                                            <optgroup label="Hong Kong">
                                                <option value="Central and West District">Central and West District
                                                </option>
                                                <option value="Eastern District">Eastern District</option>
                                                <option value="Southern District">Southern District</option>
                                                <option value="Wan Chai District">Wan Chai District</option>
                                            </optgroup>
                                            <optgroup label="Kowloon">
                                                <option value="Kowloon City District">Kowloon City District</option>
                                                <option value="Kwun Tong District">Kwun Tong District</option>
                                                <option value="Sham Shui Po District">Sham Shui Po District</option>
                                                <option value="Wong Tai Sin District">Wong Tai Sin District</option>
                                                <option value="Yau Tsim Mong District">Yau Tsim Mong District</option>
                                            </optgroup>
                                            <optgroup label="New Territories">
                                                <option value="Island District">Island District</option>
                                                <option value="Kwai Tsing District">Kwai Tsing District</option>
                                                <option value="North District">North District</option>
                                                <option value="Sai Kung District">Sai Kung District</option>
                                                <option value="Sha Tin District">Sha Tin District</option>
                                                <option value="Tai Po District">Tai Po District</option>
                                                <option value="Tsuen Wan District">Tsuen Wan District</option>
                                                <option value="Tuen Mun District">Tuen Mun District</option>
                                                <option value="Yuen Long District">Yuen Long District</option>
                                            </optgroup>
                                        </select>
                                        <label class="form-label" for="select-staff">District</label>
                                        <span class="select-arrow position-absolute"
                                            style="top:25%; right: 1rem;"></span>
                                    </div>
                                    <div class="form-outline mb-4">
                                        <textarea required class="form-control border" name="address" id="address"
                                            rows="2"><%=venueDTO != null ? venueDTO.getVenue().getAddress() : ""%></textarea>
                                        <label class="form-label" for="address">Address</label>
                                    </div>
                                    <div class="form-outline">
                                        <textarea class="form-control border" name="description" id="decription"
                                            rows="4"><%=venueDTO != null ? venueDTO.getVenue().getDescription() : ""%></textarea>
                                        <label class="form-label" for="decription">Description</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="submit"
                            class="btn btn-primary"><%=venueDTO != null ? "Save changes" : "Add Venue"%></button>
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
                        <label class="fs-4"><strong>Displaying <%=venueList.size() %> matching results</strong></label>
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
                        <% for (Venue venue : venueList) { %>
                        <div class="row">
                            <div class="card mb-3 ps-0" style="max-width: 100%;">
                                <div class="row g-0">
                                    <div class="col-md-5">
                                        <div class="bg-image hover-zoom w-100 h-100">
                                            <img src="assets/<%=venue.getImg() %>" alt="Venue Image"
                                                class="img-fluid w-100 h-100" />
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="card-body pb-0">
                                            <h5 class="card-title"><%=venue.getName() %></h5>
                                            <p class="card-text">
                                                <%=venue.getDescription() %>
                                            </p>
                                            <table class="table table-sm table-borderless">
                                                <tr>
                                                    <td>
                                                        <middle class="text-muted">Capacity:
                                                            <%=venue.getCapacity() %></middle>
                                                    </td>
                                                    <td>
                                                        <middle class="text-muted">Type: <%=venue.getTypeString() %>
                                                        </middle>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                        <middle class="text-muted">Address:
                                                            <%=venue.getAddress() %></middle>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">
                                                        <middle class="text-muted">Location:
                                                            <%=venue.getDistrict() %></middle>
                                                    </td>
                                                </tr>
                                            </table>
                                            <div
                                                class="card-footer px-0 d-flex justify-content-between align-items-center">
                                                <div class="note note-primary">
                                                    <strong>HourlyRate(HK$):
                                                        <%=venue.getHourlyRate() %>
                                                    </strong>
                                                </div>
                                                <button class="book-btn btn btn-primary">Book Venue</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>

</html>