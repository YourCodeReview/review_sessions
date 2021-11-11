import * as axios from "axios";

const BASE_URL = "http://localhost:8080";

const instance = axios.create({
    baseURL: BASE_URL,
    method: "GET",
    headers: {
        "Content-Type": "application/json",
    },
});

const headerInstance = axios.create({
    baseURL: BASE_URL,
    method: "POST",
    headers: {
        "Content-Type": "application/json",

    },
});

const login = axios.create({
    baseURL: BASE_URL,
    method: "POST",
    headers: {
        "Access-Control-Allow-Origin": "*",
        "Content-Type": "application/x-www-form-urlencoded",
    },
});


export const API = {
    postNewUser(user) {
        return headerInstance.post("/register", user);
    },
    loginIn(user) {
        return login.post('/sign-in', user, {withCredentials: true});
    },
    getStudents() {
        return headerInstance.get('/students');
    }
}
