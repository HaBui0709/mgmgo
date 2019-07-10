$(document).ready(function () {
    $('#see-more').click(function () {
        seeMore();
    })
});

function seeMore() {
    let listWrapper = $('.lazy-loading');
    let currentPage = +listWrapper.attr('current-page') + 1;
    $.ajax({
        type: 'GET',
        url: listWrapper.attr('link') + currentPage,
        success: function (res) {
            listWrapper.append(res);
            listWrapper.attr('current-page', currentPage);
            if (listWrapper.attr('current-page') == listWrapper.attr('size-of-pages')) {
                $('#see-more').addClass('d-none');
            }
        }
    })
}
