$('avatar').on('click',
     function(view, event) {
         view.animator('card_flip_right_out',
             function(animationEvent) {
                 var tick = $('tick', view.parent)
                 tick.visibility = 'visible'
                 view.visibility = 'invisible'
                 tick.animator('card_flip_left_in').start()
            }).start()
     })

$('tick').on('click',
    function(view, event) {
        view.animator('card_flip_right_out',
            function(animationEvent) {
                var avatar = $('avatar', view.parent)
                avatar.visibility = 'visible'
                view.visibility = 'invisible'
                avatar.animator('card_flip_right_in').start()
           }).start()
    })