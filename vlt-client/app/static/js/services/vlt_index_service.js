app.factory('IndexService', function ($http, $q, SERVER_HOST) {
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";

  return {
    getVlList: () => {
      return $http.get(SERVER_HOST + '/VLT/api/get_list_vl')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getOtherVl: () => {
      return $http.get(SERVER_HOST + '/VLT/api/get_other_list_vl')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    declaration: (dirName) => {
      return $http.post(SERVER_HOST + '/VLT/api/declaration/' + dirName)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getUsers: (dir) => {
      return $http.get(SERVER_HOST + '/VLT/api/get_users/' + dir)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    addVl: (name) => {
      return $http.post(SERVER_HOST + '/VLT/api/add_vl', name)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getPropertyVl: (dir) => {
      return $http.get(SERVER_HOST + '/VLT/api/get_property_vl/' + dir)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    savePropertyVl: (dir, vl) => {
      return $http.post(SERVER_HOST + '/VLT/api/save_property_vl/' + dir, vl)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getLabratoryFame: (dir) => {
      return $http.post(SERVER_HOST + '/VLT/api/frame/get_labratory/' + dir)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getServersList: () => {
      return $http.get(SERVER_HOST + '/VLT/api/get_servers_list/')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    stopServer: (url) => {
      return $http.post(SERVER_HOST + '/VLT/api/stop_interior_server', url)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    }
  }
});