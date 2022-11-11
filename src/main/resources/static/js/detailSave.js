$(document).ready(function () {
    setDate();
});

function setDate(){
    var select = $(opener.document).find("#detailSave_send").val();
    setting(select);
}

function setting(select){
    $('#number').attr('value', $(opener.document).find(`#number-${select}`).val());
    $('#route').attr('value', $(opener.document).find(`#route-${select}`).val());
    $('#customer').attr('value', $(opener.document).find(`#customer-${select}`).val());
    $('#subroute').attr('value', $(opener.document).find(`#subroute-${select}`).val());
    $('#state').attr('value', $(opener.document).find(`#state-${select}`).val());
    $('#username').attr('value', $(opener.document).find(`#username-${select}`).val());
    $('#arrivaldate').attr('value', $(opener.document).find(`#arrivalDate-${select}`).val());
}

function setDone(){
    $.ajax({
        type: 'PATCH',
        url: '/api/save/' + $('#number').val(),
        contentType: 'application/json; charset=utf-8',
        dataType: "json",
        data: JSON.stringify({
            "state" : $('#state').val(),
            "arrivalDate" : $('#arrivaldate').val(),
            "username" : $('#username').val()
        }),
        success: function (response) {
            alert(response['msg']);
            opener.document.location.reload();
            // setTimeout(() => self.close(), 2000);
            self.close();
        },
        error: function (response) {
            /* 에러시 메시지 뽑는 방법 */
            alert(response['responseJSON']['message']);
        }
    })
}