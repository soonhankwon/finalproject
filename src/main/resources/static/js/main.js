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
                console.log(key)
                html += '<tr>';
                html += '<td>'+courierList[key].id+'</td>';
                html += '<td>'+courierList[key].route+'</td>';
                html += '<td>'+courierList[key].subRoute+'</td>';
                html += '<td>'+courierList[key].state+'</td>';
                html += '<td>'+courierList[key].customer+'</td>';
                html += '<td>'+courierList[key].arrivalDate+'</td>';
                html += '<td>'+courierList[key].username+'</td>';
                if (!courierList[key].state) {
                    html += "<td><button>배송완료</button></td>";
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
                console.log(key)
                html += '<tr>';
                html += '<td>'+courierList[key].id+'</td>';
                html += '<td>'+courierList[key].route+'</td>';
                html += '<td>'+courierList[key].subRoute+'</td>';
                html += '<td>'+courierList[key].state+'</td>';
                html += '<td>'+courierList[key].customer+'</td>';
                html += '<td>'+courierList[key].arrivalDate+'</td>';
                html += '<td>'+courierList[key].username+'</td>';
                html += `<td><button onclick="completeCancel(${courierList[key].id})">배송완료취소</button></td>`;
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
                console.log(key)
                html += '<tr>';
                html += '<td>'+courierList[key].id+'</td>';
                html += '<td>'+courierList[key].route+'</td>';
                html += '<td>'+courierList[key].subRoute+'</td>';
                html += '<td>'+courierList[key].state+'</td>';
                html += '<td>'+courierList[key].customer+'</td>';
                html += '<td>'+courierList[key].arrivalDate+'</td>';
                html += '<td>'+courierList[key].username+'</td>';
                html += `<td><button onclick="completeSave(${courierList[key].id})">배송완료처리</button></td>`;
                html += '</tr>';
            }
            $("#courier-table-body").append(html);

            alert("성공")
        },
        error : function(){alert("통신실패")}
    })
}

function completeSave(id){
    let courierId = id;
    let Params = '?courierId='+courierId;
    $.ajax({
        url: "/api/save/check"+Params,
        type: "PATCH",
        success : function(datalist){
            $("#courier-table-body").empty();
            let courierList = datalist.data;
            console.log(courierList);
            let html = ``
            for(key in courierList){
                console.log(key)
                html += '<tr>';
                html += '<td>'+courierList[key].id+'</td>';
                html += '<td>'+courierList[key].route+'</td>';
                html += '<td>'+courierList[key].subRoute+'</td>';
                html += '<td>'+courierList[key].state+'</td>';
                html += '<td>'+courierList[key].customer+'</td>';
                html += '<td>'+courierList[key].arrivalDate+'</td>';
                html += '<td>'+courierList[key].username+'</td>';
                html += "<td><button>배송완료취소</button></td>";
                html += '</tr>';
            }
            $("#courier-table-body").append(html);

            alert("배송완료")
            doing()
        },
        error : function(){alert("통신실패")}
    })
}

function completeCancel(id){
    console.log(id)
    let courierId = id;
    let Params = '?courierId='+courierId;
    $.ajax({
            url: "/api/save/uncheck"+Params,
        type: "PATCH",
        success : function(datalist){
            $("#courier-table-body").empty();
            // console.log(datalist);
            let courierList = datalist.data;
            console.log(courierList);
            let html = ``
            for(key in courierList){
                console.log(key)
                html += '<tr>';
                html += '<td>'+courierList[key].id+'</td>';
                html += '<td>'+courierList[key].route+'</td>';
                html += '<td>'+courierList[key].subRoute+'</td>';
                html += '<td>'+courierList[key].state+'</td>';
                html += '<td>'+courierList[key].customer+'</td>';
                html += '<td>'+courierList[key].arrivalDate+'</td>';
                html += '<td>'+courierList[key].username+'</td>';
                html += "<td><button>배송완료</button></td>";
                html += '</tr>';
            }
            $("#courier-table-body").append(html);

            alert("배송완료취소")
            doing()
        },
        error : function(){alert("통신실패")}
    })
}