
<% if (request.getAttribute("error") == null )  {


}%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Internal Server Error</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ"
            crossorigin="anonymous"
/>
    </head>
    <body>
         <%
            if ( application.getAttribute("internalservererrocount") == null ) {
                application.setAttribute("internalservererrocount", 1);
            } else {
                int count = java.lang.Integer.parseInt(application.getAttribute("internalservererrocount").toString());
                application.setAttribute("internalservererrocount", count + 1);
            }
        %>
        <div class="h-screen w-screen flex flex-col items-center justify-center text-center">
            
            <div class="card w-[35%]">
                <div class="card-header">
                    <h1 class="text-2xl font-bold text-dark">
                        500 Server Error
                    </h1>
                </div>
                <div class="card-body text-left">
                    <p class="text-lg font-bold text-gray-500 ">
                        Sorry, something went wrong on our end. We are 
                        <br>
                        currently trying to fix the problem.
                    </p>
                    <p class="text-xl font-bold text-gray-600 mt-2">
                        In the meantime, you can try to :
                        <div class="flex flex-row items-baseline ml-3">
                            <a href=" <%= request.getAttribute("redirect") %> " class="text-blue-500">
                                Reload the page
                            </a>
                            <span class="mx-2">
                                /
                            </span>
                            <a href="<%= getServletContext().getContextPath() %> " class="text-blue-500">
                                Visit the home page
                            </a>
                        </div>
                    </p>
                    
                </div>

                <div class="card-footer">
                    <p class="text-xs text-gray-500 ">
                        Possible error:
                        <%= request.getAttribute("error") %>
                    </p>
                </div>
            </div>
        </div>
    </body>
</html>
