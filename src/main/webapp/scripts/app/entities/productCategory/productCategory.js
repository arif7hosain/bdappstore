'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('productCategory', {
                parent: 'entity',
                url: '/productCategorys',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ProductCategorys'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productCategory/productCategorys.html',
                        controller: 'ProductCategoryController'
                    }
                },
                resolve: {
                }
            })
            .state('productCategory.detail', {
                parent: 'entity',
                url: '/productCategory/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ProductCategory'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productCategory/productCategory-detail.html',
                        controller: 'ProductCategoryDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ProductCategory', function($stateParams, ProductCategory) {
                        return ProductCategory.get({id : $stateParams.id});
                    }]
                }
            })
            .state('productCategory.new', {
                parent: 'productCategory',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productCategory/productCategory-dialog.html',
                        controller: 'ProductCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    comments: null,
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
                        $state.go('productCategory', null, { reload: true });
                    }, function() {
                        $state.go('productCategory');
                    })
                }]
            })
            .state('productCategory.edit', {
                parent: 'productCategory',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productCategory/productCategory-dialog.html',
                        controller: 'ProductCategoryDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProductCategory', function(ProductCategory) {
                                return ProductCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('productCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('productCategory.delete', {
                parent: 'productCategory',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productCategory/productCategory-delete-dialog.html',
                        controller: 'ProductCategoryDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProductCategory', function(ProductCategory) {
                                return ProductCategory.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('productCategory', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
