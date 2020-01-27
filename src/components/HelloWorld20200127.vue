<template>
  <div class="root">
    <mu-paper :z-depth="1" class="demo-loadmore-wrap">
      <mu-appbar color="#FDC305">
        <!--        <mu-button icon slot="left">-->
        <!--          <mu-icon value="menu"></mu-icon>-->
        <!--        </mu-button>-->
        <mu-alert color="#FDC305" class="title_span" v-if="alert" @delete="closeAlert()">
          <mu-icon left value="priority_high"></mu-icon>
          使用方法:&nbsp;&nbsp;&nbsp;&nbsp;点击复制口令，打开淘宝！
        </mu-alert>

        <mu-button icon slot="right" @click="refresh()">
          <mu-icon value="refresh"></mu-icon>
        </mu-button>
      </mu-appbar>


      <mu-container ref="container" class="demo-loadmore-content">
        <mu-load-more @refresh="refresh" :refreshing="refreshing" :loading="loading" @load="load">
          <mu-list>
            <template v-for="item in dataList">
              <tbk_item
                :title="item.title"
                :shortTitle="item.shortTitle"
                :pictUrl="item.pictUrl"
                :itemUrl="item.itemUrl"
                :couponShareUrl="item.couponShareUrl"
                :zkFinalPrice="parseInt(item.zkFinalPrice).toFixed(2)"
                :finalPrice
                  ="(item.zkFinalPrice-(item.couponAmount?item.couponAmount:0)).toFixed(2)"
                :itemId="item.itemId"
              ></tbk_item>
              <mu-divider/>
            </template>
          </mu-list>
        </mu-load-more>
      </mu-container>
    </mu-paper>
  </div>

</template>
<script>
  import tbk_item from './TbkItem'

  export default {
    components: {
      tbk_item
    },
    data() {
      return {
        dataList: [],
        refreshing: false,
        loading: false,
        alert: true,
        currentPageNumber: 1
      }
    },
    methods: {
      closeAlert() {
        this.alert = false
      },
      refresh() {
        this.refreshing = true;
        this.$refs.container.scrollTop = 0;
        let _this = this


        setTimeout(() => {
          this.refreshing = false;
          _this.searchProduct('', 1)
        }, 2000)
      },
      load() {
        let _this = this

        this.loading = true;
        setTimeout(() => {
          this.loading = false;

          _this.currentPageNumber = _this.currentPageNumber + 1

          _this.searchProduct('', _this.currentPageNumber, true)
        }, 2000)
      },

      searchProduct(keywords, pageNumber, isMore) {

        if (!keywords) {
          keywords = '九块九包邮'
        }

        let param = {
          "keywords": keywords,
          "pageNumber": pageNumber,
          "pageSize": 10,
        }

        let config = {
          // headers: {'Content-Type': 'multipart/form-data'}
        }

        let _this = this


        this.axios.get('/api/tbkInfo/getMaterialOptional', {
          params: param
        }).then(res => {

          console.log(res);

          if (res.data.code == 200) {
            if (isMore) {
              _this.dataList = _this.dataList.concat(res.data.result)

            } else {
              _this.dataList = res.data.result
            }

          }

        }).catch(res => {
          console.log(res)
        })
      }
    },
    created() {
      this.searchProduct('九块九包邮', 1)
    },

  };

</script>

<style lang="less">
  .demo-loadmore-wrap {
    width: 100%;
    height: 800px;
    display: flex;
    flex-direction: column;

    .mu-appbar {
      width: 100%;
    }
  }

  .demo-loadmore-content {
    flex: 1;
    overflow: auto;
    -webkit-overflow-scrolling: touch;
  }


  .root {
    width: 100%;
    background: #fff;
  }

  .title_span {
    height: 50px;
  }


</style>
