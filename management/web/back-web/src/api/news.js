import request from '@/utils/request'

/**
 * 查找所有news 可通过模糊查找
 * 分页查找
 * @param data
 */
export function selectAll(data) {
  return request({
    url: '/back/news/select/all',
    method: 'post',
    data
  })
}

/**
 * 通过id查找新闻
 * @param data
 */
export function getNewsById(data) {
  return request({
    url: '/back/news/get/' + data,
    method: 'get'
  })
}
