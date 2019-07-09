// pages/useBike/countingTime/countingTime.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    hours: 0,
    minutes: 0,
    seconds: 0,
    billing: "正在计费",
    bno: 0,
    lno: 0,
    lat:'',
    log:'',
    timer:''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    //如果是主页未计费完的单车
    // if(options.usingBno != null && options.timer != null && options.lno != null){
    //   this.setData({
    //     bno: options.usingBno,
    //     timer: options.timer,
    //     lno: options.lno
    //   })

    // }else 
    if(app.globalData.bno != 0 && app.globalData.lno != 0){
      // console.log("setG?????")
      console.log(app.globalData.bno)
      console.log(app.globalData.lno)
      this.setData({
        bno: app.globalData.bno,
        lno: app.globalData.lno
        // hours: app.globalData.hours,
        // minutes: app.globalData.minutes,
        // seconds: app.globalData.seconds
      })

      this.data.timer = setInterval(() => {
        this.setData({
          seconds: app.globalData.seconds,
          hours: app.globalData.hours,
          minutes: app.globalData.minutes
        })
      }, 1000)

    }else{
      // 获取车牌号，设置定时器
      // console.log("set???????")
      this.setData({
        bno: options.bno,
        lno: options.lno
        // timer: this.data.timer
      })
      app.startCount(this.data.bno, this.data.lno)

      this.data.timer = setInterval(() => {
        this.setData({
          seconds: app.globalData.seconds,
          hours: app.globalData.hours,
          minutes: app.globalData.minutes
        })
      },1000)

      // // 初始化计时器
      // let s = 0;
      // let m = 0;
      // let h = 0;
      // // 计时开始
      // this.data.timer = setInterval(() => {
      //   this.setData({
      //     seconds: s++
      //   })
      //   if (s == 60) {
      //     s = 0;
      //     m++;
      //     //等待一秒
      //     setTimeout(() => {
      //       this.setData({
      //         minutes: m
      //       });
      //     }, 1000)
      //     if (m == 60) {
      //       m = 0;
      //       h++
      //       setTimeout(() => {
      //         this.setData({
      //           hours: h
      //         });
      //       }, 1000)
      //     }
      //   };
      // }, 1000)
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
  endRide: function () {
    var that = this;
    wx.getLocation({
      success: function (res) {
        console.log(res);
        that.setData({
          lat: res.latitude,
          log: res.longitude
        })

        //请求结算
        wx.request({
          url: app.globalData.requestURL + '/bike/createOrder',
          data: {
            // 'lno': that.data.lno,
            // 'bno': that.data.bno,
            // 'hours': that.data.hours,
            // 'minutes': that.data.minutes,
            // 'seconds': that.data.seconds,
            'lat': that.data.lat,
            'log': that.data.log,

            'lno': app.globalData.lno,
            'bno': app.globalData.bno,
            'seconds': app.globalData.seconds,
            'hours': app.globalData.hours,
            'minutes': app.globalData.minutes
          },
          method: 'POST',
          header: {
            'Content-Type': 'application/x-www-form-urlencoded'
          },
          success: function (result) {
            if (result.data.res == "success") {
              //停止计时
              app.cancelCount();

              //TODO 到收费页面！
              wx.navigateTo({
                url: '/pages/useBike/settleAccount/settleAccount?lno=' + result.data.lno + '&cost=' + result.data.cost
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

        clearInterval(that.timer);
        that.setData({
          timer: ''
        });

      },
      fail: function(){
        wx.showToast({
          title: '获取当前位置失败',
          icon: 'none',
          duration: 2000
        })
        return false;
      }
    })


  },
  // 携带定时器状态回到地图
  moveToIndex: function () {
    // 如果定时器为空
    // if (this.data.timer == "") {
    //   // 关闭计费页跳到地图
      wx.redirectTo({
        url: '/pages/userPage/userPage'
    //   })
    //   // 保留计费页跳到地图
    // } else {
    //   wx.navigateTo({
    //     //再次开锁时判断有没有timer存在，有则结算
    //     url: '/pages/userPage/userPage?seconds=' + this.data.seconds + '&minutes=' + this.data.minutes + '&hours' + this.data.hours + '&usingBno=' + this.data.bno + "&lno=" + this.data.lno
    //   })
    })
  }
})