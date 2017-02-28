'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('serviceCategory', {
                parent: 'entity',
                url: '/serviceCategorys',
                data: {
                    authorities: [],
                    pageTitle: 'ServiceCategorys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/serviceCategory/serviceCategorys.html',
                        controller: 'ServiceCategoryController'
                    }
                },
                resolve: {
                }
            })
            .state('serviceCategory.detail', {
                parent: 'entity',
                url: '/serviceCategory/{id}',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                    pageTitle: 'ServiceCategory'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/serviceCategory/serviceCategory-detail.html',
                        controller: 'ServiceCategoryDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ServiceCategory', function($stateParams, ServiceCategory) {
                        return ServiceCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('serviceCategory.new', {
                parent: 'serviceCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/serviceCategory/serviceCategory-dialog.html',
                        controller: 'ServiceCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    serviceName: null,
                                    serviceDescription: null,
                                    shortDescription: null,
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
                        $state.go('serviceCategory', null, { reload: true });
                    }, function() {
                        $state.go('serviceCategory');
                    })
                }]
            })
            .state('serviceCategory.edit', {
                parent: 'serviceCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/serviceCategory/serviceCategory-dialog.html',
                        controller: 'ServiceCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ServiceCategory', function(ServiceCategory) {
                                return ServiceCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('serviceCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('serviceCategory.delete', {
                parent: 'serviceCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER','ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/serviceCategory/serviceCategory-delete-dialog.html',
                        controller: 'ServiceCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ServiceCategory', function(ServiceCategory) {
                                return ServiceCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('serviceCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
