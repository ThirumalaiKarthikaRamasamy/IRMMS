
var marker, iconImage,map;
var markers = [],zoomLevel;

function initialize() {
	 var mapCanvas = document.getElementById('map-canvas');
     var mapOptions = {
       center: new google.maps.LatLng(44.5403, -78.5463),
       zoom: 8,
       mapTypeId: google.maps.MapTypeId.ROADMAP
     }
     var map = new google.maps.Map(mapCanvas, mapOptions);    
	
       /* $.ajax({                                             //ajax call for displaying markers at state level
			url: "./services/mvtapp/getregion",
			type:"GET",
			dataType: "xml",
			cache: false,
		}).done(function(xml){
			$.each($("region_data",xml),function(i){
			//console.log(xml);
			var region = $(this).find("group_region").text();
			var noOfWells = $(this).find("no_of_weels").text();
			var latitude = $(this).find("avg_latitude").text();
			var longitude = $(this).find("avg_longitude").text();
			iconImage="/images/G.png";
			
			marker = new google.maps.Marker({
				title: region,
				map: map,
				position: new google.maps.LatLng(latitude, longitude),
				icon: iconImage
			 });
			 markers.push(marker);
			});
		});*/
       
        
      }
      google.maps.event.addDomListener(window, 'load', initialize);
     
      /* Mapbox map --- offline mode */
      /*L.mapbox.accessToken='pk<<access-token>>';
      var map-canvas = L.mapbox.map('map-canvas', 'example-mapbox-map').setView([44.5403, -78.5463],8);
      
      var marker = L.icon({
      	iconUrl: iconImage,
      	iconAnchor:[20,50]
      });
      L.marker([],{icon,marker}).addTo(map-canvas);
      */
      
      jQuery.fn.center = function() {
    		this.css({top: ($(window).height() - $(this).outerHeight()) / 2,left: ($(window).width() - $(this).outerWidth()) / 2});
    		return this;
    	};
    	
      function setAllMap(map){
			for (var i = 0; i < markers.length; i++) {
					    markers[i].setMap(map);
			}
		}
      function search(){
    	  	$(".importTab").hide();
    		$(".searchTab").show();
      }
      
      function importTab(){
    	  $(".searchTab").hide();
    	  $(".importTab").show();
    	  $(".importDiv li").eq(0).addClass('active');
      }
      
      function showLoader(){
    		$(".overlay").css('display','block');
    		$(".loading").center();
    		$(".loading").css('display','block');	
    	}
      
      function closeLoader(){
      	$(".overlay").css('display','none');
      	$(".loading").css('display','none');
      }