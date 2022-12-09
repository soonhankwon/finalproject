
$(document).ready(function () {
    doing()

    // id 가 query 인 녀석 위에서 엔터를 누르면 execSearch() 함수를 실행.
    $('#query').on('keypress', function (e) {
        if (e.key == 'Enter') {
            execSearch();
        }
    });
});

function execSearch() {
    /**
     * 검색어 input id: query
     * 검색결과 목록: #search-result-box
     * 검색결과 HTML 만드는 함수: addHTML
     */
        // 1. 검색창의 입력값을 가져온다.
    let query = $('#query').val();

    // 2. 검색창 입력값을 검사하고, 입력하지 않았을 경우 focus.
    $.ajax({
        url: "/api/search/user/courier/customer?customer=" + query,
        type: "GET",
        success : function(datalist){
            console.log(datalist);
            $("#courier-table-body").empty();
            $("#button-cnt").empty();

            let courierList = datalist.data;
            console.log(courierList);

            let temphtml = `
                            <button type="button" className="button-js" onClick="doing()">배송중(${datalist.progressCnt})</button>
                            <button type="button" className="button-js" onClick="complete()">배송완료(${datalist.completeCnt})</button>
                           `
            $("#button-cnt").append(temphtml);

            let html = ``
            for(key in courierList){
                html += '<tr>';
                html += '<td>'+courierList[key].id+'</td>';
                html += '<td>'+courierList[key].area+'</td>';
                html += '<td>'+courierList[key].route+'</td>';
                html += '<td>'+courierList[key].subRoute+'</td>';
                html += '<td>'+courierList[key].state+'</td>';
                html += '<td>'+courierList[key].customer+'</td>';
                html += '<td>'+courierList[key].arrivalDate+'</td>';
                html += '<td>'+courierList[key].registerDate+'</td>';
                html += '<td>'+courierList[key].username+'</td>';
                html += '<td>'+courierList[key].courierUsername+'</td>';
                if (courierList[key].state === "배송중") {
                    html += `<td><button onclick="completeSave(${courierList[key].id})">배송완료</button></td>`;
                }
                else {
                    html += `<td><button onclick="completeCancel(${courierList[key].id})">배송완료취소</button></td>`;
                }
                html += '</tr>';
            }
            $("#courier-table-body").append(html);

            alert("성공3")
        },
        error : function(){alert("통신실패")}
    })

}


function complete(){
    $.ajax({
        url: "/api/search/user/courier?state=1",
        type: "GET",
        success : function(datalist){
            // console.log(datalist);
            $("#courier-table-body").empty();
            $("#button-cnt").empty();
            let courierList = datalist.data;
            console.log(courierList);

            let temphtml = `
                            <button type="button" className="button-js" onClick="doing()">배송중(${datalist.progressCnt})</button>
                            <button type="button" className="button-js" onClick="complete()">배송완료(${datalist.completeCnt})</button>
                           `
            $("#button-cnt").append(temphtml);

            let html = ``
            for(key in courierList){
                html += '<tr>';
                html += '<td>'+courierList[key].id+'</td>';
                html += '<td>'+courierList[key].area+'</td>';
                html += '<td>'+courierList[key].route+'</td>';
                html += '<td>'+courierList[key].subRoute+'</td>';
                html += '<td>'+courierList[key].state+'</td>';
                html += '<td>'+courierList[key].customer+'</td>';
                html += '<td>'+courierList[key].arrivalDate+'</td>';
                html += '<td>'+courierList[key].registerDate+'</td>';
                html += '<td>'+courierList[key].username+'</td>';
                html += '<td>'+courierList[key].courierUsername+'</td>';
                if (courierList[key].state === "배송완료") {
                    html += `<td><button onclick="completeCancel(${courierList[key].id})">배송완료취소</button></td>`;
                }
                html += '</tr>';
            }
            $("#courier-table-body").append(html);

            alert("성공2")
        },
        error : function(){alert("통신실패")}
    })
}


function doing(){
    $.ajax({
        url: "/api/search/user/courier?state=0",
        type: "GET",
        success : function(datalist){
            $("#courier-table-body").empty();
            $("#button-cnt").empty();
            let courierList = datalist.data;
            console.log(courierList);

            let temphtml = `
                            <button type="button" className="button-js" onClick="doing()">배송중(${datalist.progressCnt})</button>
                            <button type="button" className="button-js" onClick="complete()">배송완료(${datalist.completeCnt})</button>
                           `
            $("#button-cnt").append(temphtml);

            let html = ``
            for(key in courierList){
                html += '<tr>';
                html += '<td>'+courierList[key].id+'</td>';
                html += '<td>'+courierList[key].area+'</td>';
                html += '<td>'+courierList[key].route+'</td>';
                html += '<td>'+courierList[key].subRoute+'</td>';
                html += '<td>'+courierList[key].state+'</td>';
                html += '<td>'+courierList[key].customer+'</td>';
                html += '<td>'+courierList[key].arrivalDate+'</td>';
                html += '<td>'+courierList[key].registerDate+'</td>';
                html += '<td>'+courierList[key].username+'</td>';
                html += '<td>'+courierList[key].courierUsername+'</td>';
                if (courierList[key].state === "배송중") {
                    html += `<td><button onclick="completeSave(${courierList[key].id})">배송완료</button></td>`;
                }
                html += '</tr>';
            }
            $("#courier-table-body").append(html);


            // var mapContainer = document.getElementById('kakao-map'), // 지도를 표시할 div
            //     mapOption = {
            //         center: new kakao.maps.LatLng(37.512405, 126.8807795), // 지도의 중심좌표
            //         level: 3 // 지도의 확대 레벨
            //     };
            //
            // var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
            //
            // // 마커를 표시할 위치와 title 객체 배열입니다
            //
            // // 마커 이미지의 이미지 주소입니다
            // var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
            // var tempAddress = 'sadasd';
            //
            // for (var i = 0; i < courierList.length; i++) {
            //
            //     var xPos = courierList[i].xpos;
            //     var yPos = courierList[i].ypos;
            //     // 마커 이미지의 이미지 크기 입니다
            //     var imageSize = new kakao.maps.Size(24, 35);
            //
            //     // 마커 이미지를 생성합니다
            //     var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);
            //     if (tempAddress !== courierList[i].address) {
            //         var pos = new kakao.maps.LatLng(xPos, yPos);
            //         console.log(pos);
            //
            //         // 마커를 생성합니다
            //         var marker = new kakao.maps.Marker({
            //             map: map, // 마커를 표시할 지도
            //             position: pos, // 마커를 표시할 위치
            //             title : courierList[i].address, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            //             image : markerImage // 마커 이미지
            //         });
            //         tempAddress = courierList[i].address;
            //         marker.setMap(map);
            //     }
            //
            // }

            alert("성공")
        },
        error : function(){alert("통신실패")}
    })
}

function completeSave(id){
    let courierId = id;
    $.ajax({
        url: "/api/save/check/" + courierId,
        type: "PATCH",
        success : function(datalist){

            window.location.reload();
            alert("배송완료")
            doing()
        },
        error : function(){alert("통신실패")}
    })
}

function completeCancel(id){
    console.log(id)
    let courierId = id;
    $.ajax({
            url: "/api/save/uncheck/" + courierId,
        type: "PATCH",
        success : function(datalist){

            alert("배송완료취소")
            window.location.reload();
            complete()
        },
        error : function(){alert("통신실패")}
    })
}