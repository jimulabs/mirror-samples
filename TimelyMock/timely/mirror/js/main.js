include('ruler.js')

/*
$("@id/pager").on("pageTransform",
    function(leftView, rightView, leftPosition, rightPosition) {
    });
    */

$("@id/timeSwitch").on("checkedChanged",
    function(view, checked) {
        $("@id/time", view.parent).style = checked ?
            "@style/alarmOnTextStyle" : "@style/alarmOffTextStyle"
        $("@id/cloud1", view.parent).src = checked ?
            "@drawable/cloud_notifier_big_on" : "@drawable/cloud_notifier_big_off"
    });