import request from '@/utils/request'

/**
 * 爬虫解析新闻
 */
export function news(data) {
  return request({
    url: '/crawler/news',
    method: 'post',
    data
  })
}
