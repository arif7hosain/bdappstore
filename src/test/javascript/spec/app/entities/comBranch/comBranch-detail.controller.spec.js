'use strict';

describe('ComBranch Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockComBranch, MockCompanyInformation, MockServiceCategory;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockComBranch = jasmine.createSpy('MockComBranch');
        MockCompanyInformation = jasmine.createSpy('MockCompanyInformation');
        MockServiceCategory = jasmine.createSpy('MockServiceCategory');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ComBranch': MockComBranch,
            'CompanyInformation': MockCompanyInformation,
            'ServiceCategory': MockServiceCategory
        };
        createController = function() {
            $injector.get('$controller')("ComBranchDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'appstoreApp:comBranchUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
