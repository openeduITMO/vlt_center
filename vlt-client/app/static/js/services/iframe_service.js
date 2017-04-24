app.factory('iFrameService', function ($http, $q, SERVER_HOST) {
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";
  $http.defaults.headers.common["Cache-Control"] = "Cache-Control";
  $http.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";

  return {
    getJs: (vlId) => {
      var url = '/VLT/api/frame/get_js/' + vlId.res;
      if (!vlId.isDir) {
        url = '/VLT/api/session/get_js/' + vlId.res;
      }
      return $http.get(SERVER_HOST + url)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getCss: (vlId) => {
      var url = '/VLT/api/frame/get_css/' + vlId.res;
      if (!vlId.isDir) {
        url = '/VLT/api/session/get_css/' + vlId.res;
      }
      return $http.get(SERVER_HOST + url)
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
    },

    getCalculateBySession: (session) => {

    }
  }
});