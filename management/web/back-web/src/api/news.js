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

/**
 * 更新新闻
 * @param data
 */
export function updateNews(data) {
  return request({
    url: '/back/news/update',
    method: 'post',
    data
  })
}

/**
 * 未通过
 * @param data
 */
export function noPass(data) {
  return request({
    url: '/back/news/noPass',
    method: 'post',
    data
  })
}
/**
 * 新增新闻
 * @param data
 */
export function addNews(data) {
  return request({
    url: '/back/news/insert',
    method: 'post',
    data
  })
}
/**
 * 删除新闻 真
 * @param id
 */
export function deleteById(id) {
  return request({
    url: '/back/news/delete/' + id,
    method: 'post',
  })
}
/**
 * 批量审核
 * @param data
 */
export function batchAudit(data) {
  return request({
    url: '/back/news/batch/audit',
    method: 'post',
    data
  })
}
