$(document).ready(function () {
    $('#favorite').click(function () {
        updateFavorite();
    })
});

function updateFavorite() {
    $.ajax({
        type: 'GET',
        data: {
            isFavorite: !checkIsFavorite()
        },
        url: '/api/favorite/activity/' + $("#activity-id").val(),
    }).done(function (data) {
        if (data.message) {
            addMessage(data.message, true);
        }
        changeBtnFavorite();
    }).fail(function (data) {
        if (data.responseJSON.message) {
            addMessage(data.responseJSON.message, false);
        }
    })
}

function checkIsFavorite() {
    let isFavorite = true;
    if ($('#favorite').hasClass('far'))
        isFavorite = false;
    return isFavorite;
}

function changeBtnFavorite() {
    if (checkIsFavorite()) {
        $('#favorite').removeClass('fa');
        $('#favorite').addClass('far').addClass('text-secondary');
    } else {
        $('#favorite').removeClass('far').removeClass('text-secondary');
        $('#favorite').addClass('fa');
    }
}
