<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.HashMap"%>
<%@page import="ict.bean.view.CalendarTimeslot"%>
<%@page import="java.util.ArrayList"%>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"
integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
<style>
    .modal {
        --mdb-modal-width: 55%;
    }

    .date-td {
        width: 10%;
    }

    .date-td,
    .date-td label {
        cursor: pointer;
    }

    .date-td.table-danger,
    .date-td.table-danger label {
        cursor: default;
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
        $(".ts-btn").click(function () {
            addSelectedTime(this);
            $(this).find($("input[type=checkbox]")).prop("checked", function (i, v) {
                return !v;
            });
        });

        function addSelectedTime(e) {
            $(e).toggleClass("btn-primary");
            $(e).toggleClass("btn-outline-primary");
        }

        for (var i = 0; i < $("input[name='timeOption']").length; i++) {
            if ($(`input[name='timeOption']:eq(` + i + `)`).is(":checked")) {
                addSelectedTime($(`input[name='timeOption']:eq(` + i + `)`).parent());
            }
        }


        function showDateTime(e) {
            for (var i = 0; i < $("label[name='datetitle']").length; i++) {
                if ($(`label[name='datetitle']:eq(` + i + `)`).text() === $(e).find($(
                        "input[name='dateOption']")).val()) {
                    $(`label[name='datetitle']:eq(` + i + `)`).parent().parent().toggleClass("d-none");
                    break;
                }
            }
        }

        function addSelectedDate(e) {
            e.append(`<input type="hidden" id="selectedDate" name="selectedDate" value="` + $(
                    `label[name='datetitle']:eq(` + i + `)`).text() + `">`);
        }

        function selectDate(e) {
            if ($(e).hasClass("table-secondary")) {
                return;
            }
            $(e).toggleClass("table-success");
            $(e).find($("input[type=checkbox]")).prop("checked", function (i, v) {
                return !v;
            });

            showDateTime(e);
        }

        $(".date-td").click(function () {
            selectDate(this);
        });
    });
</script>
<%
    ArrayList<ArrayList<CalendarTimeslot>> monthlyDateTimeslot = (ArrayList<ArrayList<CalendarTimeslot>>) request.getAttribute("monthlyDateTimeslot");
    HashMap<String, ArrayList<Integer>> bookingVenues = (HashMap<String, ArrayList<Integer>>) session.getAttribute("bookingVenues");
    HashMap<String, ArrayList<String>> bookingDates = (HashMap<String, ArrayList<String>>) session.getAttribute("bookingDates");
    String selectedVenue = (String) request.getAttribute("selectedVenue");
    ArrayList<String> selectedDate = null;
    if (bookingDates != null) {
        selectedDate = bookingDates.get(selectedVenue);
    }
%>
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
            int nextDate = 0;
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
                        String prop = "";
                        String style = "";
                        if (selectedDate != null && selectedDate.size() > 0) {
                            if (selectedDate.contains(date.toString())) {
                                prop = "checked";
                                style = "table-success";
                            }
                        }

                        if (monthlyDateTimeslot == null || monthlyDateTimeslot.get(nextDate).isEmpty()) {
                            style = "table-danger";
                            prop = "disabled";
                        }

                        out.print("<td class=\"date-td " + style + "\"><label>" + formatter.format(date) + "</label><input type=\"checkbox\"" + prop + " class=\"d-none\" name=\"dateOption\" value=\"" + date + "\">" + "</td>");
                        date = date.plusDays(1);
                        nextDate++;
                    }
                }
                newMonth = month < date.getMonth().getValue();
                out.print("</tr>");
                rowOfMonth++;
            }
        %>
    </tbody>
</table>
<% if (monthlyDateTimeslot != null && !monthlyDateTimeslot.isEmpty()) {
        for (ArrayList<CalendarTimeslot> ctss : monthlyDateTimeslot) {
            boolean selected = false;
            String d = "";
            if (selectedDate != null && selectedDate.size() > 0) {
                if (ctss != null && !ctss.isEmpty() && selectedDate.contains(ctss.get(0).getDate().toString())) {
                    out.print("<div name=\"dateBox\" class=\"card mb-3 rounded-0\">");
                } else {
                    out.print("<div name=\"dateBox\" class=\"card mb-3 rounded-0 d-none\">");
                }
            } else {
                out.print("<div name=\"dateBox\" class=\"card mb-3 rounded-0 d-none\">");
            }

%>
<div class="card-header d-flex align-items-center justify-content-between">
    <label name="datetitle" class="fs-5"><%=ctss != null && !ctss.isEmpty() ? ctss.get(0).getDate() : ""%></label>
    <button type="button" class="btn-close"></button>
</div>
<div class="card-body">
    <div class="d-flex justify-content-start flex-wrap">
        <%
            if (ctss != null && !ctss.isEmpty()) {
                for (CalendarTimeslot cts : ctss) {
                    ArrayList<Integer> selectedTimes = null;
                    if (bookingVenues != null && !bookingVenues.isEmpty() && bookingVenues.get(cts.getVenueId() + "") != null) {
                        selectedTimes = bookingVenues.get(cts.getVenueId() + "");
                    }
                    out.print("<button type=\"button\" onclick=\"event.preventDefault();\" " + (cts.isBooked() ? "disabled " : ""));
                    out.print("class=\"ts-btn btn btn-outline-"+ (cts.isBooked() ? "secondary " : "primary ") + (ctss.indexOf(cts) == ctss.size()-1 ? "me-auto":"") + " btn-rounded m-1\">");
                    out.print(cts.getTimeslot().getStartTime() + " - " + cts.getTimeslot().getEndTime());
                    boolean st = false;
                    if (selectedTimes != null) {
                        for (int selectedTime : selectedTimes) {
                            if (selectedTime == cts.getVenuetimeslotId()) {
                                st = true;
                            }
                        }
                    }

                    if (st) {
                        out.print("<input type=\"checkbox\" class=\"d-none\" checked name=\"timeOption\" value=\"" + cts.getVenuetimeslotId() + "\">");
                    } else {
                        out.print("<input type=\"checkbox\" class=\"d-none\" name=\"timeOption\" value=\"" + cts.getVenuetimeslotId() + "\">");
                    }
                    out.print("</button>");
                }
            } else {
                out.print("<label class=\"w-100 h-100\">No time slot available</label>");
            }
        %>
    </div>
</div>
</div>
<%
        }
    }
%>