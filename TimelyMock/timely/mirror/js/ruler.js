function setRulerLabelParams(view, alpha, scale) {
    var rulerText = $('@id/rulerText', view)
    rulerText.alpha = alpha
    rulerText.textSize = 8 * scale
//    $('@id/rulerLine', view).scaleX = scale
}

function showNormalLabelOnRuler(view, index) {
    var labelInterval = 6
    setRulerLabelParams(view, index%labelInterval==0 ? 1 : 0, 1)
}

function initRuler() {
    var ruler = $('@id/ruler')
    var children = ruler.children
    for (i=0; i<children.size(); i++) {
        showNormalLabelOnRuler(children.get(i), i)
    }
}

function magnifyRuler(ratio) {
    var ruler = $('@id/ruler')
    var children = ruler.children, childCount = children.size()
    var center = Math.floor(childCount * ratio)
    var radius = 4, maxScale = 4
    var lo = Math.max(0, center-radius), hi = Math.min(childCount-1, center+radius)
    for (i=0; i<childCount; i++) {
        var v = children.get(i)
        if (lo<=i && i<=hi) {
            var vVsCenter = 1 - Math.abs(i/childCount - ratio)*5
//            log('i='+i+' vVsCenter='+vVsCenter+' ratio='+ratio+' childCount='+childCount)
            setRulerLabelParams(v, vVsCenter, maxScale * vVsCenter)
        } else {
            showNormalLabelOnRuler(v, i)
        }
    }
}

initRuler()