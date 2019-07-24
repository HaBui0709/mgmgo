$(document).ready(function () {
    $('#btn-toggle').click(function () {
        $('#icon-toggle').toggleClass('fa-bars fa-times');
    })

    if ($("#message-success-crud")) {
        setTimeout(function () {
            $("#message-success-crud").remove();
        }, 4000)
    }
});
