'use strict';

describe('Product Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProduct, MockCompanyInformation, MockServiceCategory;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProduct = jasmine.createSpy('MockProduct');
        MockCompanyInformation = jasmine.createSpy('MockCompanyInformation');
        MockServiceCategory = jasmine.createSpy('MockServiceCategory');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Product': MockProduct,
            'CompanyInformation': MockCompanyInformation,
            'ServiceCategory': MockServiceCategory
        };
        createController = function() {
            $injector.get('$controller')("ProductDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'appstoreApp:productUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
