app.directive('loginForm', function() {
  return {
    restrict: 'E',
    templateUrl: 'templates/login.html'
  };
});

app.directive('vltIndex', function() {
  return {
    restrict: 'E',
    templateUrl: 'templates/vlt.html'
  };
});

//app.directive('loginForm', function($compile, $templateRequest) {
//  return {
//    link: function($scope, $element, $attr){
//      if(!$attr.hasOwnProperty("authorized")){
//        $('.user-form-authorized').empty();
//
//        $templateRequest('templates/login.html').then(html => {
//          angular.element($('.user-form-authorized')).append($compile(html)($scope));
//        });
//      }
//    }
//  };
//});
//
//app.directive('vltIndex', function($compile, $templateRequest) {
//  return {
//    link: function($scope, $element, $attr){
//      if($attr.hasOwnProperty("authorized")){
//        $('.vlt-form-place').empty();
//
//        $templateRequest('templates/vlt.html').then(html => {
//          angular.element($('.vlt-form-place')).append($compile(html)($scope));
//        });
//      }
//    }
//  };
//});

app.directive('fileModel', ['$parse', function ($parse) {
  return {
    restrict: 'A',
    link: function(scope, element, attrs) {
      var model = $parse(attrs.fileModel);
      var modelSetter = model.assign;

      element.bind('change', function(){
        scope.$apply(function(){
          modelSetter(scope, element[0].files[0]);
        });
      });
    }
  };
}]);