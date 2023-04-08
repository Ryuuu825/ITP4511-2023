<%-- 
   Document   : login
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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css"
        integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous">
    </script>
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"
        integrity="sha256-oP6HI9z1XaZNBrJURtCoUT5SUnxFr8s3BzRl+cbzUq8=" crossorigin="anonymous"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.css" rel="stylesheet" />
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.2.0/mdb.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />
</head>
<style>
    .navbar-brand:hover {
        color: #205295 !important;
    }
</style>
<% String error = (String) request.getAttribute("error"); %>
<script>
    $(document).ready(function () {
        $(this).val() != "" ? $(this).addClass('active') : $(this).removeClass('active');
        $('#errorMsg').val() != "" ? $('#errorModal').modal('show') : $('#errorModal').modal('hide');
        $('.form-control').on('focus', function () {
            $(this).addClass('border-primary');
        });
        $('.form-control').on('blur', function () {
            $(this).removeClass('border-primary');
            $(this).val() != "" ? $(this).addClass('active') : $(this).removeClass('active');
        });
    });
</script>

<body>
    <!-- Modal -->
    <input type="hidden" value="${error}" id="errorMsg" />
    <div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h1 class="modal-title fs-5" id="errorModalLabel">Login unsuccessfully</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"
                        onclick=""></button>
                </div>
                <div class="modal-body">
                    <%=error%>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <div class="d-flex justify-content-center align-items-center vh-100" style="background-color: #93b9ee;">
        <div class="card shadow-lg border-0 px-0" style="width: 60%;">
            <div class="d-flex flex-row">
                <div class="w-50">
                    <img src="assets/img/bg-login.png" alt="Venue" class="img-fluid object-fit-cover rounded-start-3" />
                </div>
                <div class="flex-grow-1">
                    <nav class="py-4">
                        <div class="container-fluid">
                            <a title="Back to home page" class="navbar-brand text-gary-800 fw-bold fs-5"
                                href="index.jsp">
                                <i class="bi bi-house-door-fill pe-2"></i>Home</a>
                        </div>
                    </nav>
                    <div class="card-body">
                        <h1 class="text-sm-start ps-4 mb-4 fw-bold">Sign in</h1>
                        <form method="post" action="handleLogin" class="px-4">
                            <input type="hidden" name="action" value="login" />
                            <!-- Username input -->
                            <div class="form-outline mb-4">
                                <input type="text" id="username" required aria-autocomplete="none" name="username"
                                    class="form-control border" />
                                <label class="form-label" for="username">Username</label>
                            </div>

                            <!-- Password input -->
                            <div class="form-outline mb-4">
                                <input type="password" name="password" autocomplete="none" required id="password"
                                    class="form-control border" />
                                <label class="form-label" for="password">Password</label>
                            </div>

                            <!-- 2 column grid layout for inline styling -->
                            <div class="row mb-4">
                                <div class="col d-flex justify-content-center">
                                    <!-- Checkbox -->
                                    <div class="form-check">
                                        <input class="form-check-input" type="checkbox" value="" id="remember"
                                            checked />
                                        <label class="form-check-label" for="remember"> Remember me </label>
                                    </div>
                                </div>

                                <div class="col">
                                    <a href="#">Forgot password?</a>
                                </div>
                            </div>

                            <!-- Submit button -->
                            <button type="submit" class="btn btn-primary btn-block mb-4">Sign in</button>

                            <!-- Register buttons -->
                            <div class="text-center">
                                <p>Not a member? <a class="text-decoration-underline" href="register.jsp">Register</a>
                                </p>
                                <p>or sign up with:</p>
                                <button type="button" class="btn btn-primary btn-floating mx-1">
                                    <i class="fab fa-facebook-f"></i>
                                </button>

                                <button type="button" class="btn btn-primary btn-floating mx-1">
                                    <i class="fab fa-google"></i>
                                </button>

                                <button type="button" class="btn btn-primary btn-floating mx-1">
                                    <i class="fab fa-twitter"></i>
                                </button>

                                <button type="button" class="btn btn-primary btn-floating mx-1">
                                    <i class="fab fa-github"></i>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>