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
    console.log("yee")
    socket.emit("startDecisions");
}

function questionAnswered(ans) {
    socket.emit("questionAnswered", ans);
}

function restart() {
    document.getElementById("displayQuestion").innerHTML = "";
    socket.emit("restart");
}

function stop() {
    socket.emit("stop");
}



document.getElementById("yes").onclick = function yes() {
    questionAnswered("yes");
};

document.getElementById("no").onclick = function no() {
    questionAnswered("no");
};

document.getElementById("restart").onclick = function restart() {
    restart();
};