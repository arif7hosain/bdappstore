'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('softwareCategory', {
                parent: 'entity',
                url: '/softwareCategorys',
                data: {
                    authorities: ['ROLE_ADMIN,ROLE_USER'],
                    pageTitle: 'SoftwareCategorys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/softwareCategory/softwareCategorys.html',
                        controller: 'SoftwareCategoryController'
                    }
                },
                resolve: {
                }
            })
            .state('softwareCategory.detail', {
                parent: 'entity',
                url: '/softwareCategory/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'SoftwareCategory'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/softwareCategory/softwareCategory-detail.html',
                        controller: 'SoftwareCategoryDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'SoftwareCategory', function($stateParams, SoftwareCategory) {
                        return SoftwareCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('softwareCategory.new', {
                parent: 'softwareCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/softwareCategory/softwareCategory-dialog.html',
                        controller: 'SoftwareCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    categoryName: null,
                                    description: null,
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
                        $state.go('softwareCategory', null, { reload: true });
                    }, function() {
                        $state.go('softwareCategory');
                    })
                }]
            })
            .state('softwareCategory.edit', {
                parent: 'softwareCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/softwareCategory/softwareCategory-dialog.html',
                        controller: 'SoftwareCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SoftwareCategory', function(SoftwareCategory) {
                                return SoftwareCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('softwareCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('softwareCategory.delete', {
                parent: 'softwareCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/softwareCategory/softwareCategory-delete-dialog.html',
                        controller: 'SoftwareCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SoftwareCategory', function(SoftwareCategory) {
                                return SoftwareCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('softwareCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
