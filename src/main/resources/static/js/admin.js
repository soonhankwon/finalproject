$(document).ready(function () {
    getRouteCount()
    getUser();
});

// var BaseUrl = "http://localhost:8080/";
var BaseUrl = "http://tacbaetics.shop/";
var default_person = "GUROADMIN";

function getUser(){
    $.ajax({
        type: 'GET',
        url: '/api/admin/main/user',
        success: function (response){
            let courierTable = $("#courier-table-body");
            courierTable.empty();
            courierTable.append("<tr><td colspan='12'>검색된 정보가 없습니다.</td></tr>")

            let userTable = $("#user-table-body");
            let userList = response['userlist'];
            let tempCount = response['tempAssignment'];
            let directCount = response['directAssignment'];

            userTable.empty();
            usertable(userList, tempCount, directCount, userTable);
        }
    })
}

function getRouteCount(){
    $.ajax({
        type: 'GET',
        url: '/api/admin/main/route',
        success: function (response){
            let courierTable = $("#courier-table-body");
            routecount(response)
        }
    })
}

// userTable 만들기
function usertable(userList, tempCount, directCount, userTable) {
    let userinfo = [];

    userList.forEach((user) => {
        userinfo.push(tempCount.filter((item) => {
            return item.username === user
        }))
        userinfo.push(directCount.filter((item) => {
            return item.username === user
        }))
    })

    for (let i = 0; i < userinfo.length / 2; i++) {
        let idx = i * 2;

        let array = userinfo[idx].filter((item) => {
            return item.state === "배송중"
        });
        let shipping = zeroFilter(array)
        array = userinfo[idx + 1].filter((item) => {
            return item.state === "배송중"
        });
        shipping += zeroFilter(array)

        // =======================================
        array = userinfo[idx].filter((item) => {
            return item.state === "배송지연"
        });
        let delay = zeroFilter(array)
        array = userinfo[idx + 1].filter((item) => {
            return item.state === "배송지연"
        })
        delay += zeroFilter(array);

        array = userinfo[idx + 1].filter((item) => {
            return item.state === "배송완료"
        })
        let completion = zeroFilter(array)

        let html = "<tr>" +
            "<td><input type='checkbox' name='User-select'></td>" +
            "<td>" + userList[i] + "</td>" +
            "<td>" + shipping + "</td>"+
            "<td>" + delay + "</td>"+
            "<td>" + completion + "</td></tr>"
        userTable.append(html);
    }
}

function zeroFilter(array){
    return array.length !== 0 ? array[0]['count'] : 0;
}

// Route Div 채우기
function routecount(routeCount) {
    let routelist = [];

    for (let i = 0; i < 20; i++) {
        let route = String.fromCodePoint(i + 65);
        routelist.push(
            routeCount.filter((item) => {
                return item.route === route
            })
        )
    }
    let today = getToday();

    routelist.forEach((value, index, array) => {
        let success = 0;

        value.filter((item) => {
            if (item.state === today) success = item.count;
        })

        let total = value.map(item => item.count)
            .reduce((prev, curr) => prev + curr, 0);

        let html = success + "/" + total + " (" + Math.floor(success / total * 100) + "%)"
        $(`#route${index + 1}`).val(html);
    })
}

function getToday(){
    let date = new Date();
    let year = date.getFullYear();
    let month = ("0" + (1 + date.getMonth())).slice(-2);
    let day = ("0" + date.getDate()).slice(-2);

    return year + "-" + month + "-" + day;
}

// 임시할당 페이지 열기
function openDelevery(route) {
    $('#setDelivery_send').attr('value', route);

    const width = 900;
    const height = 700;

    window.name = "parentForm";
    openWin = window.open(BaseUrl + "delivery", "setDelivery", stroption(width, height));
}

// 송장번호로 검색
function searchCourier() {
    let courierId = $('#courierId').val();
    if (courierId === "") alert("송장 번호를 입력하세요");
    else {
        $.ajax({
            type: 'GET',
            url: `/api/admin/search/courier?` + `courierId=${courierId}`,
            success: function (response) {
                let length = response.length;
                courierTotal(length);

                let courierTable = $("#courier-table-body");
                courierTable.empty();

                length === 0 ? courierTable.append("<tr><td colspan='12'>검색된 정보가 없습니다.</td></tr>")
                    : setCourierTable(courierTable, response);

                let input = document.getElementById('courierId');
                input.value = '';
            },
            error: function (response) {
                /* 에러시 메시지 뽑는 방법 */
                alert(response['responseJSON']['message']);
            }
        })
    }
}

// 택배 테이블 채우기
function setCourierTable(courierTable, response) {
    response.forEach((value, index, array) => {
        let html = "<tr>" +
            "<td><input type='checkbox' name='Courier-select'></td>" +
            `<td onclick="updateOne(${index})" align='middle'><input class='courier-input' id='number-${index}' name='number-${index}' type='text' value="${value['id']}" readonly/>` + "</td>" +
            `<td align='middle'><input class='courier-input' id='registerdate-${index}' name='registerdate-${index}' type='text' value="${value['registerDate']}" readonly/>` + "</td>" +
            `<td align='middle'><input class='courier-input' id='arrivaldate-${index}' name='arrivaldate-${index}' type='text' value="${value['arrivalDate']}" readonly/>` + "</td>" +
            `<td align='middle'><input class='courier-input' id='address-${index}' name='address-${index}' type='text' value="${value['address']}" readonly/>` + "</td>" +
            `<td align='middle'><input class='courier-input' id='area-${index}' name='area-${index}' type='text' value="${value['area']}" readonly/>` + "</td>" +
            `<td align='middle'><input class='courier-input' id='route-${index}' name='route-${index}' type='text' value="${value['route']}" readonly/>` + "</td>" +
            `<td align='middle'><input class='courier-input' id='subRoute-${index}' name='subRoute-${index}' type='text' value="${value['subRoute']}" readonly/>` + "</td>" +
            `<td align='middle'><input class='courier-input' id='customer-${index}' name='customer-${index}' type='text' value="${value['customer']}" readonly/>` + "</td>" +
            `<td align='middle'><input class='courier-input' id='state-${index}' name='state-${index}' type='text' value="${value['state']}" readonly/>` + "</td>" +
            `<td align='middle'><input class='courier-input' id='tempPerson-${index}' name='tempPerson-${index}' type='text' value="${value['tempPerson']}" readonly/>` + "</td>";

        let person = (value['deliveryPerson'] === default_person) ? "대상 없음" : value['deliveryPerson'];

        html += `<td align='middle'><input class='courier-input' id='deliveryPerson-${index}' name='deliveryPerson-${index}' type='text' value='${person}'readonly/>` + "</td></tr>"
        courierTable.append(html);
    })
}

// 배송지연 할당하기
function setState() {
    if (!confirm("선택된 courier가 1000개 이상시 많은 시간이 소요됩니다." +
        "\n그래도 진행하시겟습니까?")) {
        alert("작업이 취소되었습니다.");
        throw 'finish';
    }

    let courierIds = [];
    let checkbox = $("input:checkbox[name=Courier-select]:checked");

    checkbox.each(function (i) {
        let tr = checkbox.parent().parent().eq(i);
        let td = tr.children().children();
        let result = Number(td.eq(1).val());
        if (!isNaN(result)) courierIds.push(result);
    })

    if (courierIds.length === 0) {
        alert("courier가 선택되지 않았습니다.");
        return;
    }

    $.ajax({
        type: 'PATCH',
        url: '/api/admin/update/state',
        contentType: 'application/json; charset=utf-8',
        dataType: "text",
        data: JSON.stringify({
            "couriers": courierIds
        }),
        success: function (response) {
            alert(response);
            location.reload();
        },
        error: function (response) {
            /* 에러시 메시지 뽑는 방법 */
            alert(response['responseJSON']['message']);
        }
    })
}

// 직접 할당
function updateCourier() {
    let deliveryPerson;
    let courierIds = [];

    let count = 0;

    let checkbox = $("input:checkbox[name=User-select]:checked");
    checkbox.each(function (i) {
        let tr = checkbox.parent().parent().eq(i);
        let td = tr.children();
        deliveryPerson = td.eq(1).text();
        count++
    })

    if (count > 1) {
        alert("유저를 한명만 선택하세요");
        return;
    }

    if (!confirm("선택된 courier가 1000개 이상시 많은 시간이 소요됩니다." +
        "\n그래도 진행하시겟습니까?")) {
        alert("작업이 취소되었습니다.");
        throw 'finish';
    }

    count = 0;

    checkbox = $("input:checkbox[name=Courier-select]:checked");
    checkbox.each(function (i) {
        let tr = checkbox.parent().parent().eq(i);
        let td = tr.children().children();
        let result = Number(td.eq(1).val());
        if (!isNaN(result)) courierIds.push(result);
        count++;
    })

    if (count === 0) {
        alert("courier가 선택되지 않았습니다.");
        return;
    }

    $.ajax({
        type: 'PATCH',
        url: '/api/admin/update/DeliverPerson',
        contentType: 'application/json; charset=utf-8',
        dataType: "text",
        data: JSON.stringify({
            "username": deliveryPerson,
            "couriers": courierIds
        }),
        success: function (response) {
            alert(response);
            location.reload();
        },
        error: function (response) {
            /* 에러시 메시지 뽑는 방법 */
            alert(response['responseJSON']['message']);
        }
    })
}

function searchDetail() {
    if (!confirm("아무것도 입력되지 않았다면 화면구성에 많은 시간이 듭니다. 진행하시겟습니까?")) {
        alert("작업이 취소되었습니다.");
        throw 'finish';
    }

    alert("유효성 검증을 시작합니다.");

    let username = $('#username').val();
    let route = $('#route').val();
    let subRoute = $('#subroute').val();
    let state = $('#state').val();
    let currentDay = $('#date').val();

    let radio = document.getElementsByName('option');
    let option;

    radio.forEach((node) => {
        if (node.checked) {
            option = node.value;
        }
    })

    if (username.indexOf(',') !== -1) {
        alert("username은 1개만 입력하세요");
        throw 'finish';
    }
    if (route.indexOf(',') !== -1) {
        alert("route는 1개만 입력하세요");
        throw 'finish';
    }
    if (isNaN(currentDay)) {
        if (currentDay) alert("currentDay는 숫자만 입력하세여");
        throw 'finish';
    }

    currentDay = Number(currentDay);

    let Param = '?username=' + username;
    Param += '&route=' + route;
    Param += '&subRoute=' + subRoute;
    Param += '&state=' + state;
    Param += '&currentDay=' + currentDay;
    Param += '&option=' + !isNaN(option);

    $.ajax({
        type: 'GET',
        url: `/api/admin/search/details` + Param,
        success: function (response) {
            let length = response.length;

            if (!confirm("검색된 갯수는 " + length + "입니다. 1000개 이상시 많은 시간이 소요됩니다." +
                "\n그래도 진행하시겟습니까?")) {
                alert("작업이 취소되었습니다.");
                throw 'finish';
            }

            courierTotal(length);

            let courierTable = $("#courier-table-body");
            courierTable.empty();

            length === 0 ? courierTable.append("<tr><td colspan='12'>검색된 정보가 없습니다.</td></tr>")
                : setCourierTable(courierTable, response);

            closeDetail();
        },
        error: function (response) {
            console.log(response);
            /* 에러시 메시지 뽑는 방법 */
            alert(response['responseJSON']['message']);
        }
    })
}

// 상세 검색에 대한 열기
function openDetail() {
    const search = $('#detail-search');
    search.show();
}

// 상세 검색에 대한 닫기
function closeDetail() {
    const search = $('#detail-search');
    search.hide();
}

// 상세수정 창 열기
function updateOne(select) {
    $('#detailSave_send').attr('value', select);

    const width = 900;
    const height = 300;

    window.name = "parentForm";
    openWin = window.open(BaseUrl + "detailSave", "detailSave", stroption(width, height));
}

// 전체선택 함수
function selectAll(selectAll) {
    let checkboxes = document.getElementsByName('Courier-select');
    checkboxes.forEach((checkbox) => {
        checkbox.checked = selectAll.checked;
    })
}

// 새창 위치 지정
function stroption(width, height) {
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

// courier 갯수 세기
function courierTotal(total) {
    let str = "검색된 송장의 갯수 : " + total;
    $('#courierCnt').val(str);
}