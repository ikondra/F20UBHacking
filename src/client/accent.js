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



document.getElementById("start").onclick = function start() {
    startDecisions();
    document.getElementById("content").innerHTML = "<h2 id='nextQuestion'></h2> <button id='yes'>Yes</button> <button id='no'>No</button> <button id='restart'>";
};

document.getElementById("yes").onclick = function yes() {
    questionAnswered("yes");
};

document.getElementById("no").onclick = function no() {
    questionAnswered("no");
};

document.getElementById("restart").onclick = function restart() {
    restart();
};

document.getElementById("stop").onclick = function stop() {
    stop();
    document.getElementById("content").innerHTML = "";
};