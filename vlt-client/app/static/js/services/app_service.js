app.factory('AuthProvider', function ($http, $q, store, jwtHelper, SERVER_HOST) {
  return {
    isAuthenticated: function () {
      return store.get('refreshJwtToken') != null && store.get('token') != null && store.get('role') != null;
    },
    isAuthorized: function (authorizedRoles) {
      if (!_.isArray(authorizedRoles)) {
        authorizedRoles = [authorizedRoles];
      }
      return (
        this.isAuthenticated() &&
        (
          _.indexOf(authorizedRoles, store.get('role')) !== -1 || _.indexOf(authorizedRoles, '*') !== -1
        )
      );
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