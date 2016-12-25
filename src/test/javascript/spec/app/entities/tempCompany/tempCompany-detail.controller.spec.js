'use strict';

describe('TempCompany Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockTempCompany, MockCountry, MockServiceCategory, MockUpazila;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockTempCompany = jasmine.createSpy('MockTempCompany');
        MockCountry = jasmine.createSpy('MockCountry');
        MockServiceCategory = jasmine.createSpy('MockServiceCategory');
        MockUpazila = jasmine.createSpy('MockUpazila');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'TempCompany': MockTempCompany,
            'Country': MockCountry,
            'ServiceCategory': MockServiceCategory,
            'Upazila': MockUpazila
        };
        createController = function() {
            $injector.get('$controller')("TempCompanyDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'appstoreApp:tempCompanyUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
