angular.module('movieapp', ['infinite-scroll'])
.controller('bodyController',
		function($rootScope, $scope, $http, $window){
			
			$scope.movies = [];
			
			$scope.keys = [];
			
			var first = true;
			
			$scope.page = -1;
			
			$scope.load = function(){
				$scope.page = $scope.page + 1;
				$http.get("/movies?page="+$scope.page).success(function(data){
					Array.prototype.push.apply($scope.movies, data);
					//$scope.movies.concat(data);
					if(first){
						$scope.keys = Object.keys(data[0]);
						first = false;
					}
					
				});
				
			};
			
			$scope.load();
			
		}
);