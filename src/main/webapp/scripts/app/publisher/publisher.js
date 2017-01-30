'use strict';

angular.module('appstoreApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('publisher', {
                parent: 'account',
                url: '/publisher/register',
                data: {
                    authorities: [],
                    pageTitle: 'Registration'
                },
                views: {
                       'content@': {
                           templateUrl: 'scripts/app/publisher/create_publisher.html',
                           controller: 'CreatePublisher'
                       }
                   },
                    resolve: {
                    }
            })
            .state('publisher.success', {
                parent: 'account',
                url: '/publisher/register/success',
                data: {
                    authorities: [],
                    pageTitle: 'Registration Successful at Bangladeshi Largest App Store'
                },
                views: {
                       'content@': {
                           templateUrl: 'scripts/app/publisher/success.html',
                           controller: 'PublisherSuccess'
                       }
                   },
                    resolve: {
                    }
            });
    });
