include('ruler.js')

/**************** Function definitions  **************/

function playRipple(x, y) {
    var ripple = $('@id/ripple')
    ripple.alpha = 1
    ripple.scale = 1
    ripple.x = x - ripple.width/2
    ripple.y = y - ripple.height/2
    ripple.animate({
        properties: {
            scale: 4,
            alpha: 0
        },
        duration: 601
    });
}

playRipple(200, 200)

function calcTimeTextSideX(touchX) {
    var timeBar = $('@id/timeBar')
    var timeText = $('@id/timeText')
    var oneThird = timeBar.width/2.5 - timeText.width
    var twoThird = timeBar.width/3 * 2
    var touchedLeft = touchX < timeText.x + timeText.width/2
    return touchedLeft ? twoThird : oneThird
}

function setTime(ratio, stepInMinute) {
    var timeBar = $('@id/timeBar')
    var timeBarParent = timeBar.parent
    var timeText = $('@id/timeText')
    ratio = Math.max(0, Math.min(1, ratio))
    timeBar.y = ratio * (timeBarParent.height - gTimeBarYMargin) + timeBar.height
    var hm = ratio * 24
    var h = Math.floor(hm)
    var m = Math.round((hm - h)*60)
    if (stepInMinute) m = Math.floor(m/stepInMinute)*stepInMinute
    var zeropadding = function(x) { return x<10 ? '0'+x : x }
    timeText.text = zeropadding(h) + ':' + zeropadding(m)
    gCurrentMinutes = h*60 + m

    magnifyRuler(ratio)
}

function setTimeInMinutes(minutes) {
    setTime(minutes / (24*60))
}

/**************** Code entry point **************/


var timeBar = $('@id/timeBar')
var timeBarYOffset = 0
var timeBarParent = timeBar.parent
var gTimeBarYMargin = 50
var gCurrentMinutes = 0

var hitTimeBarWhenDown = false
timeBarParent.on('touch',
    function(view, event) {
        if (event.type == 'move' && hitTimeBarWhenDown) {
            var ratio = (event.y - gTimeBarYMargin-timeBar.height) / (timeBarParent.height - gTimeBarYMargin)
            setTime(ratio, 30)
        } else {
            var timeText = $('@id/timeText', timeBar)
            var isUp = event.type == 'up'
            var isDown = event.type == 'down'
            var hitTimeBar = timeBar.y<=event.y && event.y<=timeBar.y+timeBar.height
            if (isDown) hitTimeBarWhenDown = hitTimeBar

            if (hitTimeBarWhenDown) {
                var sideX = calcTimeTextSideX(event.x)
                var centerX = (timeBar.width - timeText.width)/2
                timeText.animate({
                    properties: {
                        x: isUp ? centerX : sideX,
                        scale: isUp ? 1 : 0.8
                    },
                    interpolator: '@android:interpolator/decelerate_cubic',
                    duration: 350
                });
                if (isUp) timeBar.animate('@animator/bounce_y')
            } else if (isUp) {
                var sign = event.y < timeBar.y ? -1 : 1
                setTimeInMinutes(gCurrentMinutes + sign*5)
            }
            if (isUp) playRipple(event.x + view.x, event.y + view.y)
        }
    }
);

setTimeInMinutes(10*60+30)

