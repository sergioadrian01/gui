
function scanToJpg() {
    scanner.scan(displayImagesOnPage,
        {
            "twain_cap_setting": {
                "ICAP_PIXELTYPE": "TWPT_BW", // Preferrs GRAY, fall back Color; TWPT_BW
                "ICAP_XSCALING/RESET": "null", // Resets a capability
                "CAP_AUTOFEED": true, // TW_BOOL, No default; TRUE to use ADF or FALSE to use Flatbed

            },

            "source_name": "default",
            "use_asprise_dialog": false,

            "output_settings": [
                {
                    "type": "return-base64",
                    "format": "jpg"
                }
            ]
        }
    );
}

function scanWithoutAspriseDialog() {

    scanner.scan(displayImagesOnPage,
        {
            "twain_cap_setting": {
                "ICAP_PIXELTYPE": "TWPT_BW", // Preferrs GRAY, fall back Color; TWPT_BW
                "ICAP_XSCALING/RESET": "null", // Resets a capability
                "CAP_AUTOFEED": true, // TW_BOOL, No default; TRUE to use ADF or FALSE to use Flatbed

            },

            "source_name": "default",
            "use_asprise_dialog": false,
            "output_settings": [
                {
                    "type": "return-base64",
                    "format": "jpg"
                }
            ]
        }
    );
}

function displayImagesOnPage(successful, mesg, response) {
    if(!successful) { // On error
        console.error('Failed: ' + mesg);
        return;
    }
    if(successful && mesg != null && mesg.toLowerCase().indexOf('user cancel') >= 0) { // User cancelled.
        console.info('User cancelled');
        return;
    }
    var scannedImages = scanner.getScannedImages(response, true, false); // returns an array of ScannedImage
    for(var i = 0; (scannedImages instanceof Array) && i < scannedImages.length; i++) {
        var scannedImage = scannedImages[i];
        processScannedImage(scannedImage);
    }
}
/** Images scanned so far. */
var imagesScanned = [];
/** Processes a ScannedImage */
function processScannedImage(scannedImage) {
    imagesScanned.push(scannedImage);
    var elementImg = scanner.createDomElementFromModel( {
        'name': 'img',
        'attributes': {
            'class': 'scanned',
            'src': scannedImage.src
        }
    });
    document.getElementById('images').appendChild(elementImg);
}
<!-- Previous lines are same as demo-01, below is new addition to demo-02 -->
/** Upload scanned images by submitting the form */
function submitFormWithScannedImages() {
    if (scanner.submitFormWithImages('form1', imagesScanned, function (xhr) {
            if (xhr.readyState == 4) { // 4: request finished and response is ready
                document.getElementById('server_response').innerHTML = "<h4>Respuesta del servidor: </h4>" + xhr.responseText;
                document.getElementById('images').innerHTML = ''; // clear images
                imagesScanned = [];
            }
        })) {
        document.getElementById('server_response').innerHTML = "Enviando, Por favor espere ...";
    } else {
        document.getElementById('server_response').innerHTML = "Envio cancelado. digitalice una imagen antes.";
    }
}








