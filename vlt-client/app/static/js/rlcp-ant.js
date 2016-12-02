var ANT = new Object();

ANT.calculate = function () {
  var result = Vlab.getResults();
  $.ajax({
    cache: false,
    url: "/VLT/get_calculate",
    global: false,
    type: "POST",
    data: (
    {
      instructions: result,
      condition: Vlab.getCondition()
    }
    ),
    dataType: "text",
    success: function (text) {
      var json = JSON.parse(text);
      parent.setCalculateResult(result, json);
      $("#calculatedCode").val(json.code);
      $("#calculatedText").val(json.text);
      Vlab.calculateHandler(json.code);
    },
    error: function () {
      $(".run-server-button").attr("class", "run-server-button run-server-error");
    }

  });

}
