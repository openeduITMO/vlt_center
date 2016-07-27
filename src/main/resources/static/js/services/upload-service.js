'use strict';

app.factory('UploadService', function ($compile, $templateRequest, $http, $q) {
  return {
    uploadFileToUrl: (file, dir) => {
      var fd = new FormData();
      fd.append('uploadfile', file);

      return $http.post("/VLT/upload-file/" + dir, fd, {
          transformRequest: angular.identity,
          headers: {'Content-Type': undefined}
        })
        .then(res => {
            return res;
          },
          err => {
            return $q.reject(err);
          });
    }
  }
});