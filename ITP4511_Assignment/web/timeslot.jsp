<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="ict.bean.Timeslot"%>
<%@page import="ict.bean.Venue"%>
<%@page import="ict.bean.User"%>
<%@page import="java.lang.Integer"%>
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

    .col {
        width: 12%;
    }

    td>a:hover {
        transform: scale(1.05);
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

        $("td").mouseenter(function () {
            $(this).find($(".hover-btn")).removeClass("d-none");
            $(this).find($(".org-btn")).addClass("d-none");
        })

        $("td").mouseleave(function () {
            $(this).find($(".hover-btn")).addClass("d-none");
            $(this).find($(".org-btn")).removeClass("d-none");
        })

        $("#select-venue").change(function () {
            $("#getVenueTimeslotForm").submit();
        })

    });
</script>
<%
        String role = (String) session.getAttribute("role");
        if (role == null || "Member".equalsIgnoreCase(role)) {
            response.sendRedirect("");
        }
        ArrayList<Venue> venues = (ArrayList<Venue>) request.getAttribute("venues");
        ArrayList<Timeslot> timeslots = (ArrayList<Timeslot>) request.getAttribute("timeslots");
        int[] mon = (int[]) request.getAttribute("mon");
        int[] tue = (int[]) request.getAttribute("tue");
        int[] wed = (int[]) request.getAttribute("wed");
        int[] thu = (int[]) request.getAttribute("thu");
        int[] fri = (int[]) request.getAttribute("fri");
        int[] sat = (int[]) request.getAttribute("sat");
        int[] sun = (int[]) request.getAttribute("sun");
        int selectedVenue = (Integer) request.getAttribute("selectedVenue");
    %>

<body style="background-color: #f2f2f2;">
    <jsp:include page="header.jsp" />

    <section class="p-5">

        <ict:result />
        <div class="card bg-white p-3">
            <div class="card-header d-flex w-100 justify-content-between align-items-center">
                <h5 class="m-2">Timeslot</h5>
                <div class="input-group d-flex justify-content-end" style="width:15%">
                    <form id="getVenueTimeslotForm" action="searchTimeslots" method="get" class="w-100">
                        <select class="form-select border form-control h-100" name="venueId" id="select-venue"
                            aria-label="select">
                            <%
                                    for (Venue v : venues) {
                                        if (v.getId() == selectedVenue) {
                                            out.print("<option selected value=\"" + v.getId() + "\">" + v.getName() + "</option>");
                                        } else {
                                            out.print("<option value=\"" + v.getId() + "\">" + v.getName() + "</option>");
                                        }
                                    }
                                %>
                        </select>
                    </form>
                </div>
            </div>

            <div class="table-fixed text-nowrap mt-3 fs-6">
                <table class="table table-bordered text-center">
                    <thead>
                        <tr>
                            <th class="col"></th>
                            <th class="col">Monday</th>
                            <th class="col">Tuesday</th>
                            <th class="col">Wednesday</th>
                            <th class="col">Thursday</th>
                            <th class="col">Friday</th>
                            <th class="col">Saturday</th>
                            <th class="col">Sunday</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                                for (int i = 0; i < 12; i++) {
                                    Timeslot t = timeslots.get(i);
                                    out.print("<tr>");
                                    out.print("<th>" + formatter.format(t.getStartTime()) + " - " + formatter.format(t.getEndTime()) + "</th>");
                                    if (mon[i] == 1) {
                                        out.print("<td><span class=\"org-btn badge badge-success rounded-pill mb-0\">Available</span><a href=\"delTimeslot?action=delete&day=1&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Unavailable</a></td>");
                                    } else {
                                        out.print("<td><span class=\"org-btn badge badge-danger rounded-pill mb-0\">Unavailable</span><a href=\"addTimeslot?action=add&day=1&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Available</a></td>");
                                    }
                                    if (tue[i] == 1) {
                                        out.print("<td><span class=\"org-btn badge badge-success rounded-pill mb-0\">Available</span><a href=\"delTimeslot?action=delete&day=2&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Unavailable</a></td>");
                                    } else {
                                        out.print("<td><span class=\"org-btn badge badge-danger rounded-pill mb-0\">Unavailable</span><a href=\"addTimeslot?action=add&day=2&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Available</a></td>");
                                    }
                                    if (wed[i] == 1) {
                                        out.print("<td><span class=\"org-btn badge badge-success rounded-pill mb-0\">Available</span><a href=\"delTimeslot?action=delete&day=3&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Unavailable</a></td>");
                                    } else {
                                        out.print("<td><span class=\"org-btn badge badge-danger rounded-pill mb-0\">Unavailable</span><a href=\"addTimeslot?action=add&day=3&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Available</a></td>");
                                    }
                                    if (thu[i] == 1) {
                                        out.print("<td><span class=\"org-btn badge badge-success rounded-pill mb-0\">Available</span><a href=\"delTimeslot?action=delete&day=4&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Unavailable</a></td>");
                                    } else {
                                        out.print("<td><span class=\"org-btn badge badge-danger rounded-pill mb-0\">Unavailable</span><a href=\"addTimeslot?action=add&day=4&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Available</a></td>");
                                    }
                                    if (fri[i] == 1) {
                                        out.print("<td><span class=\"org-btn badge badge-success rounded-pill mb-0\">Available</span><a href=\"delTimeslot?action=delete&day=5&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Unavailable</a></td>");
                                    } else {
                                        out.print("<td><span class=\"org-btn badge badge-danger rounded-pill mb-0\">Unavailable</span><a href=\"addTimeslot?action=add&day=5&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Available</a></td>");
                                    }
                                    if (sat[i] == 1) {
                                        out.print("<td><span class=\"org-btn badge badge-success rounded-pill mb-0\">Available</span><a href=\"delTimeslot?action=delete&day=6&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Unavailable</a></td>");
                                    } else {
                                        out.print("<td><span class=\"org-btn badge badge-danger rounded-pill mb-0\">Unavailable</span><a href=\"addTimeslot?action=add&day=6&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Available</a></td>");
                                    }
                                    if (sun[i] == 1) {
                                        out.print("<td><span class=\"org-btn badge badge-success rounded-pill mb-0\">Available</span><a href=\"delTimeslot?action=delete&day=7&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Unavailable</a></td>");
                                    } else {
                                        out.print("<td><span class=\"org-btn badge badge-danger rounded-pill mb-0\">Unavailable</span><a href=\"addTimeslot?action=add&day=7&timeslotId=" + t.getId() + "&venueId=" + selectedVenue + "\" class=\"hover-btn badge badge-primary rounded-pill mb-0 d-none\">Set Available</a></td>");
                                    }
                                    out.print("</tr>");
                                }
                            %>
                    </tbody>
                </table>
            </div>

        </div>
    </section>
</body>

</html>