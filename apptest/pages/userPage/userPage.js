// pages/userPage/userPage.js
const app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    latitude: 0,
    longitude: 0,
    controls: [],
    markers: [],
    usingBno: null,
    timer: null,
    hours: 0,
    minutes: 0,
    seconds: 0
    // userInfo: {},
    // hasUserInfo: false
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {

    // if (app.globalData.userInfo) {
    //   this.setData({
    //     userInfo: app.globalData.userInfo,
    //     hasUserInfo: true
    //   })
    // }

    // if(options.usingBno != null && options.seconds != null && options.minutes != null && options.hours != null && options.lno != null){
    //   this.setData({
    //     usingBno: options.usingBno,
    //     seconds: options.seconds,
    //     hours: options.hours,
    //     minutes: options.minutes,
    //     lno: options.lno
    //   })
    // }

    var that = this;
    wx.getLocation({
      success: function(res) {
        console.log(res);
        that.setData({
          latitude: res.latitude,
          longitude: res.longitude
        })

        //查找单车
        findBikes(res.longitude, res.latitude, that);
      },
    })

    wx.getSystemInfo({
      success: function(res) {
        var windowWidth = res.windowWidth;
        var windowHeight = res.windowHeight;

        that.setData({
          controls: [{
            //解锁按钮
            id: 1,
            iconPath: "/images/unlock.png",
            position: {
              width: 32,
              height: 32,
              left: windowWidth / 2 - 16,
              top: windowHeight - 100
            },
            clickable: true
          },
          {
            //回到原来定位按钮
            id: 2,
            iconPath: "/images/location_zero.png",
            position: {
              width: 32,
              height: 32,
              left: 5,
              top: windowHeight - 100
            },
            clickable: true
          },
          {
            //定位按钮
            id: 3,
            iconPath: "/images/pin.png",
            position: {
              width: 32,
              height: 32,
              left: windowWidth / 2 - 18,
              top: windowHeight / 2 - 30
            },
            clickable: true
          },
          {
            //定位按钮
            id: 4,
            iconPath: "/images/user_black.png",
            position: {
              width: 32,
              height: 32,
              left: 0,
              top: 0
            },
            clickable: true
          }
          ]
        })
      },
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    //创建map上下文(保存定位信息)
    this.mapCtx = wx.createMapContext('myMap', this)
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
  
  mycontroltap: function(e){
    var cid = e.controlId;
    console.log(cid)

    if(cid == 2){
      this.mapCtx.moveToLocation();
    }

    if(cid == 4){
      wx.navigateTo({
        url: '/pages/userInfo/userInfo'
      })
    }

    if(cid == 1){

      //判断是否有正在使用的车
      if (app.globalData.bno != 0 && app.globalData.lno != 0){
        wx.navigateTo({
          url: '/pages/useBike/countingTime/countingTime'
        })
      }else{
        wx.navigateTo({
          url: '/pages/useBike/startByBikeNo/start'
        })
      }
    }
  },

  /**
   * 视野发生变化时的事件
   */
  regionChange: function(e){
    var that = this;
    //获取移动后的位置
    var etype = e.type;

    if(etype == 'end'){
      this.mapCtx.getCenterLocation({
        success:function(res){
          //重新查找单车
          var log = res.longitude;
          var lat = res.latitude;
          findBikes(log, lat, that);
        }
      })
    }

  }

})


function findBikes(log, lat, that){
  wx.request({
    url: app.globalData.requestURL + '/bike/findByLocation',
    data: {
      'log': log,
      'lat': lat
    },
    method: 'POST',
    header: {
      'Content-Type': 'application/x-www-form-urlencoded'
    },
    success: function (res) {
      console.log(res)
      if (res.data.bikeList != null) {
        //表示此范围内有自行车

        var bikes = res.data.bikeList.map((bike) => {
          return{
            longitude: bike.longitude,
            latitude: bike.latitude,
            iconPath: "/images/bicycle.png",
            id: bike.bno
          }
        })

        //将Bike数组set到当前页面的markers中
        that.setData({
          markers: bikes
        })

      } else {
      }
    },
    fail: function () {
      wx.showToast({
        title: '查找单车失败',
        icon: 'none',
        duration: 2000
      })
    }
  })
}