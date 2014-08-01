var tcHeight = $('title_container').height
var infoHeight = $('info_container').height
var infoY = $('info_container').y

$('fab').scale = 0
$('title_container').height = 0
$('info_container').height = 0

// the following two lines won't be needed after shared view animation is properly implemented
$('title_container').alpha = 0
$('info_container').alpha = 0

var heightAnim = function(oldHeight) {
                    return {
                       properties: {
                         height: [0, oldHeight],
                         alpha: [0, 1]
                       },
                       duration: 200
                    }
                 }
var tc = $('title_container').animate(heightAnim(tcHeight))
var icHeight = $('info_container').animate(heightAnim(infoHeight))
var icY = $('info_container').animate({
    properties: { y: [$('title_container').y, infoY]}
})
var ic = together([icHeight, icY])
var tcic = together([tc, ic])

var idsToAnimateAlpha = ['title', 'subtitle', 'volume', 'name', 'time']
var alphas = undefined
for (i=0; i<idsToAnimateAlpha.length; i++) {
    var view = $(idsToAnimateAlpha[i])
    view.alpha = 0
    var a = view.animate({
        properties: { alpha: [0, 1] }
    })
    alphas = alphas==undefined ? a : together([alphas, a])
}

var tcic_alphas = together([tcic, delay(alphas, 200)])

var fab = $('fab').animate({
    properties: { scale: [0, 1] }
})

var tcic_fab = together([tcic_alphas, delay(fab, 100)])
customizeScreenOpeningAnimation(function(anim) {
    return sequence([anim, tcic_fab]) // `anim => [tcic_alpha, 100 => fab]`
})

customizeScreenClosingAnimation(function(anim) {
    return null
})