var duration = 500

function makeAnim(props) {
  return {
    properties: props,
    duration: duration,
    interpolator: '@android:interpolator/accelerate_decelerate'
  }
}

var rootY = [0, $('/').height - 180]
var actionbarAlpha = [1, 0]
var contentY = [$('#content').y, 0]

function playAnims(index) {
    var from = index==0 ? 1 : 0
    var to = index==0 ? 0 : 1
    var root = $('/').animate(makeAnim({y: [rootY[from], rootY[to]]}))
    var content = $('#content').animate(makeAnim({y: [contentY[from], contentY[to]]}))
    var ab = $('#actionbar').animate(makeAnim({alpha: [actionbarAlpha[from], actionbarAlpha[to]]}))
    var window = together([root,content,ab])
    $('time').alpha = 0
    $('title').alpha = 0
    $('subtitle').alpha = 0
    var time = $('time').animate({properties: {alpha: [0,1]}})
    var title = $('title').animate({properties: {alpha: [0,1]}})
    var subtitle = $('subtitle').animate({properties: {alpha: [0,1]}})
    var labels = together([title, subtitle, time])
    sequence([window, labels]).start()
}

playAnims(0)

/*
var absX = $('thumbnail').absX
var absY = $('thumbnail').absY
var t = $('@id/thumbnail').animate({properties: {scale:[0.2, 1], absX:absX, absY:absY}})
var s = $('@id/title').animate({properties: {textSize:[1, 80]}})
together([t,s]).start()
*/

$('@android:id/home').parent.on('click', function() {
    playAnims(1)
    closeThisScreen()
})