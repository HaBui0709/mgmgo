$(document).ready(function () {
    $('#btn-toggle').on('click', function (event) {
        if ($('#btn-toggle').attr('aria-expanded') === 'true') {
            $('#icon-toggle').removeClass('fa-times').addClass('fa-bars');
        } else {
            $('#icon-toggle').removeClass('fa-bars').addClass('fa-times');
        }
    })

    if ($("#success-crud")) {
        setTimeout(function () {
            $("#success-crud").remove();
        }, 4000)
    }

    $('img').one('load', function () {
        $(this).removeClass('animated-background');
    }).each(function () {
        if (this.complete) {
            $(this).trigger('load');
        }
    })
})

function randomString() {
    return Math.random().toString(36).substring(7);
}

function createMessage(msg, isSuccess) {
    let message = $(document.createElement('div'));
    let random = randomString();
    message.attr('id', random);
    message.addClass("alert").addClass("alert-" + (isSuccess ? "success" : "danger")).addClass("message-center-info");
    message.html(msg);
    setTimeout(function () {
        message.remove();
    }, 4000)
    return message;
}

function addMessage(msg, isSuccess) {
    let msgParent = $("#message-center");

    if (msgParent.children().length >= 2) {
        msgParent.children().first().remove();
    }
    msgParent.append(createMessage(msg, isSuccess));
}
