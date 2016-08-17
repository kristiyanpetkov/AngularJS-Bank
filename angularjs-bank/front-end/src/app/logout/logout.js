/**
 * Created by kristiqn.l.petkov@gmail.com on 12.09.16.
 */
angular.module('bank.logout', [
  'bank.common.http',
  'ngCookies'
])

  .config(function config($stateProvider) {
    $stateProvider.state('logout', {
      url: '/logout',
      views: {
        "main": {
          controller: 'LogoutCtrl',
          controllerAs: 'vm'
        }
      },
      data: {pageTitle: 'Logout'}
    });
  })

  .service('logoutGateway', function (httpRequest, bankEndpoints) {
    return {
      logout: function (sessionId) {
        var req = {sessionId: sessionId};
        return httpRequest.post(bankEndpoints.LOGOUT, req);
      }
    };
  })

  .service('logoutService', function ($cookies, logoutGateway, $state, $rootScope) {
    return {
      logoutUser: function () {
        var sessionId = $cookies.get('sessionId');
        logoutGateway.logout(sessionId).then(
          function onSuccess() {
            $cookies.remove('sessionId');
            $rootScope.isLogged = false;
            $rootScope.currentUser = undefined;
            $state.go('home');
          });
      }
    };
  });

