// pages/userLogin/userLogin.js
var app = getApp();
Page({
  /**
   * 页面的初始数据
   */
  data: {
    hasCode: false, //发送状态
    countTime: 60,  //倒计时
    disable: true,  //是否禁用按钮
    phoneNum: '',    //手机号
    code: '',
    nickName: '',
    authCode: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options)
    this.setData({
      phoneNum: '',
      code: options.code,
      nickName: options.nickName
    })
    console.log(options.nickName);
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
  /**
   * 注册操作
   */
  registerFormSubmit: function(e){
    var tel = this.data.phoneNum;
    var authCode = this.data.authCode;

    // console.log(tel);
    // console.log(authCode);

    if(!tel || !authCode){
      wx.showToast({
        title: '请输入信息',
        icon: 'none',
        duration: 1500
      })
      return false;

    }else{
      wx.request({
        url: app.globalData.requestURL + '/user/register',
        data:{
          'tel': tel,
          'authCode': authCode,
          'code': this.data.code,
          'nickName': this.data.nickName
        },
        method: 'POST',
        header:{
          'Content-Type': 'application/x-www-form-urlencoded'
        },
        success:function(res){
           if(res.data.res == "success"){
             //TODO 跳转页面
              console.log(res);
              if(res.data.isExist == true){
                wx.showToast({
                  title: '用户已注册过，直接登录即可',
                  icon: 'none',
                  duration: 2000
                })
              }else {
                wx.showToast({
                  title: '注册成功，请返回登录',
                  icon: 'none',
                  duration: 2000
                })
              }

           }else{
             wx.showToast({
               title: res.data.msg + '..请退出重新授权再注册',
               icon: 'none',
               duration: 2000
             })
           }
        },
        fail: function(){
          wx.showToast({
            title: '请求失败，请退出重新授权再注册',
            icon: 'none',
            duration: 2000
          })
        }
      })
    }
  },
  authCodeInput: function(e){
    this.setData({
      authCode: e.detail.value
    })
  },
  phoneInput: function(e){
    let phone = e.detail.value

    if (phone){
      if (phone.length == 11){
        let checkNum = this.checkPhoneNum(phone)
        console.log(checkNum)

        if(checkNum){
          this.setData({
            phoneNum: phone
          })
        }
      }else {
        this.setData({
          phoneNum: ''
        })
      }
    }
  },
  /**
   * 检验phoneNum的函数
   */
  checkPhoneNum: function(phone){
    let regTel = /^[1]([3-9])[0-9]{9}$/

    if(regTel.test(phone)){
      return true

    }else {
      return false
    }
  },
  /**
   * 获取验证码
   */
  getAuthCode: function(e){
    var phoneNum = this.data.phoneNum

    if(!phoneNum){
      wx.showToast({
        title: '请输入手机号码',
        icon: 'none',
        duration: 2000
      })
      return false;
    }

    //发送短信验证码
    wx.request({
      url: app.globalData.requestURL + '/user/getAuthCode',
      data: {
        mobile: phoneNum
      },
      method: 'POST',
      header: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      success: function(res){
        //发送失败则不执行计时
        if(res.data.code != 200){
          wx.showToast({
            title: '发送失败，请重试',
            icon: 'none',
            duration: 2000
          })
          return false;
        }
      }
    })

    this.setData({
      hasCode: true
    })
    this.timer();
  },
  timer: function(){
    let promise = new Promise((resolve, reject) =>{
      let setTimer = setInterval(
        () => {
          this.setData({
            countTime: this.data.countTime - 1
          })

          if (this.data.countTime <= 0) {
            this.setData({
              countTime: 60,
              hasCode: false
            })
            resolve(setTimer)
          }
        }, 1000)
    })

    promise.then((setTimer) => {
      clearInterval(setTimer)
    })
  }
})