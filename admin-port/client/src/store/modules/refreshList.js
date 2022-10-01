const state = {
  refresh: 1
}

const mutations = {
  refreshList: (state, number) => {
    state.refresh += number
  }
}

const actions = {
  refreshList: (state, number) => {
    store.commit('refreshList', number)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
