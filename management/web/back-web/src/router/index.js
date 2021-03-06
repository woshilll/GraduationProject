import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: {title: '首页', icon: 'dashboard', affix: true}
    }]
  },
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: '个人中心', icon: 'user', noCache: true }
      }
    ]
  },

  //当前用户管理模块

  {
    path: '/admin',
    component: Layout,
    redirect: '/admin/info',
    name: 'Admin',
    meta: {title: '个人信息', icon: 'user'},
    children: [
      {
        path: 'info',
        name: 'AdminInfo',
        component: () => import('@/views/admin/info'),
        meta: {title: '修改信息', icon: 'edit'}
      },
      {
        path: 'icon',
        name: 'ProfileIcon',
        component: () => import('@/views/admin/icon'),
        meta: {title: '修改头像', icon: 'icon'}
      }
    ]
  },

  //管理员信息管理模块
  {
    path: '/user',
    component: Layout,
    redirect: '/user/adminList',
    name: 'user',
    meta: {title: '用户管理', icon: 'peoples'},
    children: [
      {
        path: 'adminList',
        name: 'AdminList',
        component: () => import('@/views/admin/list'),
        meta: {title: '管理员管理', icon: 'people'}
      },
      {
        path: 'userList',
        name: 'UserList',
        component: () => import('@/views/user/list'),
        meta: {title: '用户管理', icon: 'people'}
      },
    ]
  },
//新闻信息管理模块
  {
    path: '/news',
    component: Layout,
    redirect: '/news/newsList',
    name: 'news',
    meta: {title: '新闻管理', icon: 'peoples'},
    children: [
      {
        path: 'newsList',
        name: 'NewsList',
        component: () => import('@/views/news/list'),
        meta: {title: '新闻管理', icon: 'list'}
      },
      {
        path: 'createNews',
        name: 'CreateNews',
        component: () => import('@/views/news/create'),
        meta: {title: '新增新闻', icon: 'edit'}
      },
      {
        path: 'edit/:id(\\d+)',
        component: () => import('@/views/news/edit'),
        name: 'EditNews',
        meta: {title: '编辑新闻', noCache: true, activeMenu: '/news/list'},
        hidden: true
      },
    ]
  },
//其它管理模块
  {
    path: '/others',
    component: Layout,
    redirect: '/others/categoryList',
    name: 'others',
    meta: {title: '其它管理', icon: 'peoples'},
    children: [
      {
        path: 'categoryList',
        name: 'CategoryList',
        component: () => import('@/views/category/list'),
        meta: {title: '分类信息', icon: 'list'}
      },
      {
        path: 'commentList',
        name: 'CommentList',
        component: () => import('@/views/comment/list'),
        meta: {title: '评论管理', icon: 'list'}
      },

    ]
  },

  // 404 page must be placed at the end !!!
  {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
