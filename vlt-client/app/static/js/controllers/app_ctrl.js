app.controller('AppCtrl', function ($scope, $location, USER_ROLES, store, AuthProvider) {
  $scope.currentUser = null;
  $scope.userRoles = USER_ROLES;
  $scope.isAuthenticated = AuthProvider.isAuthenticated;
  $scope.isAuthorized = AuthProvider.isAuthorized;

  $scope.pushCalculateResult = function(data){
    $scope.$broadcast('pushCalculateResult', data);
  }

  $scope.reLocation = function(){
    var role = store.get('role');
    if (USER_ROLES.student == role){
      $location.path('/vlt/student');
    } else if (
      USER_ROLES.admin == role ||
      USER_ROLES.developer == role){
      $location.path('/vlt/dev');
    } else{
      $location.path('/login');
    }
  }
});