app.directive('loginForm', function() {
  return {
    restrict: 'E',
    controller: 'UserCtrl',
    templateUrl: 'templates/login.html'
  };
});

app.directive('vltIndex', function() {
  return {
    restrict: 'E',
    controller: 'IndexCtrl',
    templateUrl: 'templates/vlt.html'
  };
});

app.directive('headermenu', function () {
  return {
    restrict: 'E',
    scope: {
      authorized: '='
    },
    controller : "@",
    name:"controllerName",
    link: function ($scope) {
      $scope.$watch('authorized', function (authorized) {
        if (authorized) {
          $scope.dynamicTemplateUrl = 'templates/vlt.html';
        } else {
          $scope.dynamicTemplateUrl = 'templates/login.html';
        }
      });
    },

    template: '<ng-include src="dynamicTemplateUrl"></ng-include>'
  };
});

app.directive('fileModel', ['$parse', function ($parse) {
  return {
    restrict: 'A',
    link: function (scope, element, attrs) {
      var model = $parse(attrs.fileModel);
      var modelSetter = model.assign;

      element.bind('change', function () {
        scope.$apply(function () {
          modelSetter(scope, element[0].files[0]);
        });
      });
    }
  };
}]);