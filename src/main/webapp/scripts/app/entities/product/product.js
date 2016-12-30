'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('product', {
                parent: 'entity',
                url: '/products',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Products'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/product/products.html',
                        controller: 'ProductController'
                    }
                },
                resolve: {
                }
            })
            .state('product.detail', {
                parent: 'entity',
                url: '/product/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Product'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/product/product-detail.html',
                        controller: 'ProductDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Product', function($stateParams, Product) {
                        return Product.get({id : $stateParams.id});
                    }]
                }
            })
            .state('product.view', {
                parent: 'entity',
                url: '/publisher/product/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Preview full software details'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/product/view-product.html',
                        controller: 'ViewProduct'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Product', function($stateParams, Product) {
                        return Product.get({id : $stateParams.id});
                    }]
                }
            })
            .state('product.new', {
                parent: 'product',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/product/product-dialog.html',
                        controller: 'ProductDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    productTitle: null,
                                    productDescription: null,
                                    productType: null,
                                    Currency: null,
                                    price: null,
                                    durationType: null,
                                    isFurtherDevelopment: null,
                                    liveUrl: null,
                                    additionalLink: null,
                                    isAvailable: null,
                                    createdDate: null,
                                    updatedDate: null,
                                    createBy: null,
                                    updatedBy: null,
                                    isActive: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('product', null, { reload: true });
                    }, function() {
                        $state.go('product');
                    })
                }]
            })

//            .state('product.edit', {
//                parent: 'product',
//                url: '/{id}/edit',
//                data: {
//                    authorities: ['ROLE_USER'],
//                },
//                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
//                    $uibModal.open({
//                        templateUrl: 'scripts/app/entities/product/product-dialog.html',
//                        controller: 'ProductDialogController',
//                        size: 'lg',
//                        resolve: {
//                            entity: ['Product', function(Product) {
//                                return Product.get({id : $stateParams.id});
//                            }]
//                        }
//                    }).result.then(function(result) {
//                        $state.go('product', null, { reload: true });
//                    }, function() {
//                        $state.go('^');
//                    })
//                }]
//            })
            .state('product.add', {
                parent: 'product',
                url: '/add-product',
                data: {
                    authorities: [],
                    pageTitle: 'New product from here.'
                },
                views: {
                       'content@': {
                           templateUrl: 'scripts/app/entities/product/add-product.html',
                           controller: 'AddProduct'
                       }
                   },
                    resolve: {
                    }
            })
            .state('product.delete', {
                parent: 'product',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/product/product-delete-dialog.html',
                        controller: 'ProductDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Product', function(Product) {
                                return Product.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('product', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
