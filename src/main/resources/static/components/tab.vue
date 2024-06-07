
<template>
  <div class="main-content">
<!--     Nav tabs-->
    <ul class="nav nav-tabs" id="myTab" role="tablist">
      <li class="nav-item" v-for="(tab, index) in tabsData" :key="tab.id">
        <a :class="'nav-link ' + (index === 0 ? 'active' : '')" v-bind:id="tab.id+'-tab'" data-toggle="tab" v-bind:href="'#'+tab.id" role="tab" v-bind:aria-controls="tab.id" aria-selected="true">{{ tab.name }}</a>
      </li>
    </ul>
    <div class="tab-content" id="myTabContent">
      <div v-for="(tab, index) in tabsData" :class="'tab-pane fade ' + (index === 0 ? 'show active' : '')" v-bind:id="tab.id" role="tabpanel" v-bind:aria-labelledby="tab.id+'-tab'">
          <keep-alive>
             <component :is="tab.component"/>
          </keep-alive>
      </div>
    </div>
  </div>
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
  // data(){
  //   return{
  //     tabs: this.tabsData
  //   }
  // },

  created(){
    console.log("tab", this.tabsData);
  },
  // methods:{
  //   getComponent(id){
  //     const result = this.tabsData.filter((tab) => tab.id = id);
  //     return result.component()
  //   }
  // }
};
</script>
<style>
.nav-tabs {
  margin-bottom: 15px;
}
.main-content {
  width: 100vw;
  padding: 0px 20px 0px 20px;
}
</style>