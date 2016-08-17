/**
 ** Created by Kristiyan Petkov  <kristiqn.l.petkov@gmail.com> on 15.08.16.
 */
angular.module('bank.login', [
  'ui.router',
  'bank.common.http',
  'ngCookies'
])

  .config(function config($stateProvider) {
    $stateProvider.state('login', {
      url: '/login?msg',
      views: {
        "main": {
          controller: 'LoginCtrl',
          controllerAs: 'vm',
          templateUrl: 'login/login.tpl.html',
          params: {
            msg: null
          }
        }
      },
      data: {pageTitle: 'LOGIN'}
    });
  })


  .service('loginGateway', function (httpRequest, bankEndpoints) {
    return {
      loginUser: function (loginUser) {
        return httpRequest.post(bankEndpoints.LOGIN, loginUser);
      }
    };
  })

  .controller('LoginCtrl', function LoginCtrl($http, $state, $rootScope, httpRequest, $stateParams, loginGateway, $cookies) {
    var vm = this;

    vm.init = function () {
      if ($cookies.get('sessionId') !== undefined) {
        $state.go('useraccount');
      }
    };

    vm.successfulMsg = $stateParams.msg;

    vm.submit = function (loginUser) {
      vm.errorMsg = '';
      loginGateway.loginUser(loginUser).then(
        function onSuccess(successData) {
          var expiresDate = new Date();
          expiresDate.setTime(expiresDate.getTime() + 5 * 60 * 1000);
          $cookies.put('sessionId', successData, {'expires': expiresDate});
          $rootScope.currentUser = loginUser.email;
          $rootScope.isLogged = true;
          $state.go('useraccount');
        }, function onError(errorData) {
          if (errorData.errorCode == 406) {
            vm.errorMsg = 'Wrong email or password!';
          }
          if (errorData.errorCode == 400) {
            vm.errorMsg = 'Email or password invalid format!';
          }
        });
    };
  });