$('@id/textView1').animator({
    properties: {
        rotation: [0, 360],          // more than two values can be specified here, e.g. [0, 360, 0]
        scale: [0.5, 1]
    },
    duration: 2000,
    interpolator: '@android:interpolator/bounce'   // or as a shorthand, 'android:bounce'
}).start()