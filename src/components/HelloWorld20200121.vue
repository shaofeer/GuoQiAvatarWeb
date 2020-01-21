<template>
  <div class="markdown">
    <Markdown
      @on-upload-image="uploadImg"
      @on-paste-image="pasteImg"
      @on-save="save"
      @on-ready="ready"/>
  </div>
</template>

<script>
  import Markdown from 'vue-meditor';

  export default {
    name: "markdown",
    components: {
      Markdown
    },
    methods: {
      ready(param) {
        console.log(":ready");
        console.log(param);

      },

      save(param) {
        console.log(":save");
        console.log(param);

      },

      uploadImg(param) {
        console.log(":uploadImg");
        console.log(param);

      },

      pasteImg(param) {
        console.log(":pasteImg");
        console.log(param);

        let fileName = param.fileName;

        // let fileList = event.target.files
        // let file = fileList[0]

        // console.log(file)

        let reqParam = new FormData() //创建form对象
        reqParam.append('imgFile', param)//通过append向form对象添加数据

        let config = {
          // headers: {'Content-Type': 'multipart/form-data'}
        }

        //  url: "http://api.mptask.wintp.top/api/giteeBlogImg/saveImg",
        this.axios.post('/api/giteeBlogImg/saveImg', reqParam, config).then(res => {
          console.log(res)


        }).catch(res => {
          console.log(res)
        })

      }
    }
  }
</script>
