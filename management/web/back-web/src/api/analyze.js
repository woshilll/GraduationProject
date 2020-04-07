import request from '@/utils/request'

/**
 * 分析admin user news comment数量
 */
export function analyzeCount() {
  return request({
    url: '/back/analyze/count',
    method: 'get'
  })
}

/**
 * 分析每个分类下新闻数量
 */
export function analyzeCategoryNewsCount() {
  return request({
    url: '/back/analyze/categoryNewsCount',
    method: 'get'
  })
}
