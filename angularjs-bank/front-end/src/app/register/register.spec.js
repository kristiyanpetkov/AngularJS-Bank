/**
 * Created by kristiqn.l.petkov@gmail.com on 25.08.16.
 */
describe('RegisterCtrl', function () {
  var response, ctr, registerGateway, scope, state, fakeResponse, user;

  beforeEach(function () {
    module('bank.register');
    ctrl = {};
    registerGateway = {};
    scope = {};
    state = {};
    fakeResponse = {errorCode: null};
    user = {
      username: 'Kristiyan',
      email: 'kristiqn.l.petkov@gmail.com',
      password: '123456',
      confirmPassword: '123456'
    };
    inject(function ($controller, $state, $q, $rootScope) {
      state = $state;
      response = $q.defer();
      scope = $rootScope.$new();
      ctrl = $controller('RegisterCtrl', {registerGateway: registerGateway, $state: state});
    });
  });

  it('register a new user', function () {
    registerGateway.registerUser = jasmine.createSpy("registerUser() spy").andReturn(response.promise);
    state.go = jasmine.createSpy("go() spy");
    ctrl.register(user);
    response.resolve(fakeResponse);
    scope.$digest();
    expect(ctrl.successMessage).toEqual('Registration successful!');
  });

  it('fail with an user already exist error message', function () {
    registerGateway.registerUser = jasmine.createSpy("registerUser() spy").andReturn(response.promise);
    fakeResponse.errorCode = 403;
    ctrl.register(user);
    response.reject(fakeResponse);
    scope.$digest();
    expect(ctrl.errorMsg).toEqual('User with such an email already exist!');
  });

  it('fail with an incorrect data error message', function () {
    registerGateway.registerUser = jasmine.createSpy("registerUser() spy").andReturn(response.promise);
    fakeResponse.errorCode = 400;
    ctrl.register(user);
    response.reject(fakeResponse);
    scope.$digest();
    expect(ctrl.errorMsg).toEqual('Input data is not in a valid format! Username and password should be between 6-16 characters and can contain only letters and digits.');
  });
});