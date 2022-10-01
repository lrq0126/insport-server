import request from '@/utils/request'

// 专题类型options    社会安全类
export function getTypeOptions() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'scene_topic'
    }
  })
}
// 专题场景options
export function getSceneOptions() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'scene_type'
    }
  })
}
// 专题年级options
export function getGradeOptions() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'applicable_grade'
    }
  })
}

// 评论状态options
export function getCommentStatus() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'cms_del_flag'
    }
  })
}

// 留言类别options
export function getMessagetype() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'cms_guestbook'
    }
  })
}

// 获取栏目模型options
export function getColumnOptions() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'cms_module'
    }
  })
}

// 获取知识库管理状态options
export function getKnowledgeOptions() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'cms_article_flag'
    }
  })
}

// 获取试题库专题options  how do you do
export function getItembankOptions() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'applicable_grade'
    }
  })
}

// 获取难度等级
export function getDifficultyClass() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'difficulty_level'
    }
  })
}

// 获取试题题型
export function getQuestionType() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'question_type'
    }
  })
}

// 获取创建人
// export function getCreator() {
//   return request({
//     url: '/scbase/baseDict/BaseDictByType',
//     method: 'GET',
//     params: {
//       type: 'createBy'
//     }
//   })
// }

// 获取出题方式
export function getCreator() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'question_way'
    }
  })
}

// 获取考试场景
export function getExaminationScene() {
  return request({
    url: '/prevent/examExaminationInfo/sceneList',
    method: 'GET'
  })
}

// 安全应急 应急预案 预案信息列表级别
export function getPlanInfoLevel() {
  return request({
    url: '/scbase/baseDict/BaseDictByType',
    method: 'GET',
    params: {
      type: 'plan_level'
    }
  })
}

// 获取心理测评考试场景
export function getpsyExaminationScene() {
  return request({
    url: '/prevent/psyExamExaminationInfo/sceneList',
    method: 'GET'
  })
}
