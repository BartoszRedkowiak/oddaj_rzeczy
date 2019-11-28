$(function () {

    //Listeners to input fields in step 4 in form.jsp
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

    var addSummaryListener = function (inputTag, outputTag) {
        $(inputTag).on('change', function () {
            var inputValue = $(inputTag).val();
            if (inputValue === null || inputValue === '') {
                $(outputTag).text('brak danych');
            } else {
                $(outputTag).text(inputValue);
            }
        })
    };

    for (var i = 0; i < formTagsArray.length; i++) {
        addSummaryListener(formTagsArray[i], summaryTagsArray[i])
    }

    //Listener to input data from steps 1-3 into summary
    $('#summaryBtn').on('click', function () {
        //Update institution name
        var institutionName = $('input[name=institution]:checked').next().next().find('#formInstitution').text();
        $('#summInstitution').text(institutionStringBuilder(institutionName));

        //Gather quantity and array of categories
        var quantity = $('#formQuantity').val();
        var categories = $('input[name=categories]:checked').next().next();
        var categoriesArray = categories.map(function () {
            return $.trim($(this).text());
        }).get();

        //Update quantity
        $('#summQuantity').text(quantityStringBuilder(quantity, categoriesArray.length));

        //Update categories list
        var list = $('#summCategories');
        list.empty();

        for(var category of categoriesArray){
            var element = $('<li>');
            element.text(category);
            list.append(element);
        }
    });

    function institutionStringBuilder(name) {
        var result = '';
        if (name === null || name === '' ){
            result = 'Nie wybrano instytucji';
        } else {
            result = 'Dla fundacji "' + institutionName + '" w Warszawie';
        }
        return result;
    }

    function quantityStringBuilder(quantity, categoriesCount) {
        var suffix = '';
        if (categoriesCount === 0){
            suffix = ' o nieokreślonej kategorii';
        } else if (categoriesCount > 0) {
            suffix = ', w tym: ';
        }

        var result = '';
        if (quantity === null || quantity === ''){
            result = 'Nieznaną liczbę worków';
            return result + suffix;
        }

        result += quantity;
        if (quantity < 2){
            result += ' worek';
        } else if (quantity < 5){
            result += ' worki';
        } else {
            result += ' worków';
        }
        return result + suffix;
    }

});

