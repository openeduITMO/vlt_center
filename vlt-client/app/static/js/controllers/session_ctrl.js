app.controller('SessionCtrl', function ($scope, $location, VlService) {
  $scope.session = $location.path().split('/')[2];
  $scope.vlId = {isDir: false, res: $scope.session}

  $scope.nameVL = "";
  $scope.generate = null;
  $scope.generate_result = null;
  $scope.calculate_result = [];
  $scope.check_result;
  $scope.check_answer = null;
  $scope.check = null;
  $scope.previousSolution;
  var frame = frames['vl-frame'];

  frame.document.write('<script type="text/javascript">' +
    'function setGenerateCode(val){$("#preGeneratedCode").val(val) };' +
    'function setPreviousSolution(val){$("#previousSolution").val(val) };' +
    'function setSession(val){$("#session").val(val)};' +
    '</script>');

  $scope.home = function () {
    $scope.reLocation();
  };

  VlService.getPropertyBySession($scope.session)
    .then(res => {
      $scope.nameVL = res.name;
      $("#labFrame").css('width', res.width);
      $("#labFrame").css('height', res.height);
      return res.dirName;
    });

  VlService.getGenerateBySession($scope.session)
    .then(res => {
      $scope.generate = res.response;
      $scope.generate_result = res.response;
    });

  VlService.getCheckBySession($scope.session)
    .then(res => {
      //$scope.check_answer = res.request;
      //$scope.previousSolution = res.request;
      $scope.check_result = res.response;
    });


});