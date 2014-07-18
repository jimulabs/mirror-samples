var openNowPlaying = function() {
    var slideLeftOut = {
        properties: {
            x: [0, -800],
            alpha: [1, 0]
        },
        duration: 500
    }
//    openScreen('../now_playing.xml', slideLeftIn, slideLeftOut)
    openScreen('../now_playing.xml',
        '@animator/slide_up_in', '@animator/slide_up_out',
        '@animator/slide_down_in', '@animator/slide_down_out')
}
$('@id/thumbnail').on('click', openNowPlaying)
$('@id/title').on('click', openNowPlaying)
$('@id/subtitle').on('click', openNowPlaying)
