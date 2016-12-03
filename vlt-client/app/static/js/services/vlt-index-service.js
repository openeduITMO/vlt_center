app.factory('IndexService', function ($http, $q) {
  var SERVER_HOST = 'http://localhost:8012';
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";
  //$http.defaults.headers.common["X-Authorization"] = "Bearer "+store.get('token');

  return {
    getVlList: () => {
      return $http.get(SERVER_HOST+'/VLT/api/get_list_vl')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    addVl: (name) => {
      return $http.post(SERVER_HOST+'/VLT/api/add_vl', name)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getPropertyVl: (dir) => {
      return $http.get(SERVER_HOST+'/VLT/api/get_property_vl/' + dir)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    savePropertyVl: (dir, vl) => {
      return $http.post(SERVER_HOST+'/VLT/api/save_property_vl/' + dir, vl)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getLabratoryFame: (dir) => {
      return $http.post(SERVER_HOST+'/VLT/api/get_labratory_fame/' + dir)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getServersList: () => {
      return $http.get(SERVER_HOST+'/VLT/api/get_servers_list/')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    stopServer: (url) => {
      return $http.post(SERVER_HOST+'/VLT/stop_interior_server', url)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    }
  }
});