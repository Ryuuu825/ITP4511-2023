<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>JSP Page</title>

        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
            crossorigin="anonymous"
        />
        <script
            src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe"
            crossorigin="anonymous"
        ></script>

        <script src="https://cdn.tailwindcss.com"></script>

        <script defer>
            // ITP4511_Assignment/api/report
            // {"EPL(Tuen Mun)":{"totalbookings":"12","recent 14 weeks occupancy rate":{"0":"7.142857551574707","11":"0.0","1":"50.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"recent 12 months occupancy rate":{"0":"4.34782600402832","11":"0.0","1":"0.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"Total Income":"1200.0","recent 14 weeks bookings":{"0":"12","11":"0","1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0","10":"0"},"Total Income (weekly)":{"0":"600.0","11":"0.0","1":"600.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"}},"EPL(Sha Tin)":{"totalbookings":"6","recent 14 weeks occupancy rate":{"0":"1.1904761791229248","11":"0.0","1":"41.666664123535156","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"recent 12 months occupancy rate":{"0":"2.17391300201416","11":"0.0","1":"0.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"Total Income":"1200.0","recent 14 weeks bookings":{"0":"6","11":"0","1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0","10":"0"},"Total Income (weekly)":{"0":"200.0","11":"0.0","1":"1000.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"}},"EPL(Tsing Yi)":{"totalbookings":"8","recent 14 weeks occupancy rate":{"0":"3.5714287757873535","11":"0.0","1":"41.666664123535156","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"recent 12 months occupancy rate":{"0":"2.8985507488250732","11":"0.0","1":"0.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"Total Income":"1200.0","recent 14 weeks bookings":{"0":"8","11":"0","1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0","10":"0"},"Total Income (weekly)":{"0":"450.0","11":"0.0","1":"750.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"}},"EPL(Lee Wai Lee)":{"totalbookings":"8","recent 14 weeks occupancy rate":{"0":"0.0","11":"0.0","1":"66.66667175292969","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"recent 12 months occupancy rate":{"0":"2.8985507488250732","11":"0.0","1":"0.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"Total Income":"600.0","recent 14 weeks bookings":{"0":"8","11":"0","1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0","10":"0"},"Total Income (weekly)":{"0":"0.0","11":"0.0","1":"600.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"}},"EPL(Chai Wan)":{"totalbookings":"3","recent 14 weeks occupancy rate":{"0":"0.0","11":"0.0","1":"25.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"recent 12 months occupancy rate":{"0":"1.08695650100708","11":"0.0","1":"0.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"},"Total Income":"750.0","recent 14 weeks bookings":{"0":"3","11":"0","1":"0","2":"0","3":"0","4":"0","5":"0","6":"0","7":"0","8":"0","9":"0","10":"0"},"Total Income (weekly)":{"0":"0.0","11":"0.0","1":"750.0","2":"0.0","3":"0.0","4":"0.0","5":"0.0","6":"0.0","7":"0.0","8":"0.0","9":"0.0","10":"0.0"}}}

            let apiLocation = "<%=request.getContextPath()%>" + "/api/report";
            fetch(apiLocation)
                .then(response => response.json())
                .then(data => {
                });

        </script>
    </head>
    <body class="h-screen w-screen">

        <jsp:include page="header.jsp" />

        <div class="welcome message text-xl border bg-gray-100 px-3 py-1 m-4 mx-5">
            Welcome back, <%= session.getAttribute("username") %> 🕺
        </div>

        <div class="content px-5 flex flex-col">

            <div class="flex flex-row align-baseline">
                <div class="title uppercase text-xl font-bold">
                    Dashborad
                <div class="text-sm font-normal text-gray-500 lowercase">overview of your venues</div>
                </div>

            </div>

            <div class="content mt-4">
                <div class="cards grid grid-cols-3 gap-3 select-none">
                    <div class="card">
                        <div class="card-body flex flex-col animate-pulse">
                            <span class="text-2xl font-bold" id="totalIncome">Loading ...</span>
                            <span class="text-normal font-normal text-gray-500">Total Income</span>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body flex flex-col animate-pulse">
                            <span class="text-2xl font-bold" id="totalBookings">Loading ... </span>
                            <span class="text-normal font-normal text-gray-500">Total Bookings</span>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body flex flex-col animate-pulse">
                            <span class="text-2xl font-bold" id="occRate">Loading ...</span>
                            <span class="text-normal font-normal text-gray-500">Venues Occupancy Rate</span>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body flex flex-col animate-pulse">
                            <span class="text-2xl font-bold" id="userVisited">
                                <%= application.getAttribute("uservisited") %>
                            </span>
                            <span class="text-normal font-normal text-gray-500">Users Visited (Last 30 days) </span>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-body flex flex-col animate-pulse">
                            <span class="text-2xl font-bold" id="bRate">Loading ...</span>
                            <span class="text-normal font-normal text-gray-500">Bounce Rate</span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
