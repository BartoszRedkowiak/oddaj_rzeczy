$(function () {

    var summaryTagsArray = [
        $('#summStreet'),
        $('#summCity'),
        $('#summZipCode'),
        $('#summPhone'),
        $('#summDate'),
        $('#summTime'),
        $('#summComment')
    ];

    var formTagsArray = [
        $('#formStreet'),
        $('#formCity'),
        $('#formZipCode'),
        $('#formPhone'),
        $('#formDate'),
        $('#formTime'),
        $('#formComment')
    ];
// //variables with summary tags
//     var summaryStreet = $('#summStreet');
//     var summaryTown = $('#summTown');
//     var summaryZipCode = $('#summZipCode');
//     var summaryPhone = $('#summPhone');
//     var summaryDate = $('#summDate');
//     var summaryTime = $('#summTime');
//     var summaryComment = $('#summComment');
// //variables with form tags
//     var formStreet = $('#formStreet');
//     var formTown = $('#formTown');
//     var formZipCode = $('#formZipCode');
//     var formPhone = $('#formPhone');
//     var formDate = $('#formDate');
//     var formTime = $('#formTime');
//     var formComment = $('#formComment');

    var addSummaryListener = function(inputTag, outputTag){
        $(inputTag).on('change', function () {
            var inputValue = $(inputTag).val();
            if (inputValue === '' || inputValue === null){
                $(outputTag).text('brak danych');
            } else {
                $(outputTag).text(inputValue);
            }
        })
    };

    for (var i = 0; i < formTagsArray.length; i++) {
        addSummaryListener(formTagsArray[i], summaryTagsArray[i])
    }

    // addSummaryListener($('#formStreet'), $('#summStreet'))

    // formStreet.on('change', function () {
    //     var input = formStreet.val();
    //     if (input === '' || input === null){
    //         summaryStreet.text('Brak danych');
    //     } else {
    //         summaryStreet.text(input);
    //     }
    // })


});