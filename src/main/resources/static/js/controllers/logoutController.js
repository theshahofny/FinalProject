angular 
.module("Fitness") 
.controller("logoutController", logout) 

login.$inject =['$state', '$http'] 

function logout($state, $http) { 
	var ctrl = this;	

//	return $http.get("http://localhost:8080/fitness/" + ctrl.username).then(function(res) {
//	return res.data })
	
	ctrl.username="Foo";
	console.log(ctrl.username + "logout");

}
