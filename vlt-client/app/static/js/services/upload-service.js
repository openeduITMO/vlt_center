app.factory('UploadService', function ($compile, $templateRequest, $http, $q) {
  var SERVER_HOST = 'http://localhost:8012';

  return {
    uploadFileToUrl: (file, dir) => {
      var fd = new FormData();
      fd.append('uploadfile', file);

      return $http.post(SERVER_HOST+"/VLT/api/upload-file/" + dir, fd, {
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