function getCheckboxesValues() {
    let tableBody = document.getElementById('roleMatrixTable').getElementsByTagName('tbody')[0];
    let rows = tableBody.getElementsByTagName('tr');

    // Array to hold the extracted values
    var privilegeMap = new Map();
    var entityPrivilegeListMap = new Map();
    var entitiesMap = extractEntityIndexes();
    var grantAccesses = [];
    // Loop through each row
    for (let i = 0; i < rows.length; i++) {
        // Get the cells in the current row
        let cells = rows[i].getElementsByTagName('td');

        var privilegeIds = [];
        // Extract the values from the cells
        privilegeMap.set(i, cells[0].getElementsByClassName('privilege_id')[0].textContent);
        for(let j = 0; j < cells.length; j++){
            if(j > 0){
                var inp = cells[j].getElementsByTagName('input')[0];
                if(inp.type === 'checkbox'){
                    // if(inp !== undefined && inp.checked){
                    if(inp.classList.contains("checked")){
                        // console.log('privilegeMap', privilegeMap);
                        // console.log('privilegeMap', privilegeMap);
                        privilegeIds = entityPrivilegeListMap.get(j) == undefined ? [] : entityPrivilegeListMap.get(j);
                        privilegeIds.push(privilegeMap.get(i));
                        entityPrivilegeListMap.set(j, privilegeIds);
                    }
                }
            }
        }




        // Add the row values to the array
    }
    console.log('entityPrivilegeListMap', entityPrivilegeListMap);

    // console.log('privilegeMap',privilegeMap);
    for(let i = 0; i < entitiesMap.size; i++){
        if(i > 0){
            grantAccesses.push({
                entityType: entitiesMap.get(i),
                privilegeIds: entityPrivilegeListMap.get(i) == undefined ? [] : entityPrivilegeListMap.get(i)
            })
        }
    }
    let result = {
        id: ROLE,
        grantAccesses: grantAccesses
    };      // Return the extracted values
    return result;
}

 request = {
    method: String,
    headers:Array,
    body: Object
}
const HOST = 'http://localhost:8081/accuica';
function getBasicAuthorization(username, password){
    var credentials = username + ':' + password;
    return btoa(credentials);
}

function getBasicAuthorizationHeader(username, password){
    return 'Basic ' + this.getBasicAuthorization(username, password);
}
class AppFetcher {
    constructor(host) {
        this.host = host;
    }
    async fetch(api, method, headers, body) {
        var myHeaders = new Headers(headers == undefined || headers == null ? {} : headers);
        myHeaders.append("Authorization", getBasicAuthorizationHeader('spring', 'secret'));
        //temporal
        const request = {
            method: method,
            headers: myHeaders,
        };
        if(body != null || body != undefined){
          request.body = body;
      }
       return await fetch(this.host + api, request)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json(); // Parse the response as JSON
            })
            .then(data => {
                return data;
                console.log(data); // This will log the JSON data from the response
                // You can now use the data variable to access the JSON data
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                // Handle the error appropriately, such as displaying an error message to the user
            });
    }
}
const fetcher = new AppFetcher(HOST);


