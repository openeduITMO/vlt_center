jQuery(document).ready(function () {
    $('body').on("click", "#start_but", function () {
        $.ajax({
            url: "/getGenerate",
            type: "POST",
            data: {
                algorithm: $("#algorithm").html()
            },
            success: function (data) {
                $("#preGeneratedCode").val(data.code);
                $("#generate-result").find("table").html("");
                $("#generate-result").css("display", "inline-block");
                $("#generate-result").find("table").append("<tr>" +
                    "<td>Code</td>" +
                    "<td>"+data.code+"</td>" +
                    "</tr>" + "<tr>" +
                    "<td>Text</td>" +
                    "<td>"+data.text+"</td>" +
                    "</tr>" + "<tr>" +
                    "<td>Instructions</td>" +
                    "<td><div>"+data.instructions+"</div></td>" +
                    "</tr>");
                Vlab.init();
            },
            error: function () {
                alert("#ERROR");
            }
        });
    });

    $('body').on("click", "#check_but", function () {
        var result = Vlab.getResults();
        $.ajax({
            url: "/getCheck",
            type: "POST",
            dataType: 'json',
            contentType: 'application/json',
            mimeType: 'application/json',
            data: result,
            success: function (data) {
                $("#check-answer").css("display","block");
                $("#check-answer").find("table tbody").html("");
                $("#check-answer").find("table tbody").append("<tr><td>"+result+"</td></tr>");
                $("#check-result").css("display","inline-block");
                $("#check-result").find("table tbody").html("");
                $.each(data, function (key, val) {
                    $("#check-result").find("table tbody").append("<tr>" +
                        "<td>"+val.id+"</td>" +
                        "<td>"+val.time+"</td>" +
                        "<td>"+val.result+"</td>" +
                        "<td>"+val.output+"</td>" +
                        "</tr>");
                })
            },
            error: function () {
            }
        });
    });

    $('body').on("click", ".hidden", function () {
        var block = $(this).parent().parent();
        block.find($(".info-all")).hide(200);
        block.find(".button").attr("class", "button show");
    });

    $('body').on("click", ".show", function () {
        var block = $(this).parent().parent();
        block.find($(".info-all")).show(200);
        block.find(".button").attr("class", "button hidden");
    });

});

//получение значения параметров из адресной строки
function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function (m, key, value) {
        vars[key] = value;
    });
    return vars;
}
