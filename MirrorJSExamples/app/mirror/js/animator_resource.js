$('@id/textView1').animator('@animator/slide_up_appear').start()

/*
  You can play an animation specified as a standard @animator resource.
  The resource references can be simplified as following:

$('textView1').animator('slide_up_appear').start()

  If the id or @animator is an android internal resource, add the prefix "android:":

$('android:text1').animator('android:bounce').start()
*/