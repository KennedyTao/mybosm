// pages/useBike/settleAccount/settleAccount.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    cost: -1,
    lno: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      cost: options.cost,
      lno: options.lno
    })
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
  
  pay: function(){
    if(this.data.lno != '' && this.data.cost != -1){
      wx.request({
        url: app.globalData.requestURL + '/bike/settleLease',
        data: {
          'lno': this.data.lno
        },
        method: 'POST',
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (result) {
          if (result.data.res == "success") {
            wx.redirectTo({
              url: '/pages/useBike/resMsg/res'
            })

          } else {
            wx.showToast({
              title: result.data.msg,
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

    }else{
      wx.showToast({
        title: '支付信息不正确',
        icon: 'none',
        duration: 2000
      })
    }
  }
})