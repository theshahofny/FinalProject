/**
 * 
 * @param {Object} jsonData a javascript hash containing the data you want to POST, e.g.
 * <code>
 *    var data = { userName : 'ewa@example.com', password : 's3cr3t' }
 *    $http(encodeAsFormURL(data, '/login').then(...)
 * </code>
 * @param {String} url you want to post to
 * @returns {undefined}
 */
function encodeAsFormURL(jsonData, url)
{
    return {
        method : 'POST',
        url : url,
        headers : {'Content-Type' : 'application/x-www-form-urlencoded'},
        transformRequest : function()
        {
            var body = [];
            for(let p in jsonData)
                body.push(encodeURIComponent(p) + "=" + encodeURIComponent(jsonData[p]));
            return body.join("&");
        }
    };
}


