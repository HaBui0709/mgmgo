$(document).ready(function () {
    $('#btn-toggle').click(function () {
        $('#icon-toggle').toggleClass('fa-bars fa-times');
    })

    if ($("#message-success")) {
        setTimeout(function () {
            $("#message-success").remove();
        }, 4000)
    }
})
