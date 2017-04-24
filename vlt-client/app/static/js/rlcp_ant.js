var ANT = {
  calculate: function () {
    var result = Vlab.getResults();
    var condition = Vlab.getCondition();

    var parent_angular = parent.angular.element(parent.$('#iFrameCtrl')).scope();
    parent_angular.calculate(result, condition)
      .then(data => {
          $("#calculatedCode").val(data.code);
          $("#calculatedText").val(data.text);
          Vlab.calculateHandler(data.code);
        }
      );
  }
}