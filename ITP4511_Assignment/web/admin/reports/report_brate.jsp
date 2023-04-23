
<section>
    <div class="card">
        <div class="card-header flex flex-row items-center">
            <h3 class="text-2xl text-dark">Booking Rate</h3>
            <div class="ml-auto my-5"></div>
            <div class="mx-2 relative">
                <select class="form-select" id="venue" onchange="refreshUrl()">
                </select>
                <label for="venue" class="form-label absolute -top-2 left-2 bg-white text-xs text-gray-500 px-1 select-none">
                    Select a venue
                </label>
            </div>
            <script>
                function refreshUrl() {
                    this.window.location.href = "<%=request.getContextPath()%>/admin/report.jsp?report=brate&venue=" + document.getElementById("venue").value + "&time=" + document.getElementById("time").value;
                }
            </script>
            <script>
                let apiLocation_veune = "<%=request.getContextPath()%>" + "/api/report?action=venue";

                fetch(apiLocation_veune)
                    .then(response => response.json())
                    .then(data => {
                        let venue = document.getElementById("venue");

                        data.forEach(item => {
                            if (item.id == "<%=request.getParameter("venue")%>") {
                                venue.innerHTML += "<option value='" + item.id + "' selected>" + item.name + "</option>";
                            } else {
                                venue.innerHTML += "<option value='" + item.id + "'>" + item.name + "</option>";
                            }
                        });

                        loadBookingRate();

                    });
            </script>

            <div class="mx-2 relative w-[10rem]">
                <select class="form-select" id="time" onchange="refreshUrl()">
                    <% if (request.getParameter("time") == null || request.getParameter("time").equals("monthly")) { %>
                        <option value="monthly" selected>Monthly</option>
                        <option value="yearly">Yearly</option>
                    <% } else { %>
                        <option value="monthly">Monthly</option>
                        <option value="yearly" selected>Yearly</option>
                    <% } %>
                </select>
                <label for="time" class="form-label absolute -top-2 left-2 bg-white text-xs text-gray-500 px-1 select-none">
                    Select time period
                </label>
            </div>
        </div>
        <div class="card-body">


                <% 
                    // dont know why sendRedict is not working, but there are no error
                    if (request.getParameter("time") == null) { 
                        request.setAttribute("time", "monthly");
                    } else {
                        request.setAttribute("time", request.getParameter("time"));
                    } 
                %>
                

                <% if ( request.getAttribute("time") != null && request.getAttribute("time").equals("monthly")) { %>
                <div class="maincontent w-full flex flex-row items-center">
                
                <div class="main-chart mr-auto grid grid-cols-3 w-[75%]">
                    <div class="col-span-2 h-[25rem]">
                        <canvas id="mainchartref"></canvas>
                    </div>
                    <div class="col-span-1 h-[23rem] ml-6">
                        <canvas id="mainchartref1"></canvas>
                    </div>
                </div>

                <div class="times w-[20%] overflow-y-auto p-3 h-[25rem]">
                    Recent 12 months 
                    <script>
                        function loadBookingRate() {
                            let selectedVenue = $("#venue option:selected").val();
                            if (selectedVenue == "" || selectedVenue == null) {
                                selectedVenue = "1"; // default to be the first one
                            }
                            let bookingRateApiEndpoint = "<%=request.getContextPath()%>" + "/api/report/bookingrate?venue=" + selectedVenue + "&time=" + document.getElementById("time").value;
                            fetch(bookingRateApiEndpoint)
                                .then(response => response.json())
                                .then(data => {
                                    // [    {
                                    //     "month": "APRIL",
                                    //     "year": "2023",
                                    //     "occupancy": "4.34782600402832"
                                    // },]

                                    let labels = [];
                                    let dataPoints = [];

                                    data.forEach(item => {
                                        labels.push(item.month + " " + item.year);
                                        dataPoints.push(item.occupancy );
                                    });

                                    let times = document.querySelector(".times");
                                    data.forEach(item => {
                                        let card = document.createElement("div");
                                        card.classList.add("card");
                                        card.classList.add("my-1");
                                        card.classList.add("cursor-pointer");
                                        let occRate = (item.occupancy * 100).toFixed(2);
                                        let textColor = item.occupancy * 100 > 0 ? "text-green-500" :  "text-red-500";
                                        card.innerHTML = "<div class='card-body text-sm'>" + item.year + "-" + item.month + "<br>" +
                                        " <span class='text-normal " + textColor + "'>" + occRate + "%</span>" + "</div>";
                                        times.appendChild(card);

                                        // add event listener
                                        card.addEventListener("click", function() {
                                            updateBookingRateDaily(item.month, item.year);
                                        });
                                    });

                                    let ctx = document.getElementById('mainchartref').getContext('2d');
                                    let chart = new Chart(ctx, {
                                        type: 'line',
                                        data: {
                                            labels: labels,
                                            datasets: [{
                                                label: 'Booking Rate (Monthly)',
                                                backgroundColor: 'rgb(255, 99, 132)',
                                                borderColor: 'rgb(255, 99, 132)',
                                                data: dataPoints
                                            }]
                                        },
                                        options: {
                                            scales: {
                                                y: {
                                                    beginAtZero: true
                                                }
                                            },
                                            maintainAspectRatio: false,
                                        }
                                    });
                            });

                            updateBookingRateDaily("APRIL", "2023");
                        }

                        let chart1;

                        function updateBookingRateDaily(month , year) {
                            // let chart1 = new Chart(document.getElementById('mainchartref1').getContext('2d'), {
                            //     type: 'line',
                            //         data: {
                            //             labels: labels,
                            //             datasets: [{
                            //                 label: 'Booking Rate',
                            //                 backgroundColor: 'rgb(255, 99, 132)',
                            //                 borderColor: 'rgb(255, 99, 132)',
                            //                 data: dataPoints
                            //             }]
                            //         },
                            //         options: {
                            //             scales: {
                            //                 y: {
                            //                     beginAtZero: true
                            //                 }
                            //             },
                            //             maintainAspectRatio: false,
                            //             // dont scale the chart ( width and height )
                            //         }
                            //     });

                            let selectedVenue = $("#venue option:selected").val();
                            if (selectedVenue == "" || selectedVenue == null) {
                                selectedVenue = "1"; // default to be the first one
                            }
                            let endpoint = "<%=request.getContextPath()%>" + "/api/report/bookingrate?venue=" + selectedVenue + "&time=daily" + "&month=" + month + "&year=" + year;

                            fetch(endpoint)
                                .then(response => response.json())
                                .then(data => {
                                        // [                                                                    
                                        // {
                                        // "occupancy": "0.0",
                                        // "day": "1"
                                        // }, ]

                                    let labels = [];
                                    let dataPoints = [];

                                    data.forEach(item => {
                                        labels.push(item.day);
                                        dataPoints.push(item.occupancy);
                                    });

                                    let ctx = document.getElementById('mainchartref1').getContext('2d');

                                    // ror: Canvas is already in use. Chart with ID '1' must be destroyed before the canvas with ID 'mainchartref1' can be reused.
                                    // https://stackoverflow.com/questions/64170306/chart-js-canvas-is-already-in-use-chart-with-id-1-must-be-destroyed-before-the
                                    if (chart1 != null ) {
                                        chart1.destroy();
                                    } 

                                    chart1 = new Chart(ctx, {
                                        type: 'line',
                                        data: {
                                            labels: labels,
                                            datasets: [{
                                                label: 'Booking Rate (Daily)',
                                                backgroundColor: 'rgb(255, 99, 132)',
                                                borderColor: 'rgb(255, 99, 132)',
                                                data: dataPoints
                                            }]
                                        },
                                        options: {
                                            scales: {
                                                y: {
                                                    beginAtZero: true
                                                }
                                            },
                                            maintainAspectRatio: false,
                                        }
                                    });
                                })
                        }
                    </script>
   
                </div>
                </div>
                <% } else  { %>
                    <div class="te mb-3">
                        From 
                        <span id="startYear" class="text-lg font-semibold"></span> 
                            - 
                        <span id="endYear" class="text-lg font-semibold"></span>
                        , the booking rate of the venue is 
                        <span id="bookingRate" class="text-lg font-bold"></span> %
                        .
                    </div>
                   <div class="main-content flex flex-row w-full">
                    
                        
                    <div class="main-chart mr-auto grid grid-cols-5 w-[80%]">
                        <div class="col-span-3 h-[23rem]">
                            <canvas id="mainchartref"></canvas>
                        </div>
                        <div class="col-span-2 h-[27rem] ml-6">
                            <canvas id="mainchartref1"></canvas>
                        </div>
                    </div>
    
                    <div class="times w-[20%] overflow-y-auto p-3 h-[25rem]">
                        Recent 12 months 
                    </div>

                   </div>

                    <script>
                        // {
                        //     "minYear": "2023",
                        //     "data": [
                        //         {
                        //             "occupancy": "0.03333333507180214",
                        //             "year": "2023"
                        //         }
                        //     ],
                        //     "systemYear": "2023"
                        // }
                        // /ITP4511_Assignment/api/report/bookingrate?venue=1&time=yearly
                        function loadBookingRate() {
                            let selectedVenue = $("#venue option:selected").val();
                            if (selectedVenue == "" || selectedVenue == null) {
                                selectedVenue = "1"; // default to be the first one
                            }
                            let bookingRate = 0;
                            let bookingRateApiEndpoint = "<%=request.getContextPath()%>" + "/api/report/bookingrate?venue=" + selectedVenue + "&time=" + document.getElementById("time").value;
                            fetch(bookingRateApiEndpoint)
                                .then(response => {
                                    console.log(response)
                                    return response.json()
                                })
                                .then(data => {
                                    // {
                                        //     "minYear": "2023",
                                        //     "data": [
                                        //         {
                                        //             "occupancy": "0.03333333507180214",
                                        //             "year": "2023"
                                        //         }
                                        //     ],
                                        //     "systemYear": "2023"
                                        // }

                                    let labels = [];
                                    let dataPoints = [];

                                    let times = document.querySelector(".times");

                                    data.data.forEach(item => {
                                        labels.push(item.year);
                                        dataPoints.push(item.occupancy);
                                        bookingRate += parseFloat(item.occupancy);

                                        // create card on times
                                        let card = document.createElement("div");
                                        card.classList.add("card");
                                        card.classList.add("my-1");
                                        card.classList.add("cursor-pointer");
                                        let occRate = (item.occupancy * 100).toFixed(2);
                                        let textColor = item.occupancy * 100 > 0 ? "text-green-500" :  "text-red-500";
                                        card.innerHTML = "<div class='card-body text-sm'>" + item.year + "<br>" +
                                        " <span class='text-normal " + textColor + "'>" + occRate + "%</span>" + "</div>";
                                        card.addEventListener("click", function() {
                                            loadMonthlyGraph(item.year);
                                        });
                                        times.appendChild(card);
                                    });

                                    let ctx = document.getElementById('mainchartref').getContext('2d');
                                    let chart = new Chart(ctx, {
                                        type: 'bar',
                                        data: {
                                            labels: labels,
                                            datasets: [{
                                                label: 'Booking Rate',
                                                backgroundColor: 'rgb(255, 99, 132)',
                                                borderColor: 'rgb(255, 99, 132)',
                                                data: dataPoints
                                            }]
                                        },
                                        options: {
                                            scales: {
                                                y: {
                                                    beginAtZero: true
                                                }
                                            },
                                            maintainAspectRatio: false,
                                        }
                                    });

                                    document.getElementById("startYear").innerHTML = data.minYear;
                                    document.getElementById("endYear").innerHTML = data.systemYear;
                                    document.getElementById("bookingRate").innerHTML = ( bookingRate / data.data.length ).toFixed(2);

                                    loadMonthlyGraph(data.systemYear);
                            });
                        }
                        let chart2 = null;
                        function loadMonthlyGraph(year) {

                            let selectedVenue = $("#venue option:selected").val();
                            let endpoint = "<%=request.getContextPath()%>" + "/api/report/bookingrate?venue=" + selectedVenue + "&time=monthly&fromYear=" + year;

                            fetch(endpoint) 
                                .then(response => response.json())
                                .then(data => {
                                    //      [
                                    //             {
                                    //     "month": "MAY",
                                    //     "year": "2019",
                                    //     "occupancy": "1.0"
                                    // },
                                    //     ]

                                    let labels = [];
                                    let dataPoints = [];

                                    data.forEach(item => {
                                        labels.push(item.year + " " + item.month);
                                        dataPoints.push(item.occupancy);
                                    });

                                    if (chart2 != null) {
                                        chart2.destroy();
                                    }

                                    let ctx = document.getElementById('mainchartref1').getContext('2d');
                                    chart2 = new Chart(ctx, {
                                        type: 'line',
                                        data: {
                                            labels: labels,
                                            datasets: [{
                                                label: 'Booking Rate (Monthly)',
                                                backgroundColor: 'rgb(255, 99, 132)',
                                                borderColor: 'rgb(255, 99, 132)',
                                                data: dataPoints
                                            }]
                                        },
                                        options: {
                                            scales: {
                                                y: {
                                                    beginAtZero: true
                                                }
                                            },
                                            maintainAspectRatio: false,
                                        }
                                    });
                                })



                        }
                    </script>
                <% }  %>
                
            </div>

        </div>
    </div>
</section>