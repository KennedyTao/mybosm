//app.js
App({
  onLaunch: function () {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
      }
    })
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },
  globalData: {
    userInfo: null,
    requestURL: "http://localhost:8080",
    userSession: 0,  //用户登录sessionKey
    seconds: 0,
    hours: 0,
    minutes: 0,
    timer: '',
    bno: 0,
    lno: 0,
  },
  checkLogin: function(e){
    if (this.globalData.userSession == 0) {
      return false;
    }else{
      return true;
    }
  },

  startCount: function(bikeno, leaseno){
    // this.setGlobalData({
    //   bno: bikeno,
    //   lno: leaseno
    // })
    this.globalData.bno = bikeno;
    this.globalData.lno = leaseno;

    // 初始化计时器
    let s = 0;
    let m = 0;
    let h = 0;
    // 计时开始
    this.globalData.timer = setInterval(() => {
      this.globalData.seconds = s++;

      if (s == 60) {
        s = 0;
        m++;
        //等待一秒
        setTimeout(() => {
          this.globalData.minutes = m;
        }, 1000)
        if (m == 60) {
          m = 0;
          h++
          setTimeout(() => {
            this.globalData.h = h;
          }, 1000)
        }
      };
    }, 1000)
  },
  cancelCount: function(){
    clearInterval(this.globalData.timer);
    // this.setData({
    //   seconds: 0,
    //   minutes: 0,
    //   hours: 0,
    //   bikeno: 0,
    //   leaseno: 0,
    //   timer: ''
    // })
    this.globalData.seconds = 0;
    this.globalData.minutes = 0;
    this.globalData.hours = 0;
    this.globalData.bno = 0;
    this.globalData.lno = 0;
    this.globalData.timer = ''
  }
})