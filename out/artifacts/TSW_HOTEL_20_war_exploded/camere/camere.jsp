<%--
  Created by IntelliJ IDEA.
  User: Ciro
  Date: 14/05/2020
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="it">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link rel="stylesheet" href="../css/style_camere.css">
    <link rel="stylesheet" href="../css/common.css">
    <title>Camere</title>
</head>
<body class="pt-2">
<!--
NAVBAR
-->
<nav class="navbar navbar-expand-md navbar-dark fixed-top">
    <div class="container">
        <a class="navbar-brand font-weight-bold" href="../index.jsp">
            <img src="../imgs/logo.png" width="90" height="40" alt="" >
            Hotel Marbella
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="collapsibleNavbar">
            <ul class="navbar-nav navbar-right">
                <li class="nav-item">
                    <a class="nav-link font-weight-bold" href="../index.jsp">Home</a>
                </li>
                <li class="nav-item active">
                    <a class="nav-link font-weight-bold" href="../camere.jsp">Visita camere</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link font-weight-bold" href="./attivita.jsp">Esplora ristoranti ed attività</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Fine navbar-->

<!-- Inizio corpo pagina-->
<div class="container mt-5">
<div class="row py-5">
    <div class="col">
      <div class="card w-90">
        <div class="row no-gutters">
            <div class="col-lg-4" style="background-image: url('../imgs/imgs-camere/suite.jpg')">
            </div>
            <div class="col-lg-8">
                <h5 class="card-header">Camera Suite</h5>
                <div class="card-body">
                    <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                    <button type="button" class="btn btn-darkgreen" onclick="location.href='./camera_suite.jsp'">Visita la camera</button>
                </div>
            </div>
        </div>
      </div>
    </div>
</div>

    <div class="row">
        <div class="col">
            <div class="card w-90">
                <div class="row no-gutters">
                    <div class="col-lg-4" style="background-image: url('../imgs/imgs-camere/superior.jpg')">
                    </div>
                    <div class="col-lg-8">
                        <h5 class="card-header">Camera Superior</h5>
                        <div class="card-body">
                            <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                            <button type="button" class="btn btn-darkgreen" onclick="location.href='camera_superior.jsp'">Visita la camera</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <div class="row py-5">
        <div class="col">
        <div class="card w-90">
            <div class="row no-gutters">
                <div class="col-lg-4" style="background-image: url('../imgs/imgs-camere/standard.jpg')">
                </div>
                <div class="col-lg-8">
                    <h5 class="card-header">Camera Standard</h5>
                    <div class="card-body">
                        <p class="card-text">This is a wider card with supporting text below as a natural lead-in to additional content. This content is a little bit longer.</p>
                        <button type="button" class="btn btn-darkgreen" onclick="location.href='camera_standard.jsp'">Visita la camera</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<div class="jumbotron py-1 footer" style="margin-bottom:0">
    <p>Contattaci</p>
    Lorem ipsum dolor sit, amet consectetur adipisicing elit. Eius nam impedit praesentium, molestiae hic libero, unde autem deserunt repudiandae minus nisi ut, dolorem natus saepe aliquid. Recusandae assumenda officia nulla.

    <p>Informazioni</p>
    Lorem ipsum dolor, sit amet consectetur adipisicing elit. Error harum nulla est qui nam dolor aliquam necessitatibus vitae quasi accusamus veritatis impedit sequi maiores dolore assumenda, perspiciatis, quas voluptate. Fuga?
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
