vl.factory('VlService', function ($http, $q) {
  var SERVER_HOST = 'http://localhost:8012';
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";
  $http.defaults.headers.common["Cache-Control"] = "Cache-Control";
  $http.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";

  return {
    getProperty: (dir, frame) => {
      return $http.get(SERVER_HOST+'/VLT/api/get_property/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getUrl: (dir, frame) => {
      return $http.get(SERVER_HOST+'/VLT/api/get_url/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getFrame: (dir, frame) => {
      return $http.get(SERVER_HOST+'/VLT/api/get_frame/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getGenerate: (dir, frame) => {
      return $http.get(SERVER_HOST='/VLT/api/get_generate/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getAlgorithm: (dir, frame) => {
      return $http.get(SERVER_HOST+'/VLT/api/get_algorithm/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getCheck: (dir, frame) => {
      return $http.get(SERVER_HOST+'/VLT/api/get_check/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getServerStatus: () => {
      return $http.post(SERVER_HOST+'/VLT/api/get_server_status')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    stopInteriorServer: (url) => {
      return $http.post(SERVER_HOST+'/VLT/api/stop_interior_server', url)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    runInteriorServer: () => {
      return $http.post(SERVER_HOST+'/VLT/api/run_interior_server')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    generate: (algorithm) => {
      return $http.post(SERVER_HOST+'/VLT/api/get_generate', algorithm)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    repeat: () => {
      return $http.post(SERVER_HOST+'/VLT/api/repeat')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    check: (result) => {
      return $http.post(SERVER_HOST+'/VLT/api/get_check', result)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    }
  }
});