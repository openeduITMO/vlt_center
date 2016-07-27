vl.factory('iFrameService', function ($http, $q) {
  return{
    getJs: (dir, frame) => {
      return $http.get('/VLT/get_js/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getCss: (dir, frame) => {
      return $http.get('/VLT/get_css/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },
  }
});