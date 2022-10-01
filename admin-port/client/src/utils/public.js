/**
 * 将普通列表无限递归转换为树
 * @param  {[type]} list       [普通的列表，必须包括 opt.primaryKey 指定的键和 opt.parentKey 指定的键]
 * @param {[type]} opt [配置参数，支持 primaryKey(主键 默认id) parentKey(父级id对应键 默认pid) nameKey(节点标题对应的key 默认name) valueKey(节点值对应的key 默认id) checkedKey(节点是否选中的字段 默认checked，传入数组则判断主键是否在此数组中) startPid(第一层扫描的PID 默认0) currentDept(当前层 默认0) maxDept(最大递归层 默认100) childKey(递归完成后子节点对应键 默认list) deptPrefix(根据层级重复的前缀 默认'')]
 * @return {[type]}            [description]
 */
export const listConvertTree = (array, opt) => {
  const obj = {
    primaryKey: opt.primaryKey || 'id',
    parentKey: opt.parentKey || 'pid',
    startPid: opt.startPid || 0,
    currentDept: opt.currentDept || 0,
    maxDept: opt.maxDept || 100,
    childKey: opt.childKey || 'children'
  }
  return listToTree(array, obj.startPid, obj.currentDept, obj)
}

/**
 *  实际的递归函数，将会变化的参数抽取出来
 * @param list
 * @param startPid
 * @param currentDept
 * @param opt
 * @returns {Array}
 */
export const listToTree = (array, startPid, currentDept, opt) => {
  if (opt.maxDept < currentDept) {
    return []
  }
  let child = []
  if (array && array.length > 0) {
    child = array
      .map(item => {
        // 筛查符合条件的数据（主键 = startPid）
        if (
          typeof item[opt.parentKey] !== 'undefined' &&
          item[opt.parentKey] === startPid
        ) {
          // 满足条件则递归
          const nextChild = listToTree(
            array,
            item[opt.primaryKey],
            currentDept + 1,
            opt
          )
          // 节点信息保存
          if (nextChild.length > 0) {
            item['hasChild'] = true
            item[opt.childKey] = nextChild
          } else {
            item['hasChild'] = false
          }
          return item
        }
      })
      .filter(item => {
        return item !== undefined
      })
  }
  return child
}
