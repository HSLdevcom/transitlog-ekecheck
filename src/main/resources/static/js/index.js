$(document).ready(function(){
    $("#okbutton").click(function(){
        listTrainData();
    })
})

function listTrainData(){
    var trainnumber = $("#trainnumber").val();
    $.get("/api/train/" + trainnumber, function(data, status){
        buildTrainDataList(data, status);
    });
}

function buildTrainDataList(data, status){
    var newHtml = "<table id='trainDataTable'><tr><th>Train number</th><th>Timestamp</th><th>Topic</th></tr>"
    for(var i = 0 ; i < data.length ; i++){
        var trainItem = data[i];
        newHtml += "<tr><td>" + trainItem.trainNumber + "</td><td>" + trainItem.ekeDate + "</td><td>" + trainItem.end + "</td><td>" + trainItem.topicPart + "</td></tr>";
    }
    newHtml += "</table>";
    $("#content").html(newHtml);
}