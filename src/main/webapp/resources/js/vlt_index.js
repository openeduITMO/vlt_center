jQuery(document).ready(function () {
    $('body').on("click", "#add-vl", function () {
        $.ajax({
            url: "/addVL",
            type: "POST",
            data: "name=" + $("#name-vl").val(),
            success: function (data) {
                if (data.length != 0) {
                    $("#name-vl").val("");
                    $('.s-form').css("display", "none");
                    $(".list-div").find($(".table")).append("<tr>" +
                        "<td>" + data.name + "</td>" +
                        "<td>...</td>" +
                        "<td>" +
                        "<span class='button run-vl' dirName='" + data.dirName + "'/>" +
                        "<span class='button tune-vl' dirName='" + data.dirName + "'/>" +
                        "</td>" +
                        "</tr>");
                } else {
                    $(".error").css("display", "block");
                }

            },
            error: function () {
                alert("#ERROR");
            }
        });
    });

    $('body').on("click", ".run-vl", function () {
        clearSettingsForm();
        $(".running").css("display", "block");
        var vlName = $(this).attr("dirName");
        $.post("/getLabratoryFame?name=" + vlName, function (data) {
            if (data.length != 0) {
                $.each(data, function (key, val) {
                    $(".running").find($(".panel-body")).find("table tbody").append("<tr><td>" + val.id + "</td><td>" + val.sheme + "</td><td>" + val.name + "</td><td>" + val.data + "</td><td><form method='post' action='startVl?name=" + vlName + "&frameId=" + val.id + "'><button><span class='button start-vl'/></button></form></td></tr>");
                });
            } else {
                $(".running").find($(".panel-body")).find("table tbody").append("<td><td colspan='5'><div class='server-error'>LaboratoryFrames не загружен</div></td></tr>");
            }

        });
    });

    $('body').on("click", ".tune-vl", function () {
        clearSettingsForm();
        $('.settings').css("display", "block");
        var currentTr = $(this).parent().parent();
        var vlName = $(this).attr("dirName");
        $.post("/getPropertyVl?name=" + vlName, function (data) {
            $(".settings").find($(".panel-body")).html("");
            $(".import").html("");

            //$(".settings").prepend("<span class='button close'><img src='../../resources/img/close.png' alt='X'/></span><h2 class='title_close'>Свойства</h2>");
            if (data.length != 0) {
                $(".settings").find($(".panel-body")).append("<form id='save-property-vl' method='post' action='/savePropertyVl'>" +
                    "<label>Название лаборатори</label>" +
                    "<input class='form-control-tune' id='name-current-vl' name='name' value='" + data.name + "'/><br/>" +
                    "<label>Название каталога</label>" +
                    "<input class='form-control-tune' id='dir-current-vl' name='dirName' value='" + data.dirName + "' readonly/><br/>" +
                    "<button id='save'><span class='button save-vl'/></button>" +
                        //"<table id='table-configuration' class='table'>" +
                        //"<tr>" +
                        //"<td>Название лаборатории</td>" +
                        //"<td><input id='name-current-vl' name='name' value='" + data.name + "'/></td>" +
                        //"</tr>" +
                        //"<tr>" +
                        //"<td>Название каталога</td>" +
                        //"<td><input id='dir-current-vl' name='dirName' value='" + data.dirName + "' readonly/></td>" +
                        //"</tr>" +
                        //"<tr>" +
                        //"<td><button id='save'><span>Сохранить</span></button></td>" +
                        //"</tr>" +
                        //"</table>" +
                    "</form>" +
                    "<div class='import'></div>"
                );
                $("#save-property-vl").ajaxForm(function (data) {
                    currentTr.children().first().html(data.name);
                    $(".settings").css("display", "none");
                    $(".settings").find($(".panel-body")).html("");
                    $(".import").html("");
                });
            } else {
                $(".settings").append("<div class='server-error'>Не найден файл конфигурации ВЛ (lab.desc)</div>")
            }

            $(".import").append("<hr/><form id='upload_form' method='post' enctype='multipart/form-data' action='/uploadFile?nameDir=" + data.dirName + "'>" +
                "Upload File: <input type='file' name='uploadfile'/>" +
                "<input type='submit' value='Import'/>" +
                "</form>");
            $('#upload_form').ajaxForm(function (data) {
                if (data == "OK") {
                    alert("OK");
                    $(".settings").html("");
                    $(".import").html("");
                }
            });
        });
    });

    $('body').on("click", ".plusTd", function () {
        clearSettingsForm();
        $('.s-form').css("display", "block");
    });

    $('body').on("click", ".s-form .close", function () {
        $('.s-form').css("display", "none");
        $('.s-form').find($("#name-vl")).val("");
    });

    $('body').on("click", ".settings .close", function () {
        $('.settings').css("display", "none");
        $(".settings").find($(".panel-body")).html("");
        $(".import").html("");
    });

    $('body').on("click", ".running .close", function () {
        $('.running').css("display", "none");
        $(".running").find($(".panel-body")).find("table tbody").html("");
    });

});

function clearSettingsForm() {
    $(".running").css("display", "none");
    $(".running").find($(".panel-body")).find("table tbody").html("");
    $(".settings").css("display", "none");
    $(".settings").find($(".panel-body")).html("");
    $(".import").html("");
    $('.s-form').css("display", "none");
    $('.s-form').find($("#name-vl")).val("");
}