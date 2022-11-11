$(document).ready(function () {
    searchAll();
});

function searchAll(){
    $.ajax({
        type: 'GET',
        url: '/api/search/courier',
        success: function (response){
            let str = "검색된 송장의 갯수 : " + response['courierList'].length;
            $('#courierCnt').val(str);
            let courierList = response['courierList'];
            let userList = response['userList'];
            let userTable = $("#user-table-body");
            let courierTable = $("#courier-table-body");
            userTable.empty();
            courierTable.empty();
            if(userList.length === 0){
                userTable.append("<tr><td colspan='3'>검색된 정보가 없습니다.</td></tr>");
            }else{
                for (let i = 0; i < userList.length; i++) {
                    let html = "<tr>" +
                        "<td><input type='checkbox' name='User-select'></td>" +
                        "<td>" + userList[i]['id'] + "</td>" +
                        "<td>" + userList[i]['username'] + "</td></tr>"
                    userTable.append(html);
                }
            }

            if(courierList.length === 0){
                courierTable.append("<tr><td colspan='8'>검색된 정보가 없습니다.</td></tr>")
            }else{
                for(let i=0; i<courierList.length; i++){

                    let id = courierList[i]['id'];
                    let route = courierList[i]['route'];
                    let subroute = courierList[i]['subRoute'];
                    let customer = courierList[i]['customer'];
                    let realstate = courierList[i]['state'];
                    let arrivalDate = courierList[i]['arrivalDate'];
                    let trueusername = courierList[i]['username'];

                    let html = "<tr>"+
                        "<td><input type='checkbox' name='Courier-select'></td>"+
                        `<td onclick="updateOne(${i})" align='middle'><input id='number-${i}' name='number-${i}' type='text' value='${id}'readonly/>`+"</td>"+
                        `<td align='middle'><input id='route-${i}' name='route-${i}' type='text' value='${route}'readonly/>`+"</td>"+
                        `<td align='middle'><input id='subroute-${i}' name='subroute-${i}' type='text' value='${subroute}'readonly/>`+"</td>"+
                        `<td align='middle'><input id='state-${i}' name='state-${i}' type='text' value='${realstate}'readonly/>`+"</td>"+
                        `<td align='middle'><input id='customer-${i}' name='customer-${i}' type='text' value='${customer}'readonly/>`+"</td>"+
                        `<td align='middle'><input id='arrivalDate-${i}' name='arrivalDate-${i}' type='text' value='${arrivalDate}'readonly/>`+"</td>"+
                        `<td align='middle'><input id='username-${i}' name='username-${i}' type='text' value='${trueusername}'readonly/>`+"</td></tr>"
                    courierTable.append(html);
                }
            }
        }
    })
}

function openDetail() {
    window.name = "parentForm";
    openWin = window.open("http://localhost:8080/detailSearch", "detailSearch",
        "width=500, height=700, resizable = no, scrollbars = yes");
}

function searchCourier(){
    let courierId = $('#courierId').val();
    if(courierId === "")   alert("송장 번호를 입력하세요");
    else{
        $.ajax({
            type: 'GET',
            url: `/api/search/courier/${courierId}`,
            success: function (response){
                let str = "검색된 송장의 갯수 : " + response['courierList'].length;
                $('#courierCnt').val(str);
                let courierList = response['courierList'];
                let userList = response['userList'];
                let userTable = $("#user-table-body");
                let courierTable = $("#courier-table-body");
                userTable.empty();
                courierTable.empty();
                if(userList.length === 0){
                    userTable.append("<tr><td colspan='3'>검색된 정보가 없습니다.</td></tr>");
                }else{
                    for (let i = 0; i < userList.length; i++) {
                        let html = "<tr>" +
                            "<td><input type='checkbox' name='User-select'></td>" +
                            "<td>" + userList[i]['id'] + "</td>" +
                            "<td>" + userList[i]['username'] + "</td></tr>"
                        userTable.append(html);
                    }
                }

                if(courierList.length === 0){
                    courierTable.append("<tr><td colspan='8'>검색된 정보가 없습니다.</td></tr>")
                }else{
                    for(let i=0; i<courierList.length; i++) {

                        let id = courierList[i]['id'];
                        let route = courierList[i]['route'];
                        let subroute = courierList[i]['subRoute'];
                        let customer = courierList[i]['customer'];
                        let realstate = courierList[i]['state'];
                        let arrivalDate = courierList[i]['arrivalDate'];
                        let trueusername = courierList[i]['username'];

                        let html = "<tr>" +
                            "<td><input type='checkbox' name='Courier-select'></td>" +
                            `<td onclick="updateOne(${i})" align='middle'><input id='number-${i}' name='number-${i}' type='text' value='${id}'readonly/>` + "</td>" +
                            `<td align='middle'><input id='route-${i}' name='route-${i}' type='text' value='${route}'readonly/>` + "</td>" +
                            `<td align='middle'><input id='subroute-${i}' name='subroute-${i}' type='text' value='${subroute}'readonly/>` + "</td>" +
                            `<td align='middle'><input id='state-${i}' name='state-${i}' type='text' value='${realstate}'readonly/>` + "</td>" +
                            `<td align='middle'><input id='customer-${i}' name='customer-${i}' type='text' value='${customer}'readonly/>` + "</td>" +
                            `<td align='middle'><input id='arrivalDate-${i}' name='arrivalDate-${i}' type='text' value='${arrivalDate}'readonly/>` + "</td>" +
                            `<td align='middle'><input id='username-${i}' name='username-${i}' type='text' value='${trueusername}'readonly/>` + "</td></tr>"
                        courierTable.append(html);
                    }
                }
                let input = document.getElementById('courierId');
                input.value = '';
            },
            error: function (response){
                /* 에러시 메시지 뽑는 방법 */
                alert(response['responseJSON']['message']);
            }
        })
    }
}

/* 다대다 할당 */
function updateCourier(){
    let usernames = [];
    let courierIds = [];
    let checkbox = $("input:checkbox[name=User-select]:checked");
    checkbox.each(function (i){
        let tr = checkbox.parent().parent().eq(i);
        let td = tr.children();
        usernames.push(td.eq(2).text());
    })
    if(usernames.length <1){
        alert("유저를 한명만 선태하세요");
        return;
    }
    checkbox = $("input:checkbox[name=Courier-select]:checked");
    checkbox.each(function (i){
        let tr = checkbox.parent().parent().eq(i);
        let td = tr.children().children();
        result = Number(td.eq(1).val());
        if(!isNaN(result))  courierIds.push(result);
    })

    let Params = '?usernames='+usernames+"&courierIds="+courierIds;
    $.ajax({
        type: 'PATCH',
        url: '/api/save/courier'+Params,
        contentType: 'application/json; charset=utf-8',
        success: function (response){
            alert(response['msg']);
            location.reload();
        },
        error: function (response){
            /* 에러시 메시지 뽑는 방법 */
            alert(response['responseJSON']['message']);
        }
    })
}


// user와 subroute 테이블 구성
function openSubRoute(){
    let saveCount = $('#saveCount').val();
    if(isNaN(saveCount) || saveCount==0){
        alert("갯수를 입력하세요");
        return;
    }
    let savetable = $("#subRoute-table-body");
    savetable.empty();
    const table = $('#subRoute-Save');
    for(let i=0; i < saveCount; i++){
        let html = "<tr>"+
            `<td align='middle'><input id='user-${i}' type='text'></td>`+
            `<td align='middle'><input id='subroute-${i}' type='text'></td>`+
            "</tr>"
        savetable.append(html);
    }
    table.show();
}

// 실질적인 subroute 요청보내기
function saveSubRoute(){
    let saveCount = $('#saveCount').val();
    let usernames = [];
    let subRoutes = [];
    for(let i=0; i<saveCount; i++){
        let user = $(`#user-${i}`).val();
        let sub = $(`#subroute-${i}`).val();
        if(user === '' || sub === '' || isNaN(sub)){
            alert("입력값에 이상이 있습니다.");
            return;
        }
        usernames.push(user);
        subRoutes.push(sub);
    }

    let Params = '?subRoutes='+subRoutes+"&usernames="+usernames;
    $.ajax({
        type: 'PATCH',
        url: '/api/save/subroutes/courier'+Params,
        contentType: 'application/json; charset=utf-8',
        success: function (response){
            alert(response['msg']);
            location.reload()
        },
        error: function (response){
            /* 에러시 메시지 뽑는 방법 */
            alert(response['responseJSON']['message']);
        }
    })
}

// 상세수정 창
function updateOne(select){
    $('#detailSave_send').attr('value', select);
    window.name = "parentForm";
    openWin = window.open("http://localhost:8080/detailSave", "detailSave",
        "width=900, height=500, resizable = no, scrollbars = yes");
}

// 전체선택 함수
function selectAll(selectAll) {
    const checkboxes = document.getElementsByName('Courier-select');
    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })
}

/** 태그값을 가져오는 여러 가지 방법
console.log("입력된 username : "+$('#username').val());
console.log("입력된 state : "+$('#state').val());
console.log("입력된 sorted : "+$('input[name=sorted]').val());
$('input:checkbox[name=subRoute]').each(function (index){
    if($(this).is(":checked")==true){
        console.log($(this).val());
    }
}) */

/** 테그 비우는 방법들
$('#subRoute-table-body').empty();
$('#subRoute-Save').hide();

let input = document.getElementById('saveCount');
input.value = '';
 */