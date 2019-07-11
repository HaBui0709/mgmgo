$(document).ready(function () {
    $("#upload-image").change(function () {
        readImage(this);
    });
    $('#btn-upload').click(function(){
        $("#image-error").addClass("d-none");
    });
});

function readImage(input) {
    if (input.files && input.files[0]) {
        if (input.files[0].size > 10 * Math.pow(2, 20)) {
            $("#image-error").removeClass("d-none");
            $("#image-error").text("Your file size is over 10MB");
            $('#upload-image').val("");
        } else {
            let reader = new FileReader();
            reader.onload = function (e) {
                $('#image').attr('src', e.target.result);
            };
            reader.readAsDataURL(input.files[0]);
            $("#image-error").addClass("d-none");
        }
    }
}

function setMessage(msg, isSuccess) {
    $("#image-error").removeClass("d-none");
    $("#image-error").text(msg);
}

function getRequestImageURL() {
    return '/api/image/activity/' + getActivityId();
}

function getActivityId() {
    return $("#activity-id").val();
}

$(function () {
    $("form#upload-image-form").submit(function (event) {
        event.preventDefault();
        let url = getRequestImageURL();
        let image_file = $('#upload-image').get(0).files[0];
        let formData = new FormData();
        formData.append("image_file", image_file);

        $.ajax({
            type: 'POST',
            url: url,
            data: formData,
            contentType: false,
            processData: false,
            beforeSend: function (xhr) {
                xhr.setRequestHeader($("meta[name=csrf-header]").attr("content"), $("meta[name=csrf-token]").attr("content"));
            }
        }).done(function (data) {
            $("#exampleModalCenter").modal("hide");
            $("#image-on-detail-page").attr('src', `/${data.imageFromRestAPI}`);

        }).fail(function () {
            setMessage("Something went wrong! Please try again.", false);
        })
        return false;
    });

});