var markers = [],map,marker,well_name,well_id, xmlData,recData,well_nameSave,well_idSave,regionFlag=0,searchFlag=0, overdueFlag =0;
var markers1 =[],marker1,label =[],tempMarker=[]; 
function setAllMap1(map){
	for (var i = 0; i < markers1.length; i++) {
			    markers1[i].setMap(map);
	}
}

function setAllMap(map){
	for (var i = 0; i < markers.length; i++) {
			    markers[i].setMap(map);
	}
}

function setAllMapSearch(map){
	for (var i = 0; i < tempMarker.length; i++) {
		tempMarker[i].setMap(map);
	}
}


function search(){
	$("#Pointingarrow1").css('display','none');
	  $(".importTab").hide();
	$("#Pointingarrow").css('display','');
	 $(".uploadalert").remove();
	$(".searchTab").toggle();
	
	//ajax call to get the customers in customer-drop down
	$.ajax({   
		   url: "./services/mvtapp/getCustomer",
		   type:"GET",
		   dataType: "xml"	,
		   cache: false,
		   contentType: "application/json; charset=utf-8"     
	}).done(function(xml){
		   $('#customerDropDown').html("<option value=''>Customer Name</option>");
			 $.each($("customer_data",xml),function(){
							   var customerName = $(this).find("customer_name").text();
							   var option= $("<option value='"+customerName+"'>"+customerName+"</option>");
							   $("#customerDropDown").append(option);
			 }); 
			 closeLoader();
	});
}

function changeTab()
{
	$(".tab-pane1").css('display','');
	if($(".historyDiv").is(':hidden'))
	{
	$(".popup-ul").show();
	}
	$(".tab-pane2").css('display','none');
	$("#wellNameid").addClass("active1");
	$("#maintenanceid").removeClass("active1");
}
function changeTab1()
{
	$(".tab-pane2").css('display','');
	$(".popup-ul").hide();
/*	$('.tab-pane2').attr('padding-left', '40');*/
	$(".tab-pane1").css('display','none');
	$("#wellNameid").removeClass("active1");
	$("#maintenanceid").addClass("active1");
}

$(document).ready(function() {
$(".legend").click(function(e){
	//e.preventDefault(); 
	$(this).find('ul').slideToggle();
	$(this).find("strong span").toggleClass("control_down");
});
});

function importTab(){
	$("#Pointingarrow1").css('display','');
	$("#Pointingarrow").css('display','none');
  $(".searchTab").hide();
  $(".uploadalert").remove();
  $(".importTab").show();
  $(".importDiv li").eq(0).addClass('active');
}
//loading map on a page
google.maps.event.addDomListener(window, 'load', function() {
	map = new google.maps.Map(document.getElementById('map-canvas'), {
		zoom :9,
		minZoom : 5,
		center : new google.maps.LatLng(27.063377, 51.550261),
		mapTypeId : google.maps.MapTypeId.ROADMAP
	});
	showLoader();
	$.ajax({                                             
		url: "./services/mvtapp/getregion",   
		type:"GET",
		dataType: "xml"	
	}).done(function(xml){
			$.each($("region_data",xml),function(i){
				regionFlag=1;
				console.log(xml);
				var latitude=$(this).find("avg_latitude").text();
				var longitude=$(this).find("avg_longitude").text();
				var no_of_wells=$.trim($(this).find("no_of_weels").text());
				var region=$.trim($(this).find("group_region").text());
				iconImage="images/Marker.png";
				
				
				marker = new google.maps.Marker({
					title: region,
					map: map,
					position: new google.maps.LatLng(latitude, longitude),
					icon: iconImage
				 });
				  
				markers.push(marker);
				
				label[i] = new Label({ 
					map: map
				});
				label[i].bindTo('position', marker, 'position');
				label[i].set('text',region+"&nbsp;["+no_of_wells+"]");
				
				for(var i=0;i<label.length;i++){
					label[i].set('visible', 'block');
				}
				getRegionWells(region,no_of_wells);
			});
			closeLoader();
		});
	
});	

function getRegionWells(region,no_of_wells){
	google.maps.event.addListener(marker, 'click', function() {
		var label_new = region+"&nbsp;["+no_of_wells+"]";
		regions = this.title;
		setAllMap(null);
		map.setZoom(8);
		map.setCenter(this.position);
		for(var i=0;i<label.length;i++){
			//if(label_new!=label[i].get('text').toString())
			label[i].set('visible', 'none');
		}
		
		$(".regionButton").show();
		showLoader(); 
		$.ajax({                                             //ajax call for displaying wells
			url: "./services/mvtapp/getWellbyregion?region="+regions,   
			type:"GET",
			dataType: "xml"	
		}).done(function(xml){
			$.each($("wells_data",xml),function(){
				var latitude=$(this).find("latitude").text();
				var longitude=$(this).find("longitude").text();
				var customer_name=$(this).find("customer_name").text();
				well_name=$.trim($(this).find("well_name").text());
				var field=$.trim($(this).find("field").text());
				var block_no=$.trim($(this).find("block_no").text());
				well_id=$.trim($(this).find("well_id").text());
				var maintenance_status=$.trim($(this).find("maintenance_status").text());
				iconImage="images/"+maintenance_status+".png";
				marker1 = new google.maps.Marker({
					title: well_name,
					map: map,
					position: new google.maps.LatLng(latitude, longitude),
					icon: iconImage
				 });
				markers1.push(marker1);
				showWell(marker1, latitude, longitude, well_id, well_name,customer_name);
				
			});
			closeLoader();
		});
	});
}


function showWell(marker1, latitude, longitude, well_id, well_name,customer_name){
	var infoBubble = new InfoBubble( {
		shadowStyle : 1,
		padding : 0,
		borderRadius : 5,
		minWidth : 400,
		minHeight : 470,
		arrowSize : 10,
		borderWidth : 0,
		borderColor : '#2c2c2c',
		disableAutoPan : false,
		hideCloseButton : false,
		arrowPosition : 50,
		backgroundClassName : 'infoBubbleContainer',
		arrowStyle : 2,
		height: 90,
		maxWidth : 700,
		maxHeight : 570,
	});
	google.maps.event.addListener(marker1, 'click', function() {
		
		well_nameSave = well_name;
		well_idSave = well_id;
		var content = '<div class="popupHeader"><h2>Well Info</h2></div>'
		+'<div style="height: 35px !important;"><table style="border:0px;width:500px; height:35px;padding-left:10px;text-align:center;"><tr><td>'
		+'<div id="wellNameid"  class="active1" onclick="changeTab();return false;"><a ><span class="wellName" id="wellName">'+well_name+'</span></a></div></td>'
		+'<td><div id="maintenanceid" onclick="changeTab1();return false;"><a><span class="maintenance" id="maintenance" >Recommended Maintenance</span></a></div></td>'
		+'</tr></table></div>'
		+'</br>'
		+'<div class="popup-ul"><span class="historyLink" onClick="viewHistoryBlock();"><img src="images/history_icon.png"/>&nbsp;View History</span><span class="exportwell"><a href="./services/mvtapp/getexportwell?wellid='+well_id+'" target="_blank"><img src="images/excel_icon.png">&nbsp;Export Excel</a></span><span class="saveLink" onClick="confirmSave();"><img src="images/save_icon.png"/>&nbsp;Save</span></div>'
		+'<div  class="tab-pane1">'		
		+'<ol class="wellInfoList">'
		+'<br/><div class="wellDetailsDiv" style="display:block;">'
		+'<br/><div class="wellImg '+well_id+'"><img src="images/Well.png"/>'
		+'<div class="popupImageData" id="1" style="top: 175px;right: 20px;"><b></b><label>P/N: <span class="partNo" contenteditable="true"></span></label><br><label>S/N: <span class="serialNo" contenteditable="true"></span></label></div>'
		+'<div class="popupImageData" id="2" style="top: 255px;right: 20px;"><b></b><label>P/N: <span class="partNo" contenteditable="true"></span></label><br><label>S/N: <span class="serialNo" contenteditable="true"></span></label></div>'
		+'<div class="popupImageData" id="3" style="top: 335px;right: 20px;"><b></b><label>P/N: <span class="partNo" contenteditable="true"></span></label><br><label>S/N: <span class="serialNo" contenteditable="true"></span></label></div>'
		+'<div class="popupImageData" id="4" style="right: 20px;top: 410px;"><b></b><label>P/N: <span class="partNo" contenteditable="true"></span></label><br><label>S/N: <span class="serialNo" contenteditable="true"></span></label></div>'
		+'</div>'				//End of wellImg Block
		
		+'</div>'   //End of wellDetailsDiv Block
		+'<div class="historyDiv" style="display:none;">'
		+'<div class="historyHead"><span class="detailsLink" onClick="viewWellDetailsBlock();">View Well Details</span></div>'
		+'<ul class="partInfoList">'
		+'<li><span id="part1" onclick="openSectionPart(event);">Part 01<label class="addSymbol">+</label></span>'
		+'<div class="part1Div">'
		+'<table class="table table-bordered partwidth"><thead>'
		+'<tr><th>Part No</th><th>Serial No</th><th>Updated Date</th><th>Updated By</th></thead>'
		+'<tbody class="part1"></tbody></table>'
		+'</div></li>'//End of part1 div
		+'<li><span id="part2" onclick="openSectionPart(event);">Part 02<label class="addSymbol">+</label></span>'
		+'<div class="part2Div">'
		+'<table class="table table-bordered partwidth"><thead>'
		+'<tr><th>Part No</th><th>Serial No</th><th>Updated Date</th><th>Updated By</th></tr></thead>'
		+'<tbody class="part2"></tbody></table>'
		+'</div></li>'//End of part2 div
		+'<li><span id="part3" onclick="openSectionPart(event);">Part 03<label class="addSymbol">+</label></span>'
		+'<div class="part3Div">'
		+'<table class="table table-bordered partwidth"><thead>'
		+'<tr><th>Part No</th><th>Serial No</th><th>Updated Date</th><th>Updated By</th></tr></thead>'
		+'<tbody class="part3"></tbody></table>'
		+'</div></li>'//End of part3 div
		+'<li><span id="part4" onclick="openSectionPart(event);">Part 04<label class="addSymbol">+</label></span>'
		+'<div class="part4Div">'
		+'<table class="table table-bordered partwidth"><thead>'
		+'<tr><th>Part No</th><th>Serial No</th><th>Updated Date</th><th>Updated By</th></tr></thead>'
		+'<tbody class="part4"></tbody></table>'
		+'</div></li>'//End of part4 div
		+'</ul>'
		+'</div></li>' //end of history div          
		+'</ol>'
		+'</div>'
		+'</br><div class="tab-pane2" style="display:none;font-size:13px;">'
		+'</br><span class="saveDateLink" onClick="confirmDateSave();"><img src="images/save_icon.png"/>&nbsp;Save</span><br>'
		+'<br/><div class="maintaindv"><table class="maintain" border="1px solid #cccccc;"><thead style="background-color:#5D758D; color:white !important;">'
		+'<tr><th>Component Name</th><th>Last Maintenance Date</th><th>Recommended<br> Maintenance Date</th></thead>'
		+'<tbody class="maintenanceBlock"></tbody></table></div>'
		+'</div>'; //End of maintenance block
		
		
		google.maps.event.addListener(map, 'click', function() {
			infoBubble.close();
			$(".infoBubbleContainer").parent().parent().remove();
		});
	
		var newLatLng = new google.maps.LatLng(latitude, longitude);
		infoBubble.open(map, marker1);
		infoBubble.setContent(content);
		$(".infoBubbleContainer").parent().parent().remove();
		showLoader();
		setTimeout(updateData(), 100);
	});
	
	  
}

jQuery.fn.center = function() {
	this.css({top: ($(window).height() - $(this).outerHeight()) / 2,left: ($(window).width() - $(this).outerWidth()) / 2});
	return this;
};

function updateData(){
	showLoader();
	$.ajax({                                             //ajax call for displaying wells
		url: "./services/mvtapp/getWellPartsData?well_id="+well_idSave,   
		//url: "./services/mvtapp/getWellPartsData?well_id=1",   
		type:"GET",
		dataType: "xml"	
	}).done(function(xml){
		var $table1 = $(".maintenanceBlock");
		$table1.find("tr").remove();
		$.each($("well_parts_data",xml),function(i){
			var id = i+1;
			if($(this).find("maintenance_status").text() == "R"){
				$("."+well_idSave+" #"+id+" b").text($(this).children("product_description").text()).css('color', '#EE3324');
				$("."+well_idSave+" #"+id+" label").css('color', '#EE3324');
				$("."+well_idSave+" #"+id+" span.partNo").text($(this).children("part_no").text());
				$("."+well_idSave+" #"+id+" span.serialNo").text($(this).children("serial_no").text());
			}else if($(this).find("maintenance_status").text() == "Y"){
				$("."+well_idSave+" #"+id+" b").text($(this).children("product_description").text()).css('color', '#F4B366');
				$("."+well_idSave+" #"+id+" label").css('color', '#F4B366');
				$("."+well_idSave+" #"+id+" span.partNo").text($(this).children("part_no").text()).css('color', '#F4B366');
				$("."+well_idSave+" #"+id+" span.serialNo").text($(this).children("serial_no").text()).css('color', '#F4B366');
			}else if($(this).find("maintenance_status").text() == "G"){
				$("."+well_idSave+" #"+id+" b").text($(this).children("product_description").text()).css('color', '#446B00');
				$("."+well_idSave+" #"+id+" label").css('color', '#446B00');
				$("."+well_idSave+" #"+id+" span.partNo").text($(this).children("part_no").text()).css('color', '#446B00');
				$("."+well_idSave+" #"+id+" span.serialNo").text($(this).children("serial_no").text()).css('color', '#446B00');
			}
			else{
				$("."+well_idSave+" #"+id+" b").text($(this).children("product_description").text());
				$("."+well_idSave+" #"+id+" span.partNo").text($(this).children("part_no").text());
				$("."+well_idSave+" #"+id+" span.serialNo").text($(this).children("serial_no").text());
				$("."+well_idSave+" #"+id+" span.serialNo").text($(this).children("serial_no").text());
			}
			if($(this).find("next_maintenance_date").text()!=""){
			  var tr= $("<tr class='recRow'><td class='product_description'>"+$(this).find("product_description").text()+"</td><td class='last_maintenance_date' contenteditable='true'><input class='last_maintenance_date1'  onclick='calenderfunc(this);return false;' type='text' onchange='addDays(this);'  value='"+$(this).find("last_maintenance_date").text()+"'></td><td  class='recommendDown' ><a class='recommendDown3'  href='images/IRMMS.PDF' target='_blank'>"+$(this).find("next_maintenance_date").text()+"</a></td>");
			  $table1.append(tr);
			}

			$(".part"+id).find("tr").remove();
			$.each($("parts_history",$(this)),function(){
				var part_no= $(this).find("part_no").text();
				var serial_no= $(this).find("serial_no").text();
				var update_date= $(this).find("update_date").text();
				var update_by= $(this).find("update_by").text();
				var tr= $("<tr><td>"+part_no+"</td><td>"+serial_no+"</td><td>"+update_date+"</td><td>"+update_by+"</td></tr>");
				$(".part"+id).append(tr);
				$(".part"+id+"Div table").show();
				$(".part"+id+"Div b").remove();
			});
			if($(".part"+id).find("tr").length == 0){
				$(".part"+id+"Div table").hide();
				$(".part"+id+"Div b").remove();
				$( "<b>No History Found!!!</b>" ).appendTo( ".part"+id+"Div" );
			}else{
				$(".part"+id+"Div").show();
				$(".part"+id+"Div").siblings().children('.addSymbol').text("-");
			}
				
		});
		
		closeLoader();
	});
}
function calenderfunc(cal){
	$(cal).datepicker({ dateFormat: 'dd-mm-yy', 
		showButtonPanel: true,
        closeText: "X",
	}).datepicker("show");
}
function addDays(theDate) {
	 $('.datepicker').hide();
	var from = $(theDate).val().split("-");
	 var nd =new Date(from[2], from[1] - 1, from[0]);
	 function pad(s) { return (s < 10) ? '0' + s : s; }
	 nd.setMonth(nd.getMonth()+6);
	 $(theDate).closest('tr').find('.recommendDown3').html([pad(nd.getDate()), pad(nd.getMonth()+1), nd.getFullYear()].join('-')) ;
}
function searchClose()
{
	$(".searchTab").css('display','none');
	$("#Pointingarrow").css('display','none');
}
function importClose()
{
	$(".importTab").css('display','none');
	$("#Pointingarrow1").css('display','none');
}
function custAlert(msg){
	$(".cancelSave,.saveData").css('display','none');
	$(".okButton").show();
	$(".lightBox").center();
	$(".lightBox,.overlay").show(500);
	$("#alertMsg b").text(msg);
}
function confirmSave(){
	$(".okButton").css('display','none');
	$(".cancelSave").show();
	$(".saveData").show();
	$(".lightBox,.overlay").show(500);
	$(".lightBox").center();
	$("#alertMsg b").text("Do you really want to save Data?");
}
function confirmDateSave(){
	$(".okButton1").css('display','none');
	$(".cancelSave1").show();
	$(".saveData1").show();
	$(".lightBox1,.overlay").show(500);
	$(".lightBox1").center();
	$("#alertMsg1 b").text("Do you really want to save Data?");
}
$(document).ready(function() {
	$("#Data_file").on('change',function(){
		$(".importTab").css("width","25%");
		$('.wellDataMenu img').css('width','21%');
		$('.download').css('margin-left','20px');
		$('.wellDataMenu div').css('width','79%');
		$('.wellDataMenu div form').css('margin-left','20px');
    });
$(".cancelSave").click(function(){
	$(".lightBox,.overlay").hide(500);
});
$(".cancelOverdue").click(function(){
	$(".uploadalert").remove();
});
$(".closePop").click(function(){
	$(".lightBox,.overlay").hide(500);
});
$(".cancelSave1").click(function(){
	$(".lightBox1,.overlay").hide(500);
});
$(".closePop1").click(function(){
	$(".lightBox1,.overlay").hide(500);
});

$(".saveData").click(function(){
	$(".lightBox").hide(500);
	showLoader();
	xmlData = "";
	xmlData += "<collection>";
	$(".popupImageData").each(function(){
	xmlData += "<Well_Parts>"+
			   "<well_id>"+well_idSave+"</well_id>"+
			   "<well_name>"+well_nameSave+"</well_name>"+
			   "<product_description>"+$(this).children('b').text()+"</product_description>"+
			   "<part_no>"+$(this).children('label').children('.partNo').text()+"</part_no>"+
			   "<serial_no>"+$(this).children('label').children('.serialNo').text()+"</serial_no>"+
			   "</Well_Parts>";
	});
	xmlData += "</collection>";
	
	 $.ajax({
          url: './services/mvtapp/editwellparts',
          type: 'POST',
          data: xmlData,
		  dataType: "xml",
          contentType: 'application/xml',
          success: function(data) {
			 updateData();
			 custAlert("Data Saved Successfully");
          }
		  
	});
	
});

$(".saveData1").click(function(){
	$(".lightBox1").hide(500);
	showLoader();
	recData = "";
	recData += "<collection>";
	$(".recRow").each(function(){
		recData += "<Well_Rec_Parts>"+
			   "<well_id>"+well_idSave+"</well_id>"+
			   "<well_name>"+well_nameSave+"</well_name>"+
			   "<product_description>"+$(this).children('.product_description').text()+"</product_description>"+
			   "<last_maintenanc_date>"+$(this).find('.last_maintenance_date1').val()+"</last_maintenanc_date>"+
			   "<next_maintenanc_date>"+$(this).children('.recommendDown').text()+"</next_maintenanc_date>"+
			   "</Well_Rec_Parts>";
	});
	
	recData += "</collection>";
	 $.ajax({
         url: './services/mvtapp/editmaintenance',
         type: 'POST',
         data: recData,
		  dataType: "xml",
         contentType: 'application/xml',
         success: function(data) {
			 updateData();
			 custAlert("Data Saved Successfully");
         }
		  
	});
	
});

});
function showLoader(){
	$(".overlay").css('display','block');
	$(".loading").center();
	$(".loading").css('display','block');	
}

function closeLoader(){
	$(".overlay").css('display','none');
	$(".loading").css('display','none');
}

function openSection(){
	var element = event.target.id;
	
	if(element != 'wellName'){
		$(".popupImageData").hide();
	}else{
		$(".popupImageData").fadeIn(1000);
	}
	$(".wellInfoList > li > div").slideUp(700);
	$("#"+element).siblings('div').slideDown(700);
	$(".historyDiv").hide();
}

function viewHistoryBlock(){
	$(".wellDetailsDiv").hide();
	$(".popup-ul").hide();
	$(".historyDiv").fadeIn(500);
}
function viewWellDetailsBlock(){
	$(".historyDiv").hide();
	$(".popup-ul").fadeIn(500);
	$(".wellDetailsDiv").fadeIn(500);
}
function openSectionPart(){
	var element = event.target.id;
	if($("#"+element).siblings('div').is(":visible")){
		$("#"+element).siblings('div').slideUp(700);
		$("#"+element).children('label').text("+");
	}else{
		$("#"+element).siblings('div').slideDown(700);
		$("#"+element).children('label').text("-");
	}
}

/*var tableToExcel = (function() {
  var uri = 'data:application/vnd.ms-excel;base64,'
    , base64 = function(s) { return window.btoa(unescape(encodeURIComponent(s))); }
    , format = function(s, c) { return s.replace(/{(\w+)}/g, function(m, p) { return c[p]; }); };
  return function(table, name) {
    if (!table.nodeType) table = document.getElementById(table);
    var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML};
    window.location.href = uri + base64(format(template, ctx)) ;
  };
})();*/

$(window).load(function(){
	$(".regionButton").click(function() {
			$(".regionButton").hide();
			setAllMap1(null);
			map.setZoom(8);	
	});	
	
	//$(".exportExcel").hide();
	/*$.ajax({                                             
		url: "./services/mvtapp/getexportwell",   
		type:"GET",
		dataType: "xml"	
	}).done(function(xml){
		console.log(xml);
			$(".excelTable").find("tr").remove();
			$.each($("well_export_data",xml),function(i){
				var BLOCK_NO= $(this).find("BLOCK_NO").text();
				var CUSTOMER_NAME= $(this).find("CUSTOMER_NAME").text();
				var FIELD= $(this).find("FIELD").text();
				var LAST_MAINTENANCE_DATE= $(this).find("LAST_MAINTENANCE_DATE").text();
				var NEXT_MAINTENANCE_DATE= $(this).find("NEXT_MAINTENANCE_DATE").text();
				var LATITUDE= $(this).find("LATITUDE").text();
				var LONGITUDE= $(this).find("LONGITUDE").text();
				var PART_NO= $(this).find("PART_NO").text();
				var PMS_DESCRIPTION= $(this).find("PMS_DESCRIPTION").text();
				var PRICE= $(this).find("PRICE").text();
				var PRODUCT_DESCRIPTION= $(this).find("PRODUCT_DESCRIPTION").text();
				var QUANTITY= $(this).find("QUANTITY").text();
				var SALES_ORDER_TICKET_NO= $(this).find("SALES_ORDER_TICKET_NO").text();
				var SERIAL_NO= $(this).find("SERIAL_NO").text();
				var SHIPPED_SERVICE_DATE= $(this).find("SHIPPED_SERVICE_DATE").text();
				var WELL_ID= $(this).find("WELL_ID").text();
				var WELL_NAME= $(this).find("WELL_NAME").text();

				var tr = $("<tr><td>"+WELL_ID+"</td><td>"+CUSTOMER_NAME+"</td><td>"+WELL_NAME+"</td><td>"+FIELD+"</td><td>"+BLOCK_NO+"</td><td>"+LATITUDE+"</td><td>"+LONGITUDE+"</td><td>"+PART_NO+"</td><td>"+SERIAL_NO+"</td><td>"+PRODUCT_DESCRIPTION+"</td><td>"+PMS_DESCRIPTION+"</td><td>"+SHIPPED_SERVICE_DATE+"</td><td>"+LAST_MAINTENANCE_DATE+"</td><td>"+NEXT_MAINTENANCE_DATE+"</td><td>"+QUANTITY+"</td><td>"+PRICE+"</td><td>"+SALES_ORDER_TICKET_NO+"</td></tr>");
				$(".excelTable").append(tr);
			});
			$(".exportExcel").show();
		});*/
	
});

function searchBtnClick(){
	setAllMapSearch(null);
	tempMarker=[];
	var custName = $(".customerName").val();
	var well_name = $(".wellName").val();
	var part_number = $(".partNo").val();
	var serial_number = $(".serialNo").val();
	var maintenanceDt = $(".maintenanceDate").val();
	var xmassTree = $(".xmassTree").val();
	
	var jsonObj = [];
	var item = {};
	item["custName"]=custName;
	item["well_name"]=well_name;
	item["part_number"]=part_number;
	item["serial_number"] = serial_number;
	item["maintenanceDt"] = maintenanceDt;
	item["xmassTree"] = xmassTree;
	
	jsonObj.push(item);
	//$rootScope.searchFlag=1;
	searchFlag = 1;
	setAllMap(null);
	for(var i=0;i<label.length;i++){
		label[i].set('visible', 'none');
	}
	$(".infoBubbleContainer").parent().parent().remove();
	
	showLoader();
	$.ajax({
        url: './services/mvtapp/searchWellData',
        type: 'POST',
        data: JSON.stringify({"searchData":jsonObj}),
        dataType: 'xml',
        contentType: 'text/plain'
   }).done(function(xml){
	   
				$.each($("search_wells_data",xml),function(){
					console.log(xml);
							var latitude=$(this).find("latitude").text();
							var longitude=$(this).find("longitude").text();
							var customer_name=$(this).find("customer_name").text();
							well_name=$.trim($(this).find("well_name").text());
							//var field=$.trim($(this).find("field").text());
							//var block_no=$.trim($(this).find("block_no").text());
							well_id=$.trim($(this).find("well_id").text());
							//var maintenance_status=$.trim($(this).find("maintenance_status").text());
							var criteria_status=$.trim($(this).find("criteria_status").text());
							var maintenance_status=$.trim($(this).find("maintenance_status").text());
							//var drawing_type=$.trim($(this).find("drawing_type").text());
							if(criteria_status == "" || criteria_status == "N" || criteria_status == "n"){
								iconImage="images/"+maintenance_status+".png";
							}
							if(criteria_status == "Y" || criteria_status == "y"){
								iconImage="images/wells.png";
							}
							//iconImage="images/wells.png";
															
							marker1 = new google.maps.Marker({
								title: well_name,
								map: map,
								id:well_id,
								position: new google.maps.LatLng(latitude, longitude),
								icon: iconImage,
								optimized: false
							 });
							tempMarker.push(marker1);
							showWell(marker1, latitude, longitude, well_id, well_name,customer_name);
							var latlng = new google.maps.LatLng(latitude, longitude);
							map.setCenter(latlng);
							
							
						});
				
						setAllMap1(null);
						$(".searchResultPopup").show();
						$(".cName").text("");
						$(".searchCount").text("");
					
						var custNameArray = [];
					
					if(tempMarker.length > 0){
						$.each($("customerName",xml),function(){
							var name = $(this).text();
							$(".cName").append(name+"</BR>");
							custNameArray.push(name);
						});
					}
					/*if(custNameArray.length == 1){
						//$(".searchResultPopup .countLi").css("margin-top","15px");
					}else{
						$(".searchResultPopup .countLi").css("margin-top","54px");
					}
					$(".searchCount").text(tempMarker.length);
					if($rootScope.backFlag==1 || $rootScope.dashboardFlag==1)
					{
						$(".searchIcon").trigger("click");
					}
					else
					{
						closeLoader();
					}*/
					closeLoader();
					   		
	});

	        setAllMapSearch(null);
	        
}

function closealert(){
	
	$(".uploadalert").remove();
}

var options = { 
		   beforeSend:function(){
		$(".importTab").show();

		   },
		    success: function() 
		    {
		      

		    },
			
			error: function()
			{

			},
		    complete: function(response) 
		    {
		    	importClose();
		    	var title = "";
		    	 $(".uploadalert").remove();
		    	var message = response.responseText;
		         if(message != undefined && message!='' && message!=null)
		       {
		        	 if(message != "File Uploaded Successfully")
			 			{
			 				 title = "Error Alert";
			 			}
					 else{
						  title = "Success Alert";
					 }
				 var tAlert = '<div class="uploadalert">'+
				    '<div class="lightBoxTitle"><b>'+title+'</b></div>'+
					'<div class="videoPlace2"><div class="alertMsg"><strong>'+message+'</strong></div><br>'+
					'<div><button class="alertButton" onclick="closealert();return false;">OK</button></div></div></div>';
					$('body').append(tAlert);
					 if(message != "File Uploaded Successfully")
			 			{
			 				$(".alertMsg").css("color","red");
			 			}
					 else{
						 $(".alertMsg").css("color","green");
					 }
					 google.maps.event.addListener(map, 'click', function() {
						 $(".uploadalert").remove();
						});
		       }
			}		
		}; 

		     $("#myForm").ajaxForm(options);  
		     var overdueData ="";		     
function getOverdue(){
	
	var overdueData = '<div class="uploadalert">'+
    				  '<div class="lightBoxTitle"><b>Overdue Well Details</b><span onclick="closealert();return false;" class="cancelOverdue">X</span></div>'+
    				  '<div class="overduediv">'+
    				  '<div class="overdue"><h4>Already overdue well details:</h4>'+
    				  '<table cellpadding="5" class="overduetable" border="1px solid #cccccc;">'+
    				  '<thead><tr><th>Well Name</th><th>Part No</th><th>Recommended<br> Maintenance Date</th><th>No 0f days overdue</th></thead>'+
    				  '<tbody class="overdueBlock"></tbody>'+
    				  '</table>'+
    				  '</div>'+
    				  '<div class="todayoverdue"><h4>Well details whose maintenance is due today:</h4>'+
    				  '<table cellpadding="5" class="overduetable" border="1px solid #cccccc;">'+
    				  '<thead><tr><th>Well Name</th><th>Part No</th></thead>'+
    				  '<tbody class="todayoverdueBlock"></tbody>'+
    				  '</table>'+
    				  '</div>'+
    				  '<div class="lastoverdue"><h4>Well details whose maintenance is approaching and due in the next 3 days:</h4>'+
    				  '<table cellpadding="5" class="overduetable" border="1px solid #cccccc;">'+
    				  '<thead><tr><th>Well Name</th><th>Part No</th><th>Recommended<br> Maintenance Date</th></thead>'+
    				  '<tbody class="lastoverdueBlock"></tbody>'+
    				  '</table>'+
    				  '</div>'+
    				  '<br><div><button class="alertButton" onclick="closealert();return false;">OK</button></div>'+
    				  '</div>'+
    				  '</div>';
	
	uploadOverdue(overdueData);
		$('body').append(overdueData);
}
function uploadOverdue(overdueData){
	$.ajax({                                             
		url: "./services/mvtapp/getOverdue",   
		type:"GET",
		dataType: "xml"	
	}).done(function(xml){
		$(".overdue").hide();
		$(".todayoverdue").hide();
		$(".lastoverdue").hide();
		var $table1 = $(".overdueBlock");
		$table1.find("tr").remove();
		var $table2 = $(".todayoverdueBlock");
		$table2.find("tr").remove();
		var $table3 = $(".lastoverdueBlock");
		$table3.find("tr").remove();
		$.each($("overdue_data",xml),function(i){
			overdueFlag=1;
			console.log(xml);
			if($(this).find("NEXT_MAINTENANCE_DATE").text()!="" && $(this).find("DAYS").text() < 0){
				  var tr= $("<tr class='recRow'><td >"+$(this).find("WELL_NAME").text()+"</td><td>"+$(this).find("PART_NO").text()+"</td><td>"+$(this).find("NEXT_MAINTENANCE_DATE").text()+"</td><td>"+Math.abs($(this).find("DAYS").text())+"</td>");
				  $table1.append(tr);
				  $(".overdue").show();
				}
			if($(this).find("NEXT_MAINTENANCE_DATE").text()!="" && $(this).find("DAYS").text() == 0){
				  var tr= $("<tr class='recRow'><td >"+$(this).find("WELL_NAME").text()+"</td><td>"+$(this).find("PART_NO").text()+"</td>");
				  $table2.append(tr);
				  $(".todayoverdue").show();
				}
			if($(this).find("NEXT_MAINTENANCE_DATE").text()!="" && $(this).find("DAYS").text() > 0){
				var tr= $("<tr class='recRow'><td >"+$(this).find("WELL_NAME").text()+"</td><td>"+$(this).find("PART_NO").text()+"</td><td>"+$(this).find("NEXT_MAINTENANCE_DATE").text()+"</td>");
				  $table3.append(tr);
				  $(".lastoverdue").show();
				}
		});
		if(overdueFlag == 0){
			$('.uploadalert').hide();
		}
	});
}