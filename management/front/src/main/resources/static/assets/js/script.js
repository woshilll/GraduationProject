(function($) {
	
	"use strict";


    // Back to top
    $.scrollUp({
        scrollText: '<i class="fa fa-angle-up"></i>',
        easingType: 'linear',
        scrollSpeed: 900,
        animation: 'fade'
    });


	
    //js code for mobile menu toggle
   $(".menu-toggle").on("click", function() {
       $(this).toggleClass("is-active");
   });



    

    // Preloader Js
    $(window).on('load', function(){
      $('.preloader').fadeOut(1000); // set duration in brackets    
    });


    // Full Screen Search
    $(".search-btn").on('click', function(){
        $(".search-full").removeClass("close");
        $(".search-full").addClass("open");
    })

    $(".search-close").on('click', function(){
        $(".search-full").removeClass("open");
        $(".search-full").addClass("close");
    })
    

    $(".post-details").each(function () {
        $(this).find(".popup-img").magnificPopup({
            type: "image",
            gallery: {
                enabled: true
            }
        });
    });



    // Featured posts
    $('.featured-carousel').owlCarousel({
        loop:true,
        dots: false,
        autoplay: true,
        mouseDrag: true,
        animateOut: 'fadeOut',
        animateIn: 'fadeIn',
        autoplayTimeout: 10000,
        smartSpeed: 1000,
        nav:true,
        navText: [
            '<i class="fa fa-angle-left"></i>',
            '<i class="fa fa-angle-right"></i>'
        ],
        responsive:{
            0:{
                items:1,
                nav:false,
            },
            576:{
                items:1
            },
            1000:{
                items:1
            }
        }
    });


    // Post Carousel
    $('.post-carousel').owlCarousel({
        loop:true,
        dots: false,
        autoplay: false,
        mouseDrag: true,
        animateOut: 'fadeOut',
        animateIn: 'fadeIn',
        autoplayTimeout: 10000,
        smartSpeed: 1000,
        nav:true,
        navText: [
            '<i class="fa fa-angle-left"></i>',
            '<i class="fa fa-angle-right"></i>'
        ],
        responsive:{
            0:{
                items:1,
                nav:false,
            },
            576:{
                items:1
            },
            1000:{
                items:1
            }
        }
    });

    // Post Carousel 2
    $('.full-carousel').owlCarousel({
        loop:true,
        dots: false,
        autoplay: true,
        mouseDrag: true,
        animateOut: 'fadeOut',
        animateIn: 'fadeIn',
        autoplayTimeout: 5000,
        smartSpeed: 500,
        margin: 5,
        nav:true,
        navText: [
            '<i class="fa fa-angle-left"></i>',
            '<i class="fa fa-angle-right"></i>'
        ],
        responsive:{
            0:{
                items:1,
                nav:false,
            },
            450:{
                items:1
            },
            576:{
                items:2
            },
            767:{
                items:2
            },
            991:{
                items:3
            },
            1024:{
                items:3
            },
            1200:{
                items:3
            }
        }
    });
    
    // Post Carousel 3
    $('.hot-carousel').owlCarousel({
        loop:true,
        dots: false,
        autoplay: true,
        mouseDrag: true,
        animateOut: 'fadeOut',
        animateIn: 'fadeIn',
        autoplayTimeout: 10000,
        smartSpeed: 1000,
        margin: 30,
        nav:true,
        navText: [
            '<i class="fa fa-angle-left"></i>',
            '<i class="fa fa-angle-right"></i>'
        ],
        responsive:{
            0:{
                items:1,
                nav:false,
            },
            450:{
                items:1
            },
            576:{
                items:1
            },
            767:{
                items:2
            },
            991:{
                items:2
            },
            1024:{
                items:3
            },
            1200:{
                items:3
            }
        }
    });



    // Tranding News ticker
    $('.breaking-news-ticker').newsTicker({
        row_height: 40,
        max_rows: 1,
        speed: 600,
        duration: 5000,
        prevButton:  $('.news-ticker-prev'),
        nextButton:  $('.news-ticker-next'),
    });




	
})(jQuery);