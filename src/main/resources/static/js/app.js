var webchat= angular.module('webchat',['webChat.controllers','webChat.services','webChat.directives','ngRoute']);

webchat.config(function ($routeProvider, USER_ROLES) {
 
    $routeProvider.when("/", {
        templateUrl: "index.html",
        controller: 'LoginController'
    });
    });

    
