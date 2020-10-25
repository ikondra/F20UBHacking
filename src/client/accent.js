"use strict";
const socket = io.connect("http://localhost:8080", {transports: ['websocket']});



//messages received from server

socket.on('nextQuestion', function (question) {
    document.getElementById("displayQuestion").innerHTML = question;
});

socket.on('finished', function (finalAns) {
    document.getElementById("displayFinalAnswer").innerHTML = finalAns;
});



//messages sent to server

function startDecisions() {
    socket.emit("startDecisions");
}

function questionAnswered(ans) {
    socket.emit("questionAnswered", ans);
}

function restart() {
    socket.emit("restart");
}

function stop() {
    socket.emit("stop");
}