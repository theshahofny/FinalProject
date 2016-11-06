angular
    .module("Fitness", ["ui.router", "ngMaterial"])
    .config(configure)

configure.$inject = ['$stateProvider', '$urlRouterProvider']

function configure($stateProvider, $urlRouterProvider) {

    $stateProvider
        .state('default', {
            url: '/',
            controller: 'SummaryController',
            controllerAs: 'sC',
            templateUrl: '/templates/summary.html',
        })
        .state('ViewRegimen', {
            url: '/regimen',
            controller: 'AccountController',
            controllerAs: 'auC',
            templateUrl: '/templates/regimen.html',
        })
        .state('Exercises', {
            url: '/exercises',
            controller: 'exerciseController',
            controllerAs: 'eC',
            templateUrl: '/templates/exercises.html'
        })
        .state('WorkoutHistory', {
            url: '/workouthistory',
            controller: 'workoutHistoryController',
            controllerAs: 'wHC',
            templateUrl: '/templates/workoutHistory.html',
            params: {
                "target": null
            }
        })
        .state('WorkoutHistory.Exercises', {
            url: '/:category',
            controller: 'whExerciseController',
            controllerAs: 'wEC',
            templateUrl: '/templates/exercises.html'
        })
        .state('History', {
            url: '/history/{month}',
            controller: 'monthlyWorkoutController',
            templateUrl: '/templates/calendar.html'
        })
        .state('CreateRegimen', {
            url: '/CreateMyRegimen',
            controller: 'regimenController',
            controllerAs: 'rC',
            templateUrl: '/templates/createRegimen.html'
        })

    $urlRouterProvider.otherwise('/')

    console.log("setting up", $stateProvider)
}