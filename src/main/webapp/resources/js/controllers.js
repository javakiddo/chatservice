'use strict';



chatApp.controller('LoginController', function ($rootScope, $scope, AuthSharedService) {
        $scope.rememberMe = true;
        $scope.login = function () {
            $rootScope.authenticationError = false;
            AuthSharedService.login(
                $scope.username,
                $scope.password,
                $scope.rememberMe
            );
        }

    })
    .controller('HomeController', function ($scope, HomeService) {
        $scope.technos = HomeService.getTechno();

    })
    .controller('UserController', function ($scope, $log, UsersService) {
        $scope.users = UsersService.getAll();

    })
    .controller('ApiDocController', function ($scope) {

        $scope.isLoading = false;
        $scope.url = $scope.swaggerUrl = 'v3/api-docs';
        $scope.errorHandler = function (data, status) {
            console.log('failed to load swagger: ' + status + ' ' + data);

        };
        $scope.infos = false;

    })
    .controller('TokenController', function ($scope, TokensService, UsersService, $q) {
        var browsers = ["Firefox", 'Chrome', 'Trident'];

        $q.all([
        UsersService.getAll().$promise,
        TokensService.getAll().$promise
    ])
            .then(function (data) {
                var users = data[0];
                var tokens = data[1];
                tokens.forEach(function (token) {
                    users.forEach(function (user) {
                        if (token.userLogin === user.login) {
                            token.firstName = user.firstName;
                            token.lastName = user.lastName;
                            browsers.forEach(function (browser) {
                                if (token.userAgent.indexOf(browser) > -1) {
                                    token.browser = browser;
                                }
                            });
                        }

                    });
                });
                $scope.tokens = tokens;
            });
    })
    .controller('LogoutController', function (AuthSharedService) {
        AuthSharedService.logout();

    })
    .controller('ErrorController', function ($scope, $routeParams) {
        $scope.code = $routeParams.code;

        switch (code) {
            case 403:
                $scope.message = "You are not authorized to view this page.";
                break;

            case 404:
                $scope.message = "Page Not found.";
                break;
            default:
                $scope.code = 500;
                $scope.message = "An Unexpected Error Occured";


        }


    });
