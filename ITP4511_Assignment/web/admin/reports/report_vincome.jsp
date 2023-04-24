<%@page import="ict.bean.view.BookingDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/ict-taglib.tld" prefix="ict" %>

<% String role = (String) session.getAttribute("role");%>
<section>
    <div class="card">
        <div class="card-header flex flex-row items-center">
            <h3 class="text-2xl text-dark">Venue</h3>
            <div class="ml-auto my-5"></div>
            <div class="mx-2 relative">
                <script>
                    function refreshUrl() {
                        this.window.location.href = "<%=request.getContextPath()%>/admin/report.jsp?report=vincome&venue=" + document.getElementById("venue").value + "&year=" + document.getElementById("year").value;
                    }
                </script>
                <select class="form-select" id="venue">
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

                            if ("<%=request.getParameter("venue")%>" == "all") {
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

                            loadYear();
                            loadTimeText();
                            loadVenueText();
                        });

            </script>

            <div class="mx-2 relative w-[10rem]">
                <select class="form-select" id="year">
                </select>

                <label for="venue" class="form-label absolute -top-2 left-2 bg-white text-xs text-gray-500 px-1 select-none">
                    Select year
                </label>
                <script>
                    function loadYear() {
                        // /api/report/vincome?action=year&venue=1
                        let apiLocation_year = "<%=request.getContextPath()%>" + "/api/report/vincome?action=list&venue=" + document.getElementById("venue").value;

                        fetch(apiLocation_year)
                                .then(res => res.json())
                                .then(data => {
                                    //                             {
                                    //     "minYear": "2018",
                                    //     "systemYear": "2023"
                                    // }
                                    let minYear = data.minYear;
                                    let systemYear = data.systemYear;

                                    // create option
                                    let year = document.getElementById("year");
                                    for (let i = minYear; i <= systemYear; i++) {
                                        if ("<%=request.getParameter("year")%>" == i) {
                                            year.innerHTML += "<option value='" + i + "' selected>" + i + "</option>";
                                        } else {
                                            year.innerHTML += "<option value='" + i + "'>" + i + "</option>";
                                        }
                                    }
                                });

                        loadYearlyVeuneIncome();
                    }

                </script>
            </div>

            <button class="btn btn-primary" onclick="refreshUrl()">
                Go <i class="fas fa-arrow-right ml-2"></i>
            </button>

        </div>
        <div class="card-body">

            <div class="alert alert-info">
                Total income in 
                <span class="text-lg font-semibold" id="venuetext"></span>
                at
                <span class="text-lg font-semibold" id="timetext"></span>
                is HKD 
                <span class="text-lg font-semibold" id="income"></span>
            </div>

            <script>
                function loadVenueText() {
                    let par = '<%= request.getParameter("venue")%>';
                    if (par == 'all') {
                        document.getElementById("venuetext").innerHTML = "EPL";
                    } else {
                        document.getElementById("venuetext").innerHTML = document.getElementById("venue").options[document.getElementById("venue").selectedIndex].text;
                    }
                }

                function loadTimeText() {
                    let par = '<%= request.getParameter("year")%>';
                    if (par == 'all') {
                        document.getElementById("timetext").innerHTML = "all time";
                    } else {
                        document.getElementById("timetext").innerHTML = par;
                    }
                }

            </script>


            <div class="main-content flex flex-row w-full">


                <div class="main-chart mr-auto grid grid-cols-5 gap-12 w-full">

                    <div class="col-span-2 h-[27rem]">
                        <canvas id="mainchartref1"></canvas>
                    </div>

                    <div class="col-span-3 h-[27rem]">
                        <canvas id="mainchartref"></canvas>
                    </div>

                    <script>
                        // /ITP4511_Assignment/api/report/vincome?venue=1&year=2023&action=year
                        function loadYearlyVeuneIncome() {
                            let apiLocation = "<%=request.getContextPath()%>" + "/api/report/vincome?venue=" + document.getElementById("venue").value + "&action=year";
                            fetch(apiLocation)
                                    .then(res => res.json())
                                    .then(data => {
                                        // {
                                        //     "income": "0.0",
                                        //     "month": "JANUARY",
                                        //     "count": "0"
                                        // },



                                        let income = [];
                                        let month = [];

                                        data.forEach(item => {
                                            income.push(item.income);
                                            month.push(item.year);
                                        });


                                        let ctx = document.getElementById('mainchartref1').getContext('2d');
                                        let myChart = new Chart(ctx, {
                                            type: 'bar',
                                            data: {
                                                labels: month,
                                                datasets: [{
                                                        label: 'Income (All)',
                                                        data: income,
                                                        backgroundColor: [
                                                            'rgba(255, 99, 132, 0.2)',
                                                        ],
                                                        borderColor: [
                                                            'rgba(255, 99, 132, 1)',
                                                        ],
                                                        borderWidth: 1
                                                    }]
                                            },
                                            options: {
                                                scales: {
                                                    y: {
                                                        beginAtZero: true
                                                    }
                                                },
                                                maintainAspectRatio: false
                                            }
                                        });

                                        loadVeuneIncome();

                                    });

                        }
                    </script>

                    <script>
                        // /ITP4511_Assignment/api/report/vincome?venue=1&year=2023&action=year
                        function loadVeuneIncome() {
                            let apiLocation = "<%=request.getContextPath()%>" + "/api/report/vincome?venue=" + document.getElementById("venue").value + "&year=" + document.getElementById("year").value;
                            fetch(apiLocation)
                                    .then(res => res.json())
                                    .then(data => {
                                        // {
                                        //     "income": "0.0",
                                        //     "month": "JANUARY",
                                        //     "count": "0"
                                        // },

                                        let totalIncome = 0;

                                        let income = [];
                                        let month = [];

                                        data.forEach(item => {
                                            income.push(item.income);
                                            month.push(item.month);
                                            totalIncome += parseFloat(item.income);
                                        });

                                        document.getElementById("income").innerHTML = totalIncome;

                                        let ctx = document.getElementById('mainchartref').getContext('2d');
                                        let myChart = new Chart(ctx, {
                                            type: 'line',
                                            data: {
                                                labels: month,
                                                datasets: [{
                                                        label: 'Income - ' + document.getElementById("year").value,
                                                        data: income,
                                                        backgroundColor: [
                                                            'rgba(255, 99, 132, 0.2)',
                                                        ],
                                                        borderColor: [
                                                            'rgba(255, 99, 132, 1)',
                                                        ],
                                                        borderWidth: 1
                                                    }]
                                            },
                                            options: {
                                                scales: {
                                                    y: {
                                                        beginAtZero: true
                                                    }
                                                },
                                                maintainAspectRatio: false
                                            }
                                        });
                                    });
                        }
                    </script>


                </div>
            </div>

        </div>
    </div>
</section>
