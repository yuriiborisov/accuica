
<template>

    <nested-tabs :tabs-data="tabsData"></nested-tabs>
</template>

<script>
module.exports = {

  props:{
    tabsData: {
      type: Array,
      default: () => ([{
        name: String,
        id: String,
        component: Object
      }])
    }
  },

  created(){
    console.log(this.tabsData);
  },
  methods:{
    getComponent(id){
      const result = tabsData.filter((tab) => tab.id = id);
      return result.component()
    }
  },
  components: {
    'nestedTabs': httpVueLoader('/components/tab.vue')
  }
};
</script>
<style>
.nav-tabs {
  margin-bottom: 15px;
}

</style>
