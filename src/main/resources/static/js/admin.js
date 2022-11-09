$(document).ready(function () {

});

function openDetail() {
    window.name = "parentForm";
    openWin = window.open("http://localhost:8080/detailSearch", "detailSearch",
        "width=500, height=700, resizable = no, scrollbars = yes");
}

function setParentValue(){
    let Params = '?username='+$('#username').val();
    let subRoute = new Array();
    $('input:checkbox[name=subRoute]').each(function (index){
        if($(this).is(":checked")==true){
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
            console.log(response);
        }
    })
    window.close();
}

function searchCourier(){
    $.ajax()
}

function updateCourier(){

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