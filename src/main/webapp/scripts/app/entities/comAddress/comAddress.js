'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('comAddress', {
                parent: 'entity',
                url: '/addresses',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ComAddresss'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/comAddress/comAddresss.html',
                        controller: 'ComAddressController'
                    }
                },
                resolve: {
                }
            })
            .state('comAddress.detail', {
                parent: 'entity',
                url: '/Address/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ComAddress'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/comAddress/comAddress-detail.html',
                        controller: 'ComAddressDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ComAddress', function($stateParams, ComAddress) {
                        return ComAddress.get({id : $stateParams.id});
                    }]
                }
            })
            .state('comAddress.new', {
                parent: 'comAddress',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/comAddress/comAddress-dialog.html',
                        controller: 'ComAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    addressType: null,
                                    officePhone: null,
                                    contactNumber: null,
                                    postalCode: null,
                                    house: null,
                                    RoadNo: null,
                                    city: null,
                                    CreatedDate: null,
                                    updatedDate: null,
                                    createBy: null,
                                    updatedBy: null,
                                    isActive: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('comAddress', null, { reload: true });
                    }, function() {
                        $state.go('comAddress');
                    })
                }]
            })
            .state('comAddress.edit', {
                parent: 'comAddress',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/comAddress/comAddress-dialog.html',
                        controller: 'ComAddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ComAddress', function(ComAddress) {
                                return ComAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('comAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('comAddress.delete', {
                parent: 'comAddress',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/comAddress/comAddress-delete-dialog.html',
                        controller: 'ComAddressDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ComAddress', function(ComAddress) {
                                return ComAddress.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('comAddress', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
