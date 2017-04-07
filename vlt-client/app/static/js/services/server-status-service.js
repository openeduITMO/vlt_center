app.factory('ServerStatusService', function ($http, $q) {
  var SERVER_HOST = 'http://localhost:8012';
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";
  $http.defaults.headers.common["Cache-Control"] = "Cache-Control";
  $http.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";

  return {
    checkTypeServer: (dir) => {
      return $http.post(SERVER_HOST+'/VLT/api/'+dir+'/get_type_server')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    checkServerStatus: (dir) => {
      return $http.post(SERVER_HOST+'/VLT/api/'+dir+'/get_server_status')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },
  }
});