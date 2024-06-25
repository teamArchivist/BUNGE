var stompClient = null;
let href = null;
let chatroomId = null;

function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.debug = null
    stompClient.connect({}, function () {
        stompClient.subscribe("/rooms/" + chatroomId, function (frame) {
            parseMessage(frame);
            $(".bg-body-tertiary").scrollTop($(".bg-body-tertiary")[0].scrollHeight);
        });
    });
}

function parseMessage(frame) {

    let message = JSON.parse(frame.body);
    message.createDate = message.createDate.toString().substring(0, 16);

    let chat;
    if (loginId == message.memberId) {
        chat = $(`
            <div class="d-flex mb-2 justify-content-end">
                <div class="bubble bubble-primary">
                    <p class="mb-1">${message.data}</p>
                    <small class="opacity-50">${message.createDate}</small>
                </div>
            </div>
        `);
    } else {
        chat = $(`
            <div class="d-flex mb-2">
                <div class="bubble">
                    <p class="mb-1">${message.data}</p>
                    <small class="opacity-50">${message.createDate}</small>
                </div>
            </div>
        `);
    }

    $(".bg-body-tertiary > .justify-content-end").append(chat);

    // 채팅 리스트에 메세지 업데이트
    $("a#list-nick").each(function() {
        const href = $(this).prop("href");
        const chatroomId = href.slice(href.lastIndexOf("/") + 1);
        if (chatroomId === message.chatroomId) {
            $(this).parent().next().children().text(message.data);
        }
    });
}

function send() {
    let message = {};
    message.chatroomId = chatroomId;
    message.type = "T";
    message.data = $(".chat-message-input").val();

    stompClient.send("/pub/message", {}, JSON.stringify(message));
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

    assignChatroom.call(this, "init");
    scrollDown();

    $(".btn-send").click(function () {
        send();
        $(".bg-body-tertiary").scrollTop($(".bg-body-tertiary")[0].scrollHeight);
    });

    $(".chat-message-input").keydown(function (e) {
        handleEnterKey(e);
    });

    // 채팅 리스트 검색
    $(".search").on("keyup", function () {
        const value = $(this).val().toLowerCase();
        $("a#list-nick").filter(function () {
            var hasContain = $(this).text().toLowerCase().indexOf(value) > -1
            if (hasContain) {
                $(this).closest(".list-group-item").removeClass("custom-display");
            } else {
                $(this).closest(".list-group-item").addClass("custom-display");
            }
        });
    });

    function assignChatroom(type) {
        if (type === "init") {
            href = $(".active a").prop("href");
        } else {
            href = this.toString();
        }
        chatroomId = href.slice(href.lastIndexOf("/") + 1);
        if (chatroomId == null || isNaN(chatroomId)) {
            return;
        }

        connect();
    }

    $(".list-group a").click(function () {
        assignChatroom.call(this);
        scrollDown();
    });
});