import { localAxios } from "@/util/http-commons";

const local = localAxios();

function listBoard(param, success, fail) {
  local.get(`/board/list`, { params: param }).then(success).catch(fail);
}

function detailBoard(articleno, success, fail) {
  local.get(`/board/${articleno}`).then(success).catch(fail);
}

function registBoard(article, success, fail) {
  console.log("boardjs article", article);
  local.post(`/board`, JSON.stringify(article)).then(success).catch(fail);
}

function getModifyBoard(articleno, success, fail) {
  local.get(`/board/modify/${articleno}`).then(success).catch(fail);
}

function modifyBoard(article, success, fail) {
  local.put(`/board`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteBoard(boardId, success, fail) {
  local.delete(`/board/${boardId}`).then(success).catch(fail);
}
function listComment(param, success, fail) {
  // 코멘트 받아오는 함수
  local.get(`/comment`, { params: param }).then(success).catch(fail);
}
function registComment(comment, success, fail) {
  // 코멘트 등록하는 함수
  local.post(`/comment`, JSON.stringify(comment)).then(success).catch(fail);
}

function updateBoom(boardId, success, fail) {
  // 추천하는 함수
  local.get(`/board/updateBoom/${boardId}`).then(success).catch(fail);
}

export {
  listBoard,
  detailBoard,
  registBoard,
  getModifyBoard,
  modifyBoard,
  deleteBoard,
  registComment,
  listComment,
  updateBoom,
};
