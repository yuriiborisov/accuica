<!--Сайдбар-->
<template>
  <!-- Sidebar -->
  <div class="sidebar">
    <navigation />
  </div>
</template>

<script>
module.exports = {
  // computed:{
  //   data(){
  //     return Array.of({
  //       id: "tab1",
  //       name: "Tab",
  //       component: this.roles
  //     });
  //   }
  // },
  // computed:{
  //   data() {
  //     return {
  //       id: "tab1",
  //       name: "Tab",
  //       component: this.roles
  //     }]
  //   },
  // },
  components: {
    navigation: httpVueLoader('/components/app/nav.vue')
  }

};
</script>
<style>
.sidebar {
  height: 100vh;
  width: 200px;
  position: fixed;
  z-index: 1;
  top: 0;
  left: 0;
  background-color: #f8f9fa;
  overflow-x: hidden;
  padding-top: 20px;
}

.sidebar a {
  padding: 6px 8px 6px 16px;
  text-decoration: none;
  font-size: 18px;
  color: #333;
  display: block;
}

.sidebar a:hover {
  color: #16181b;
}
</style>

