app.directive('vlAvailable', function () {
  return {
    restrict: 'E',
    templateUrl: 'templates/elements/vl_available.html',
    replace: true,
    scope: true
  };
});

app.directive('vlCurrent', function () {
  return {
    restrict: 'E',
    templateUrl: 'templates/elements/vl_current.html',
    replace: true,
    scope: true
  };
});

app.directive('vlAll', function () {
  return {
    restrict: 'E',
    templateUrl: 'templates/elements/vl_all.html',
    replace: true,
    scope: true
  };
});