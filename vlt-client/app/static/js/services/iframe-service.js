app.factory('iFrameService', function ($http, $q, SERVER_HOST) {
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";
  $http.defaults.headers.common["Cache-Control"] = "Cache-Control";
  $http.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";

  return{
    getJs: (dir, frame) => {
      return $http.get(SERVER_HOST+'/VLT/api/frame/get_js/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getCss: (dir, frame) => {
      return $http.get(SERVER_HOST+'/VLT/api/frame/get_css/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    calculate: (dir, session, result, condition) => {
      return $http.get(SERVER_HOST + '/VLT/api/' + dir + '/get_calculate', {
          params: {session: session, instructions: result, condition: condition}
        })
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    }
  }
});