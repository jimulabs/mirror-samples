var openNowPlaying = function() {
    openScreen('../now_playing.xml')
}
$('@id/thumbnail').on('click', openNowPlaying)
$('@id/title').on('click', openNowPlaying)
$('@id/subtitle').on('click', openNowPlaying)