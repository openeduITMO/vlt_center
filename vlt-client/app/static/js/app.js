var app = angular.module('App', ['ui.router', 'angular-storage', 'angular-jwt', 'angularUtils.directives.dirPagination'])
  .constant(
    'USER_ROLES', {
      all: '*',
      admin: 'ROLE_ADMIN',
      developer: 'ROLE_DEVELOPER',
      student: 'ROLE_STUDENT'
    })
  .constant(
    'SERVER_HOST', 'http://localhost:8012'
  )
  .config(['jwtOptionsProvider', 'jwtInterceptorProvider', '$httpProvider',
    function (jwtOptionsProvider, jwtInterceptorProvider, $httpProvider) {
      jwtOptionsProvider.config({
        whiteListedDomains: ['efimchick.com', 'localhost'],
        authPrefix: 'Bearer '
      });
      jwtInterceptorProvider.tokenGetter = ['AuthProvider', 'jwtHelper', '$http', 'store', function (AuthProvider, jwtHelper, $http, store) {
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
                  jwt = res.data.token;
                  AuthProvider.setStore(jwt, res.data.refreshJwtToken)
                  return jwt;
                },
                err => {
                  AuthProvider.destroy();
                })
          }
          return jwt;
        }
      }];
      $httpProvider.interceptors.push('jwtInterceptor');
    }])
  .config(['$stateProvider', '$urlRouterProvider', '$locationProvider', 'USER_ROLES',
    function ($stateProvider, $urlRouterProvider, $locationProvider, USER_ROLES) {
      $stateProvider
        .state('main', {
          url: '/login',
          controller: 'UserCtrl',
          templateUrl: 'templates/login.html',
          data: {
            authorizedRoles: [USER_ROLES.all]
          }
        })
        .state('dev_vlt', {
          url: '/vlt/dev',
          controller: 'IndexCtrl',
          templateUrl: 'templates/dev_vlt.html',
          data: {
            authorizedRoles: [USER_ROLES.admin, USER_ROLES.developer]
          }
        })
        .state('student_vlt', {
          url: '/vlt/student',
          controller: 'IndexCtrl',
          templateUrl: 'templates/student_vlt.html',
          data: {
            authorizedRoles: [USER_ROLES.student]
          }
        })
        .state('vl', {
          url: '/start_vl/:dir/:frame',
          controller: 'VlCtrl',
          templateUrl: 'templates/vl.html',
          data: {
            authorizedRoles: [USER_ROLES.all]
          }
        })
        .state('session', {
          url: '/show/:session',
          controller: 'SessionCtrl',
          templateUrl: 'templates/session.html',
          data: {
            authorizedRoles: [USER_ROLES.admin, USER_ROLES.developer]
          }
        });

      $urlRouterProvider.otherwise('/login');

      /*
       Удаляет # из url. '/#/test' -> '/test', но при попытке обновить страницу '/test' - ошибка 404
       TODO: удалить # из url
       */
      //$locationProvider.html5Mode(true);
    }])
  .run(['$rootScope', '$location', 'AuthProvider', function ($rootScope, $location, AuthProvider) {
    $rootScope.$on('$stateChangeStart', function (event, next) {
      var authorizedRoles = next.data.authorizedRoles;
      if ($location.path() != '/login') {
        if (!AuthProvider.isAuthorized(authorizedRoles)) {
          console.log('DENY : Redirecting to Login');
          $location.path('/login');
        } else {
          if (!AuthProvider.testConnect()) {
            console.log('DENY : Redirecting to Login');
            $location.path('/login');
          }
        }
      }
    });
  }]);
