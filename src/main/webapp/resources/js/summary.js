$(function () {

    var summaryFieldsArray = [
        {
            summaryField: $('#summStreet'),
            formField: $('#formStreet'),
            blankFormMessage: 'Brak adresu'
        },
        {
            summaryField: $('#summCity'),
            formField: $('#formCity'),
            blankFormMessage: 'Brak nazwy miasta'
        },
        {
            summaryField: $('#summZipCode'),
            formField: $('#formZipCode'),
            blankFormMessage: 'Brak kodu Zip'
        },
        {
            summaryField: $('#summPhone'),
            formField: $('#formPhone'),
            blankFormMessage: 'Brak numeru telefonu'
        },
        {
            summaryField: $('#summDate'),
            formField: $('#formDate'),
            blankFormMessage: 'Brak daty'
        },
        {
            summaryField: $('#summTime'),
            formField: $('#formTime'),
            blankFormMessage: 'Brak godziny'
        },
        {
            summaryField: $('#summComment'),
            formField: $('#formComment'),
            blankFormMessage: 'Brak uwag'
        }
    ];

    //Listener to input data from steps 1-4 into summary
    $('#summaryBtn').on('click', function () {
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

        //Update institution name
        var institutionName = $('input[name=institution]:checked').next().next().find('#formInstitution').text();
        $('#summInstitution').text(institutionStringBuilder(institutionName));

        //Update information from step 4
        for (var element of summaryFieldsArray){
            var input = element.formField.val();
            if (input === null || input === ''){
                element.summaryField.text(element.blankFormMessage);
            } else {
                element.summaryField.text(input);
            }
        }

    });

    function institutionStringBuilder(name) {
        var result = '';
        if (name === null || name === '' ){
            result = 'Nie wybrano instytucji';
        } else {
            result = 'Dla fundacji "' + name + '" w Warszawie';
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

