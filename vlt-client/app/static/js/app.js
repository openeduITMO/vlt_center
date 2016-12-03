var app = angular.module('App', ['angular-jwt', 'angular-storage', 'angularUtils.directives.dirPagination'])
  .config(['jwtOptionsProvider', 'jwtInterceptorProvider', '$httpProvider', function (jwtOptionsProvider, jwtInterceptorProvider, $httpProvider) {
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
    }
    $httpProvider.interceptors.push('jwtInterceptor');
  }]);
