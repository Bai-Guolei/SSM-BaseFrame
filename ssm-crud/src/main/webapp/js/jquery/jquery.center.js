(function ($)
{
	$.fn.center = function (settings)
	{
		var style = $.extend(
		{
			position: 'absolute', //absolute or fixed
			top     : '50%', //50%�����У���Ӧ�ø��߾���㣬������迼���ˡ�
			left    : '50%',
			zIndex  : 2009,
			relative: true //����ڰ��������������л�������ҳ��
		}, settings || {});

    return this.each(function ()
	{
		var $this = $(this);

		if (style.top == '50%')
			style.marginTop = -$this.outerHeight() / 2;
      
		if (style.left == '50%')
			style.marginLeft = -$this.outerWidth() / 2;
      
		if (style.relative && !$this.parent().is('body') && $this.parent().css('position') == 'static')
			$this.parent().css('position', 'relative');

		delete style.relative;

		// ie6
		/*
		if (style.position == 'fixed' && $.browser.version == '6.0')
		{
			style.marginTop += $(window).scrollTop();
			style.position = 'absolute';
			$(window).scroll(function ()
			{
				$this.stop().animate(
				{
					marginTop: $(window).scrollTop() - $this.outerHeight() / 2
				});
			});
		}
		*/

		$this.css(style);
    });
  };
})(jQuery);