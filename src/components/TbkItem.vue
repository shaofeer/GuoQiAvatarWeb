<template>
  <div class="item">
    <mu-flex justify-content="start" class="item">
      <mu-flex class="left" justify-content="center">
        <img :src="pictUrl" :title="shortTitle" :alt="shortTitle">
      </mu-flex>

      <mu-flex class="right" justify-content="start" direction="column">
        <p class="title">{{shortTitle}}</p>
        <mu-flex class="left" direction="column">
          <span class="desc">{{title}}</span>
          <mu-flex class="item">
            <p>
              <span class="del">￥{{zkFinalPrice}}</span>
              <span class="price">￥{{finalPrice}}</span>
              <mu-button color="#FC6E05" class="btn-right" @click="copyTKL">
                复制口令
              </mu-button>
            </p>
          </mu-flex>
        </mu-flex>


      </mu-flex>

    </mu-flex>

  </div>
</template>

<script>
  export default {
    name: "TbkItem",
    props: [
      'title',
      'shortTitle',
      'pictUrl',
      'itemUrl',
      'zkFinalPrice',
      'finalPrice',
      'itemId',
      'model',
    ],
    data() {
      return {
      }
    },

    watch: {},
    methods: {
      copyTKL() {

        let _this = this

        this.$copyText("淘口令" + this.model).then(function (e) {
          console.log(e);
          _this.$toast.success('复制成功!');

        }, function (e) {
          console.log(e);
          _this.$toast.error('复制失败!' + e.text);
          _this.$toast.error(e.text);


        })
      }
    }
  }
</script>

<style lang="less">
  .item {
    height: 150px;
    margin-bottom: 10px;
    margin-top: 10px;

    p {
      width: 244px; /*根据自己项目进行定义宽度*/
    }

  }


  .btn-right {
    margin-left: 15px;
    float: right;
  }

  .del {
    text-decoration: line-through;
    color: gray;
    font-size: 10px;
  }

  .price {
    color: red;
    font-size: 20px;
  }

  .right {

    .title {
      width: 280px; /*根据自己项目进行定义宽度*/
      overflow: hidden; /*设置超出的部分进行影藏*/
      text-overflow: ellipsis; /*设置超出部分使用省略号*/
      white-space: nowrap; /*设置为单行*/
      font-size: 15px;
    }

    .desc {
      height: 40px;
      overflow: hidden;
      overflow: hidden; /*设置超出的部分进行影藏*/
      text-overflow: ellipsis; /*设置超出部分使用省略号*/
      font-size: 10px;
    }
  }


  .left {
    img {
      height: 150px;
      width: 150px;
      margin-right: 10px;
    }
  }


</style>
