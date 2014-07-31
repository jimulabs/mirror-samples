$('@id/textView1').animate({
    properties: {
        rotation: [0, 360],
        scale: [0.5, 1]
    },
    duration: 2000,
    interpolator: '@android:interpolator/bounce'
}).start()