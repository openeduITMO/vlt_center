app.factory('UploadService', function ($compile, $templateRequest, $http, $q) {
  var SERVER_HOST = 'http://localhost:8012';
  $http.defaults.headers.common["Accept"] = "application/json";
  $http.defaults.headers.common["Content-Type"] = "application/json";
  $http.defaults.headers.common["Cache-Control"] = "Cache-Control";
  $http.defaults.headers.common["X-Requested-With"] = "XMLHttpRequest";

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