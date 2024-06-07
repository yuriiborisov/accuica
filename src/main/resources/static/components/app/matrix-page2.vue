
<template>
  <div class="main-content">
    <!--     Nav tabs-->
    <script>hljs.highlightAll();</script>
    <ul class="nav nav-tabs" id="myTab" role="tablist">
      <li class="nav-item" v-for="(tab, index) in tabsData" :key="tab.id">
        <a :class="'nav-link ' + (index === 0 ? 'active' : '')" v-bind:id="tab.id+'-tab'" data-toggle="tab" v-bind:href="'#'+tab.id" role="tab" v-bind:aria-controls="tab.id" aria-selected="true">{{ tab.name }}</a>
      </li>
    </ul>
    <div class="tab-content" id="myTabContent">
      <div class="tab-pane fade show active" v-bind:id="tab.id" role="tabpanel" v-bind:aria-labelledby="tab.id+'-tab'">

      </div>
      <div v-for="(tab, index) in tabsData" :class="'tab-pane fade ' + (index === 0 ? 'show active' : '')" v-bind:id="tab.id" role="tabpanel" v-bind:aria-labelledby="tab.id+'-tab'">


      </div>
    </div>
  </div>
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
          component: httpVueLoader('/components/json-viewer.vue')
        }
      ]
    }
  },
  methods: {

  },
  components:{
    tabs: httpVueLoader('/components/tab.vue'),
    matrix: httpVueLoader('/components/matrix.vue'),
    jsonviewer: httpVueLoader('/components/json-viewer.vue')
}

};
</script>
<style>
</style>
