function initMap() {
    var map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: -34.397, lng: 150.644},
        zoom: 19
    });

    var image = 'punch.png';
    var infoWindow = new google.maps.Marker({
        map: map,
        icon: image
    });





    // Try HTML5 geolocation.
    if (navigator.geolocation) {

        navigator.geolocation.getCurrentPosition(function(position) {
            var dt = new Date();
            var utcDate = dt.toUTCString();
            var pos = {
                lat: position.coords.latitude,
                lng: position.coords.longitude
            };

            alert(JSON.stringify(pos));

            infoWindow.setPosition(pos);

            map.setCenter(pos);


        }, function() {
            handleLocationError(true, infoWindow, map.getCenter());
        });
    } else {
        // Browser doesn't support Geolocation
        handleLocationError(false, infoWindow, map.getCenter());
    }
}

function handleLocationError(browserHasGeolocation, infoWindow, pos) {
    infoWindow.setPosition(pos);
    infoWindow.setContent(browserHasGeolocation ?
        'Error: The Geolocation service failed.' :
        'Error: Your browser doesn\'t support geolocation.');
}