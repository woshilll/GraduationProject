import request from '@/utils/request'
/**
 * 获取个人信息
 * @param username 用户名
 */
export function info(username) {
  return request({
    url: '/back/user/info/' + username,
    method: 'get'
  })
}

/**
 * 更新个人信息
 * @param data
 */
export function update(data) {
  return request({
    url: '/back/user/update',
    method: 'post',
    data
  })
}

/**
 * 更新用户头像
 * @param data
 */
export function modifyIcon(data) {
  return request({
    url: '/back/user/modify/icon',
    method: 'post',
    data
  })
}

/**
 * 查找所有user 可通过模糊查找
 * 分页查找
 * @param data
 */
export function selectAll(data) {
  return request({
    url: '/back/user/select/all',
    method: 'post',
    data
  })
}

/**
 * 根据用户id删除用户
 * @param data
 */
export function deleteById(data) {
  return request({
    url: '/back/user/delete/' + data,
    method: 'post'
  })
}
