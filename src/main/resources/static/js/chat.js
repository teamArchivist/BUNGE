var stompClient = null;

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic-test', function (frame) {
            parseMessage(frame);
            $(".bg-body-tertiary").scrollTop($(".bg-body-tertiary")[0].scrollHeight);
        });
    });
}

function parseMessage(frame) {
    let message = JSON.parse(frame.body);

    if (loginId == message.memberId) {
        return;
    }

    console.log("messageDt" + message.createDate);
    var dateArray = message.createDate.toString().split(",");
    console.log("dateArray=", dateArray);
    var dateTime = dateArray[0] + "-" + dateArray[1] + "-" + dateArray[2] + " " + dateArray[3] + ":" + dateArray[4];

    const chat = $(`
        <div class="d-flex mb-2">
            <div class="bubble">
                <p class="mb-1">${message.data}</p>
                <small class="opacity-50">${dateTime}</small>
            </div>
        </div>
    `)
    $(".bg-body-tertiary > .justify-content-end").append(chat);
}

function send() {
    let message = {};
    const href = $(".active a").prop("href");
    message.chatroomId = href.slice(href.lastIndexOf("/") + 1);
    message.memberId = loginId;
    message.type = "T";
    message.data = $(".chat-message-input").val();
    message.createDate = moment().format("yyyy-MM-DD HH:mm:ss");

    stompClient.send("/pub/hello", {}, JSON.stringify(message));
    console.log("보낸 메세지: " + JSON.stringify(message));
    const dateTime = moment().format("yyyy-MM-DD HH:mm");

    const chat = $(`
        <div class="d-flex mb-2 justify-content-end">
            <div class="bubble bubble-primary">
                <p class="mb-1">${message.data}</p>
                <small class="opacity-50">${dateTime}</small>
            </div>
        </div>
    `)
    $(".bg-body-tertiary > .justify-content-end").append(chat);
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

    $(".btn-send").click(function () {
        send();
        $(".bg-body-tertiary").scrollTop($(".bg-body-tertiary")[0].scrollHeight);
    });

    $(".chat-message-input").keydown(function (e) {
        handleEnterKey(e);
    });
});