app.controller('uploadCtrl', function ($scope, UploadService) {
  $scope.uploadFile = function (dir) {
    var file = $scope.zipVl;

    UploadService.uploadFileToUrl(file, dir)
      .then(res => {
        $scope.closeForm('import-form');
    });
  };
});