<% if ( request.getAttribute("bookingDTOs") == null ) { 
    // forward to /api/report/venue
    String venudId = request.getParameter("venue");
    if (venudId != null) {
        request.getRequestDispatcher("/api/report/venue?venue=" + venudId).forward(request, response);
    } else {
        request.getRequestDispatcher("/api/report/venue" ).forward(request, response);

    }
    
} %>

<%@page import="ict.bean.view.BookingDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>

<% String role = (String)session.getAttribute("role"); %>
<section>
    <div class="card">
        <div class="card-header flex flex-row items-center">
            <h3 class="text-2xl text-dark">Venue</h3>
            <div class="ml-auto my-5"></div>
            <div class="mx-2 relative">
                <script>
                    function refreshUrl() {
                        this.window.location.href = "<%=request.getContextPath()%>/admin/report.jsp?report=venue&venue=" + document.getElementById("venue").value;
                    }
                </script>
                <select class="form-select" id="venue" onchange="refreshUrl()">
                </select>
                <label for="venue" class="form-label absolute -top-2 left-2 bg-white text-xs text-gray-500 px-1 select-none">
                    Select a venue
                </label>
            </div>
            <script>
                let apiLocation_veune = "<%=request.getContextPath()%>" + "/api/report?action=venue";

                fetch(apiLocation_veune)
                    .then(response => response.json())
                    .then(data => {
                        let venue = document.getElementById("venue");
                        venue.innerHTML += "<option value='all'>EPL(All)</option>";

                        data.forEach(item => {
                            if (item.id == "<%=request.getParameter("venue")%>") {
                                venue.innerHTML += "<option value='" + item.id + "' selected>" + item.name + "</option>";
                            } else {
                                venue.innerHTML += "<option value='" + item.id + "'>" + item.name + "</option>";
                            }
                        });

                        // add all option
                    });

            </script>
        </div>
        <div class="card-body">

            <div class="table-responsive text-nowrap mt-3 fs-6">
                <%
                    ArrayList<BookingDTO> bookings = (ArrayList<BookingDTO>) request.getAttribute("bookingDTOs");
                %>
                <ict:bookingTable bookings="<%=bookings%>" role="<%=role%>"/>
            </div>

        </div>
    </div>
</section>
