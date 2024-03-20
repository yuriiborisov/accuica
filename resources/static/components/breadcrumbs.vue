
<template>
  <div class="breadcrumb" >
<!--    <ul>-->
<!--      <li v-for="(breadcrumb, idx) in breadcrumbList"-->
<!--          :key="idx"-->
<!--          @click="routeTo(idx)"-->
<!--          :class="{'linked': !!breadcrumb.link}">-->
<!--        {{breadcrumb.name}}-->
<!--      </li>-->
<!--    </ul>-->
<!--    <router-link :to="{name: 'Privileges'}">Go to Privileges</router-link>-->
        <div v-for="(breadcrumb, idx) in breadcrumbList"
              :key="idx">
<!--          <router-link v-if="breadcrumb.link" :to="breadcrumb.link">-->
          <span :class="{'linked': !!breadcrumb.link}">
             <a @click="routeTo(idx)">{{breadcrumb.name }}</a>
          </span>
          <span v-if="idx != breadcrumbList.length-1"> / </span>
<!--          </router-link>-->
        </div>


  </div>
<!--  <nav aria-label="breadcrumb">-->
<!--    <ol class="breadcrumb">-->
<!--      <li class="breadcrumb-item" v-for="(item, index) in items" :key="index">-->
<!--        <router-link :to="item.link">-->
<!--          <a v-if="item.link" :href="item.link">{{ item.text }}</a>-->
<!--          <span v-else>{{ item.text }}</span>-->
<!--        </router-link>-->
<!--      </li>-->
<!--    </ol>-->
<!--  </nav>-->
</template>

<script>
module.exports = {
  data(){
    return{
      breadcrumbList:[]
    }
  },
  mounted(){
    console.log('breadcrumbList', this.breadcrumbList);
    this.updateList();
  },
  watch:{
    '$route' (){
      console.log('$route', this.$route);
      this.updateList()
    }
  },
  methods:{
    routeTo(id){
      if(this.breadcrumbList[id].link ) this.$router.push(this.breadcrumbList[id].link);
    },
    updateList(){
      this.breadcrumbList = this.$route.meta.breadcrumb
    }
  },
  // props: {
  //   data: {
  //     type: Array,
  //     required: true
  //   }
  // },
  // data(){
  //   return{
  //     items: this.data
  //   }
  // },
  // mounted(){
  //   this.$root.$on("breadcrumbs_update", (items) => this.performItems(items));
  // },
  // methods:{
  //   performItems(items){
  //       this.items = items;
  //   }
  // }
};
</script>
<style>
.breadcrumb .linked {
  color: #5476e3;
}
.breadcrumb {
  background-color: inherit !important;
  padding: 0 !important;
  color: #8a8a8a;
  font-size: 10pt;
  margin-left: 4rem;
}
.breadcrumb .linked:hover {
  color: #000;
  cursor: pointer;
}
.breadcrumb span {
  margin: 0 0 0 10px;
}
</style>
