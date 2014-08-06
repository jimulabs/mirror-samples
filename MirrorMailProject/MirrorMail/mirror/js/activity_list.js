$('@id/avatar').on('click',
     function(view, event) {
         view.animator('@animator/card_flip_right_out',
             function(animationEvent) {
                 var tick = $('@id/tick', view.parent)
                 tick.visibility = 'visible'
                 view.visibility = 'invisible'
                 tick.animator('@animator/card_flip_left_in').start()
            }).start()
     })

$('@id/tick').on('click',
    function(view, event) {
        view.animator('@animator/card_flip_right_out',
            function(animationEvent) {
                var avatar = $('@id/avatar', view.parent)
                avatar.visibility = 'visible'
                view.visibility = 'invisible'
                avatar.animator('@animator/card_flip_right_in').start()
           }).start()
    })