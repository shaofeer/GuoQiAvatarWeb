import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld,
      meta: {
        title: '趣学程序'
      }
    },
    {
      path: '/niceyoo',
      name: 'HelloWorld',
      component: HelloWorld,
      meta: {
        title: 'niceyoo'
      }
    }
  ]
})
