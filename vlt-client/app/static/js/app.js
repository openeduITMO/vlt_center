var app = angular.module('App', ['ngCookies', 'angularUtils.directives.dirPagination'])
  .controller('AppCtrl', function ($scope, $cookieStore) {
    $scope.isAuthorized = false;

    //$scope.init = function(){
    //  var refreshToken = $cookieStore.get('refreshJwtToken');
    //  var token = $cookieStore.get('token');
    //
    //  if (refreshToken==null || token==null){
    //    isAuthorized = false;
    //  } else{
    //    isAuthorized = true;
    //  }
    //}
  });
