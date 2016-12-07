var app = angular.module('App', ['ui.router', 'angular-storage', 'angular-jwt', 'angularUtils.directives.dirPagination'])
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
  .config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {
      $stateProvider.state('main', {
          url: '/login',
          controller: 'UserCtrl',
          templateUrl: 'templates/login.html'
        })
        .state('vlt', {
          url: '/vlt',
          controller: 'IndexCtrl',
          templateUrl: 'templates/vlt.html'
        })
        .state('vl', {
          url: '/start_vl/:dir/:frame',
          controller: 'VlCtrl',
          templateUrl: 'templates/vl.html'
        });

      $urlRouterProvider.otherwise('/login');
    }])
  .run(['$rootScope', '$location', 'AuthProvider', function ($rootScope, $location, AuthProvider) {
    $rootScope.$on('$stateChangeStart', function (event) {
      if ($location.path() != '/login') {
        if (!AuthProvider.isAuthorized()) {
          console.log('DENY : Redirecting to Login');
          event.preventDefault();
          $location.path('/login');
        } else {
          if (!AuthProvider.testConnect()) {
            console.log('DENY : Redirecting to Login');
            event.preventDefault();
            $location.path('/login');
          }
        }
      }
    });
  }]);
