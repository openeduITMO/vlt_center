app.controller("iFrameCtrl", function ($scope, iFrameService) {
  var ifrm = $('iframe')[0].contentWindow;
  var SERVER_HOST = 'http://localhost:8012';
  iFrameService.getJs($scope.dirName, $scope.frameId)
    .then(res => {
      ifrm.document.write('<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>');
      ifrm.document.write('<script type="text/javascript" src="static/js/rlcp-ant.js"></script>');
      res.lib.forEach(function (name) {
        ifrm.document.write('<script type="text/javascript" src="' + SERVER_HOST + '/VLT/VLabs/' + res.dirName + '/tool/js/lib/' + name + '"></script>');
      });
      res.dev.forEach(function (name) {
        ifrm.document.write('<script type="text/javascript" src="' + SERVER_HOST + '/VLT/VLabs/' + res.dirName + '/tool/js/dev/' + name + '"></script>');
      });
      iFrameService.getCss($scope.dirName, $scope.frameId)
        .then(res => {
          res.lib.forEach(function (name) {
            ifrm.document.write('<link rel="stylesheet" href="' + SERVER_HOST + '/VLT/VLabs/' + res.dirName + '/tool/css/lib/' + name + '"/>');
          });
          res.dev.forEach(function (name) {
            ifrm.document.write('<link rel="stylesheet" href="' + SERVER_HOST + '/VLT/VLabs/' + res.dirName + '/tool/css/dev/' + name + '"/>');
          });
        });
      ifrm.document.write('<div id="jsLab"></div>');
      ifrm.document.write('<input type="hidden" id="preGeneratedCode" name="preGeneratedCode" ng-value="' + $scope.generate.code + '"/>');
      ifrm.document.write('<input type="hidden" value="calcfake" id="calculatedCode"/>');
      ifrm.document.write('<input type="hidden" value="calcfake" id="calculatedText"/>');
      ifrm.document.write('<input type="hidden" value="prevsolfake" id="previousSolution"/>');
      ifrm.document.write('<script type="text/javascript">' +
        'Vlab.init();' +
        'function setGenerateCode(val){$("#preGeneratedCode").val(val) };' +
        'function setPreviousSolution(val){$("#previousSolution").val(val) };' +
        '</script>');
    });
});