<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<%@ taglib uri="/WEB-INF/tlds/ict-taglib" prefix="ict" %>
<ict:checkDbConnection />
<ict:checkRole roleStr="SeniorManager" />

<!DOCTYPE html>

<%@ page import="ict.bean.User" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>JSP Page</title>

        <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.css" rel="stylesheet" />

        <script src="https://cdn.tailwindcss.com"></script>

        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

        <style>
            .btn-primary {
                background-color: #3b71ca !important;
            }
        </style>
    </head>
    <body class="h-screen w-screen overflow-x-hidden">
        <jsp:include page="header.jsp" />

        <div
            class="welcome message text-xl border bg-gray-100 px-3 py-1 m-4 mx-5"
            >
            Welcome back, <%
                User u = ((User) session.getAttribute("userInfo")); 
                if (u != null) { // the tag could not redirect to login page immediately
                    out.print(u.getFirstName());
                }
            %>

            🕺
        </div>

        <div class="content px-5 flex flex-col pb-3">
            <div class="flex flex-row align-baseline">
                <div class="title uppercase text-xl font-bold">
                    Dashborad
                    <div class="text-sm font-normal text-gray-500 lowercase">
                        overview of your venues
                    </div>
                </div>

                <a class="btn btn-primary ml-auto mr-4 h-fit" href="./report.jsp">
                    Get detailed report
                </a>
            </div>

            <div class="content mt-4">
                <div class="cards grid grid-cols-3 gap-3 select-none">
                    <div class="card">
                        <div class="card-body flex flex-col">
                            <span
                                class="text-2xl font-bold animate-pulse"
                                id="totalIncome"
                                >Loading ...</span
                            >
                            <span class="text-normal font-normal text-gray-500"
                                  >Total Income</span
                            >
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body flex flex-col">
                            <span
                                class="text-2xl font-bold animate-pulse"
                                id="totalBookings"
                                >Loading ...
                            </span>
                            <span class="text-normal font-normal text-gray-500"
                                  >Total Bookings</span
                            >
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body flex flex-col">
                            <span
                                class="text-2xl font-bold animate-pulse"
                                id="occRate"
                                >Loading ...</span
                            >
                            <span class="text-normal font-normal text-gray-500">
                                Avgerage Venues Occupancy Rate ( 1 weeks )</span
                            >
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body flex flex-col">
                            <span class="text-2xl font-bold" id="userVisited">
                                <% if (application.getAttribute("uservisited")
                                            != null) {%> <%=application.getAttribute("uservisited")%> <% } else {
                                    application.setAttribute("uservisited",
                                            0); %> 0 <% } %>
                            </span>
                            <span class="text-normal font-normal text-gray-500"
                                  >Users Visited (Last 30 days)
                            </span>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body flex flex-col">
                            <span
                                class="text-2xl font-bold animate-pulse"
                                id="bRate"
                                >Loading ...</span
                            >
                            <span class="text-normal font-normal text-gray-500"
                                  >Bounce Rate</span
                            >
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body flex flex-col">
                            <span class="text-2xl font-bold">
                                <% if (application.getAttribute("internalservererrocount")
                                            != null
                                            && application.getAttribute("uservisited") != null) {
                                        double uservisited
                                                = Double.parseDouble(application.getAttribute("uservisited").toString());
                                        double internalservererrocount
                                                = Double.parseDouble(application.getAttribute("internalservererrocount").toString());
                                        if (internalservererrocount == 0) {
                                            out.print("100%");
                                        } else {
                                            double res
                                                    = (internalservererrocount / uservisited) * 100;
                                            // format to 2 decimal places
                                            out.print(String.format("%.2f", res));
                                            out.print("%");
                                        }
                                    } else {
                                        out.print("0%");
                                    }
                                %>
                            </span>
                            <span class="text-normal font-normal text-gray-500"
                                  >Failure Rate</span
                            >
                        </div>
                    </div>
                </div>

                <div class="graphs flex flex-row w-full mt-4 h-full">
                    <div class="w-[38%] mr-2">
                        <div class="card">
                            <div class="card-header">
                                <div class="title uppercase text-xl font-bold">
                                    Total Income for Each Venue
                                </div>
                            </div>
                            <div class="card-body">
                                <canvas id="tiev" ></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="w-[38%] mx-2">
                        <div class="card">
                            <div class="card-header">
                                <div class="title uppercase text-xl font-bold">
                                    Total Bookings for Each Venue
                                </div>
                            </div>
                            <div class="card-body">
                                <canvas id="tbev"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="ml-2">
                        <div class="card">
                            <div class="card-header">
                                <div class="title uppercase text-xl font-bold">
                                    Seasonality
                                </div>
                            </div>
                            <div class="card-body">
                                <canvas id="ror"></canvas>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script defer>
            // ITP4511_Assignment/api/report
            // {"EPL(Tuen Mun)":{"totalbookings":"12","recent 14 weeks occupancy rate":{"0":"7.142857551574707","11":"0.0","1":"50.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"recent 12 months occupancy rate":{"0":"4.34782600402832","11":"0.0","1":"0.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"Total Income":"1200.0","recent 14 weeks bookings":{"0":"12","11":"0","1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0","10":"0"},"Total Income (weekly)":{"0":"600.0","11":"0.0","1":"600.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"}},"EPL(Sha Tin)":{"totalbookings":"6","recent 14 weeks occupancy rate":{"0":"1.1904761791229248","11":"0.0","1":"41.666664123535156","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"recent 12 months occupancy rate":{"0":"2.17391300201416","11":"0.0","1":"0.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"Total Income":"1200.0","recent 14 weeks bookings":{"0":"6","11":"0","1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0","10":"0"},"Total Income (weekly)":{"0":"200.0","11":"0.0","1":"1000.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"}},"EPL(Tsing Yi)":{"totalbookings":"8","recent 14 weeks occupancy rate":{"0":"3.5714287757873535","11":"0.0","1":"41.666664123535156","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"recent 12 months occupancy rate":{"0":"2.8985507488250732","11":"0.0","1":"0.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"Total Income":"1200.0","recent 14 weeks bookings":{"0":"8","11":"0","1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0","10":"0"},"Total Income (weekly)":{"0":"450.0","11":"0.0","1":"750.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"}},"EPL(Lee Wai Lee)":{"totalbookings":"8","recent 14 weeks occupancy rate":{"0":"0.0","11":"0.0","1":"66.66667175292969","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"recent 12 months occupancy rate":{"0":"2.8985507488250732","11":"0.0","1":"0.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"Total Income":"600.0","recent 14 weeks bookings":{"0":"8","11":"0","1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0","10":"0"},"Total Income (weekly)":{"0":"0.0","11":"0.0","1":"600.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"}},"EPL(Chai Wan)":{"totalbookings":"3","recent 14 weeks occupancy rate":{"0":"0.0","11":"0.0","1":"25.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"recent 12 months occupancy rate":{"0":"1.08695650100708","11":"0.0","1":"0.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"Total Income":"750.0","recent 14 weeks bookings":{"0":"3","11":"0","1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0","10":"0"},"Total Income (weekly)":{"0":"0.0","11":"0.0","1":"750.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"}}}

            let apiLocation = "<%=request.getContextPath()%>" + "/api/report";
            let apiLocation_veune =
                    "<%=request.getContextPath()%>" + "/api/report?action=venue";

            let totalIncome = 0;
            let totalIncomeRef = document.getElementById("totalIncome");

            let totalBookings = 0;
            let totalBookingsRef = document.getElementById("totalBookings");

            let occRate = 0;
            let occTotal = 0;
            let occRateRef = document.getElementById("occRate");

            let bRate = 0;
            let bRateRef = document.getElementById("bRate");

            let vname = [];

            let veunes = [];

            let occRateBySeason = [
                {season: "Spring", occRate: 0},
                {season: "Summer", occRate: 0},
                {season: "Autumn", occRate: 0},
                {season: "Winter", occRate: 0},
            ];

            async function fetchVenueName() {
                let response = await fetch(apiLocation_veune);
                let data = await response.json();
                return data.map((venue) => venue.name);
            }

            fetch(apiLocation)
                    .then((response) => response.json())
                    .then(async (data) => {
                        vname = await fetchVenueName();

                        vname.forEach((location) => {
                            totalIncome += parseInt(data[location]["Total Income"]);
                            totalBookings += parseInt(data[location].totalbookings);
                            occTotal += parseInt(
                                    data[location]["recent 12 months occupancy rate"][0]
                                    );
                            veunes.push({
                                name: location,
                                totalIncome: parseInt(
                                        data[location]["Total Income"]
                                        ),
                                totalBooking: parseInt(
                                        data[location].totalbookings
                                        )
                            });

                            const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

                            for (i = 0; i < 12; i++) {

                                // get current month
                                let currentMonth = new Date().getMonth() + 1;
                                /// get current year
                                let currentYear = new Date().getFullYear();

                                // the month of the data
                                let month = currentMonth - i;
                                let monthString = "";

                                // turn the month into string from number
                                // check if the month is negative
                                if (month < 0) {
                                    monthString = currentYear - 1 + "-" + months[month + 12];
                                } else {
                                    monthString = currentYear + "-" + months[month];
                                }

                                // determine the season
                                let currSeason = "";
                                if (monthString.includes("Mar") || monthString.includes("Apr") || monthString.includes("May")) {
                                    currSeason = "Spring";
                                } else if (monthString.includes("Jun") || monthString.includes("Jul") || monthString.includes("Aug")) {
                                    currSeason = "Summer";
                                } else if (monthString.includes("Sep") || monthString.includes("Oct") || monthString.includes("Nov")) {
                                    currSeason = "Autumn";
                                } else {
                                    currSeason = "Winter";
                                }

                                // get the occupancy rate
                                let totalBooking = parseInt(data[location]["recent 12 months bookings"][i]);

                                // add the occupancy rate to the season
                                occRateBySeason.forEach((season) => {
                                    if (season.season == currSeason) {
                                        season.occRate += totalBooking;
                                    }
                                });
                            }
                        });

                        totalIncomeRef.innerHTML = "HKD " + totalIncome + ".00";
                        totalIncomeRef.classList.remove("animate-pulse");

                        totalBookingsRef.innerHTML = totalBookings;
                        totalBookingsRef.classList.remove("animate-pulse");

                        occRate = occTotal / vname.length;
                        occRateRef.innerHTML = occRate.toFixed(2) + "%";
                        occRateRef.classList.remove("animate-pulse");

                        // chart for total income
                        new Chart(document.getElementById("tiev"), {
                            type: "bar",
                            data: {
                                labels: vname,
                                datasets: [
                                    {
                                        label: "Total Income",
                                        data: veunes.map(
                                                (veune) => veune.totalIncome
                                        ),
                                        backgroundColor: [
                                            "rgba(255, 99, 132, 0.2)",
                                            "rgba(54, 162, 235, 0.2)",
                                            "rgba(255, 206, 86, 0.2)",
                                            "rgba(75, 192, 192, 0.2)",
                                            "rgba(153, 102, 255, 0.2)",
                                            "rgba(255, 159, 64, 0.2)",
                                        ],
                                        borderColor: [
                                            "rgba(255, 99, 132, 1)",
                                            "rgba(54, 162, 235, 1)",
                                            "rgba(255, 206, 86, 1)",
                                            "rgba(75, 192, 192, 1)",
                                            "rgba(153, 102, 255, 1)",
                                            "rgba(255, 159, 64, 1)",
                                        ],
                                        borderWidth: 1,
                                    },
                                ],
                            },
                            options: {
                                scales: {
                                    y: {
                                        beginAtZero: true,
                                    },
                                },
                            },
                        });

                        // chart for total bookings
                        new Chart(document.getElementById("tbev"), {
                            type: "bar",
                            data: {
                                labels: vname,
                                datasets: [
                                    {
                                        label: "Total Bookings",
                                        data: vname.map(
                                                (veune) =>
                                            data[veune].totalbookings
                                        ),
                                        backgroundColor: [
                                            "rgba(255, 99, 132, 0.2)",
                                            "rgba(54, 162, 235, 0.2)",
                                            "rgba(255, 206, 86, 0.2)",
                                            "rgba(75, 192, 192, 0.2)",
                                            "rgba(153, 102, 255, 0.2)",
                                            "rgba(255, 159, 64, 0.2)",
                                        ],
                                        borderColor: [
                                            "rgba(255, 99, 132, 1)",
                                            "rgba(54, 162, 235, 1)",
                                            "rgba(255, 206, 86, 1)",
                                            "rgba(75, 192, 192, 1)",
                                            "rgba(153, 102, 255, 1)",
                                            "rgba(255, 159, 64, 1)",
                                        ],
                                        borderWidth: 1,
                                    },
                                ],
                            },
                            options: {
                                scales: {
                                    y: {
                                        beginAtZero: true,
                                    },
                                },
                            },
                        });

                        // chart for recent 12 months occupancy rate
                        new Chart(document.getElementById("ror"), {
                            type: "doughnut",
                            data: {
                                labels: occRateBySeason.map((data) => data.season),
                                datasets: [
                                    {
                                        label: "Occupancy Rate",
                                        data: occRateBySeason.map((data) => (data.occRate)),
                                        backgroundColor: [
                                            "rgba(255, 99, 132, 0.2)",
                                            "rgba(54, 162, 235, 0.2)",
                                            "rgba(255, 206, 86, 0.2)",
                                            "rgba(75, 192, 192, 0.2)",
                                        ],
                                    },
                                ],
                            },
                        });
                    });
        </script>
    </body>
</html>
