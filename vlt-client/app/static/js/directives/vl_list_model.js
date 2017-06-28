app.directive('vlAll', function () {
  return {
    restrict: 'E',
    templateUrl: 'templates/vl_list/vl_all.html',
    replace: true,
    scope: true
  };
});

app.directive('vlAvailable', function () {
  return {
    restrict: 'E',
    templateUrl: 'templates/vl_list/vl_available.html',
    replace: true,
    scope: true
  };
});

app.directive('vlCurrent', function () {
  return {
    restrict: 'E',
    templateUrl: 'templates/vl_list/vl_current.html',
    replace: true,
    scope: true
  };
});

app.directive('vlOther', function () {
  return {
    controller: 'otherController',
    restrict: 'E',
    templateUrl: 'templates/vl_list/vl_other.html',
    replace: true,
    scope: true
  };
});

app.directive('vlApprobation', function () {
  return {
    restrict: 'E',
    templateUrl: 'templates/vl_list/vl_approbation.html',
    replace: true,
    scope: true
  };
});

app.directive('dropdownList',function($timeout, IndexService){
  return {
    restrict: 'E',
    scope: {
      itemsList: '=',
      studentList: '=',
      currentDir: '=',
      placeholder: '@'
    },
    template: '<input class="select-input" type="text" ng-model="search" placeholder="{{placeholder}}" />' +
    '<div class="search-item-list"><ul class="list">' +
    '<li ng-repeat="item in itemsList | filter:search" ng-click="chooseItem(item)">{{item.name}}' +
    '</li>' +
    '</ul></div>',
    link: function(scope, el, attr){
      var $listContainer = angular.element( el[0].querySelectorAll('.search-item-list')[0] );
      el.find('input').bind('focus',function(){
        $listContainer.addClass('show');
      });
      el.find('input').bind('blur',function(){
        $timeout(function(){ $listContainer.removeClass('show') }, 200);
      });

      scope.chooseItem = function(item ){
        scope.search = item.name;
        scope.currentDir = item.dirName;
        $listContainer.removeClass('show');
        IndexService.getUsers(item.dirName)
          .then(res => {
            _.forEach(res, function(key, value) {
              key.sort(compareDate);
            });
            scope.studentList = res;
          });
      };

      function compareDate(a, b) {
        if (a.startDate > b.startDate)
          return -1;
        else if (a.startDate < b.startDate)
          return 1;
        else
          return 0;
      }
    }
  }
});