'use strict';



chatApp.controller('LoginController',function($rootScope,$scope,AuthSharedService){
    $scope.rememberMe=true;
    $scope.login=function(){
        $rootScope.authenticationError=false;
        AuthSharedService.login(
            $scope.username,
            $scope.password,
            $scope.rememberMe        
        );              
    }
    
})
.controller('HomeController',function($scope,HomeService){
    $scope.technos=HomeService.getTechno();
    
})
.controller('UserController',function($scope, $log, UsersService){
    $scope.users=UsersService.getAll();
    
})
.controller('ApiDocController',function($scope){
    
    $scope.isLoading=false;
    $scope.url=$scope.swaggerUrl='v3/api-docs';
    $scope.errorHandler=function(data,status){
        console.log('failed to load swagger: '+ status+ ' '+data);        
        
    };
    $scope.infos=false;   
    
})