describe('AppCtrl', function () {
  describe('isCurrentUrl', function () {
    var ctrl, scope, userGateway, response;

    beforeEach(function () {
      module('bank');
      userGateway = {};

      inject(function ($controller, $q, $rootScope) {
        scope = $rootScope.$new();
        response = $q.defer();
        ctrl = $controller('AppCtrl', {$scope: scope, userGateway: userGateway});
      });
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
