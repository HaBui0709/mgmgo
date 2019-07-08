$(document).ready(function () {
    $('#btn-toggle').click(function () {
        $('#icon-toggle').toggleClass('fa-bars fa-times');
    })
})

setInterval(function () {
    document.getElementById("message-success").remove();
}, 4000)