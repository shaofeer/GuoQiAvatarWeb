<template>
  <div id="home_div">
    <div class="top_img_div">
      <img :src="logo">
      <img :src="topImg">
    </div>

    <mu-flex align-items="center">
      <mu-flex justify-content="center" fill>
        <mu-chip>
          使用方式:上传头像之后,长按头像即可保存到手机。
        </mu-chip>
      </mu-flex>
    </mu-flex>

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
      </mu-carousel>
    </div>


    <mu-flex align-items="center">
      <mu-flex justify-content="center" fill>
        <mu-button @click="selectImg" color="primary">
          <mu-icon left value="add_photo_alternate"></mu-icon>
          上传头像
        </mu-button>
      </mu-flex>

      <input ref="filElem" id="uploadFile" type="file" multiple @change='uploadFile($event)' style="display: none;"/>
    </mu-flex>
    <br>

    <div class="happy_img_div">
      <img :src="happyImg">
      <img :src="logo">
    </div>

  </div>
</template>

<script>

  import template1 from '../assets/template_1.png'
  import template2 from '../assets/template_2.png'
  import template3 from '../assets/template_3.png'

  import topImg from '../assets/topImg.png'
  import happyImg from '../assets/happy.png'
  import logo from '../assets/logo.png'

  export default {
    name: 'HelloWorld',
    data () {
      return {
        template1,
        template2,
        template3,
        topImg,
        happyImg,
        logo,
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
        }).catch(res => {
          console.log(res)
        })

      },

    },
    watch: {
      changeTemplate (n, o) {
        console.log(n)
      }
    },
    mounted () {
    }
  }
</script>

<style scoped>

  #home_div {
    background-image: url("../assets/backage.png");
  }

  .item-img {
    width: 250px;
    height: 250px;
  }


  .mu-carousel-item > img {
    min-width: 0px;
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
