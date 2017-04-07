var ANT = {
  SERVER_HOST: 'http://localhost:8012',

  calculate: function () {
    var result = Vlab.getResults();
    var condition = Vlab.getCondition();
    var session = $("#session").val();
    var dirName = window.location.href.split('/')[5];
    $.ajax({
      cache: false,
      url: this.SERVER_HOST + "/VLT/public/resources/" + dirName + "/get_calculate",
      global: false,
      type: "GET",
      data: (
      {
        session: session,
        instructions: result,
        condition: condition
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
}