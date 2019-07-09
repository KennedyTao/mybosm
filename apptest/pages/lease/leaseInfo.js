// pages/lease/leaseInfo.js
var time = require('../../utils/util.js');
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    hasLease: false,
    leaseList: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (app.globalData.userSession == 0){
      wx.showToast({
        title: '请先登录',
        icon: 'none',
        duration: 2000
      })
    }else {
      var that = this;
      wx.request({
        url: app.globalData.requestURL + '/lease/listByUser',
        data: {
          'sessionKey': app.globalData.userSession
        },
        method: 'POST',
        header: {
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success: function (result) {
          console.log(result)
          if (result.data.res == "success") {

            if(result.data.hasLease == true){
              // console.log("setting...")
              that.setData({
                hasLease: true,
                leaseList: result.data.leaseList
              })

              //转换时间
              var list = that.data.leaseList;
              for(var index = 0; index < list.length; index++){
                // that.setData({
                //   leaseList[index]["startTime"]: '111'
                // })
                list[index].startTime = time.tsFormatTime(list[index].startTime, 'Y-M-D h:m:s')

                // var spend = list[index].spendTime
                // spend = spend / 60;
                // list[index].spendTime = spend;
              }

              console.log(list)

              that.setData({
                leaseList: list
              })

              
            }else{

            }

          } else {
            wx.showToast({
              title: '请求失败',
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
  toPay: function(event){
    console.log(event)
    wx.redirectTo({
      url: '/pages/useBike/settleAccount/settleAccount?lno=' + event.currentTarget.dataset.lno + '&cost=' + event.currentTarget.dataset.cost
      // data: ({
      //   'lno': event.currentTarget.dataset.lno,
      //   'cost': event.currentTarget.dataset.cost
      // })
    })
  }
})