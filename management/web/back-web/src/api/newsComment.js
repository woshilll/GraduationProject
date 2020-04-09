import request from '@/utils/request'

/**
 * 通过新闻id查找评论
 */
export function getCommentsByNewsId(newsId) {
  return request({
    url: '/back/newsComment/getCommentsByNewsId?newsId=' + newsId,
    method: 'get'
  })
}

/**
 * 通过id更新新闻状态
 */
export function updateComment(data) {
  return request({
    url: '/back/newsComment/update',
    method: 'post',
    data
  })
}
