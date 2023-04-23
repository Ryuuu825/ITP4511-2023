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
    <div class="modal fade" id="exportModal" tabindex="-1" aria-labelledby="errorModalLabel">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="errorModalLabel">
                        Exporting Data
                    </h1>
                </div>
                <div class="modal-body">
                    <div class="flex flex-row items-center justify-start">
                        <p>Exporting data to CSV file, please wait</p>
                        <span class="spinner-border spinner-border-sm ml-3" role="status" aria-hidden="true"></span>
                    </div>
                </div>
                <div class="modal-footer">
                    <a class="btn btn-info hidden" id="apre" target="_blank">Preview</a>
                    <button type="button" id="dbtn" class="btn btn-primary hidden" data-bs-dismiss="modal">Download</button>
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
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

                        if ("<%=request.getParameter("venue")%>" == null || "<%=request.getParameter("venue")%>" == "all") {
                            venue.innerHTML += "<option value='all' selected>EPL(All)</option>";
                        } else {
                            venue.innerHTML += "<option value='all'>EPL(All)</option>";
                        }
                    

                        data.forEach(item => {
                            if (item.id == "<%=request.getParameter("venue")%>") {
                                venue.innerHTML += "<option value='" + item.id + "' selected>" + item.name + "</option>";
                            } else {
                                venue.innerHTML += "<option value='" + item.id + "'>" + item.name + "</option>";
                            }
                        });
                    });

            </script>

            <button class="btn btn-primary ml-6 " href="<%=request.getContextPath()%>/api/report/venue?export=true&venue=<%=request.getParameter("venue")%>"
                onclick="preFetchCsv();"
                >
                Export to CSV
            </button>

            <script>

                function preFetchCsv() {
                    // open the modal
                    $('#exportModal').modal('show');

                    fetchCsvFileFromServer()
                        .then(res => fetchFinishCallback(res));
                }

                async function fetchCsvFileFromServer() {
                    let venueDataFilePath = "<%=request.getContextPath()%>" + "/api/report/venue?export=true&venue=<%=request.getParameter("venue")%>";

                    let response = await fetch(venueDataFilePath);

                    return await response.blob();
                }

                function fetchFinishCallback(res) {

                    // set the text to Finish
                    let modalBody = document.querySelector("#exportModal .modal-body");
                    modalBody.innerHTML = "<p class='alert alert-success'>The file is ready to go</p>";

                    // display the dbtn 
                    let dbtn = document.getElementById("dbtn");
                    dbtn.style.display = "block";

                    // apre
                    let apre = document.getElementById("apre");
                    apre.href = URL.createObjectURL(res);
                    apre.style.display = "block";

                    // add onclick event to dbtn
                    dbtn.onclick = function () {

                        // create a link
                        let a = document.createElement("a");
                        a.href = URL.createObjectURL(res);
                        a.download = $('#venue option[selected]').text() + " Booking Records.csv"
                        a.click();

                        // reset the modal
                        modalBody.innerHTML = "<div class='flex flex-row items-center justify-start'><p>Exporting data to CSV file, please wait</p><span class='spinner-border spinner-border-sm ml-3' role='status' aria-hidden='true'></span></div>";

                        // hide the dbtn
                        dbtn.style.display = "none";

                        // hide the apre
                        apre.style.display = "none";

                    }
                    
                }
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
