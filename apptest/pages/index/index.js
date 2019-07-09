//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    console.log("---index page onLoad---")
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function(e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  onReady:function(){
    //页面渲染完成
    console.log("---index page onReady---");
  },
  onShow:function(){
    //页面显示
    console.log("---index page onShow---");
  },
  onHide:function(){
    //页面隐藏
    console.log("---index page onHide");
  },
  onUnload:function(){
    //页面关闭
    console.log("---index page onUnload---");
  },
  itemClick: function(){
    console.log("---index page itemClick---");
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  toEvent: function(){
    //跳转到event.wxml页面
    wx.navigateTo({
      url: '/pages/wxml/event'
    })
  },
  toUserRegister: function(e){
    console.log(e.detail.errMsg)
    console.log(e.detail.userInfo)
    console.log(e.detail.rawData)

    wx.login({
      success: function(res){
        console.log(res);
        //获取登录的临时凭证
        var code = res.code;
        //传递code，跳转到下一页面
        wx.navigateTo({
          url: '/pages/userRegister/userRegister?code=' + code + "&nickName=" + e.detail.userInfo.nickName
          // data:{
          //   code: code,
          //   nickName: e.detail.userInfo.nickName
          // }
        })
      }
    })
  },
  userLogin: function(e){
    wx.login({
      success: function(res){
        var code = res.code;
        wx.request({
          url: app.globalData.requestURL + '/user/login',
          data: {
            code: code
          },
          method: 'POST',
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (res) {
            console.log(res);
           if(res.data.res == "success"){
            //存储sessionkey,wx.setStorageSync保存到存储卡中
            //  wx.setStorageSync("loginSessionKey", res.sessionKey);
             //设置用户状态,保存到内存
             app.globalData.userSession = res.data.sessionKey;
             console.log(app.globalData.userSession)
             //跳转 
             wx.navigateTo({
               url: '../userPage/userPage'
             });

           }else{
             wx.showToast({
               title: res.data.msg,
               icon: 'none',
               duration: 2000
             })
           }
          },
          fail: function(e){
            wx.showToast({
              title: "登录失败，请重试",
              icon: 'none',
              duration: 2000
            })
          }
        })
      }
    })
  },
  toUserPage: function(e){
    wx.navigateTo({
      url: '/pages/userPage/userPage'
    })
  }
})
