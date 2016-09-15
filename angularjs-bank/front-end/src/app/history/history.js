/**
 * Created by kristiqn.l.petkov@gmail.com on 16.09.16.
 */
angular.module('bank.history', [
  'ui.router'
])

  .config(function config($stateProvider) {
    $stateProvider.state('history', {
      url: '/history',
      views: {
        "main": {
          controller: 'HistoryCtrl',
          controllerAs: 'vm',
          templateUrl: 'history/history.tpl.html'
        }
      },
      data: {pageTitle: 'HISTORY'}
    });
  })

  .service('historyGateway', function (httpRequest, bankEndpoints) {
    return {
      getHistory: function (pageNumber) {
        return httpRequest.get(bankEndpoints.HISTORY, {page: pageNumber});
      }
    };
  })

  .controller('HistoryCtrl', function HistoryCtrl(historyGateway) {
    var vm = this;

    vm.getHistoryLog = function (pageNumber) {
      historyGateway.getHistory(pageNumber).then(
        function onSuccess(transactions) {
          if (transactions.length === 0) {
            return;
          }
          vm.page = pageNumber;
          vm.transactions = transactions;
        }
      );
    };
  });