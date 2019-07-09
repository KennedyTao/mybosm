// pages/rent/rent.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    rent: 0,
    userStatus: 0
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    if (!app.checkLogin()){
      wx.showToast({
        title: '用户还没登录，请先授权',
        icon: 'none',
        duration: 2000
      })
      
      wx.redirectTo({
        url: '/pages/index/index'
      })

    }else{
      wx.request({
        url: app.globalData.requestURL + '/user/getRentInfo',
        data: {
          'sessionKey': app.globalData.userSession
        },
        method: 'POST',
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (result) {
          if(result.data.res == "success"){

            that.setData({
              rent: result.data.rent,
              userStatus: result.data.userStatus
            })

          }else{
            wx.showToast({
              title: result.data.msg,
              icon: 'none',
              duration: 2000
            })
          }
        },
        fail: function () {
          wx.showToast({
            title: '请求失败，请退出重试',
            icon: 'none',
            duration: 2000
          })
        }
      })
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  rentBack: function(){
    wx.request({
      url: app.globalData.requestURL + '/user/rentBack',
      data: {
        'sessionKey': app.globalData.userSession
      },
      method: 'POST',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (result) {
        if (result.data.res == "success") {

          // wx.navigateTo({
          //   url: '/pages/msg/msg_success?canUseBike=false'
          // })
          wx.redirectTo({
            url: '/pages/msg/msg_success?canUseBike=false',
          })

        } else {
          wx.redirectTo({
            url: '/pages/msg/msg_fail'
          })
        }
      },
      fail: function () {
        wx.redirectTo({
          url: '/pages/msg/msg_fail'
        })
      }
    })
  },
  rentPay: function(){
    wx.request({
      url: app.globalData.requestURL + '/user/rentPay',
      data: {
        'sessionKey': app.globalData.userSession
      },
      method: 'POST',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (result) {
        if (result.data.res == "success") {

          // wx.navigateTo({
          //   url: '/pages/msg/msg_success?canUseBike=true'
          // })
          wx.redirectTo({
            url: '/pages/msg/msg_success?canUseBike=true'
          })

        } else {
          // wx.navigateTo({
          //   url: '/pages/msg/msg_fail'
          // })
          wx.redirectTo({
            url: '/pages/msg/msg_fail'
          })
        }
      },
      fail: function () {
        wx.redirectTo({
          url: '/pages/msg/msg_fail'
        })
      }
    })
  }
})