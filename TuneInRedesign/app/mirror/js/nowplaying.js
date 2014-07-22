var duration = 400

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

var repeatY = [$('@id/repeat').y, 0]
var repeatX = [$('@id/repeat').x, 315]
var pauseY = [$('@id/pause').y, 0]
var pauseX = [$('@id/pause').x, 444]
var stopY = [$('@id/stop').y, 0]
var stopX = [$('@id/stop').x, 571]
var thumbnailY = [$('@id/thumbnail').y, -215]
var thumbnailX = [$('@id/thumbnail').x, -200]
var thumbnailScale = [1, 0.16]


$('/').y = rootY[1]
$('#content').y = contentY[1]
$('#actionbar').alpha = actionbarAlpha[1]
$('@id/repeat').y = repeatY[1]
$('@id/repeat').x = repeatX[1]
$('@id/pause').y = pauseY[1]
$('@id/pause').x = pauseX[1]
$('@id/stop').y = stopY[1]
$('@id/stop').x = stopX[1]
$('@id/thumbnail').y = thumbnailY[1]
$('@id/thumbnail').x = thumbnailX[1]
$('@id/thumbnail').scale = thumbnailScale[1]

function playAnims(index) {
    $('@id/repeat').animate(makeAnim({x: repeatX[index], y: repeatY[index]}))
    $('@id/pause').animate(makeAnim({x: pauseX[index], y: pauseY[index]}))
    $('@id/stop').animate(makeAnim({x: stopX[index], y: stopY[index]}))
    $('@id/thumbnail').animate(makeAnim({x: thumbnailX[index], y: thumbnailY[index], scale: thumbnailScale[index]}))

    $('/').animate(makeAnim({y: rootY[index]}))
    $('#content').animate(makeAnim({y: contentY[index]}))
    $('#actionbar').animate(makeAnim({alpha: actionbarAlpha[index]}))
}


playAnims(0)

$('@android:id/home').parent.on("click", function() {
    playAnims(1)
})

