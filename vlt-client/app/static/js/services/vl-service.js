app.factory('VlService', function ($http, $q, SERVER_HOST) {
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";
  $http.defaults.headers.common["Cache-Control"] = "Cache-Control";
  $http.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";

  return {
    getProperty: (dir, frame) => {
      return $http.get(SERVER_HOST + '/VLT/api/frame/get_property/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getUrl: (dir, frame) => {
      return $http.get(SERVER_HOST + '/VLT/api/frame/get_url/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getFrame: (dir, frame) => {
      return $http.get(SERVER_HOST + '/VLT/api/frame/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getGenerate: (dir, frame) => {
      return $http.get(SERVER_HOST + '/VLT/api/frame/get_generate/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getAlgorithm: (dir, frame) => {
      return $http.get(SERVER_HOST + '/VLT/api/frame/get_algorithm/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getCheck: (dir, frame) => {
      return $http.get(SERVER_HOST + '/VLT/api/frame/get_check/' + dir + '/' + frame)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getServerStatus: (dir) => {
      return $http.post(SERVER_HOST + '/VLT/api/' + dir + '/get_server_status')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    stopInteriorServer: (url) => {
      return $http.post(SERVER_HOST + '/VLT/api/stop_interior_server', url)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    runInteriorServer: (dir, frameId) => {
      return $http.post(SERVER_HOST + '/VLT/api/' + dir + '/' + frameId + '/run_interior_server')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    generate: (dir, algorithm) => {
      return $http.get(SERVER_HOST + '/VLT/public/resources/' + dir + '/get_generate', {
          params: {algorithm: algorithm}
      })
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    repeat: (session) => {
      return $http.get(SERVER_HOST + '/VLT/public/resources/repeat', {
          params: {session: session}
        })
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    check: (dir, frameId, session, result) => {
      return $http.get(SERVER_HOST + '/VLT/public/resources/' + dir + '/' + frameId + '/get_check', {
          params: {session: session, instructions: result}
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