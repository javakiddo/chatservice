'use strict';

var chatApp = angular
    .module('chatApp', ['ngResource', 'ngRoute', 'swaggerUi', 'http-auth-interceptor', 'ngAnimate', 'angular-spinkit','toaster']);


chatApp.constant('USER_ROLES', {
    all: '*',
    admin: 'admin',
    user: 'user'
});

chatApp.config(function($routeProvider,USER_ROLES){
    
    
    
    
});
