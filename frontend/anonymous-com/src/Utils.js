import {useNavigate} from "react-router-dom";

export const setCookie =  (name, value, options = {}) => {

    options = {
        path: '/',
        ...options
    };

    if (options.expires instanceof Date) {
        options.expires = options.expires.toUTCString();
    }

    let updatedCookie = encodeURIComponent(name) + "=" + encodeURIComponent(value);

    for (let optionKey in options) {
        updatedCookie += "; " + optionKey;
        let optionValue = options[optionKey];
        if (optionValue !== true) {
            updatedCookie += "=" + optionValue;
        }
    }

    document.cookie = updatedCookie;
}

export const getFromLocalStorage = (attribute) =>{
    return JSON.parse(localStorage.getItem('user'))[attribute]
}

export const getCookie = (name) => {
    let matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}


export const deleteCookie = (name) => {
    setCookie(name, "", {
        'max-age': -1
    })

}

export const isLoggedIn = () => {
    return getCookie('token') !== undefined
}

export const makeRestCall = async (url,operationType,optionalBody) => {
    let requestOptions = null
    if(optionalBody === undefined) {
        requestOptions = {
            method: operationType,
            headers: {
                Authorization: "Bearer " + getCookie('token')
            },

        };
    }
    else{
        requestOptions = {
            method: operationType,
            headers: {
                "Authorization": "Bearer " + getCookie('token'),
                "Content-Type": "application/json"
            },
            body : JSON.stringify(optionalBody)

        };
    }

    let response = await fetch("http://localhost:8080" + url, requestOptions);

    if(operationType === 'DELETE'){
        return
    }
    let data = await response.json();

    return data
}

export const oddOrEven = (i) => {
    return i % 2 == 0 ? "bg-even" : "bg-odd"
}


