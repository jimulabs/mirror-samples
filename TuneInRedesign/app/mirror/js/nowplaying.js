var duration = 300

function makeAnim(props) {
  return {
    properties: props,
    duration: duration,
    interpolator: '@android:interpolator/accelerate_decelerate'
  }
}

var rootY = [0, $('/').height - 150]
var actionbarAlpha = [1, 0]
var contentY = [$('#content').y, 0]
$('time').alpha = 0

function createAnimators(isClosing) {
    var from = isClosing ? 0 : 1
    var to = isClosing ? 1 : 0
    var root = $('/').animator(makeAnim({y: [rootY[from], rootY[to]]}))
    var content = $('#content').animator(makeAnim({y: [contentY[from], contentY[to]]}))
    var ab = $('#actionbar').animator(makeAnim({alpha: [actionbarAlpha[from], actionbarAlpha[to]]}))
    var window = together([root,content,ab]) // window = `[root, content, ab]`
    var labelDuration = 200
    var fromAlpha = isClosing ? 1 : 0
    var toAlpha = isClosing ? 0 : 1
    var labelAnim = {properties: {alpha: [fromAlpha, toAlpha]}, duration: labelDuration}
    var time = $('time').animator(labelAnim)
    return isClosing ? together([time, window]) : sequence([window, time]) // return `[root, content, ab] => [title, subtitle, time]`
}


customizeScreenOpeningAnimation(function(anim) {
    return together([anim, createAnimators(0)])
})


customizeScreenClosingAnimation(function(anim) {
    return together([anim, createAnimators(1)])
})


$('@android:id/home').parent.on('click', function() {
    closeThisScreen()
})