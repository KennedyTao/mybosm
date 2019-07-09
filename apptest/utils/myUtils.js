function getSessionKey(){
  sessionKey = getApp().gobalData[userSession];
  return sessionKey;
}

module.exports = {
  get
}