$(document).ready(function () {

	// //Hide Sidebar
    // $('#sidebarCollapse').on('click', function () {
    //     $('#sidebar').toggleClass('active');
    //     $('#offtoggle').toggleClass('d-none');
    //     $('#ontoggle').toggleClass('d-none');
    //     $('#content').toggleClass('change-margins');
    // });

    //Show data when clicking Home Location
    $('#homeLocation').on("click", function(){
    	$('#showWeather').removeClass('d-none');
    	$('#weather-data').addClass('d-none');
    	$('#weather-today').removeClass('d-none');
    });
    
    //Change views when clicking on weather daily/weekly navigation bar
    $('#daily').on("click", function(){
    	$('#weather-today').removeClass('d-none');
    	$('#weather-data').addClass('d-none');
    });
    
    $('#weekly').on("click", function(){
    	$('#weather-data').removeClass('d-none');
    	$('#weather-today').addClass('d-none');
    });
    
    //Set form id to submit for form path variable
    $( "[name='cityClicked']" ).on("click", function(){
    	var id = $(this).attr('class');
    	var tag = "."+id ;
    	$(tag).submit();
    });
    
    //Search in add function
    $( "#cityAddSearch" ).keyup(function() {
    	
		  // Declare variables
		  var input, filter, ul, li, a, i, txtValue;
		  input = document.getElementById('cityAddSearch');
		  filter = input.value.toUpperCase();
		  ul = document.getElementById("cityList");
		  li = ul.getElementsByTagName('li');
		  
		  // Loop through all list items, and hide those who don't match the search query
		  for (i = 0; i < li.length; i++) {
		    a = li[i].getElementsByTagName("a")[0];
		    txtValue = a.textContent || a.innerText;
		    if (txtValue.toUpperCase().indexOf(filter) > -1) {
		      li[i].style.display = "";
		    } else {
		      li[i].style.display = "none";
		    }
		  }
	});
});