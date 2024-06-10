var stompClient = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic-test', function (frame) {
            console.log("받은 메세지" + frame)
        });
    });
}

function send() {
    stompClient.send("/pub/hello", {}, $(".chat-message-input").val());
    console.log("보낸 메세지: " + $(".chat-message-input").val());
}

function handleEnterKey(e) {
    if (e.shiftKey) {
        return;
    }
    if (e.key === "Enter") {
        e.preventDefault();
        $('.btn-send').click();
        $(".chat-message-input").val("");
    }
}

$(function () {

    connect();

    $(".btn-send").click(function() {
        send();
    });

    $(".chat-message-input").keydown(function (e) {
        handleEnterKey(e);
    });
});