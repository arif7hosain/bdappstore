'use strict';

describe('ComAddress Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockComAddress, MockComBranch, MockUpazila;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockComAddress = jasmine.createSpy('MockComAddress');
        MockComBranch = jasmine.createSpy('MockComBranch');
        MockUpazila = jasmine.createSpy('MockUpazila');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ComAddress': MockComAddress,
            'ComBranch': MockComBranch,
            'Upazila': MockUpazila
        };
        createController = function() {
            $injector.get('$controller')("ComAddressDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'appstoreApp:comAddressUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
