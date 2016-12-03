app.factory('UserService', function ($http, $q) {
  var SERVER_HOST = 'http://localhost:8012';
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";
  $http.defaults.headers.common["Cache-Control"] = "Cache-Control";
  $http.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";

  return {
    register: (user) => {
      return $http.post(SERVER_HOST+'/VLT/auth/register', user)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    login: (user) => {
      return $http.post(SERVER_HOST+'/VLT/auth/login', user)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    }
  }
});