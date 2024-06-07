
<template>
    <tabs :tabs-data = "tabsData"></tabs>
</template>

<script>
module.exports = {
  data(){
    return{
      tabsData: [
        {
          name: "Matrix",
          id: "tab1",
          component: httpVueLoader('/components/matrix.vue')
        },
        {
          name: "Json",
          id: "tab2",
          component: httpVueLoader('/components/app/json-viewer-custom.vue')
        }
      ]
    }
  },
  methods: {

  },
  components:{
    tabs: httpVueLoader('/components/tab.vue'),
}

};
</script>
<style>
</style>
