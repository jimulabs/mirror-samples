var v1 = $('view1').animator('@animator/rotate')
var v2 = $('view2').animator('@animator/rotate')

sequence([v1, v2]).start()
//together([v1, v2]).start()
//together([v1, delay(v2, 500)]).start()