var app = angular.module('App', ['ui.router',  'angular-storage', 'angular-jwt', 'angularUtils.directives.dirPagination'])
  .config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('main', {
        url: '/',
        controller: 'UserCtrl',
        templateUrl: 'templates/login.html'
      })
        .state('vlt', {
          url: '/vlt',
          controller: 'IndexCtrl',
          templateUrl: 'templates/vlt.html'
        });

      $urlRouterProvider.otherwise('/');
    }]
  )
  .config(['jwtOptionsProvider', 'jwtInterceptorProvider', '$httpProvider',
    function (jwtOptionsProvider, jwtInterceptorProvider, $httpProvider) {
      jwtOptionsProvider.config({
        whiteListedDomains: ['efimchick.com', 'localhost'],
        authPrefix: 'Bearer '
      });
      jwtInterceptorProvider.tokenGetter = function (jwtHelper, $http, store) {
        var SERVER_HOST = 'http://localhost:8012';
        var jwt = store.get('token');
        var refreshToken = store.get('refreshJwtToken');
        if (jwt) {
          if (jwtHelper.isTokenExpired(jwt)) {
            return $http({
              url: SERVER_HOST + '/VLT/auth/token',
              method: 'GET',
              headers: {
                'Cache-Control': 'no-cache',
                'Authorization': 'Bearer ' + refreshToken
              }
            })
              .then(res => {
                  store.set('token', res.data.token);
                  return res.data.token;
                },
                err => {
                  store.remove('token');
                  store.remove('refreshJwtToken');
                })
            return refreshToken;
          } else {
            return jwt;
          }
        }
      };
      $httpProvider.interceptors.push('jwtInterceptor');
    }])
  .factory('AppService', function ($http, $q) {
    var SERVER_HOST = 'http://localhost:8012';
    $http.defaults.headers.common["Accept"] = "application/json";
    $http.defaults.headers.common["Content-Type"] = "application/json";
    $http.defaults.headers.common["Cache-Control"] = "Cache-Control";
    $http.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";
    return {
      testConnect: (dir, frame) => {
        return $http.get(SERVER_HOST + '/VLT/api/test_connect/')
          .then(res => {
              return res.data;
            },
            err => {
              return $q.reject(err);
            });
      }
    }
  })
  .controller('AppCtrl', function ($scope, store, AppService) {
    // $scope.isAuthorized = false;
    //
    // if (store.get('refreshJwtToken') != null && store.get('token') != null) {
    //   AppService.testConnect()
    //     .then(res => {
    //         $scope.isAuthorized = true;
    //       },
    //       err => {
    //         $scope.isAuthorized = false;
    //         store.remove('token');
    //         store.remove('refreshJwtToken');
    //       })
    // } else {
    //   $scope.isAuthorized = false;
    // }

  });
