$(document).ready(function () {
    setDate();
});

function setDate(){
    var select = $(opener.document).find("#detailSave_send").val();
    setting(select);
}

function setting(select){
    $('#number').attr('value', $(opener.document).find(`#number-${select}`).val());

    $('#registerdate').attr('value', $(opener.document).find(`#registerdate-${select}`).val());
    $('#arrivaldate').attr('value', $(opener.document).find(`#arrivaldate-${select}`).val());
    $('#area').attr('value', $(opener.document).find(`#area-${select}`).val());
    $('#route').attr('value', $(opener.document).find(`#route-${select}`).val());
    $('#subroute').attr('value', $(opener.document).find(`#subRoute-${select}`).val());

    $('#customer').attr('value', $(opener.document).find(`#customer-${select}`).val());
    $('#address').attr('value', $(opener.document).find(`#address-${select}`).val());
    $('#state').attr('value', $(opener.document).find(`#state-${select}`).val());
    $('#tempPerson').attr('value', $(opener.document).find(`#tempPerson-${select}`).val());
    $('#deliveryPerson').attr('value', $(opener.document).find(`#deliveryPerson-${select}`).val());
}

function setDone(){
    $.ajax({
        type: 'PATCH',
        url: '/api/admin/update/courier/' + $('#number').val(),
        contentType: 'application/json; charset=utf-8',
        dataType: "json",
        data: JSON.stringify({
            "state" : $('#state').val(),
            "arrivalDate" : $('#arrivaldate').val(),
            "deliveryPerson" : $('#deliveryPerson').val()
        }),
        success: function (response) {
            alert(response['msg']);
            opener.document.location.reload();
            self.close();
        },
        error: function (response) {
            /* 에러시 메시지 뽑는 방법 */
            alert(response['responseJSON']['message']);
        }
    })
}