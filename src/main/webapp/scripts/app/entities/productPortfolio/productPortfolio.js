'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('productPortfolio', {
                parent: 'entity',
                url: '/productPortfolios',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ProductPortfolios'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productPortfolio/productPortfolios.html',
                        controller: 'ProductPortfolioController'
                    }
                },
                resolve: {
                }
            })
            .state('productPortfolio.detail', {
                parent: 'entity',
                url: '/productPortfolio/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ProductPortfolio'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/productPortfolio/productPortfolio-detail.html',
                        controller: 'ProductPortfolioDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ProductPortfolio', function($stateParams, ProductPortfolio) {
                        return ProductPortfolio.get({id : $stateParams.id});
                    }]
                }
            })
            .state('productPortfolio.new', {
                parent: 'productPortfolio',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productPortfolio/productPortfolio-dialog.html',
                        controller: 'ProductPortfolioDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    image: null,
                                    imageContentType: null,
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
                        $state.go('productPortfolio', null, { reload: true });
                    }, function() {
                        $state.go('productPortfolio');
                    })
                }]
            })
            .state('productPortfolio.edit', {
                parent: 'productPortfolio',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productPortfolio/productPortfolio-dialog.html',
                        controller: 'ProductPortfolioDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ProductPortfolio', function(ProductPortfolio) {
                                return ProductPortfolio.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('productPortfolio', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('productPortfolio.delete', {
                parent: 'productPortfolio',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/productPortfolio/productPortfolio-delete-dialog.html',
                        controller: 'ProductPortfolioDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ProductPortfolio', function(ProductPortfolio) {
                                return ProductPortfolio.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('productPortfolio', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
