import { localAxios } from "@/util/http-commons";

const local = localAxios();
const pf = '/apt/list';

function getAptsByDong(param, success, fail) {
  local.get(`${pf}/dong`, { params: param }).then(success).catch(fail);
}

function getAptsByName(param, success, fail) {
  local.get(`${pf}/name`, { params: param }).then(success).catch(fail);
}

function getAptsByLatLngs(param, success, fail) {
  local.get(`${pf}/range`, { params: param }).then(success).catch(fail);
}

function getRecApts(param, success, fail) {
  local.get(`${pf}/rec`, { params: param }).then(success).catch(fail);
}

function getAptsAvgByDong(param, success, fail) {
  local.get(`${pf}/average`, { params: param }).then(success).catch(fail);
}


export {
  getAptsByDong,
  getAptsByName,
  getAptsByLatLngs,
  getRecApts,
  getAptsAvgByDong
};
