$(document).ready(function () {
    setDate();
});

var length = 0;

function setDate(){
    let select = $(opener.document).find("#setDelivery_send").val();

    $('#route').attr('value', select + "지역배송담당자 배정");

    $.ajax({
        type: 'GET',
        url: `/api/admin/show/${select}`,
        success: function (response) {
            setting(response);
            length = response.length;
        }
    });
}

function setting(response) {
    let deliveryTable = $("#delivery-table-body");
    deliveryTable.empty();

    response.forEach((value, index, array) => {
        let html = "<tr>" +
            `<td><input class="default-text" id='id' name='id' type="text" value="${value['id']}" style="text-align: center" readOnly>`+"</td>"+
            `<td><input class="default-text" id="subRoute" name='subRoute' type="text" value="${value['subRoute']}" style="text-align: center" readOnly>`+"</td>"+
            `<td><input class="default-text" id="zipCode" name='zipCode' type="text" value="${value['zipCode']}" style="text-align: center" readOnly>`+"</td>"+
            `<td><input class="default-text" id="difficulty" name='difficulty' type="text" value="${value['difficulty']}" style="text-align: center" readOnly>`+"</td>"+
            `<td><input class="default-text" id="count" name='count' type="text" value="${value['count']}" style="text-align: center" readOnly>`+"</td>"+
            `<td><input class="default-text" id="username" name='username' type="text" value="" style="text-align: center">`+"</td></tr>"
        deliveryTable.append(html);
    })
}

function setDone() {
    let ids = [];
    let username = [];

    for (let i = 0; i < length; i++) {
        ids.push($("input[name=id]").eq(i).val());
        username.push($("input[name=username]").eq(i).val());
    }

    $.ajax({
        type: 'POST',
        url: '/api/admin/update/delivery',
        contentType: 'application/json; charset=utf-8',
        dataType: "text",
        // dataType: "json",
        data: JSON.stringify({
            "ids": ids,
            "username": username
        }),
        success: function (response) {
            alert(response);
            opener.document.location.reload();
            self.close();
        },
        error: function (response) {
            /* 에러시 메시지 뽑는 방법 */
            alert(response['responseJSON']['message']);
        }
    })
}

    function autoSet() {
        let userList = [];
        let capacity = [];
        let subRouteCount = [];
        let difficulty = [];

        for (let i = 0; i < length; i++) {
            subRouteCount.push($("input[name=count]").eq(i).val());
            difficulty.push($("input[name=difficulty]").eq(i).val());
        }

        let userlen = $(opener.document).find("#user_length").val();

        for(let i= 0; i< userlen; i++){
            let user = $(opener.document).find(`#username-${i}`).val();
            let cap = Number($(opener.document).find(`#capacity-${i}`).val());
            let com = Number($(opener.document).find(`#completion-${i}`).val());
            let delay = Number($(opener.document).find(`#delay-${i}`).val());
            let ship = Number($(opener.document).find(`#shipping-${i}`).val());

            let cal = cap-(com+delay+ship);
            if(cal > 10){
                userList.push(user);
                capacity.push(cal);
            }
        }

        $.ajax({
            type: 'POST',
            url: '/api/admin/update/delivery/auto',
            contentType: 'application/json; charset=utf-8',
            dataType: "json",
            data: JSON.stringify({
                "userList" : userList,
                "capacity": capacity,
                "subRouteCount" : subRouteCount,
                "difficulty": difficulty
            }),
            success: function (response) {
                console.log(response)
                for (let i = 0; i < length; i++) {
                    $("input[name=username]").eq(i).attr('value', response[i]);
                }
            },
            error: function (response) {
                /* 에러시 메시지 뽑는 방법 */
                alert(response['responseJSON']['message']);
            }
        })
}