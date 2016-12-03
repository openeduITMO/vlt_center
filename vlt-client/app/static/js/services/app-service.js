app.factory('AppService', function ($http, $q) {
  var SERVER_HOST = 'http://localhost:8012';
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";

  return {
    refreshToken: () => {
      //$http.defaults.headers.common["X-Authorization"] = "Bearer " + store.get('refreshJwtToken');
      return $http.get(SERVER_HOST + '/VLT/auth/token')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    }
  }
});