import { createStore } from 'vuex';
import kefu from '@/api/kefu';

// Create a new store instance.
export default createStore({
  state: {
    defaultState: {
      count: 0,
      token: ''
    },

    currentGameName: '',
    searchText: '',
    gameTypeList: [],
    // 用户信息
    user: null,

  },

  mutations: {
    increment(state) {
      state.count += 1;
    },

    // 游戏类型
    setCurrentGameName(state, value) {
      state.currentGameName = value
    },
    // 游戏名字
    setSearchText(state, value) {
      state.searchText = value
    },
    // 游戏列表
    setGameTypeList(state, value) {
      state.gameTypeList = value
    },
    // 用户信息
    setUser(state, value) {
      state.userInfo = value
    }

  },
  actions: {
    increment(context) {
      context.commit('increment');
    },
    // 用户信息
    fetchUser({ commit }) {
      kefu.getUserInfo().then(res => {
        if (res.code === 200) {
          commit('setUser', res.data)
          console.log('获取用户信息成功');
        }
      })
        .catch(error => {
          console.log(error)
        })
    }
  },
  getters: {
    double(state) {
      return 2 * state.count, state.user;
    }
  }
});
