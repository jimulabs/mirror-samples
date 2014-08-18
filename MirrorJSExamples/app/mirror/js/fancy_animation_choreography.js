// hide squares first
for (i=1; i<5; i++) {
    $('s'+i).alpha = 0
}

// save the original location of c1
var c1x = $('c1').x
var c1y = $('c1').y

// set up animations for circles

var moveC1 = $('c1').animator({
    properties: {
        x: [c1x, c1x + 300],
        y: [c1y, c1y - 180]
    },
    duration: 500
})
var scaleC1 = $('c1').animator({
    properties: { scale: [1, 8.2] }
})
var c1 = together([moveC1, delay(scaleC1, 200)])

var shrinkCircle = {
    properties: {
        scale: [1, 0],
        alpha: [1, 0]
    }
}
var cDelay = 100
var shrinkC2 = $('c2').animator(shrinkCircle)
var shrinkC3 = delay($('c3').animator(shrinkCircle), cDelay)
var shrinkC4 = delay($('c4').animator(shrinkCircle), cDelay*2)

var c234 = together([shrinkC2, shrinkC3, shrinkC4])
var c1234 = together([c1, c234])

// set up animations for squares, using a loop

var s1y = $('s1').y
var showSquare = {
    properties: {
        y: [s1y+400, s1y],
        alpha: [0, 1]
    }
}
var s1234 = undefined
for (i=1; i<5; i++) {
    var moveS = $('s'+i).animator(showSquare)
    if (s1234!=undefined) {
        s1234 = together([s1234, delay(moveS, 50 * i)])
    } else {
        s1234 = moveS
    }
}
sequence([c1234, s1234]).start()
