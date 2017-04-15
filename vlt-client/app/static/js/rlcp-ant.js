var ANT = {
  SERVER_HOST: 'http://localhost:8012',

  calculate: function () {
    var result = Vlab.getResults();
    var condition = Vlab.getCondition();
    var session = $("#session").val();
    var dirName = window.location.href.split('/')[5];

    var parent_angular = parent.angular.element(parent.$('#iFrameCtrl')).scope();
    parent_angular.calculate(session, result, condition)
      .then(data => {
          $("#calculatedCode").val(data.code);
          $("#calculatedText").val(data.text);
          Vlab.calculateHandler(data.code);
        }
      );
  }
}