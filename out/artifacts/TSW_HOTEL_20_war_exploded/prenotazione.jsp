<%--
  Created by IntelliJ IDEA.
  User: alex
  Date: 19/05/20
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <title>Prenota ora</title>
</head>
<body style="padding-top: 5rem">
<!-- NAVBAR -->

<nav class="navbar navbar-expand-md bg-dark navbar-dark fixed-top">
    <div class="container">
        <img src="" alt="">
        <a class="navbar-brand" href="#">Hotel Marbel</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="collapsibleNavbar">
            <ul class="navbar-nav navbar-right">
                <li class="nav-item ">
                    <a class="nav-link active" href="/index.jsp">Home</a>
                </li>
                <li class="nav-item px-2">
                    <a class="nav-link" href="camere.jsp">Visita Camere</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Esplora ristoranti ed attività</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<!-- -->

<!-- FORM DI REGISTRAZIONE -->
<div class="container py-2">
    <h1>Prenotati ora</h1>
    <form method="post" action="${pageContext.request.contextPath}/doReservationControl">
        <h3>Dati personali</h3>
        <div class="form-group">
            <label for="inputCodiceFiscale">Codice Fiscale</label>
            <input type="text" class="form-control" id="inputCodiceFiscale" name="codicefiscale" placeholder="Inserisci qui il tuo codice fiscale">
        </div>
        <div class="form-group">
            <label for="inputNome">Nome</label>
            <input type="text" class="form-control" name="nome" id="inputNome">
        </div>
        <div class="form-group">
            <label for="inputCognome">Cognome</label>
            <input type="text" class="form-control" name="cognome" id="inputCognome">
        </div>
        <div class="form-group">
            <label for="inputData">Data di nascita</label>
            <input type="text" class="form-control" id="inputData" name="nascita" placeholder="yyyy-mm-dd">
        </div>
        <h3>Informazioni prenotazione</h3>
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <label class="input-group-text" for="inputCamera">Tipo di camera</label>
            </div>
            <select class="custom-select" id="inputCamera" name="tipocamera">
                <option value="suite">Suite</option>
                <option value="superior">Superior</option>
                <option value="standard">Standard</option>
            </select>
        </div>
        <div class="form-group">
            <label for="inputIn">Data di Check in</label>
            <input type="text" class="form-control" id="inputIn" name="check_in" placeholder="yyyy-mm-dd hh:mm:ss">
        </div>
        <div class="form-group">
            <label for="inputOut">Data di Check out</label>
            <input type="text" class="form-control" id="inputOut" name="check_out" placeholder="yyyy-mm-dd hh:mm:ss">
        </div>

        <h3>Informazioni di registrazione</h3>
        <div class="form-check">
            <input type="checkbox" class="form-check-input" id="registerCheck" name="register" value="toregister">
            <label class="form-check-label" for="registerCheck">Desidero registrarmi al sito</label>
        </div>
        <div class="form-group">
            <label for="inputEmail">Email</label>
            <input type="text" class="form-control" id="inputEmail" name="email">
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Password</label>
            <input type="password" class="form-control" id="exampleInputPassword1" name="password" placeholder="Password">
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>
