app.factory('UserService', function ($http, $q, SERVER_HOST) {
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";
  $http.defaults.headers.common["Cache-Control"] = "Cache-Control";
  $http.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";

  return {
    register: (user, userRole) => {
      return $http.post(SERVER_HOST+'/VLT/auth/register/' + user.login + '/' + userRole, user.password)
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