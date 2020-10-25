"use strict";
const socket = io.connect("http://localhost:8080", {transports: ['websocket']});

//messages received from server

socket.on('nextQuestion', function (question) {
    document.getElementById("displayQuestion").innerHTML = question;
});

socket.on('finished', function (finalAns) {
    document.getElementById("answerButtons").style.display = "block";
    document.getElementById("restart").style.display = "none";
    document.getElementById("displayQuestion").innerHTML = "Your accent is " + finalAns + "!";
});



//messages sent to server

function startDecisions() {
    console.log("yee");
    window.location = "questions.html";
    document.getElementById("restart").style.display = "block";
    socket.emit("startDecisions");
}

function questionAnswered(ans) {
    socket.emit("questionAnswered", ans);
}

function answeredYes() {
    socket.emit("questionAnswered", "yes");
}

function answeredNo() {
    socket.emit("questionAnswered", "no");
}

function restart() {
    document.getElementById("displayQuestion").innerHTML = "";
    window.location = "accent.html";
    socket.emit("restart");
}

function stop() {
    socket.emit("stop");
}
