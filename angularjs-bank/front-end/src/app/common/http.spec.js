/**
 * Created by kristiqn.l.petkov@gmail.com on 25.08.16.
 */
describe('httpRequest', function () {
  beforeEach(module('bank.common.http'));
  var $httpBackend;
  var httpRequest;
  var $scope = {};
  var user = {
    username: 'kristiqn.l.petkov@gmail.com',
    password: 123456
  };


  beforeEach(inject(function (_$httpBackend_, _httpRequest_, _$rootScope_) {
    $httpBackend = _$httpBackend_;
    httpRequest = _httpRequest_;
    $scope = _$rootScope_.$new();
  }));

  describe('httpRequest should ', function () {
    it('send get request', function () {
      $httpBackend.when('GET', '/whatismyname')
        .respond('Kristiyan');

      httpRequest.get('/whatismyname').then(function (data) {
        $scope.name = data;
      });

      $httpBackend.flush();
      $scope.$apply();
      expect(httpRequest.send);
      expect($scope.name).toEqual('Kristiyan');
    });

    it('send post request', function () {
      $httpBackend.when('POST', '/login', user)
        .respond('Login Successful');

      httpRequest.post('/login', user).then(function (data) {
        $scope.message = data;
      });

      $httpBackend.flush();
      $scope.$apply();
      expect(httpRequest.send);
      expect($scope.message).toEqual('Login Successful');
    });

    it('send put request', function () {
      $httpBackend.when('PUT', '/article/1234', user)
        .respond('Article 1234 updated successfully!');

      httpRequest.put('/article/1234', user).then(function (data) {
        $scope.message = data;
      });

      $httpBackend.flush();
      $scope.$apply();
      expect(httpRequest.send);
      expect($scope.message).toEqual('Article 1234 updated successfully!');
    });

    it('send delete request', function () {
      $httpBackend.when('DELETE', '/myorders/2')
        .respond('Order deleted successful!');

      httpRequest.del('/myorders/2').then(function (data) {
        $scope.message = data;
      });

      $httpBackend.flush();
      $scope.$apply();
      expect(httpRequest.send);
      expect($scope.message).toEqual('Order deleted successful!');
    });

    it('return an error message: Page not found!', function () {
      $httpBackend.when('GET', '/whatismyip')
        .respond(404, 'Page not found!');

      httpRequest.get('/whatismyip').then(function (data) {
      }, function (error) {
        $scope.errorMsg = error;
      });

      $httpBackend.flush();
      $scope.$apply();
      expect(httpRequest.send);
      expect($scope.errorMsg).toEqual('Page not found!');
    });
  });
});