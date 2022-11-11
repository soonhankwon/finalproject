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
            courierTable.append("<tr><td colspan='8'>검색된 정보가 없습니다.</td></tr>")
            // if(courierList.length === 0){
            //     courierTable.append("<tr><td colspan='8'>검색된 정보가 없습니다.</td></tr>")
            // }else{
            //     for(let i=0; i<courierList.length; i++){
            //
            //         let id = courierList[i]['id'];
            //         let route = courierList[i]['route'];
            //         let subroute = courierList[i]['subRoute'];
            //         let customer = courierList[i]['customer'];
            //         let realstate = courierList[i]['state'];
            //         let arrivalDate = courierList[i]['arrivalDate'];
            //         let trueusername = courierList[i]['username'];
            //
            //         let html = "<tr>"+
            //             "<td><input type='checkbox' name='Courier-select'></td>"+
            //             `<td onclick="updateOne(${i})" align='middle'><input id='number-${i}' name='number-${i}' type='text' value='${id}'readonly/>`+"</td>"+
            //             `<td align='middle'><input id='route-${i}' name='route-${i}' type='text' value='${route}'readonly/>`+"</td>"+
            //             `<td align='middle'><input id='subroute-${i}' name='subroute-${i}' type='text' value='${subroute}'readonly/>`+"</td>"+
            //             `<td align='middle'><input id='state-${i}' name='state-${i}' type='text' value='${realstate}'readonly/>`+"</td>"+
            //             `<td align='middle'><input id='customer-${i}' name='customer-${i}' type='text' value='${customer}'readonly/>`+"</td>"+
            //             `<td align='middle'><input id='arrivalDate-${i}' name='arrivalDate-${i}' type='text' value='${arrivalDate}'readonly/>`+"</td>"+
            //             `<td align='middle'><input id='username-${i}' name='username-${i}' type='text' value='${trueusername}'readonly/>`+"</td></tr>"
            //         courierTable.append(html);
            //     }
            // }
        }
    })
}

function openDetail() {
    const width = 500;
    const height = 700

    openWin = window.open("http://localhost:8080/detailSearch", "detailSearch", stroption(width, height));
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
    alert("입력된 값의 유효성을 검사합니다.");
    let usernames = [];
    let usernamelenth = 0;
    let courierIds = [];
    let checkbox = $("input:checkbox[name=User-select]:checked");
    checkbox.each(function (i){
        let tr = checkbox.parent().parent().eq(i);
        let td = tr.children();
        usernames.push(td.eq(2).text());
        usernamelenth += td.eq(2).text().length;
    })
    if(usernames.length <1){
        alert("유저를 한명만 선택하세요");
        return;
    }
    checkbox = $("input:checkbox[name=Courier-select]:checked");
    checkbox.each(function (i){
        let tr = checkbox.parent().parent().eq(i);
        let td = tr.children().children();
        result = Number(td.eq(1).val());
        if(!isNaN(result))  courierIds.push(result);
    })

    if(usernames.length !== 1 && usernames.length !== courierIds.length){
        alert("user 다중 선택시 운송장 갯수와 같아야 합니다.");
        return;
    } else if(usernames.length===1 && usernames[0].length + courierIds.length > 2080){
        alert("courier의 갯수가 너무 많습니다.");
        return;
    } else if(usernamelenth + courierIds.length > 2080){
        alert("username의 문자 총 길이가 너무 많습니다.");
        return;
    }
    if(!confirm("작업을 수행하시겟습니까?")){
        alert("작업 취소");
        return;
    }
    let Params = '?usernames='+usernames+"&courierIds="+courierIds;
    $.ajax({
        type: 'PATCH',
        url: '/api/save/courier'+Params,
        contentType: 'application/json; charset=utf-8',
        success: function (response){
            alert(response['msg']);
            location.reload();},
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
    let usernamelength = 0;
    let subRoutes = [];
    alert("입력 값에 대한 유효성을 검사합니다.");
    for(let i=0; i<saveCount; i++){
        let user = $(`#user-${i}`).val();
        let sub = $(`#subroute-${i}`).val();
        if(user === '' || sub === '' || isNaN(sub)){
            alert("입력값에 이상이 있습니다.");
            return;
        }
        usernamelength += user.length;
        usernames.push(user);
        subRoutes.push(sub);
    }
    if(usernamelength + saveCount > 2080){
        alert("입력 문자가 너무 많습니다. 총 입력 문자 길이: " + usernamelength+ saveCount);
        return;
    }

    if(!confirm("작업을 수행하시겟습니까?")){
        alert("작업 취소");
        return;
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

    const width = 900;
    const height = 300;

    window.name = "parentForm";
    openWin = window.open("http://localhost:8080/detailSave", "detailSave", stroption(width, height));
}

// 전체선택 함수
function selectAll(selectAll) {
    const checkboxes = document.getElementsByName('Courier-select');
    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })
}

function stroption(width, height){
    let curX = window.screenLeft;
    let curY = window.screenTop;
    let curWidth = document.body.clientWidth;
    let curHeight = document.body.clientHeight;

    let nLeft = curX + (curWidth / 2) - (width / 2);
    let nTop = curY + (curHeight / 2) - (height / 2);

    let strOption = "";
    strOption += "left=" + nLeft + "px,";
    strOption += "top=" + nTop + "px,";
    strOption += "width=" + width + "px,";
    strOption += "height=" + height + "px,";
    strOption += "resizable=yes,status=yes";
    return strOption;
}