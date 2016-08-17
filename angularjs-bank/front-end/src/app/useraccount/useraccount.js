/**
 * Created by kristiqn.l.petkov@gmail.com on 16.08.16.
 */
angular.module('bank.useraccount', [
  'ui.router',
  'bank.common.http'
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

  .service('operationGateway', function (httpRequest, bankEndpoints) {
    return {
      doTransaction: function (bankOperation) {
        return httpRequest.post(bankEndpoints.OPERATION, bankOperation);
      }
    };
  })

  .controller('UserAccountCtrl', function UserAccountCtrl($rootScope, httpRequest, operationGateway) {
    var vm = this;

    vm.doTransaction = function (bankOperation) {
      vm.successMsg = null;
      vm.errMsg = null;
      operationGateway.doTransaction(bankOperation).then(
        function onSuccess(currentBalance) {
          vm.successMsg = 'Transaction successful!';
          bankOperation.amount = '';
          $rootScope.currentBalance = currentBalance;
        }, function onError(data) {
          if (data.errorCode == 422) {
            vm.errMsg = 'Amount not valid. Example for a valid amount: 123.23 or 100.00';
          }
          if (data.errorCode == 400) {
            vm.errMsg = 'No amount entered!';
          }
          if (data.errorCode == 406) {
            vm.errMsg = 'Not enough funds!';
          }
          bankOperation.amount = '';
        });
    };
  });


