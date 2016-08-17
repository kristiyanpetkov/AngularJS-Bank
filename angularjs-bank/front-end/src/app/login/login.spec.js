/**
 * Created by kristiqn.l.petkov@gmail.com on 01.09.16.
 */
describe('LoginCtrl', function () {
  var response, ctrl, loginGateway, state, scope, fakeResponse, loginUser, cookies;

  beforeEach(function () {
    module('bank.login');
    ctrl = {};
    loginGateway = {};
    state={};
    cookies = {};
    scope = {};
    fakeResponse = {errorCode: null};
    loginUser = {
      email: 'kristiqn.l.petkov@gmail.com',
      password: '123456'
    };

    inject(function ($controller, $q, _$rootScope_) {
      response = $q.defer();
      scope = _$rootScope_.$new();
      ctrl = $controller('LoginCtrl', {loginGateway: loginGateway, $state: state, $cookies: cookies});
    });
  });

  it('log in an existing user', function () {
    cookies.get = jasmine.createSpy("get() spy").andReturn("sessionId");
    cookies.put = jasmine.createSpy("put() spy");
    loginGateway.loginUser = jasmine.createSpy("loginUser() spy").andReturn(response.promise);
    state.go = jasmine.createSpy("go() spy");
    ctrl.submit(loginUser);
    response.resolve(fakeResponse);
    scope.$digest();
    expect(state.go).toHaveBeenCalledWith("useraccount");
  });

  it('fail with an invalid data error message', function () {
    loginGateway.loginUser = jasmine.createSpy("loginUser() spy").andReturn(response.promise);
    fakeResponse.errorCode = 400;
    ctrl.submit(loginUser);
    response.reject(fakeResponse);
    scope.$digest();
    expect(ctrl.errorMsg).toEqual('Email or password invalid format!');
  });

  it('fail with wrong email or password error message', function () {
    loginGateway.loginUser = jasmine.createSpy("loginUser() spy").andReturn(response.promise);
    fakeResponse.errorCode = 406;
    ctrl.submit(loginUser);
    response.reject(fakeResponse);
    scope.$digest();
    expect(ctrl.errorMsg).toEqual('Wrong email or password!');
  });

  it('redirect to useraccount',function () {
    cookies.get = jasmine.createSpy("get() spy").andReturn("sessionId");
    state.go = jasmine.createSpy("go() spy");
    ctrl.init();
    expect(state.go).toHaveBeenCalledWith("useraccount");
  });
});