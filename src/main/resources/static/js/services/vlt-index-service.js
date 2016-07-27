'use strict';
app.factory('IndexService', function ($http, $q) {
  return {
    getVlList: () => {
      return $http.get('/VLT/get_list_vl')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    addVl: (name) => {
      return $http.post('/VLT/add_vl', name)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getPropertyVl: (dir) => {
      return $http.get('/VLT/get_property_vl/' + dir)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    savePropertyVl: (dir, vl) => {
      return $http.post('/VLT/save_property_vl/' + dir, vl)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getLabratoryFame: (dir) => {
      return $http.post('/VLT/get_labratory_fame/' + dir)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    getServersList: () => {
      return $http.get('/VLT/get_servers_list/')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    stopServer: (url) => {
      return $http.post('/VLT/stop_interior_server', url)
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    }
  }
});