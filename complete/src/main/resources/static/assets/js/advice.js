var getAdvice = function() {
    $.getJSON('http://api.adviceslip.com/advice', function(data) {
        $("h2#advice").replaceWith('<h2 id="advice">' + data.slip.advice + '</h2>');
    });
};

getAdvice();

$('button.advice').on('click', function() {
    getAdvice();
});
