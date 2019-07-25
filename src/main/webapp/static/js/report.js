$(document).ready(function () {
    $("#report-button").click(getReport);
    $("#report-confirm").click(postReport);
});

function getReport() {
    $.get(
        '/report/activity/' + $("#activity-id-report").val()
    ).done(function (data) {
        if (data.status !== "SUCCESS") {
            setErrorMessageReport(data.message)
            $("#report-confirm").attr("disabled", "disabled");
        }
    }).fail(function (data) {
        setErrorMessageReport(data.message);
        $("#report-confirm").attr("disabled", "disabled");
    });
}

function postReport() {
    $.ajax({
        type: "POST",
        url: '/report/activity/' + $("#activity-id-report").val(),
        async: true,
        beforeSend: function (xhr) {
            xhr.setRequestHeader($("meta[name=csrf-header]").attr("content"), $("meta[name=csrf-token]").attr("content"));
        }
    }).done(function (data) {
        if (data.status === "SUCCESS") {
            hideReportModal();
            addMessage(data.message, true);
        } else {
            setErrorMessageReport(data.message)
            $("#report-confirm").attr("disabled", "disabled");
        }
    }).fail(function (data) {
        console.log(data)
        setErrorMessageReport(data.message)
        $("#report-confirm").attr("disabled", "disabled");
    });
}

function hideReportModal() {
    $('#reportModal').modal('hide');
}

function setErrorMessageReport(msg) {
    $("#report-return").removeClass("alert-danger").removeClass("alert-success").removeClass("d-none");
    $("#report-return").addClass("alert-danger");
    $("#report-return").text(msg);
}
