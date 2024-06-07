<!--Сайдбар-->
<template>
  <div>
    <!-- Button to toggle the sidebar -->

    <!-- Sidebar content -->
    <div id="sidebar" class="p-3">
      <div class="toggle-sidebar">
        <div id="sidebar-toggle" class="btn-primary" v-on:click="toggleSidebar"></div>
      </div>
      <role-edit :item="item" :parent-ids="parentIds" :create-new="createNew"/>
    </div>
  </div>
</template>

<script>
module.exports = {
  props:{
    item: Object,
    parentIds: Array,
    createNew: Boolean,
    component:{
      data:Object,
      component: Function
    }
    // component: Function
  },
  mounted(){

  },
  methods:{
    toggleSidebar(event){
        document.getElementById('sidebar').classList.toggle('hide');
      document.getElementById('sidebar-toggle').classList.toggle('sidebar-hidden');
    }
  },
  components: {
    navigation: httpVueLoader('/components/app/nav.vue'),
    roleEdit: httpVueLoader('/components/role-edit.vue')
  }

};
</script>
<style>
/* Custom CSS for the sidebar */
#sidebar {
  position: fixed;
  top: 0;
  right: 0; /* Start off-screen */
  width: 350px;
  height: 100vh;
  overflow: scroll;
  background-color: #f8f9fa;
  transition: right 0.3s ease-in-out;
  z-index: 9999; /* Ensure the sidebar is above other content */
  box-shadow: 10px 0px 30px 0px #929292;
}
#sidebar.hide {
  right: -350px; /* Slide in when the 'show' class is added */
}
#sidebar-toggle {
  transition: top 0.3s ease-in-out;
  position: fixed;
  top: 10px;
  right: 10px;
  z-index: 10000;
  font-size: 10pt;
  justify-content: center;
  display: flex;
  align-items: center;
  width: 30px;
  height: 30px;
  border-radius: 15px;
}

#sidebar-toggle::after {
  content: '×';
}
#sidebar-toggle:hover {
  cursor: pointer;
}
.sidebar-hidden::after {
  content: '↤' !important;
}

.sidebar-hidden {
  top: 50vh !important;}

.toggle-sidebar {
  display: flex;
  align-items: center;
}
</style>

