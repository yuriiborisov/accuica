<!--Навигация Меню-->
<template>
  <div>
    <router-link to="/privileges">
      <a href="#">Privileges</a>
    </router-link>
    <router-link to="/roles">
      <a href="#roles">Roles</a>
    </router-link>
  </div>
</template>

<script>
module.exports = {
  data(){
    return{
      activeURL: this.getActiveUrl(),
    }
  },
  methods:{
    getActiveUrl(){
      var activeUrl = window.location.pathname;
      return activeUrl;
    }
  },
  components: {
    'user-info': httpVueLoader('/components/user-info.vue'),
    'tabs': httpVueLoader('/components/tabs.vue'),
    'roles': httpVueLoader('/components/roles.vue'),
    'jsonViewer': httpVueLoader('/components/json-viewer.vue')
  }
};
</script>