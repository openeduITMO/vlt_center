app.controller('UserCtrl', function ($scope, store, UserService) {
    $scope.isLogin = true;
    $scope.errors = {
      loginIsError: '',
      passwordIsError: '',
      registerRole: ''
    };
    $scope.roles = [
      {name: 'STUDENT'},
      {name: 'DEVELOPER'}
    ];
    $scope.user = {
      login: '',
      password: ''
    };
    $scope.role = "Выберите роль";

    $scope.resetVariables = function () {
      $scope.user.login = '';
      $scope.user.password = '';
      $scope.errors.loginIsError = '';
      $scope.errors.passwordIsError = '';
      $scope.errors.registerRole = '';
    }

    $scope.login = function () {
      if ($scope.user.login != '' && $scope.user.password != '') {
        UserService.login($scope.user)
          .then(res => {
              store.set('refreshJwtToken', res.refreshJwtToken);
              store.set('token', res.token);
              $scope.$parent.isAuthorized = true;
            }, err => {
            $scope.errors.loginIsError='Некорректные логин или пароль';
            $scope.errors.passwordIsError='Некорректные логин или пароль';
          }
          );
      } else {
        if ($scope.user.login == '') {
          $scope.errors.loginIsError = "Заполните поле"
        }
        if ($scope.user.password == '') {
          $scope.errors.passwordIsError = "Заполните поле"
        }
      }
    }

    $scope.register = function () {
      if ($scope.user.login != '' && $scope.user.password != '' && $scope.userRole != null) {
        UserService.register($scope.user, $scope.userRole.name)
          .then(res => {
            store.set('refreshJwtToken', res.refreshJwtToken);
            store.set('token', res.token);
            $scope.$parent.isAuthorized = true;
          });
      } else {
        if ($scope.user.login == '') {
          $scope.errors.loginIsError = "Заполните поле"
        }
        if ($scope.user.password == '') {
          $scope.errors.passwordIsError = "Заполните поле"
        }
        if ($scope.userRole == null) {
          $scope.errors.registerRole = "Заполните поле"
        }
      }
    }

  });