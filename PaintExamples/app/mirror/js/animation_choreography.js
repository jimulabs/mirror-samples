var anim = {
               properties: {
                   scale: [1, 2, 1]
               },
               duration: 1000
           }
var t1 = $('@id/textView1').animate(anim)
var t2 = $('@id/textView2').animate(anim)

//sequence([t1, t2]).start()
//together([t1, t2]).start()
together([t1, delay(t2, 500)]).start()