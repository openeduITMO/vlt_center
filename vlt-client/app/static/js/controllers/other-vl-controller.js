app.controller('otherController', function ($scope, store, $location, IndexService) {
  $scope.otherVlCollection = [];

  //init vlt
  IndexService.getOtherVl()
    .then(res => {
      $scope.otherVlCollection = res;
    });

  $scope.sendDeclaration = function(vl){
    IndexService.declaration(vl.dirName)
      .then(res => {
        _.pull($scope.otherVlCollection, vl);
        $scope.vlCollection.push(res);
      });
  }

});