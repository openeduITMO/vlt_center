app.factory('AuthProvider', function ($http, $q, store) {
  var SERVER_HOST = 'http://localhost:8012';
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";
  $http.defaults.headers.common["Cache-Control"] = "Cache-Control";
  $http.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";
  var isAuthorized = store.get('refreshJwtToken') != null && store.get('token') != null;
  return {
    setAuthorized: function (flag) {
      isAuthorized = flag;
    },
    isAuthorized: function () {
      return isAuthorized;
    },
    testConnect: () => {
      return $http.get(SERVER_HOST + '/VLT/api/test_connect/')
        .then(res => {
            isAuthorized = true;
            return true;
          },
          err => {
            isAuthorized = false;
            store.remove('token');
            store.remove('refreshJwtToken');
            return false;
          });
    }
  };
})