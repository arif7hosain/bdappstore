'use strict';

describe('CompanyInformation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCompanyInformation, MockCountry, MockServiceCategory, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCompanyInformation = jasmine.createSpy('MockCompanyInformation');
        MockCountry = jasmine.createSpy('MockCountry');
        MockServiceCategory = jasmine.createSpy('MockServiceCategory');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'CompanyInformation': MockCompanyInformation,
            'Country': MockCountry,
            'ServiceCategory': MockServiceCategory,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("CompanyInformationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'appstoreApp:companyInformationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
