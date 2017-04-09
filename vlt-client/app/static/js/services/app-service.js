app.factory('AuthProvider', function ($http, $q, store, jwtHelper) {
  var SERVER_HOST = 'http://localhost:8012';
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";
  $http.defaults.headers.common["Cache-Control"] = "Cache-Control";
  $http.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";
  return {
    isAuthenticated: function () {
      return store.get('refreshJwtToken') != null && store.get('token') != null && store.get('role') != null;
    },
    isAuthorized: function (authorizedRoles) {
      if (!angular.isArray(authorizedRoles)) {
        authorizedRoles = [authorizedRoles];
      }
      return (this.isAuthenticated() &&
      authorizedRoles.indexOf(store.get('role')) !== -1);
    },
    testConnect: () => {
      return $http.get(SERVER_HOST + '/VLT/api/test_connect/')
        .then(res => {
            return true;
          },
          err => {
            return false;
          });
    },
    setStore: (token, refreshJwtToken) => {
      store.set('refreshJwtToken', refreshJwtToken);
      store.set('token', token);
      store.set('role', jwtHelper.decodeToken(token)['scopes'][0]);
    },
    destroy: () => {
      store.remove('role');
      store.remove('token');
      store.remove('refreshJwtToken');
    }
  };
})