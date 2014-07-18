/*
var nowPlaying = $('@id/nowplaying_container')

$.openScreen('now_playing.xml', {
    viewToAnimate: nowPlaying,
    transition: {
        scenes: ['@layout/nowplaying_mini', '@layout/nowplaying_fullscreen'],
        type: 'changeBounds'
    }
})

nowPlaying.animateByTransition({
    scenes: ['@layout/nowplaying_mini', '@layout/nowplaying_fullscreen'],
    type: 'changeBounds'
})
*/

/*
$('/').animate({
    properties: {
        scale: [2, 1],
        y: [0, 500]
    },
    interpolator: '@android:interpolator/bounce',
    duration: 1000
})
*/

/*
 Goal: find an easy way to define flexible transition animation between screens in JavaScript
*/

//$('@android:id/action_bar_overlay_layout').background = '@drawable/background_gradient'
//$('@android:id/action_bar_overlay_layout').beginDelayedTransition()
/*
$('/').x = 800
$('/').alpha = 0
$('/').animate({
  properties: {
    x: [800, 0],
    alpha: [0, 1],
    rotation: [-90, 0]
  },
  duration: 5000
})
*/

var slideDownFunc = function() {
    $('@id/thumbnail').x = 50
    $('@id/thumbnail').y = 500

                        /*
                        $('@id/nowplaying_container').animateByTransition({
                            scenes: ['@layout/nowplaying_fullscreen', '@layout/nowplaying_mini'],
                            transitions: ['changeBounds']
                        })
                        */
                        /*
                        $('/').animate({
                            properties: {
                                y: [0, 500]
                            },
                            interpolator: '@android:interpolator/deaccelerate',
                            duration: duration
                        })
                        $('@android:id/action_bar_container').animate({
                            properties: {
                                scaleY: [1, 0]
                            },
                            duration: duration
                        })
                        */
                    }
//slideDownFunc()

$('@android:id/home').parent.on("click", slideDownFunc)
