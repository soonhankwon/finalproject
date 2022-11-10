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
                userTable.append("<tr><td colspan='2'>검색된 정보가 없습니다.</td></tr>");
            }else{
                for(let i =0; i< userList.length; i++){
                    let html = "<tr>"+
                        "<td><input type='radio' name='User-Select'></td>"+
                        "<td>"+userList[i]['username']+"</td>"
                    userTable.append(html);
                }
            }

            if(courierList.length === 0){
                courierTable.append("<tr><td colspan='8'>검색된 정보가 없습니다.</td></tr>")
            }else{
                for(let i=0; i<courierList.length; i++){
                    let username = courierList[i]['username'];
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
                        "<td><input type='checkbox' class='Courier-select'></td>"+
                        "<td>"+courierList[i]['id']+"</td>"+
                        "<td>"+courierList[i]['route']+"</td>"+
                        "<td>"+courierList[i]['subRoute']+"</td>"+
                        "<td>"+state+"</td>"+
                        "<td>"+courierList[i]['customer']+"</td>"+
                        "<td>"+courierList[i]['arrivalDate']+"</td>"+
                        "<td>"+username+"</td>"
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
    let subRoute = new Array();
    $('input:checkbox[name=subRoute]').each(function (index){
        if($(this).is(":checked") == true){
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
                userTable.append("<tr><td colspan='2'>검색된 정보가 없습니다.</td></tr>");
            }else{
                for(let i =0; i< userList.length; i++){
                    let html = "<tr>"+
                        "<td><input type='radio' name='User-Select'></td>"+
                        "<td>"+userList[i]['username']+"</td>"
                    userTable.append(html);
                }
            }

            if(courierList.length === 0){
                courierTable.append("<tr><td colspan='8'>검색된 정보가 없습니다.</td></tr>")
            }else{
                for(let i=0; i<courierList.length; i++){
                    let username = courierList[i]['username'];
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
                        "<td><input type='checkbox' class='Courier-select'></td>"+
                        "<td>"+courierList[i]['id']+"</td>"+
                        "<td>"+courierList[i]['route']+"</td>"+
                        "<td>"+courierList[i]['subRoute']+"</td>"+
                        "<td>"+state+"</td>"+
                        "<td>"+courierList[i]['customer']+"</td>"+
                        "<td>"+courierList[i]['arrivalDate']+"</td>"+
                        "<td>"+username+"</td>"
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
                    userTable.append("<tr><td colspan='2'>검색된 정보가 없습니다.</td></tr>");
                }else{
                    for(let i =0; i< userList.length; i++){
                        let html = "<tr>"+
                            "<td><input type='radio' name='User-Select'></td>"+
                            "<td>"+userList[i]['username']+"</td>"
                        userTable.append(html);
                    }
                }

                if(courierList.length === 0){
                    courierTable.append("<tr><td colspan='8'>검색된 정보가 없습니다.</td></tr>")
                }else{
                    for(let i=0; i<courierList.length; i++){
                        let username = courierList[i]['username'];
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
                            "<td>"+courierList[i]['id']+"</td>"+
                            "<td>"+courierList[i]['route']+"</td>"+
                            "<td>"+courierList[i]['subRoute']+"</td>"+
                            "<td>"+state+"</td>"+
                            "<td>"+courierList[i]['customer']+"</td>"+
                            "<td>"+courierList[i]['arrivalDate']+"</td>"+
                            "<td>"+username+"</td>"
                        courierTable.append(html);
                    }
                }
            },
            error: function (response){
                /* 에러시 메시지 뽑는 방법 */
                alert(response['responseJSON']['message']);
            }
        })
    }
}
// class='User-select'
// class='Courier-select'
// http://localhost:8080/api/save/subroute/{{usernameId}}/courier
/* 1대 다중 할당 */
function updateCourier(){
    let userId =
    $('input:checkbox[class=User-select]').each(function (index){
        if($(this).is(":checked")===true){

        }
    }
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