'use strict';

describe('ProductPortfolio Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProductPortfolio, MockProduct;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProductPortfolio = jasmine.createSpy('MockProductPortfolio');
        MockProduct = jasmine.createSpy('MockProduct');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProductPortfolio': MockProductPortfolio,
            'Product': MockProduct
        };
        createController = function() {
            $injector.get('$controller')("ProductPortfolioDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'appstoreApp:productPortfolioUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
