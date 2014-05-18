function registerHandleBarIfCond()
{
	Handlebars.registerHelper('ifCond', function (v1, operator, v2, options) 
	{
	    switch (operator) {
	        case '==':
	            return (v1 == v2) ? options.fn(this) : options.inverse(this);
	        case '===':
	            return (v1 === v2) ? options.fn(this) : options.inverse(this);
	        case '<':
	            return (v1 < v2) ? options.fn(this) : options.inverse(this);
	        case '<=':
	            return (v1 <= v2) ? options.fn(this) : options.inverse(this);
	        case '>':
	            return (v1 > v2) ? options.fn(this) : options.inverse(this);
	        case '>=':
	            return (v1 >= v2) ? options.fn(this) : options.inverse(this);
	        case '&&':
	            return (v1 && v2) ? options.fn(this) : options.inverse(this);
	        case '||':
	            return (v1 || v2) ? options.fn(this) : options.inverse(this);
	        default:
	            return options.inverse(this);
	    }
	});
}
	
function postAjax(url, data, successMethod, errorMethod) 
{
	$.ajax({ 
	    url: url, 
	    type: 'POST', 
	    dataType: 'json', 
	    data: data, 
	    contentType: 'application/json',
	    mimeType: 'application/json',
	    success: successMethod,
	    error: errorMethod
	}); 
}

function getAjax(url, data, successMethod)
{
	$.ajax({
		  url: url,
		  data: data,
		  success: successMethod
		});
}















