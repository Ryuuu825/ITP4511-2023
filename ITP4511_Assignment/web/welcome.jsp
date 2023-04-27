<%-- Document : welcome Created on : 2023年4月7日, 上午1:31:01 Author : jyuba
--%> <%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <title>Welcome</title>

        <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />

        <script src="https://cdn.tailwindcss.com"></script>

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

        <style>
            .mask {
                background: transparent;
                background: rgba(18, 18, 18, 0.3);
            }
        </style>
    </head>

    <body class="relative overflow-x-hidden">
        <header class="fixed z-3 w-full flex flex-row items-center p-3 shadow-xl">
            <div class="title text-3xl uppercase ml-12 mr-auto font-extrabold text-white">
                <a href="index.jsp" class="hover:text-white">
                    Event Point Limited
                </a>
            </div>
            <ul class="nav justify-content-end font-semibold flex align-items-center mr-5">
                <li class="nav-item">
                    <a class="text-white mx-3 hover:text-lg" href="#">
                        <!-- Browse Spaces -->
                        Browse Spaces
                    </a>
                </li>
                <li class="nav-item border-l">
                    <a class="text-white mx-3 hover:text-lg" href="findVenue">
                        <!-- Browse Spaces -->
                        Find venues
                    </a>
                </li>
                <li class="nav-item border-l">
                    <a class="text-white mx-3 px-3 hover:text-lg" href="login.jsp"> Login </a>
                </li>
                <li class="nav-item border bg-light rounded-sm py-1">
                    <a class=" mx-3 text-dark" href="register.jsp"> Sign Up </a>
                </li>
            </ul>
        </header>
        <!--<div class="absolute z-2">${sessionScope.role}</div>-->
        <div class="h-screen w-screen overflow-hidden relative flex flex-col">
            <div class="h-[85%] overflow-hidden select-none">
                <div
                    id="carousel"
                    class="carousel slide"
                    data-bs-ride="carousel"
                    >
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img
                                src="assets/img/venue/slide1.jpg"
                                class="d-block h-screen w-screen object-cover"
                                />
                        </div>
                        <div class="carousel-item">
                            <img
                                src="assets/img/venue/slide2.jpeg"
                                class="d-block h-screen w-screen object-cover"
                                />
                        </div>
                        <div class="carousel-item">
                            <img
                                src="assets/img/venue/slide3.jpg"
                                class="d-block h-screen w-screen object-cover"
                                />
                        </div>
                    </div>
                </div>
            </div>

            <div class="z-90 h-[15%]" style="background-color: #121212"></div>

            <div class="absolute w-full h-full mask">
                <div
                    class="absolute bottom-50 w-9/12 h-[5rem] flex items-center justify-center left-[13%]"
                    >
                    <button onclick="location.href = 'findVenue'; " class="rounded-pill btn btn-light w-25 fs-4 fw-bold">Get Start Here</button>
                </div>

                <div class="absolute bottom-20 left-10 text-5xl font-bold">
                    <div
                        class="text-white mb-3"
                        style="font-size: 5rem; font-weight: 700"
                        >
                        Find a venue.
                    </div>
                    <div
                        class="text-white -mb-2"
                        style="font-size: 3rem; font-weight: 700"
                        >
                        Unleash your event potential.
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
