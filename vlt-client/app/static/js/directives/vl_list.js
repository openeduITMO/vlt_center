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

app.directive('vlOther', function () {
  return {
    controller: 'otherController',
    restrict: 'E',
    templateUrl: 'templates/elements/vl_other.html',
    replace: true,
    scope: true
  };
});