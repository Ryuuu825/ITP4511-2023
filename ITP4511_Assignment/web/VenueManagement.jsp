<%-- 
   Document   : venueInfo
   Created on : 2023年4月8日, 下午2:57:20
   Author     : jyuba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"> -->
    <!-- <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"
        integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e" crossorigin="anonymous"> -->
    <!-- <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"> -->
    <!-- </script> -->
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"
        integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.css" rel="stylesheet" />
    <!-- <script src="https://cdn.tailwindcss.com"></script> -->
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />
</head>
<style>
    .nav-hover:hover {
        font-size: 1.125rem;
        line-height: 1.75rem;
    }
</style>
<script>
    $(document).ready(function () {
        const searchFocus = document.getElementById('search-focus');
        const keys = [{
                keyCode: 'AltLeft',
                isTriggered: false
            },
            {
                keyCode: 'ControlLeft',
                isTriggered: false
            },
        ];

        window.addEventListener('keydown', (e) => {
            keys.forEach((obj) => {
                if (obj.keyCode === e.code) {
                    obj.isTriggered = true;
                }
            });

            const shortcutTriggered = keys.filter((obj) => obj.isTriggered).length === keys.length;

            if (shortcutTriggered) {
                searchFocus.focus();
            }
        });

        window.addEventListener('keyup', (e) => {
            keys.forEach((obj) => {
                if (obj.keyCode === e.code) {
                    obj.isTriggered = false;
                }
            });
        });

        $('#search-focus').on('focus', function () {
            $(this).addClass('border-primary');
        });

        $('#search-focus').on('blur', function () {
            $(this).removeClass('border-primary');
        });
    });
</script>

<body>
    <header class="z-3 w-100 d-flex flex-row align-items-center justify-content-between p-3"
        style="background-color: #144272;">
        <div class="title text-uppercase ms-5 me-auto fw-bold text-white"
            style="font-size: 1.875rem;line-height: 2.25rem;">
            <a href="index.jsp" class="text-white">
                Event Point Limited
            </a>
        </div>
        <ul class="nav justify-content-end fs-6 fw-semibold flex align-items-center mr-5">
            <li class="nav-item nav-hover">
                <a class="text-white mx-3" href="#">
                    <!-- Browse Spaces -->
                    Venue
                </a>
            </li>
            <li class="nav-item border-start nav-hover">
                <a class="text-white mx-3" href="venueInfo.jsp">
                    <!-- Browse Spaces -->
                    Timeslot
                </a>
            </li>
            <li class="nav-item border-start nav-hover">
                <a class="text-white mx-3" href="login.jsp"> Booking </a>
            </li>
            <li class="nav-item border bg-light rounded-1 py-1">
                <a class="mx-3 text-dark" href="login.jsp"> Sign Out </a>
            </li>
        </ul>
    </header>
    <section style="background-color: #eee;">
        <div class="text-start container py-5 w-75">
            <div class="d-flex flex-row justify-content-between align-items-center">
                <label class="mt-4 mb-5 fs-3 flex-grow-1"><strong>Find venues</strong></label>
                <form class="input-group w-25" action="searchVenue" method="GET">
                    <div class="form-outline">
                        <input id="search-focus" name="keyword" type="search" id="keyword"
                            class="form-control border" />
                        <label class="form-label" for="keyword">Search</label>
                    </div>
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-search"></i>
                    </button>
                </form>
            </div>
            <div class="row">
                <p class="text-lg">Find the perfect venue for your event</p>
            </div>

            <h4 class="mt-4 mb-5 fs-3"><strong>Displaying 0 matching results</strong></h4>
            <div class="row">
                <div class="col-lg-4 col-md-12 mb-4">
                    <div class="card">
                        <div class="bg-image hover-zoom ripple ripple-surface ripple-surface-light"
                            data-mdb-ripple-color="light">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/belt.webp"
                                class="w-100" />
                            <a href="#!">
                                <div class="mask">
                                    <div class="d-flex justify-content-start align-items-end h-100">
                                        <h5><span class="badge bg-primary ms-2">New</span></h5>
                                    </div>
                                </div>
                                <div class="hover-overlay">
                                    <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                </div>
                            </a>
                        </div>
                        <div class="card-body">
                            <a href="" class="text-reset">
                                <h5 class="card-title mb-3">Product name</h5>
                            </a>
                            <a href="" class="text-reset">
                                <p>Category</p>
                            </a>
                            <h6 class="mb-3">$61.99</h6>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card">
                        <div class="bg-image hover-zoom ripple ripple-surface ripple-surface-light"
                            data-mdb-ripple-color="light">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/img%20(4).webp"
                                class="w-100" />
                            <a href="#!">
                                <div class="mask">
                                    <div class="d-flex justify-content-start align-items-end h-100">
                                        <h5><span class="badge bg-success ms-2">Eco</span></h5>
                                    </div>
                                </div>
                                <div class="hover-overlay">
                                    <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                </div>
                            </a>
                        </div>
                        <div class="card-body">
                            <a href="" class="text-reset">
                                <h5 class="card-title mb-3">Product name</h5>
                            </a>
                            <a href="" class="text-reset">
                                <p>Category</p>
                            </a>
                            <h6 class="mb-3">$61.99</h6>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card">
                        <div class="bg-image hover-zoom ripple" data-mdb-ripple-color="light">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/shoes%20(3).webp"
                                class="w-100" />
                            <a href="#!">
                                <div class="mask">
                                    <div class="d-flex justify-content-start align-items-end h-100">
                                        <h5><span class="badge bg-danger ms-2">-10%</span></h5>
                                    </div>
                                </div>
                                <div class="hover-overlay">
                                    <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                </div>
                            </a>
                        </div>
                        <div class="card-body">
                            <a href="" class="text-reset">
                                <h5 class="card-title mb-3">Product name</h5>
                            </a>
                            <a href="" class="text-reset">
                                <p>Category</p>
                            </a>
                            <h6 class="mb-3">
                                <s>$61.99</s><strong class="ms-2 text-danger">$50.99</strong>
                            </h6>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-4 col-md-12 mb-4">
                    <div class="card">
                        <div class="bg-image hover-zoom ripple" data-mdb-ripple-color="light">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/img%20(23).webp"
                                class="w-100" />
                            <a href="#!">
                                <div class="mask">
                                    <div class="d-flex justify-content-start align-items-end h-100">
                                        <h5>
                                            <span class="badge bg-success ms-2">Eco</span><span
                                                class="badge bg-danger ms-2">-10%</span>
                                        </h5>
                                    </div>
                                </div>
                                <div class="hover-overlay">
                                    <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                </div>
                            </a>
                        </div>
                        <div class="card-body">
                            <a href="" class="text-reset">
                                <h5 class="card-title mb-3">Product name</h5>
                            </a>
                            <a href="" class="text-reset">
                                <p>Category</p>
                            </a>
                            <h6 class="mb-3">
                                <s>$61.99</s><strong class="ms-2 text-danger">$50.99</strong>
                            </h6>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card">
                        <div class="bg-image hover-zoom ripple ripple-surface ripple-surface-light"
                            data-mdb-ripple-color="light">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/img%20(17).webp"
                                class="w-100" />
                            <a href="#!">
                                <div class="mask">
                                    <div class="d-flex justify-content-start align-items-end h-100"></div>
                                </div>
                                <div class="hover-overlay">
                                    <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                </div>
                            </a>
                        </div>
                        <div class="card-body">
                            <a href="" class="text-reset">
                                <h5 class="card-title mb-3">Product name</h5>
                            </a>
                            <a href="" class="text-reset">
                                <p>Category</p>
                            </a>
                            <h6 class="mb-3">$61.99</h6>
                        </div>
                    </div>
                </div>

                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card">
                        <div class="bg-image hover-zoom ripple" data-mdb-ripple-color="light">
                            <img src="https://mdbcdn.b-cdn.net/img/Photos/Horizontal/E-commerce/Products/img%20(30).webp"
                                class="w-100" />
                            <a href="#!">
                                <div class="mask">
                                    <div class="d-flex justify-content-start align-items-end h-100">
                                        <h5>
                                            <span class="badge bg-primary ms-2">New</span><span
                                                class="badge bg-success ms-2">Eco</span><span
                                                class="badge bg-danger ms-2">-10%</span>
                                        </h5>
                                    </div>
                                </div>
                                <div class="hover-overlay">
                                    <div class="mask" style="background-color: rgba(251, 251, 251, 0.15);"></div>
                                </div>
                            </a>
                        </div>
                        <div class="card-body">
                            <a href="" class="text-reset">
                                <h5 class="card-title mb-3">Product name</h5>
                            </a>
                            <a href="" class="text-reset">
                                <p>Category</p>
                            </a>
                            <h6 class="mb-3">
                                <s>$61.99</s><strong class="ms-2 text-danger">$50.99</strong>
                            </h6>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>

</html>