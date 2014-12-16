function add(a,b) {
    return a+b;
}

/*
function titleContainer(toExpand) {
    var v = $('title_ContainerEnter')
    var bStart = toExpand ? 0 : v.height;
    var bEnd = toExpand ? v.height : 0;
    var anim = v.animator({
        properties: {
            bottom: [bStart, bEnd]
        }
    })
    return anim
}

function _titleContainerEnter() {
    return titleContainer(true)
}

function _infoContainerEnter() {
  ...
}

function _fab() {
  ...
}

function _enter() {
  return `[300 -> _fab, _titleContainerEnter -> _infoContainerEnter]`
  // return together([delay(_fab(), 300), seq([_titleContainerEnter(),_infoContainerEnter()])])
}

function _return() {
  return `_fab -> _infoContainerEnter -> _titleContainerEnter`
}

function albumDetails(albumArt) {
    $('albumArt').image = albumArt
}
*/