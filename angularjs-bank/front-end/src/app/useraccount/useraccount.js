/**
 * Created by kristiqn.l.petkov@gmail.com on 16.08.16.
 */
angular.module('bank.useraccount', [
  'ui.router'
])

  .config(function config($stateProvider) {
    $stateProvider.state('useraccount', {
      url: '/useraccount',
      views: {
        "main": {
          controller: 'UserAccountCtrl',
          controllerAs: 'vm',
          templateUrl: 'useraccount/useraccount.tpl.html'
        }
      },
      data: {pageTitle: 'User Account Homepage'}
    });
  })

  .service('userGateway', function (httpRequest, bankEndpoints) {
    return {
      getCurrentUser: function () {
        return httpRequest.get(bankEndpoints.USER);
      }
    };
  })

  .controller('UserAccountCtrl', function UserAccountCtrl($scope, $rootScope, httpRequest, userGateway) {
    var vm = this;
    var getCurrentUserEmail = function () {
      userGateway.getCurrentUser().then(
        function onSuccess(successData) {
          vm.currentUser = 'You have been logged as: ' + successData;
        }
      );
    };

    getCurrentUserEmail();
  });