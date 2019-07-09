// pages/useBike/startByBikeNo/start.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    bikeNo: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

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
  startFormSubmit:function(e){
    var bikeNo = this.data.bikeNo;

    if (!app.checkLogin()) {
      wx.showToast({
        title: '用户还没登录，请先授权',
        icon: 'none',
        duration: 2000
      })
      return false;

    }

    wx.request({
      url: app.globalData.requestURL + '/bike/hasRent',
      data: {
        'bno': bikeNo,
        "sessionKey": app.globalData.userSession
      },
      method: 'POST',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function (res2) {
        if (res2.data.res != "success") {
          // 跳转页面
          wx.showToast({
            title: '请先缴纳押金',
            icon: 'none',
            duration: 2000
          })
          return false;
        }
      },
      fail: function () {
        wx.showToast({
          title: '查询押金请求失败',
          icon: 'none',
          duration: 2000
        })
        return false;
      }
    })

    if (!bikeNo) {
      wx.showToast({
        title: '请输入信息',
        icon: 'none',
        duration: 1500
      })
      return false;

    }else {
      wx.request({
        url: app.globalData.requestURL + '/bike/unlock',
        data: {
          'bno': bikeNo,
          "sessionKey": app.globalData.userSession
        },
        method: 'POST',
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (res2) {
          if (res2.data.res == "success") {
            // 跳转页面
            wx.redirectTo({
              url: '/pages/useBike/countingTime/countingTime?bno=' + res2.data.usingBike + "&lno=" + res2.data.lno
            })

          } else {
            wx.showToast({
              title: res2.data.msg,
              icon: 'none',
              duration: 2000
            })
          }
        },
        fail: function () {
          wx.showToast({
            title: '请求失败',
            icon: 'none',
            duration: 2000
          })
        }
      })
    }
  },
  bikeNoInput: function(e){
    this.setData({
      bikeNo: e.detail.value
    })
  }
})