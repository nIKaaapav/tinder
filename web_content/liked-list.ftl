<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="img/favicon.ico">

    <title>People list</title>
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css"
          integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous">
    <!-- Bootstrap core CSS -->
    <link href="static/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link rel="stylesheet" href="static/css/style.css">
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-8 offset-2">
            <div class="panel panel-default user_panel">
                <div class="panel-heading">
                    <h3 class="panel-title" >My liked list</h3>
                </div>
                <div style="display: flex; flex-direction: column;">
                    <#list listUsers as user>
                        <form action="/liked/" method="post">
                            <a href="/messages/${user.getId()}">
                                <div class="card bg-dark text-white" style="margin: 10px;">
                                    <img src=${user.getUrlImg()}
                                         class="card-img" alt="...">
                                    <div class="card-img-overlay">
                                        <h5 class="card-title">${user.getName()}</h5>
                                        <p class="card-text"> ${user.getPosition()} </p>
                                        <p class="card-text">Last Login: ${user.getDate()}<br><small
                                                    class="text-muted">${user.getTimeDif()}</small></p>
                                    </div>
                                </div>
                            </a>
                        </form>
                    </#list>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>