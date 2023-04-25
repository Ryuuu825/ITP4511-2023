<%@page import="java.time.LocalDate"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.util.HashMap"%>
<%@page import="ict.bean.view.CalendarTimeslot"%>
<%@page import="java.util.ArrayList"%>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"
integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
<style>
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

    .date-td.table-danger,
    .date-td.table-danger label{
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
            $(this).toggleClass("btn-primary");
            $(this).toggleClass("btn-outline-primary");
            $(this).find($("input[type=checkbox]")).prop("checked", function (i, v) {
                return !v;
            });
        });

        $(".date-td").click(function () {
            if ($(this).hasClass("table-secondary")) {
                return;
            }
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
    ArrayList<ArrayList<CalendarTimeslot>> monthlyDateTimeslot = (ArrayList<ArrayList<CalendarTimeslot>>) request.getAttribute("monthlyDateTimeslot");
    String[] selectedDate = (String[]) session.getAttribute("selectedDate");
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
                        String prop = "";
                        String style = "";
                        if (selectedDate != null && selectedDate.length > 0) {
                            for (String d : selectedDate) {
                                if (d.equals(date.toString())) {
                                    prop = "checked";
                                    style = "table-success";
                                }
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
        for (ArrayList<CalendarTimeslot> ctss : monthlyDateTimeslot) {%>
<div class="card mb-3 rounded-0 d-none">
    <input type="hidden" name="selectedDate" value="<%=ctss != null && !ctss.isEmpty() ? ctss.get(0).getDate() : ""%>">
    <div class="card-header d-flex align-items-center justify-content-between">
        <label class="fs-5"><%=ctss != null && !ctss.isEmpty() ? ctss.get(0).getDate() : ""%></label>
        <button type="button" class="btn-close"></button>
    </div>
    <div class="card-body">
        <%
            if (ctss != null && !ctss.isEmpty()) {
                for (CalendarTimeslot cts : ctss) {
        %>
        <button type="button" onclick="event.preventDefault();"
                <%=cts.isBooked() ? "disabled" : ""%>
                class="ts-btn btn btn-outline-<%=cts.isBooked() ? "secondary" : "primary"%> btn-rounded m-1">
            <%=cts.getTimeslot().getStartTime()%> - <%=cts.getTimeslot().getEndTime()%>
            <input type="checkbox" class="d-none" name="timeOption"
                   value="<%=cts.getVenuetimeslotId()%>">
        </button>
        <%
                }
            } else {
                out.print("<label class=\"w-100 h-100\">No time slot available</label>");
            }
        %>
    </div>
</div>
<%
        }
    }
%>