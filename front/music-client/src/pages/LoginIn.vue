<template>
  <div>
    <loginLogo/>
    <div class="signUp">
      <el-tabs class="login-tab" v-model="activeName" type="border-card"
               @tab-click="handleClick">
        <el-tab-pane label="帐号密码登录" name="account">
          <div class="signUp-head">
            <span>帐号登录</span>
          </div>
          <el-form :model="loginForm" ref="loginForm" label-width="70px" class="demo-ruleForm" :rules="rules">
            <el-form-item prop="username" label="用户名">
              <el-input v-model="loginForm.username" placeholder="用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码">
              <el-input type="password" v-model="loginForm.password" placeholder="密码"></el-input>
            </el-form-item>

            <div class="login-btn">
              <el-button @click="goSignUp">注册</el-button>
              <el-button type="primary" @click="handleLoginIn">登录</el-button>
            </div>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="第三方登录" name="qrcode">
          <div class="qrcode-box">
            <!--二维码生成区域-->
            <code-card v-if="isCreateQRCodeDom"></code-card>
            <div v-else class="fresh-qrcode-status"></div>
            <el-button icon="el-icon-refresh" type="primary"
                       class="fresh-qrcode" @click="refreshQRCode">点此刷新二维码
            </el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
      <!---->
    </div>
  </div>
</template>
<script>
import loginLogo from '../components/LoginLogo'
import {mixin} from '../mixins'
import {loginIn} from '../api/index'
import CodeCard from '../components/CodeCard'

export default {
  name: 'login-in',
  mixins: [mixin],
  components: {
    loginLogo,
    CodeCard
  },
  data () {
    return {
      //默认使用帐号登录的方式
      activeName: 'account',
      isCreateQRCodeDom: false,
      loginForm: {
        username: '',       //用户名
        password: '',       //密码
      },
      rules: {
        username: [
          {required: true, trigger: 'blur', message: '请输入用户名'}
        ],
        password: [
          {required: true, trigger: 'blur', message: '请输入密码'}
        ]
      }
    }
  },
  mounted () {
    this.changeIndex('登录')
  },
  methods: {
    //刷新二维码
    refreshQRCode () {
      this.isCreateQRCodeDom = false
      setTimeout(() => {
        this.isCreateQRCodeDom = true
      }, 500)
    },
    //选择标签卡
    handleClick (tab, event) {
      this.activeName = tab.name
      if (tab.name === 'qrcode') {
        this.isCreateQRCodeDom = true
      } else {
        this.isCreateQRCodeDom = false
      }
    },
    handleLoginIn () {
      let _this = this
      let params = new URLSearchParams()
      params.append('username', this.loginForm.username)
      params.append('password', this.loginForm.password)
      loginIn(params)
        .then(res => {
          if (res.code !== 200) {
            _this.notify(res.msg, 'error')
          } else {
            _this.notify('登录成功', 'success')
            _this.$store.commit('setLoginIn', true)
            _this.$store.commit('setUserId', res.data.id)
            _this.$store.commit('setUsername', res.data.username)
            _this.$store.commit('setAvator', res.data.avator)
            console.log("---------------",res.data)
            _this.$store.commit('userVipStatus', res.data.isVipUser)
            _this.changeIndex('首页')
            _this.$router.push({path: '/'})
          }
        })
        .catch(err => {
          _this.notify('用户名或密码错误', 'error')
        })
    },
    goSignUp () {
      this.changeIndex('注册')
      this.$router.push({path: '/sign-up'})
    },
    changeIndex (value) {
      this.$store.commit('setActiveName', value)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/sign-up.scss';

.login-tab {
  width: 100%;
  height: 100%;

  .qrcode-box {
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    align-items: center;
  }

  .fresh-qrcode {
    color: #fff;
    margin: 20px 25px;
    padding: 15px;
    font-size: 16px
  }

  /*刷新二维码的中间状态*/
  .fresh-qrcode-status {
    width: 200px;
    height: 200px;
  }
}

</style>
