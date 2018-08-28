/**
 * Phoenix is a jQuery Slider that obtains new life by arising from the ashes of its predecessor
 */
(function ($) {

	"use strict";

	// No jQuery? No reborn. 
	if (!$) return;

	var Phoenix = function () {

		var _ = this;

		this.setup = function (element, options) {

			this.element = $(element);
			this.options = options;
			this.interval = null;


			this.ashes = this.element.children("div");
			this.total = this.ashes.length;
			this._static = this.ashes.length == 1;
			this.current = this.ashes.length - 1;

			this.elevation = null;

			this._defaults = {
				delay: 3000,
				speed: 5000,
				height: null,
				fullscreen: true,
				dots: true,
				keys: true,
				complete: function (index, currentElement, prevElement) {}
			};

			this.init();
		};

		this.init = function () {

		    // Store a reference to the original remove method.
		    var originalMethod = jQuery.fn.transition || jQuery.fn.animate;

		    // Define overriding method.
		    jQuery.fn.spitFire = function(){
		        // Execute the original method.
		        return originalMethod.apply( this, arguments );
		    }

			this._defaults = $.extend(this._defaults, this.options);

			var ash, pic;

			$(window).on('resize', function () {
				_.resize();
			}).trigger('resize');

			// Show Dots
			this._defaults.dots && !_._static && this.dots();

			//  Custom keyboard support
			this._defaults.keys && !_._static && $(document).keydown(this.keys);

			this.ashes.each(function (index) {

				ash = $(this);

				pic = ash.children("img");

				ash.css({
					'background-image': 'url(' + pic.attr('src') + ')',
					// 'filter': "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + pic.attr('src') + "', sizingMethod='scale')",
					// '-ms-filter': "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + pic.attr('src') + "', sizingMethod='scale')",
				});

				if (index == 0) ash.css('z-index', 4);

				pic.remove();
			});

			// Adding Touch Support with jQueryMobile
			!_._static && $().swiperight && $().swipeleft && _.element.swipeleft(_.next) && _.element.swiperight(_.prev);

			this.next();
			!_._static && this.play();
		};

		this.resize = function () {

			this.computeTallness();

			this.element.css({
				'height': (this._defaults.fullscreen === true ? $(window).height() : _.elevation) + 'px'
			});
		};

		this.computeTallness = function() {
			if (this._defaults.fullscreen === true) {

				_.elevation = $(window).height();

			} else {
				if ((typeof _._defaults.height).toLowerCase() === 'string') {
					if (_._defaults.height.slice(-1) === '%') {
						_.elevation = $(window).height() * (_._defaults.height.substr(0, _._defaults.height.length - 1) / 100);
					}	
				} else if ((typeof _._defaults.height).toLowerCase() === 'number') {
					_.elevation = _._defaults.height;
				} else {
					_.elevation = _.element.height() == 0 ? $(window).height() : _.element.height();
				}
			}
		};

		this.dots = function () {
			//  Create the HTML
			var html = '<div class="container-dots-wrapper"><div class="container-dots-inner"><ol class="dots">';
			$.each(this.ashes, function (index) {
				html += '<li class="dot' + (index < 1 ? ' active' : '') + '">' + (index + 1) + '</li>';
			});
			html += '</ol></div></div>';

			//  Add it to the Slider
			this.element.append(html).find('.dot').click(function () {
				_.move($(this).index(), true);
			});
		}

		this.index = function () {
			return _.current;
		}

		this.destroy = function () {

			// Remove elements, unregister listeners, etc
			// TODO

			// Remove data
			this.element.removeData();
		};

		this.play = function () {
			_.interval = setInterval(function () {
				_.next();
			}, _._defaults.delay);

			return _;
		};

		//  Stop autoplay
		this.stop = function () {
			_.interval = clearInterval(_.interval);

			return _;
		};

		//  Keypresses
		this.keys = function (e) {
			var key = e.which;
			var map = {
				//  Prev/next
				37: _.prev,
				39: _.next,
			};

			if ($.isFunction(map[key])) {
				map[key](true);
			}
		};

		//  Arrow navigation
		this.next = function (f) {
			return _.move(_.current + 1, f);
		};

		this.prev = function (f) {
			return _.move(_.current - 1, f);
		};

		this.move = function (index, f) {

			if (index == _.current) return;

			var newIndex, oldIndex,
				oldIndex = _.current,
				oldElement = this.ashes.eq(_.current),
				newElement;

			//  If it's out of bounds, go to the first slide
			if (!this.ashes.eq(index).length) index = 0;
			if (index < 0) index = (this.ashes.length - 1);

			_.current = index;

			newIndex = _.current;
			newElement = this.ashes.eq(newIndex);

			newElement.css('opacity', 1);

			if (_._static) return;

			_.element.find('.dot:eq(' + index + ')').addClass('active').siblings().removeClass('active');

			oldElement.spitFire({
				'opacity': 0,
				'duration': this._defaults.speed
			}, function () {
				oldElement.css('z-index', 'auto');
				newElement.css('z-index', 4);

				if (f) _.stop().play();

				if (typeof (_._defaults.complete) === 'function') _._defaults.complete(newIndex, newElement, oldElement);
			});
		}

		this.getRandomInt = function (min, max) {
			return Math.floor(Math.random() * (max - min + 1)) + min;
		}

	};


	//  Create a jQuery plugin
	return $.fn.phoenix = function (o) {
		var len = this.length;

		//  Enable multiple-slider support
		return $(this.each(function (index) {
			//  Cache a copy of $(this), so it 
			var me = $(this),
				instance = (new Phoenix).setup(me, o);

			//  Invoke a Phoenix Slider instance
			me.data('phoenix' + (len > 1 ? '-' + (index + 1) : ''), instance);
		})).data('phoenix');
	};

})(jQuery);