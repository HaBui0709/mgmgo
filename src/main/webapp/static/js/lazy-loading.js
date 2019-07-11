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
        url: rebuildLink(listWrapper.attr('link'), currentPage),
        success: function (res) {
            listWrapper.append(res);
            listWrapper.attr('current-page', currentPage);
            if (listWrapper.attr('current-page') == listWrapper.attr('size-of-pages')) {
                $('#see-more').addClass('d-none');
            }
        }
    })
}

function rebuildLink(link, currentPage) {
    let index = link.indexOf("?");
    if (index < 0)
        return link + currentPage;
    return link.substring(0, index) + currentPage + link.substring(index, link.length);
}
