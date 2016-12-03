app.controller('AppCtrl', function ($scope, store, AppService) {
  $scope.isAuthorized = false;

  if (store.get('refreshJwtToken')!=null && store.get('token')!=null){
    $scope.isAuthorized = true;
  } else{
    $scope.isAuthorized = false;
  }

  //$scope.refreshToken = function(){
  //  AppService.refreshToken()
  //    .then(res => {
  //      $cookieStore.put('token', res.token);
  //      $scope.isAuthorized = true;
  //      console.log("refreshToken");
  //    })
  //}
});