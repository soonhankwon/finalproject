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

                    let id = courierList[i]['id'];
                    let route = courierList[i]['route'];
                    let subroute = courierList[i]['subRoute'];
                    let customer = courierList[i]['customer'];
                    let realstate = courierList[i]['state'];
                    let arrivalDate = courierList[i]['arrivalDate'];
                    let trueusername = courierList[i]['username'];

                    let html = "<tr>"+
                        "<td><input type='checkbox' name='Courier-select'></td>"+
                        `<td onclick="updateOne(${i})" align='middle'><input name='number-${i}' type='text' value='${id}'readonly/>`+"</td>"+
                        `<td align='middle'><input name='route-${i}' type='text' value='${route}'readonly/>`+"</td>"+
                        `<td align='middle'><input name='subroute-${i}' type='text' value='${subroute}'readonly/>`+"</td>"+
                        `<td align='middle'><input name='customer-${i}' type='text' value='${customer}'readonly/>`+"</td>"+
                        `<td align='middle'><input name='state-${i}' type='text' value='${realstate}'readonly/>`+"</td>"+
                        `<td align='middle'><input name='arrivalDate-${i}' type='text' value='${arrivalDate}'readonly/>`+"</td>"+
                        `<td align='middle'><input name='username-${i}' type='text' value='${trueusername}'readonly/>`+"</td></tr>"
                    courierTable.append(html);
                }
            }
        }
    })
    setTimeout(() => window.close(), 2000);
}