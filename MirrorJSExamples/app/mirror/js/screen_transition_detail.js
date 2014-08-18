/*

  @id/title_container : the blue bar just below the image
  @id/info_container  : the white bar below "titile_container"

*/

/*******************************************
 Set up initial states and save the height
 and y location for creating animations
********************************************/

var tcHeight = $('title_container').height
var infoHeight = $('info_container').height
var infoY = $('info_container').y

$('fab').scale = 0
$('title_container').height = 0
$('info_container').height = 0

$('title_container').alpha = 0
$('info_container').alpha = 0


/*******************************************
 Create the animations for the containers
********************************************/

var heightAnim = function(oldHeight) {
                    return {
                       properties: {
                         height: [0, oldHeight],
                         alpha: [0, 1]
                       },
                       duration: 200
                    }
                 }
var tc = $('title_container').animator(heightAnim(tcHeight))
var icHeight = $('info_container').animator(heightAnim(infoHeight))
var icY = $('info_container').animator({
    properties: { y: [$('title_container').y, infoY]}
})
var ic = together([icHeight, icY])
var tcic = together([tc, ic])

/*******************************************
 Create the animations that fade in "title",
 "subtitle", "volume" etc.
********************************************/

var idsToAnimateAlpha = ['title', 'subtitle', 'volume', 'name', 'time']
var alphas = undefined
for (i=0; i<idsToAnimateAlpha.length; i++) {
    var view = $(idsToAnimateAlpha[i])
    view.alpha = 0
    var a = view.animator({
        properties: { alpha: [0, 1] }
    })
    alphas = alphas==undefined ? a : together([alphas, a])
}

var tcic_alphas = together([tcic, delay(alphas, 200)])

/*******************************************
 Create the animation for the FAB
 (Floating Action Button)
********************************************/

var fab = $('fab').animator({
    properties: { scale: [0, 1] }
})

var tcic_fab = together([tcic_alphas, delay(fab, 100)])

/*******************************************
 Make sure to play the "tcic_fab" animation
 after shared view animation is done
********************************************/

customizeScreenOpeningAnimation(function(anim) {
    return sequence([anim, tcic_fab])
})

customizeScreenClosingAnimation(function(anim) {
    return null
})