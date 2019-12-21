<template>
  <div id="home_div">
    <div class="top_img_div">
      <img :src="logo_niceyoo" v-if="is_niceyoo">
      <img :src="logo" v-else>
      <!--      <img :src="topImg">-->
    </div>
    <div class="top_title_div">
      <mu-flex align-items="center">
        <mu-flex justify-content="center" fill>
          <mu-chip>
            使用方式:上传头像之后,长按头像即可保存到手机。
          </mu-chip>
        </mu-flex>
      </mu-flex>
    </div>

    <div id="carousel_div">


      <mu-carousel
        :cycle="false"
        :hide-indicators="true"
        :hide-controls="false"
        @change="changeTemplate">

        <mu-icon value="first_page" size="30" color="while" slot="left"></mu-icon>
        <mu-icon value="last_page" size="30" color="while" slot="right"></mu-icon>

        <mu-carousel-item>
          <img class="item-img" :src="template1">
        </mu-carousel-item>
        <mu-carousel-item>
          <img class="item-img" :src="template2">
        </mu-carousel-item>
        <mu-carousel-item>
          <img class="item-img" :src="template3">
        </mu-carousel-item>
        <mu-carousel-item>
          <img class="item-img" :src="template4">
        </mu-carousel-item>
        <mu-carousel-item>
          <img class="item-img" :src="template5">
        </mu-carousel-item>
      </mu-carousel>
    </div>


    <mu-flex align-items="center">
      <mu-flex justify-content="center" fill>
        <mu-button @click="selectImg" color="primary">
          <mu-icon left value="add_photo_alternate"></mu-icon>
          上传头像
        </mu-button>
      </mu-flex>

      <input ref="filElem" id="uploadFile" type="file" multiple @change='uploadFile($event)'
             style="display: none;"/>
    </mu-flex>
    <br>

    <div class="happy_img_div">
      <img :src="qrcode_niceyoo" v-if="is_niceyoo">
      <img :src="qrcode" v-else>
      <!--      <img :src="logo">-->
    </div>
  </div>


</template>

<script>

  import template1 from '../assets/template_1.png'
  import template2 from '../assets/template_2.png'
  import template3 from '../assets/template_3.png'
  import template4 from '../assets/template_4.png'
  import template5 from '../assets/template_5.png'

  import topImg from '../assets/topImg.png'
  import qrcode from '../assets/qrcode.png'
  import logo from '../assets/logo.png'
  import logo_niceyoo from '../assets/logo_niceyoo.png'
  import qrcode_niceyoo from '../assets/qrcode_niceyoo.png'


  export default {
    name: 'HelloWorld',
    data() {
      return {
        template1,
        template2,
        template3,
        template4,
        template5,
        topImg,
        qrcode,
        logo,
        qrcode_niceyoo,
        logo_niceyoo,
        is_niceyoo: false,
      }
    },
    methods: {
      changeTemplate: function (index) {
        // this.$toast.error({'message': index, 'close': false, 'infoIcon': ''})
      },
      selectImg: function () {
        this.$refs.filElem.dispatchEvent(new MouseEvent('click'))
      },
      uploadFile: function (event) {
        let fileList = event.target.files
        let file = fileList[0]

        console.log(file)

        let param = new FormData() //创建form对象
        param.append('file', file)//通过append向form对象添加数据

        let config = {
          // headers: {'Content-Type': 'multipart/form-data'}
        }

        this.axios.post('/api/wechatavatar/uploadFile', param, config).then(res => {
          console.log(res)
          this.template1 = res.data['template1']
          this.template2 = res.data['template2']
          this.template3 = res.data['template3']
          this.template4 = res.data['template4']
          this.template5 = res.data['template5']
        }).catch(res => {
          console.log(res)
        })

      },

    },
    watch: {
      changeTemplate(n, o) {
      }
    },
    mounted() {
    },
    beforeCreate() {
      var ua = navigator.userAgent.toLowerCase();
      //获取判断用的对象
      if (ua.match(/MicroMessenger/i) == "micromessenger") {
        //在微信中打开
      } else {
        console.log("非微信中打开");
        window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx&connect_redirect=1#wechat_redirect';
      }

    },
    created() {

      if ('/niceyoo' === this.$route.path) {
        this.is_niceyoo = true
      }
    }
  }
</script>

<style scoped>

  #home_div {
    background-image: url("../assets/backage.png");
    background-size: 400px 940px;
    background-repeat: no-repeat;
  }

  .item-img {
    width: 250px;
    height: 250px;
  }


  .mu-carousel-item > img {
    min-width: 0px;
  }

  .top_title_div {
    padding-top: 120px;
  }

  .top_img_div, .happy_img_div {
    text-align: center;
  }

  .top_img_div > img, .happy_img_div > img {
    margin-top: 0px;
    width: 300px;
  }


  .mu-carousel-item, #carousel_div, .mu-carousel {
    height: 300px;
  }

</style>
