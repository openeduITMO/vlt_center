app.controller('AppCtrl', function ($scope, $location, store, AuthProvider) {
  $scope.pushCalculateResult= function(data){
    $scope.$broadcast('pushCalculateResult', data);
  }
});