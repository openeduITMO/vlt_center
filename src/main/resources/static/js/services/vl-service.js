'use strict';
vl.factory('VlService', function ($http, $q) {
  return {
    getProperty: (dir, frame) => {
      return $http.get('/VLT/get_property/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getUrl: (dir, frame) => {
      return $http.get('/VLT/get_url/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getFrame: (dir, frame) => {
      return $http.get('/VLT/get_frame/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getGenerate: (dir, frame) => {
      return $http.get('/VLT/get_generate/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getAlgorithm: (dir, frame) => {
      return $http.get('/VLT/get_algorithm/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getCheck: (dir, frame) => {
      return $http.get('/VLT/get_check/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getServerStatus: () => {
      return $http.post('/VLT/get_server_status')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    stopInteriorServer: (url) => {
      return $http.post('/VLT/stop_interior_server', url)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    runInteriorServer: () => {
      return $http.post('/VLT/run_interior_server')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    generate: (algorithm) => {
      return $http.post('/VLT/get_generate', algorithm)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    repeat: () => {
      return $http.post('/VLT/repeat')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    check: (result) => {
      return $http.post('/VLT/get_check', result)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    }
  }
});