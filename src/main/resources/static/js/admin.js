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
                for(let i =0; i< userList.length; i++){
                    let html = "<tr>"+
                        "<td><input type='checkbox' name='User-select'</td>"+
                        "<td>"+userList[i]['id']+"</td>"+
                        "<td>"+userList[i]['username']+"</td></tr>"
                    userTable.append(html);
                }
            }

            if(courierList.length === 0){
                courierTable.append("<tr><td colspan='8'>검색된 정보가 없습니다.</td></tr>")
            }else{
                for(let i=0; i<courierList.length; i++){
                    let username = courierList[i]['username'];
                    let id = courierList[i]['id'];
                    let nowname = $("#title-TMS span").text();
                    let state;
                    if(username === nowname){
                        state = "배송준비 중";
                        username = "할당 전";
                    }else{
                        if(courierList[i]['state'])   state = "배송 완료";
                        else    state="배송 중";
                    }
                    let html = "<tr>"+
                        "<td><input type='checkbox' name='Courier-select'></td>"+
                        `<td onclick="updateOne(${i})" align='middle'><input name='Select-row-${i}' type='text' value='${id}'readonly/>`+"</td>"+
                        "<td>"+courierList[i]['route']+"</td>"+
                        "<td>"+courierList[i]['subRoute']+"</td>"+
                        "<td>"+state+"</td>"+
                        "<td>"+courierList[i]['customer']+"</td>"+
                        "<td>"+courierList[i]['arrivalDate']+"</td>"+
                        "<td>"+username+"</td></tr>"
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

function setDone(){
    let Params = '?username='+$('#username').val();
    let subRoute = [];
    $('input:checkbox[name=subRoute]').each(function (index){
        if($(this).is(":checked") === true){
            console.log(typeof($(this).val()));
            subRoute.push($(this).val());
        }
    })
    Params += `&subRoute=`+subRoute;
    Params += `&state=`+$('#state').val();
    Params += `&arri=`+$('input[name=sorted]').val();
    $.ajax({
        type: 'GET',
        url: `/api/search/courier/sorted` + Params,
        success: function (response) {
            let userTable = $(opener.document).find("#user-table-body");
            let courierTable = $(opener.document).find("#courier-table-body");
            userTable.empty();
            courierTable.empty();
            let str = "검색된 송장의 갯수 : " + response['courierList'].length;
            $(opener.document).find('#courierCnt').val(str);
            let courierList = response['courierList'];
            let userList = response['userList'];
            if(userList.length === 0){
                userTable.append("<tr><td colspan='3'>검색된 정보가 없습니다.</td></tr>");
            }else{
                for(let i =0; i< userList.length; i++){
                    let html = "<tr>"+
                        "<td><input type='checkbox' name='User-select'></td>"+
                        "<td>"+userList[i]['id']+"</td>"+
                        "<td>"+userList[i]['username']+"</td></tr>"
                    userTable.append(html);
                }
            }

            if(courierList.length === 0){
                courierTable.append("<tr><td colspan='8'>검색된 정보가 없습니다.</td></tr>")
            }else{
                for(let i=0; i<courierList.length; i++){
                    let username = courierList[i]['username'];
                    let id = courierList[i]['id'];
                    let nowname = $(opener.document).find("#title-TMS span").text();
                    let state;
                    if(username === nowname){
                        state = "배송준비 중";
                        username = "할당 전";
                    }else{
                        if(courierList[i]['state'])   state = "배송 완료";
                        else    state="배송 중";
                    }
                    let html = "<tr>"+
                        "<td><input type='checkbox' name='Courier-select'></td>"+
                        `<td onclick="updateOne(${i})" align='middle'><input name='Select-row-${i}' type='text' value='${id}'readonly/>`+"</td>"+
                        "<td>"+courierList[i]['route']+"</td>"+
                        "<td>"+courierList[i]['subRoute']+"</td>"+
                        "<td>"+state+"</td>"+
                        "<td>"+courierList[i]['customer']+"</td>"+
                        "<td>"+courierList[i]['arrivalDate']+"</td>"+
                        "<td>"+username+"</td></tr>"
                    courierTable.append(html);
                }
            }
        }
    })
    setTimeout(() => window.close(), 2000);
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
                    for(let i =0; i< userList.length; i++){
                        let html = "<tr>"+
                            "<td><input type='checkbox' name='User-select'></td>"+
                            "<td>"+userList[i]['id']+"</td>"+
                            "<td>"+userList[i]['username']+"</td></tr>"
                        userTable.append(html);
                    }
                }

                if(courierList.length === 0){
                    courierTable.append("<tr><td colspan='8'>검색된 정보가 없습니다.</td></tr>")
                }else{
                    for(let i=0; i<courierList.length; i++){
                        let username = courierList[i]['username'];
                        let id = courierList[i]['id'];
                        let nowname = $("#title-TMS span").text();
                        let state;
                        if(username === nowname){
                            state = "배송준비 중";
                            username = "할당 전";
                        }else{
                            if(courierList[i]['state'])   state = "배송 완료";
                            else    state="배송 중";
                        }
                        let html = "<tr>"+
                            "<td><input type='checkbox' name='Courier-select'></td>"+
                            `<td onclick="updateOne(${i})" align='middle'><input name='Select-row-${i}' type='text' value='${id}'readonly/>`+"</td>"+
                            "<td>"+courierList[i]['route']+"</td>"+
                            "<td>"+courierList[i]['subRoute']+"</td>"+
                            "<td>"+state+"</td>"+
                            "<td>"+courierList[i]['customer']+"</td>"+
                            "<td>"+courierList[i]['arrivalDate']+"</td>"+
                            "<td>"+username+"</td></tr>"
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
        usernames.push(td.eq(1).text());
    })
    checkbox = $("input:checkbox[name=Courier-select]:checked");
    checkbox.each(function (i){
        let tr = checkbox.parent().parent().eq(i);
        let td = tr.children();
        courierIds.push(td.eq(1).text());
    })

    let Params = '?usernameIds='+usernames+"&courierIds="+courierIds;
    $.ajax({
        type: 'PATCH',
        url: '/api/save/subroute/courier'+Params,
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

// 오늘까지
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

function saveSubRoute(){
    let saveCount = $('#saveCount').val();
    let usernames = [];
    let courierIds = [];
    for(let i=0; i<saveCount; i++){
        usernames.push($(`#user-${i}`).val());
        courierIds.push($(`#subroute-${i}`).val());
    }

    $('#subRoute-table-body').empty();
    $('#subRoute-Save').hide();

    let input = document.getElementById('saveCount');
    input.value = '';

    let Params = '?'
    $.ajax()
}

// 내일하자
function updateOne(select){
    $('#detailSave_send').attr('value', select);
    console.log($('#detailSave_send').val());
    window.name = "parentForm";
    openWin = window.open("http://localhost:8080/detailSave", "detailSave",
        "width=500, height=700, resizable = no, scrollbars = yes");
}

// 전체선택 함수
function selectAll(selectAll){
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