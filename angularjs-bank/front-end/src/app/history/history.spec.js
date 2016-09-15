/**
 * Created by kristiqn.l.petkov@gmail.com on 16.09.16.
 */
describe('HistoryCtrl', function () {
  var ctrl, historyGateway, scope, response;

  beforeEach(function () {
    module('bank.history');
    historyGateway = {};

    inject(function ($controller, $q, $rootScope) {
      response = $q.defer();
      scope = $rootScope.$new();
      ctrl = $controller('HistoryCtrl', {
        historyGateway: historyGateway
      });
    });
  });

  it('return first page transactions', function () {
    historyGateway.getHistory = jasmine.createSpy("getHistory() spy").andReturn(response.promise);
    ctrl.getHistoryLog(1);
    response.resolve([{id: 1}, {id: 2}, {id: 3}]);
    scope.$digest();
    expect(ctrl.page).toEqual(1);
    expect(ctrl.transactions).toEqual([{id: 1}, {id: 2}, {id: 3}]);
  });

  it('return empty list of transactions with negative page number', function () {
    historyGateway.getHistory = jasmine.createSpy("getHistory() spy").andReturn(response.promise);
    ctrl.getHistoryLog(-5);
    response.resolve([]);
    scope.$digest();
    expect(ctrl.transactions).toBeUndefined();
    expect(ctrl.page).toBeUndefined();
  });

  it('return empty list of transactions with huge page number', function () {
    historyGateway.getHistory = jasmine.createSpy("getHistory() spy").andReturn(response.promise);
    ctrl.getHistoryLog(9999999999999999999999999999999);
    response.resolve([]);
    scope.$digest();
    expect(ctrl.transactions).toBeUndefined();
    expect(ctrl.page).toBeUndefined();
  });
});