app.controller('AppCtrl', function ($scope, $location, USER_ROLES, store, AuthProvider) {
  $scope.currentUser = null;
  $scope.userRoles = USER_ROLES;
  $scope.isAuthenticated = AuthProvider.isAuthenticated;
  $scope.isAuthorized = AuthProvider.isAuthorized;

  $scope.pushCalculateResult= function(data){
    $scope.$broadcast('pushCalculateResult', data);
  }
});