/**
 *  jQuery Tooltip Plugin
 *  @requires jQuery v1.2.6 or greater
 *  http://hernan.amiune.com/labs
 *
 *  Copyright (c)  Hernan Amiune (hernan.amiune.com)
 *  Licensed under MIT license:
 *  http://www.opensource.org/licenses/mit-license.php
 * 
 *  Version: 1.0
 */
 
(function($){ $.fn.jqtool_tip = function(options){

    var defaults = {
        cssClass: "",     //CSS class or classes to style the tooltip
		delay : 0,        //The number of milliseconds before displaying the tooltip
        duration : 3000,   //The number of milliseconds after moving the mouse cusor before removing the tooltip.
        xOffset : 5,     //X offset will allow the tooltip to appear offset by x pixels.
        yOffset : 5,     //Y offset will allow the tooltip to appear offset by y pixels.
		opacity : 0,      //0 is completely opaque and 100 completely transparent
		fadeDuration: 500, //[toxi20090112] added fade duration in millis (default = "normal")
		eventshow : 'mouseover', //[Stijn Haulotte] added custom show event
		eventhide : 'mouseleave', //[Stijn Haulotte] added custom hide event
		ajaxSource : 'href', //[Stijn Haulotte] added custom ajax source
		closebutton : 'no', //[Stijn Haulotte] added close button
		fixed: false
	};
  
    var options = $.extend(defaults, options);
	
	
	return this.each(function(index) {
		
		var $this = $(this);
		
		//use just one div for all tooltips
		// [toxi20090112] allow the tooltip div to be already present (would break currently)
		$jqtool_tip=$("#divTooltip");
		$jqtool_tipT=$("#divTooltipT");
		$jqtool_tip.hide();
		if($jqtool_tip.length == 0){
			$jqtool_tip = $('<div id="divTooltip"><div id="divTooltipT"></div><div id="divTooltipC"></div></div>');			
			$('body').append($jqtool_tip);
			$jqtool_tip.hide();
		}
		$jqtool_tipC=$("#divTooltipC");

		//displays the tooltip
		function show (e){
			//compatibility issue
			e = e ? e : window.event;
			
			// show the close button
			if(options.closebutton == 'yes'){
				$jqtool_tipT.html("<div id='close'>Sluiten</div>");
			}
			
			//don't hide the tooltip if the mouse is over the element again
			clearTimeout($jqtool_tip.data("hideTimeoutId"));
			
			//set the tooltip class
			$jqtool_tip.removeClass($jqtool_tip.attr("class"));
			$jqtool_tip.css("width","");
			$jqtool_tip.css("height","");
			$jqtool_tip.addClass(options.cssClass);
			$jqtool_tip.css("opacity",1-options.opacity/100);
			$jqtool_tip.css("position","absolute");			
			
			//save the title text and remove it from title to avoid showing the default tooltip
			$jqtool_tip.data("title",$this.attr("data"));
			$this.attr("title","");
			$jqtool_tip.data("alt",$this.attr("alt"));
			$this.attr("alt","");
			
			//set the tooltip content
			$jqtool_tipC.html($jqtool_tip.data("title"));
			// [toxi20090112] only use ajax if there actually is a href or rel attrib present
			var el=$this.attr(options.ajaxSource);
			if(el!=undefined && el!="" && el != "#")
			    $jqtool_tipC.html($.ajax({url:$this.attr(options.ajaxSource),async:false}).responseText);
			
			if(options.fixed === false){
				//set the tooltip position
				winw = $(window).width();
				w = $jqtool_tip.width();
				xOffset = options.xOffset;
				
				//right priority
				if(w+xOffset+50 < winw-e.clientX)
				  $jqtool_tip.css("left", $(document).scrollLeft() + e.clientX+xOffset);
				else if(w+xOffset+50 < e.clientX)
				  $jqtool_tip.css("left", $(document).scrollLeft() + e.clientX-(w+xOffset));
				else{
				  //there is more space at left, fit the tooltip there
				  if(e.clientX > winw/2){
					$jqtool_tip.width(e.clientX-50);
					$jqtool_tip.css("left", $(document).scrollLeft() + 25);
				  }
				  //there is more space at right, fit the tooltip there
				  else{
					$jqtool_tip.width((winw-e.clientX)-50);
					$jqtool_tip.css("left", $(document).scrollLeft() + e.clientX+xOffset);
				  }
				}
				
				winh = $(window).height();
				h = $jqtool_tip.height();
				yOffset = options.yOffset;
				//top position priority
				if(h+yOffset + 50 < e.clientY)
				  $jqtool_tip.css("top", $(document).scrollTop() + e.clientY-(h+yOffset));
				else if(h+yOffset + 50 < winh-e.clientY)
				  $jqtool_tip.css("top", $(document).scrollTop() + e.clientY+yOffset);
				else 
				  $jqtool_tip.css("top", $(document).scrollTop() + 10);
			}
			
			//start the timer to show the tooltip
			//[toxi20090112] modified to make use of fadeDuration option
			$jqtool_tip.data("showTimeoutId", setTimeout("$jqtool_tip.fadeIn("+options.fadeDuration+")",options.delay));
		}
		$this.bind(options.eventshow, function(event){
			show(event);
		});
		
		$jqtool_tip.bind('mouseover', function(event){
			$this.stop();
			clearTimeout($jqtool_tip.data("hideTimeoutId"));
		});
		
		$jqtool_tip.bind('mouseleave', function(event){
			hide(event);
		});
		
		function hide(e){
			//compatibility issue
			e = e ? e : window.event;
			//restore the title
			$this.attr("title",$jqtool_tip.data("title"));
			$this.attr("alt",$jqtool_tip.data("alt"));
			//don't show the tooltip if the mouse left the element before the delay time
			clearTimeout($jqtool_tip.data("showTimeoutId"));
			//start the timer to hide the tooltip
			//[toxi20090112] modified to make use of fadeDuration option
			$jqtool_tip.data("hideTimeoutId", setTimeout("$jqtool_tip.fadeOut("+options.fadeDuration+")",options.duration));
		}
		$this.bind(options.eventhide, function(event){
			hide(event);
		});
		
		$this.click(function(e){
		    e.preventDefault();
		});
		$jqtool_tipT.click(function(event){
		    hide(event);
		});

	});

}})(jQuery);