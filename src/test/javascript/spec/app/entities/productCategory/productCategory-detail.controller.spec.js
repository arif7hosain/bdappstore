'use strict';

describe('ProductCategory Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProductCategory, MockProduct, MockSoftwareCategory;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProductCategory = jasmine.createSpy('MockProductCategory');
        MockProduct = jasmine.createSpy('MockProduct');
        MockSoftwareCategory = jasmine.createSpy('MockSoftwareCategory');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProductCategory': MockProductCategory,
            'Product': MockProduct,
            'SoftwareCategory': MockSoftwareCategory
        };
        createController = function() {
            $injector.get('$controller')("ProductCategoryDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'appstoreApp:productCategoryUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
