jQuery(document).ready(function () {
    $('body').on("click", "#add_vl", function () {
        $.ajax({
            url: "/addVL",
            type: "POST",
            data: "name=" + $("#name_vl").val(),
            success: function (data) {
                if (data.length != 0) {
                    $(".error").css("display", "none");
                    $("#name_vl").val("");
                    $("#vl_table").append(data);
                } else {
                    $(".error").css("display", "block");
                }

            },
            error: function () {
                alert("#ERROR");
            }
        });
    });

    $('body').on("click", ".tune", function () {
        $(".error").css("display", "none");
        var currentTr = $(this).parent().parent();
        var vlName = $(this).attr("dirName");
        $.post("/getPropertyVl?name=" + vlName, function (data) {
            $(".settings").html("");
            $(".import").html("");

            $(".settings").prepend("<form id='save_property_vl' method='post' action='/savePropertyVl'><table id='table_configuration'>" +
                "<tr>" +
                "<th class='name' colspan='2'>Свойства</th>" +
                "</tr>" +
                "<tr>" +
                "<td>Название лаборатории</td>" +
                "<td><input id='name_current_vl' name='name' value='" + data.name + "'/></td>" +
                "</tr>" +
                "<tr>" +
                "<td>Название каталога</td>" +
                "<td><input id='dir_current_vl' name='dirName' value='" + data.dirName + "' readonly/></td>" +
                "</tr>" +
                "<tr>" +
                "<td>Высота стенда</td>" +
                "<td><input id='height_current_vl' name='height' value='" + data.height + "'/></td>" +
                "</tr>" +
                "<tr>" +
                "<td>Ширина стенда</td>" +
                "<td><input id='width_current_vl' name='width' value='" + data.width + "'/></td>" +
                "</tr>" +
                "<tr>" +
                "<td><button id='save'><span>Сохранить</span></button><a id='cancel'><span>Отменить</span></a></td>" +
                "</tr>" +
                "</table></form>"
            );
            $("#save_property_vl").ajaxForm(function (data) {
                currentTr.children().first().html(data.name);
                $(".settings").html("");
                $(".import").html("");
            });
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

    $('body').on("click", "#cancel", function () {
        $(".settings").html("");
        $(".import").html("");
    });

})
