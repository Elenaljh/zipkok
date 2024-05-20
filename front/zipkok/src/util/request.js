import axios from "axios";

const service = axios.create({
  withCredentials: true,
});

service.interceptors.request.use(
  (config) => {
    config.headers["Content-Type"] = "application/json;charset=UTF-8";
    config.headers["Access-Control-Allow-Origin"] = "*";
    config.headers["Access-Control-Allow-Headers"] = "*";
    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);

export default service;
