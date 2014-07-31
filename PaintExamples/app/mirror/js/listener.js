$('textView1').animate('@animator/slide_up_appear').start()

$('textView1').on('click', function() {
    $('textView1').animate('@animator/slide_down_disappear').start()
})