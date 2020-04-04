import request from '@/utils/request'

/**
 * 查找所有newsCategory
 */
export function getAll() {
  return request({
    url: '/back/category/get/all',
    method: 'get',
  })
}
