/*

  The key for making this animation is to move the entire screen
  together with the shared view animations (the thumbnail and buttons).

  The action bar is being faded in at the same time.

  A few special references are used:
  - $('/')  -- the entire screen
  - $('#actionbar') -- action bar including tabs

  customizeScreenOpeningAnimation() and customizeScreenClosingAnimation()
  are used to make sure the custom animations play together with the
  shared view animations.

*/


var rootY = [0, $('/').height - 150]
var actionbarAlpha = [1, 0]
$('time').alpha = 0

/*
  This function creates the animator to be played then screen is opening/closing.
  The parameter "isClosing" is used to choose between the opening/closing modes.
*/
function createAnimators(isClosing) {
    var duration = 300
    var makeAnim = function (props) {
      return {
        properties: props,
        duration: duration,
        interpolator: 'android:accelerate_decelerate'
      }
    }

    var from = isClosing ? 0 : 1
    var to = isClosing ? 1 : 0
    var root = $('/').animator(makeAnim({y: [rootY[from], rootY[to]]}))
    var ab = $('#actionbar').animator(makeAnim({alpha: [actionbarAlpha[from], actionbarAlpha[to]]}))
    var window = together([root, ab])
    var fromAlpha = isClosing ? 1 : 0
    var toAlpha = isClosing ? 0 : 1
    var time = $('time').animator({properties: {alpha: [fromAlpha, toAlpha]}, duration: 200})
    return isClosing ? together([time, window]) : sequence([window, time])
}


customizeScreenOpeningAnimation(function(anim) {
    return together([anim, createAnimators(false)])
})


customizeScreenClosingAnimation(function(anim) {
    return together([anim, createAnimators(true)])
})


$('@android:id/home').parent.on('click', function() {
    closeThisScreen()
})