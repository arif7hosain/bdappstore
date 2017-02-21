'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contact', {
                parent: 'entity',
                url: '/contact',
                data: {
                    authorities: [],
                    pageTitle: 'Leave a message at Bangladeshi Apps Store'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contact/contact.html',
                        controller: 'ContactController'
                    }
                },
                resolve: {
                }
            }).state('contact.about', {
                parent: 'entity',
                url: '/about',
                data: {
                    authorities: [],
                    pageTitle: 'Leave a message at Bangladeshi Apps Store'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contact/about.html',
                        controller: 'AboutController'
                    }
                },
                resolve: {
                }
            });
    });
