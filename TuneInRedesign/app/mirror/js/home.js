var openNowPlaying = function() {
    var slideLeftOut = {
        properties: {
            x: [0, -800],
            alpha: [1, 0]
        },
        duration: 500
    }
//    openScreen('../now_playing.xml', slideLeftIn, slideLeftOut)


    // using the old view animations (which can be used in overridePendingAnimations())
//    openScreen('../now_playing.xml',
//        '@anim/slide_up_in', '@anim/slide_up_out',
//        '@anim/slide_down_in', '@anim/slide_down_out')

    // using property animations
//    openScreen('../now_playing.xml',
//        '@animator/slide_up_in', '@animator/slide_up_out',
//        '@animator/slide_down_in', '@animator/slide_down_out')

    // A null param means no corresponding animation
    // here, the new screen will slide in when launched, and slide out when Back is pressed
    // however, the calling screen won't move
//    openScreen('../now_playing.xml',
//        '@anim/slide_up_in', null, null, '@anim/slide_down_out')

    // An omitted param means null
//    openScreen('../now_playing.xml', '@anim/slide_up_in', '@anim/slide_up_out', '@anim/slide_down_in', '@anim/slide_down_out')


//    openScreen('../now_playing.xml')

//    openScreenWithSharedElements('../now_playing.xml', ['repeat', 'pause', 'stop', 'thumbnail'])

//    openScreen('../now_playing.xml', {properties: { rotation:[0, 90], alpha:[0,1] }})

    openScreen('../now_playing.xml', ['thumbnail', 'title', 'subtitle', 'repeat', 'pause', 'stop'])//, '@android:animator/fade_in', '@android:animator/fade_out', null, null)

}
$('@id/thumbnail').on('click', openNowPlaying)
$('@id/title').on('click', openNowPlaying)
$('@id/subtitle').on('click', openNowPlaying)

var r = $('@id/repeat').animate({
    properties: { rotation: [0, 90, 0], scale: [1,2], x: 300}
})

var p = $('@id/pause').animate({
    properties: { rotation: [0, 90, 0]}
})

//together([r,p]).start()