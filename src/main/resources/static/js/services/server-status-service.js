'use strict';
vl.factory('ServerStatusService', function ($http, $q) {
  return {
    checkTypeServer: () => {
      return $http.post('/VLT/get_type_server')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },

    checkServerStatus: () => {
      return $http.post('/VLT/get_server_status')
        .then(res => {
            return res.data;
          },
          err => {
            return $q.reject(err);
          });
    },
  }
});