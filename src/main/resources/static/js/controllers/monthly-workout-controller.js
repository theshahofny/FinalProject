angular
.module('Fitness')
.controller('monthlyWorkoutController', MonthlyWorkoutController);

MonthlyWorkoutController.$inject = ['$http', '$location', '$scope', '$state', '$stateParams' ];
function MonthlyWorkoutController($http, $location , $scope, $state, $stateParams)
{
	var MHC = $scope.MHC = { }; //simmulate 'as controller' syntax w/out the bugs
	MHC.detailOpen = false;
	$(document).ready(function(){
	    $('[data-toggle="tooltip"]').tooltip();
	});
	MHC.status;
	MHC.calendar = new WorkoutCalendar(MonthlyWorkoutController.parseState($stateParams.month));

	var queryStart = MHC.calendar.firstDisplay();
	if (queryStart <= new Date()) //no need to bother the server when it's the future
	{
		var path = $location.absUrl();
		var length = ($location.absUrl().length) - ($location.path().length);
		var url = path.substring(35, length - 1);

		$http.get('http://localhost:8080/fitness/home/' + url + "/calendar/", { params: { "start": WorkoutCalendar.toJSONRequest(queryStart), 
			"end": WorkoutCalendar.toJSONRequest(MHC.calendar.lastDisplay()) }}).
			then(function (response)
					{
				console.log("got json response" + response.data);
				var success = response.data;
				for (let i = 0; i < success.length; i++)
				{
					var m =success[i].date.monthValue;
					var y = success[i].date.year;
					var d = success[i].date.dayOfMonth;
					
					
					success[i].date = m + "/" + d + "/" + y;
					console.log(success[i]);
					let resp = success[i];
					MHC.blocks.find(function (workDay)
							{
						if (workDay.match(resp))
						{
							console.log("found match " + resp.date)
							return true;
						}

							});
				}
					});
	}
	MHC.blocks = MHC.calendar.fill();

	console.log("state parameters " + $stateParams)
	/**
	 * @param {String} dir 'prior'|'next' is user scrolling backward/forward
	 */
	MHC.scroll = function(ev,dir)
	{
		ev.preventDefault();
		let first = MHC.calendar.firstOfMonth();
		if (dir === 'prior')
			first.setMonth(first.getMonth() - 1);
		else
			first.setMonth(first.getMonth() + 1);
		//MHC.calendar = new WorkoutCalendar(newMonth);
		//MHC.blocks = MHC.calendar.fill();
		$state.transitionTo('History', { "month": MonthlyWorkoutController.toState(first) }, 
				{ "reload": true, "notify": true, "inherit": true });
	}

	
	MHC.viewDetails = function(day){
		console.log("inside view details");
		if(day.active()){
			MHC.status = true;
			MHC.detailOpen = true;
			var path = $location.absUrl();
			var length = ($location.absUrl().length) - ($location.path().length);
			console.log(length);
			var url = path.substring(35, length - 1);
			console.log(url);
			var date1 = day.date;
			var mm = date1.getMonth()+1;
			var dd = date1.getDate();
			var yyyy = date1.getFullYear();
			var sumDate = mm + "/" + dd + "/" + yyyy;
			MHC.thisDate = sumDate;
		
			MHC.list = [];
			
			console.log(day);
			$http.get("http://localhost:8080/fitness/home/"+ url + "/calendarDetail" + "?date="+ sumDate).then(function(res){
				console.log(res.data.success);
		    	for(i = 0 ; i<res.data.success.length; i++){
		    		MHC.list.push(res.data.success[i]);
		    	}
		    	console.log(MHC.list);
				console.log(res.data.success);
			})
		}
		
		else if (day.addable()){
			MHC.status = false;
			$state.go('WorkoutHistory', {target: day.date});
		}
	
	}
	
	MHC.closePopUp = function (){
		MHC.detailOpen = false;
	}
	

}
/**
 * @static
 * @argument {String} parameter the state-parameter included when transitioned to (may be undefined)
 *                    in form month{1,2}'-'year{4}
 * @return {Date} a date used to construct a calendar
 * @see MonthlyWorkoutController.toState
 */
MonthlyWorkoutController.parseState = function(parameter)
{
	try
	{
		let md = parameter.split('-').map(s => Number(s));
		let ret = new Date(md[1], md[0] - 1, 1)
		return ret.getMonth() >= 0 && ret;  //javascript is a joke
	}
	catch (error)
	{
		return new Date();
	}
	return new Date();
}

MonthlyWorkoutController.toState = function(date)
{
	var parts = [];
	if (date instanceof Date)
	{
		parts.push(date.getMonth() + 1);
		parts.push(date.getFullYear())
	}    
	return parts.join('-');
}

/**
 * @constructor
 * Note: instances of this class are immutable (even though js Date's are not)
 */
function WorkoutCalendar(initial)
{
	initial = initial || new Date();
	this.month = new Date(initial); //leave the formal argument untouched
	this.month.setDate(1); //easier when it's always the first of the month
	this.month.setHours(0, 0, 0, 0); //strip time info
}

WorkoutCalendar.prototype.fullMonth = function()
{

	switch (this.month.getMonth())
	{
	case 0:
		return 'JANUARY';
	case 1:
		return 'FEBRUARY';
	case 2:
		return 'MARCH';
	case 3:
		return 'APRIL';
	case 4:
		return 'MAY';
	case 5:
		return 'JUNE';
	case 6:
		return 'JULY';
	case 7:
		return 'AUGUST';
	case 8:
		return 'SEPTEMBER';
	case 9:
		return 'OCTOBER';
	case 10:
		return 'NOVEMBER';
	default:
		return 'DECEMBER';
	}
}
WorkoutCalendar.prototype.fullYear = function()
{
	return this.month.getFullYear();
}
/**
 * @return {Date} a free-to-mutate Date 
 */
WorkoutCalendar.prototype.firstOfMonth = function()
{
	return new Date(this.month);
}

WorkoutCalendar.prototype.lastOfMonth = function()
{
	var first = this.firstOfMonth();
	first.setMonth(first.getMonth() + 1);
	first.setDate(0);
	return first;

}

/**
 * 
 * @returns {Date} the first date of this calendar, usually **not** the 1st of a month
 */
WorkoutCalendar.prototype.firstDisplay = function()
{
	var first = this.firstOfMonth();
	first.setDate(first.getDate() - first.getDay());
	return first;

}

/**
 * 
 * @returns {Date} the last date of this calendar, usually **not** the last of a month
 */
WorkoutCalendar.prototype.lastDisplay = function()
{
	var first = this.firstDisplay();
	var last = this.lastOfMonth();
	var diff = WorkoutCalendar.daysDiff(first, last);
	first.setDate(first.getDate() + Math.ceil(diff / 7) * 7 - 1);
	return first;

}

WorkoutCalendar.prototype.daysInMonth = function()
{
	var first = this.firstOfMonth();
	var nextMonth = new Date(first.getYear(), first.getMonth() + 1, 1);
	return (nextMonth - first) / 1000 * 60 * 60 * 24;
}
/**
 * @static
 * @param {Date} less
 * @param {Date} more
 * @returns {Number} difference in days between two dates
 */
WorkoutCalendar.daysDiff = function(less, more)
{
	return Math.abs((more - less) / (1000 * 60 * 60 * 24)) + 1;
}
WorkoutCalendar.prototype.fill = function()
{
	var results = [];
	var first = this.firstDisplay();
	var last =  this.lastOfMonth();
	results.push(new WorkoutDay(new Date(first)));
	var diff = WorkoutCalendar.daysDiff(last, first);
	console.log("days diff = " + diff)
	for (let i = 1; i < (Math.ceil(diff / 7) * 7); i++)
	{
		first = new Date(first);
		first.setDate(first.getDate() + 1);
		results.push(new WorkoutDay(new Date(first)));
	}
	return results;
}

WorkoutCalendar.prototype.dayClass = function(date)
{
	console.log("class called")
	var classes = [];
	var diff = date.getMonth() - this.month.getMonth();
	if (diff < 0)
		classes.push('prior');
	else if (diff > 0)
		classes.push('next');
	else if (WorkoutCalendar.today(date))
		classes.push('today');
	if (date > new Date())
		classes.push('future')
		return classes.join(' ')
}

WorkoutCalendar.today = function(date)
{
	var today = new Date();
	return date && date.getDate() === today.getDate() && date.getFullYear() === today.getFullYear() &&
	date.getMonth() === today.getMonth(); 
	
}
/**
 * 
 * @param {Date} date
 * @returns {String}
 */
WorkoutCalendar.toJSONRequest = function(date)
{
	var mdy = [];
	date && mdy.push(date.getMonth() + 1)
	date && mdy.push(date.getDate())
	date && mdy.push(date.getFullYear());
	return mdy.join('/');
}
/**
 * @constructor
 * @param {Date} date the date of this workout day
 * @param {[]} categories the categories of exercises worked out on this day
 */
function WorkoutDay(date, categories)
{
	this.date = date, this.categories = categories || [];
}
WorkoutDay.prototype.active = function()
{
	return !! this.categories.length;
}

WorkoutDay.prototype.addable = function (){
	if(this.date > new Date() || this.active())
		return false;
	return true;
	
	
	
}
WorkoutDay.prototype.match = function(json)
{
	var strDate = json.date;
	if (strDate)
	{
		let mdy = strDate.split('/');
		mdy = mdy.map( str => Number(str));
		if (mdy[0] === (this.date.getMonth() + 1) && mdy[1] === this.date.getDate() && mdy[2] === this.date.getFullYear())
		{
			this.categories = json.categories;
			return true;
		}
	}
	return false;
	function toResponseDate(date)
	{
		return [date.getMonth() + 1, date.getDate(), date.getFullYear()].join('/');
	}
}

WorkoutDay.prototype.classes = function(calendar)
{
	var classes = [];
	this.categories.length && classes.push('active')
	calendar && classes.push(calendar.dayClass(this.date));
	return classes.join(' ');
}
WorkoutDay.prototype.display = function()
{
	return this.categories && this.categories.join(', ');
}




