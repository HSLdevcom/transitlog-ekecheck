$(document).ready(function(){
    $("#okbutton").click(function(){
        listTrainData();
    });
    $("#unitNumber").keypress(function(event){
        if (event.keyCode === 13) {
            $("#okbutton").click();
        }
    });
})

function listTrainData(){
    var unitNumber = $("#unitNumber").val();
    $.get("/api/train/" + unitNumber, function(data, status){
        buildTrainDataList(data, status);
    });
}

function buildTrainDataList(data, status){
    var newHtml = "<table class='table table-striped' id='trainDataTable'><tr><th>Unit number</th><th>Timestamp</th><th>Topic</th></tr>"
    for(var i = 0 ; i < data.length ; i++){
        var trainItem = data[i];
        newHtml += "<tr><td>" + trainItem.unitNumber + "</td><td>" + trainItem.ekeDate + "</td><td>" + trainItem.topicPart + "</td></tr>";
    }
    newHtml += "</table>";
    $("#content").html(newHtml);
}