/**
 * Created by kristiqn.l.petkov@gmail.com on 07.09.16.
 */
describe('UserAccountCtrl', function () {
  var response, ctrl, operationGateway, scope;

  beforeEach(function () {
    module('bank.useraccount');
    operationGateway = {};

    inject(function ($controller, $q, $rootScope) {
      response = $q.defer();
      scope = $rootScope.$new();
      ctrl = $controller('UserAccountCtrl', {operationGateway: operationGateway});
    });
  });

  it('make a deposit', function () {
    operationGateway.doTransaction = jasmine.createSpy("doTransaction() spy").andReturn(response.promise);
    ctrl.doTransaction({amount: 200.00, operation: 'Deposit'});
    response.resolve({errorCode: 600});
    expect(operationGateway.doTransaction).toHaveBeenCalledWith({amount: 200.00, operation: 'Deposit'});
    scope.$digest();
    expect(ctrl.successMsg).toEqual('Transaction successful!');
  });

  it('fail to make a deposit with incorrect amount format', function () {
    operationGateway.doTransaction = jasmine.createSpy("doTransaction() spy").andReturn(response.promise);
    ctrl.doTransaction({amount: 'blabla', operation: 'Deposit'});
    response.reject({errorCode: 422});
    expect(operationGateway.doTransaction).toHaveBeenCalledWith({amount: 'blabla', operation: 'Deposit'});
    scope.$digest();
    expect(ctrl.errMsg).toEqual('Amount not valid. Example for a valid amount: 123.23 or 100.00');
  });

  it('fail to make a deposit with no amount entered', function () {
    operationGateway.doTransaction = jasmine.createSpy("doTransaction() spy").andReturn(response.promise);
    ctrl.doTransaction({amount: '', operation: 'Deposit'});
    response.reject({errorCode: 400});
    expect(operationGateway.doTransaction).toHaveBeenCalledWith({amount: '', operation: 'Deposit'});
    scope.$digest();
    expect(ctrl.errMsg).toEqual('No amount entered!');
  });

  it('fail to make a withdraw with not enough funds error message', function () {
    operationGateway.doTransaction = jasmine.createSpy("doTransaction() spy").andReturn(response.promise);
    ctrl.doTransaction({amount: 2000.00, operation: 'Withdraw'});
    response.reject({errorCode: 406});
    expect(operationGateway.doTransaction).toHaveBeenCalledWith({amount: 2000.00, operation: 'Withdraw'});
    scope.$digest();
    expect(ctrl.errMsg).toEqual('Not enough funds!');
  });
});