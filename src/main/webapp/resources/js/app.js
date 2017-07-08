'use strict';

var chatApp = angular
    .module('chatApp', ['ngResource', 'ngRoute', 'swaggerUi', 'http-auth-interceptor', 'ngAnimate', 'angular-spinkit', 'toaster']);


chatApp.constant('USER_ROLES', {
    all: '*',
    admin: 'admin',
    user: 'user'
});

chatApp.config(function ($routeProvider, USER_ROLES) {
    $routeProvider.when("/home", {
        templateUrl: "partials/home.html",
        controller: 'HomeController',
        access: {
            loginRequired: true,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when('/', {
        redirectTo: '/home'
    }).when('/users', {
        templateUrl: 'partials/users.html',
        controller: 'UsersController',
        access: {

            loginRequired: true,
            authorizedRoles: [USER_ROLES.admin]
        }
    }).when('/chat', {
        templateUrl: 'partials/chat.html',
        controller: 'ChatController',
        access: {
            loginRequired: true,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when('/apiDoc', {
        templateUrl: 'partials/apiDoc.html',
        controller: 'ApiDocController',
        access: {
            loginRequired: true,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when('/tokens', {
        templateUrl: 'partials/tokens.html',
        controller: 'TokensController',
        access: {
            loginRequired: true,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when('/login', {
        templateUrl: 'partials/login.html',
        controller: 'LoginController',
        access: {
            loginRequired: false,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when('/loading', {
        templateUrl: 'partials/loading.html',
        access: {
            loginRequired: false,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when("/logout", {
        template: " ",
        controller: "LogoutController",
        access: {
            loginRequired: false,
            authorizedRoles: [USER_ROLES.all]
        }
    }).when("/error/:code", {
        templateUrl: "partials/error.html",
        controller: "ErrorController",
        access: {
            loginRequired: false,
            authorizedRoles: [USER_ROLES.all]
        }
    }).otherwise({
        redirectTo: '/error/404',
        access: {
            loginRequired: false,
            authorizedRoles: [USER_ROLES.all]
        }
    });



});

chatApp.run(function ($rootScope, $location, $http, AuthSharedService, Session, USER_ROLES, $q, $timeout) {
    $rootScope.$on('$routeChangeStart', function (event, next) {
       /* if ($rootScope.authenticated === true) {
            console.log($rootScope.authenticated);
            $rootScope.$broadcast('event:auth-loginConfirmed', {});
            event.preventDefault();
        }*/
      

        if (next.originalPath === '/login' && $rootScope.authenticated) {
            event.preventDefault();

        } else if (next.originalPath === '/chat' && $rootScope.authenticated) {
            console.log(next)
            event.preventDefault();
             $rootScope.$broadcast("event:auth-loginConfirmed", {});

        } else if (next.access && next.access.loginRequired && !$rootScope.authenticated) {
            event.preventDefault();

            $rootScope.$broadcast('event:auth-loginRequired', {});
        } else if (next.access && !AuthSharedService.isAuthorized(next.access.authorizedRoles)) {
            event.preventDefault();
            $rootScope.$broadcast("event:auth-forbidden", {});
        }
    });

    $rootScope.$on('$routeChangeSuccess', function (scope, next, current) {
        /*if ($rootScope.authenticated === true) {
            console.log($rootScope.authenticated);
            $rootScope.$broadcast('event:auth-loginConfirmed', {});
            event.preventDefault();
        }*/

        $rootScope.$evalAsync(function () {
            $.material.init();
        });
    });

    $rootScope.$on('event:auth-loginConfirmed', function (event, data) {
        console.log('login confirmed start ',event, data);
        $rootScope.loadingAccount = false;
        var nextLocation = ($rootScope.requestedUrl ? $rootScope.requestedUrl : "/chat");
        var delay = ($location.path() === "/loading" ? 1500 : 0);

        $timeout(function () {

            Session.create(data);
            $rootScope.account = Session;
            $rootScope.authenticated = true;
            $location.path(nextLocation).replace();


        }, delay);

    });

    $rootScope.$on('event:auth-loginRequired', function (event, data) {
         console.log('event loginRequired  ',event, data);
        
        console.log('$rootScope.authenticated  ',$rootScope.authenticated);
        
        if($rootScope.authenticated)
            {
                console.log('Login Required but rootscope authenticated',event);
                event.preventDefault();
                return;
            }
        if ($rootScope.loadingAccount && data.status != 401) {
            $rootScope.requestedUrl = $location.path()
            $location.path('/loading');
        } else {
            Session.invalidate();
            $rootScope.authenticated = false;
            $rootScope.loadingAccount = false;
            $location.path('/login');
        }

    });

    // Call when the 403 response is returned by the server
    $rootScope.$on('event:auth-forbidden', function (rejection) {
        $rootScope.$evalAsync(function () {
            $location.path('/error/403').replace();
        });
    });

    // Call when the user logs out
    $rootScope.$on('event:auth-loginCancelled', function () {
        $location.path('/login').replace();
    });

    // Get already authenticated user account
    //   AuthSharedService.getAccount();






});
