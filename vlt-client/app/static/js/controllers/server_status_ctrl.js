app.controller('ServerStatusCtrl', function ($scope, $location, ServerStatusService) {
  $scope.interior_server = false;
  $scope.dirName = $location.path().split('/')[2];
  $scope.frameId = $location.path().split('/')[3];

    var first_load = true;
    if (first_load) {
      ServerStatusService.checkTypeServer($scope.dirName)
        .then(res => {
          $('#check-checkbox').prop("checked", res);

          $scope.setCheckBox();
        });
      first_load = false;
    }

    $scope.checkServerStatus = function () {
      ServerStatusService.checkServerStatus($scope.dirName)
        .then(res => {
            $("#start-btn-wain-run-server").attr("id", "start-btn");
            if (res) {
              setStyleForRunningInteriorServer();
            } else {
              $("#check-status-server-btn").css("color", "green");
              $("#check-status-server-btn").parent().find("p").detach();
              $("#check-status-server-btn").parent().append("<p class='success'>Внешний сервер запушен</p>");
            }
          },
          err => {
            $("#start-btn").attr("id", "start-btn-wain-run-server");
            if (err.data) {
              setStyleForStoppedInteriorServer();
            } else {
              $("#check-status-server-btn").css("color", "red");
              $("#check-status-server-btn").parent().find("p").detach();
              $("#check-status-server-btn").parent().append("<p class='error'>Запустите внешний сервер по адресу " + $scope.url + "</p>");
            }
          });
    };

    $scope.setCheckBox = function () {
      $(".run-server-button").attr("class", "run-server-button");
      $scope.interior_server = $('#check-checkbox').prop("checked");
      $scope.checkServerStatus();
    };

    var setStyleForRunningInteriorServer = function () {
      $(".loader-div").css("display", "none");
      $("#start-btn-wain-run-server").attr("id", "start-btn");
      $("#wait-start-btn").attr("id", "interior-start-run");
      $("#interior-start-btn").attr("id", "interior-start-run");
      $("#interior-stop-btn").attr("id", "interior-stop-run");
    }

    var setStyleForStoppedInteriorServer = function () {
      $("#interior-start-run").attr("id", "interior-start-btn");
      $("#interior-stop-run").attr("id", "interior-stop-btn");
      $("#start-btn").attr("id", "start-btn-wain-run-server");
    }
  });