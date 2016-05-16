jQuery(document).ready(function () {
  $(document).on('change', '.uploader :file', function () {
    var name = $(this).get(0).files[0].name;
    if (name != "") {
      $(this).parent().find("label").attr("data-file", name);
    }
  });

  $('body').on("click", "#add-vl", function () {
    $.ajax({
      url: "/VLT/addVL",
      type: "POST",
      data: "name=" + $("#name-vl").val(),
      success: function (data) {
        if (data.length != 0) {
          $("#name-vl").val("");
          $('.s-form').css("display", "none");
          $(".list-div").find($(".table")).append("<tr>" +
            "<td>" + data.name + "</td>" +
              //"<td>...</td>" +
            "<td>" +
            "<span class='button run-vl' dirName='" + data.dirName + "'/>" +
            "<span class='button tune-vl' dirName='" + data.dirName + "'/>" +
            "<span class='button import-vl' dirName='" + data.dirName + "'/>" +
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
    var currentTr = $(this).parent().parent();

    $(".running").find($(".panel-heading")).find($(".name-vl")).html("\"" + currentTr.find('td').first().html() + "\"");
    var vlName = $(this).attr("dirName");
    $.post("/VLT/getLabratoryFame?name=" + vlName, function (data) {
      if (data.length != 0) {
        $.each(data, function (key, val) {
          $(".running").find($(".panel-body")).find("table tbody").append("<tr>" +
            "<td>" + val.id + "</td>" +
            "<td>" + val.sheme + "</td>" +
            "<td>" + val.name + "</td>" +
            "<td>" + val.data + "</td>" +
            "<td>" +
            "<form method='get' action='/VLT/startVl/" + vlName + "/" + val.id + "'>" +
            "<button><span class='button start-vl'/></button>" +
            "</form></td></tr>");
        });
      } else {
        $(".running").find($(".panel-body")).find("table tbody").append("<td><td colspan='5'><div class='server-error'>LaboratoryFrames не загружен</div></td></tr>");
      }

    });
  });

  $('body').on("click", ".import-vl", function () {
    clearSettingsForm();
    $('.import').css("display", "block");
    $(".import").find($(".panel-body")).html("");
    var currentTr = $(this).parent().parent();

    $(".import").find($(".panel-heading")).find($(".name-vl")).html("\"" + currentTr.find('td').first().html() + "\"");
    var vlName = $(this).attr("dirName");
    $(".import").find($(".panel-body")).append("<form id='upload_form' method='post' enctype='multipart/form-data' action='/VLT/uploadFile?nameDir=" + vlName + "'>" +
      "<div class='uploader'>" +
      "<label for='uploader' data-file='Выберите файл'></label>" +
      "<input type='file' name='uploadfile' id='uploader'/>" +
      "</div>" +
      "<button><span class='button import-btn'></span></button>" +
      "</form>");
    $('#upload_form').ajaxForm(function (data) {
      if (data == "OK") {
        $(".import").css("display", "none");
        $(".import").find($(".panel-body")).html("");
      }
    });

  });

  $('body').on("click", ".tune-vl", function () {
    clearSettingsForm();
    $('.settings').css("display", "block");
    var currentTr = $(this).parent().parent();
    $(".settings").find($(".panel-heading")).find($(".name-vl")).html("\"" + currentTr.find('td').first().html() + "\"");
    var vlName = $(this).attr("dirName");
    $.post("/VLT/getPropertyVl/" + vlName, function (data) {
      $(".settings").find($(".panel-body")).html("");


      if (data.length != 0) {
        $(".settings").find($(".panel-body")).append("<form id='save-property-vl' method='post' action='/VLT/savePropertyVl/" + data.dirName + "'>" +
          "<table><tr><td><label>Название лаборатори</label></td>" +
          "<td><input class='form-control-tune' id='name-current-vl' name='name' value='" + data.name + "'/></td></tr>" +
          "<tr><td><label>Название каталога</label></td>" +
          "<td><input class='form-control-tune lock' id='dir-current-vl' name='dirName' placeholder='" + data.dirName + "' disabled/><br/></td></tr></table>" +
          "<button id='save'><span class='button save-vl'/></button>" +
          "</form>"
        );
        $("#save-property-vl").ajaxForm(function (data) {
          currentTr.children().first().html(data.name);
          $(".settings").css("display", "none");
          $(".settings").find($(".panel-body")).html("");
          //$(".import").html("");
        });
      } else {
        $(".settings").append("<div class='server-error'>Не найден файл конфигурации ВЛ (lab.desc)</div>")
      }
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

  $('body').on("click", ".import .close", function () {
    $('.import').css("display", "none");
    $('.import').find($(".panel-body")).find("table tbody").html("");
  });

});

function clearSettingsForm() {
  $(".running").css("display", "none");
  $(".running").find($(".panel-body")).find("table tbody").html("");
  $(".settings").css("display", "none");
  $(".settings").find($(".panel-body")).html("");
  $(".import").css("display", "none");
  $(".import").find($(".panel-body")).find("table tbody").html("");
  $('.s-form').css("display", "none");
  $('.s-form').find($("#name-vl")).val("");
}