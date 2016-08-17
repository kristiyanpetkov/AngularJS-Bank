describe('AppCtrl', function () {
  describe('isCurrentUrl', function () {
    var ctrl, scope, userGateway, responseData, response;

    beforeEach(function () {
      module('bank');
      userGateway = {};
      responseData = {};

      inject(function ($controller, $q, $rootScope) {
        scope = $rootScope.$new();
        response = $q.defer();
        ctrl = $controller('AppCtrl', {$scope: scope, userGateway: userGateway});
      });
    });

    it('return all active sessions', function () {
      userGateway.getOnlineUsers = jasmine.createSpy("getOnlineUsers() spy").andReturn(response.promise);
      responseData = 5;
      ctrl.getActiveSessions();
      response.resolve(responseData);
      scope.$digest();
      expect(scope.onlineUsers).toEqual(5);
    });
    it('display current connected user', function () {
      userGateway.getCurrentUser = jasmine.createSpy("getCurrentUser() spy").andReturn(response.promise);
      var responseData = 'admin@abv.bg';
      ctrl.getCurrentUserEmail();
      response.resolve(responseData);
      scope.$digest();
      expect(scope.currentUser).toEqual("admin@abv.bg");
    });
  });
});
