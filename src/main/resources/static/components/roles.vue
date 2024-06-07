
<template>
  <div class="roles-panel" >
    <sidebar-setup :params="params" v-if="(params.data.selected != undefined || params.data.createNew)"></sidebar-setup>
<!--    <router-link class="new-button" to="/new-role">-->

<!--    </router-link>-->
    <div class="container-xxl">
      <div class="table-container">
        <table class="table table-striped table-fixed" id="dynamicTable">
          <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Priority</th>
            <th>Description</th>
            <th>Parent ID</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in roles" :key="item.id" v-on:click="handleEditRole2($event, item)">
            <td class="id-cell">{{item.id}}</td>
            <td>{{item.name}}</td>
            <td class="priority-cell">{{item.sortOrder}}</td>
            <td class="description-cell">{{item.description}}</td>
            <td>{{item.parent?.id}}</td>
            <td class="action-cell">
<!--              <router-link :to="{ path: '/edit-role', query: { roleId: item.id }}">-->
<!--                <button class="btn btn-outline-primary btn-sm">Edit</button>-->
<!--              </router-link>-->
              <router-link :to="{ path: '/matrix', query: { roleId: item.id }} ">
                <button class="btn btn btn-secondary btn-sm">Matrix</button>
              </router-link>
            </td>
          </tr>
          <!-- Table rows will be inserted here dynamically -->
          </tbody>
        </table>
     </div>
    </div>
    <button type="button" class="new-button btn btn-primary" @click="handleNewButton">Create New Role</button>
  </div>
</template>

<script>
module.exports = {
  data(){
    return{
      roles: [],
      selected: undefined,
      parentIds: [],
      createNew: false,
      params:{
        data:{
          selected: undefined,
          parentIds: [],
          createNew: false,
        },
        component: httpVueLoader('/components/role-edit.vue')
      }

    }
  },

  async created(){
    this.roles = await this.getAllRoles();
    this.params.data.parentIds = this.roles.filter(e => e.id != this.params.data.selected).map(e => e.id);
    console.log('this.roles1', this.roles);
  },
  mounted(){
    this.$root.$on("updatedRoles", async () =>  {
      this.roles =  await this.getAllRoles();
      this.params.data.parentIds = this.roles.filter(e => e.id != this.params.data.selected).map(e => e.id);
      console.log('this.roles2', this.roles);
    });
  },
  methods: {

    handleNewButton(){
      this.params.data.createNew = true;
      this.params.data.selected = {};
      var els = document.getElementsByClassName("selected-role");
      Array.prototype.forEach.call(els, (el) => {
          el.classList.toggle("selected-role");
      });
      var el =  document.getElementById("sidebar")
      el.classList.remove('hide');
      if(!el.classList.contains('hide')){
        document.getElementById('sidebar-toggle').classList.remove('sidebar-hidden');
      }
    },
    handleEditRole2(event, selected){
      //toggle css

      var els = document.getElementsByClassName("selected-role");
      event.target.parentElement.classList.toggle("selected-role");
      Array.prototype.forEach.call(els, (el) => {
       if(el != event.target.parentElement){
         el.classList.remove("selected-role");
       }
      });

      // this.sendMessageIdToSideBar(id);
      this.params.data.selected = els.length == 0 ? undefined : selected;
      this.params.data.createNew = false;
      console.log('handleEditRole2', this.params);
    },
    handleEditRole(id){
      router.push({
        to: '/matrix',
        // preserve current path and remove the first char to avoid the target URL starting with `//`
        params: { roleId: id }
      })
    },
    async getAllRoles(){
      return await fetcher.fetch('/role/get-all', 'GET',{
          'Content-Type': 'application/json',
        });
    }
  },
  components: {
    'sidebar-setup': httpVueLoader('/components/sidebar-setup2.vue'),
    'roleedit': httpVueLoader('/components/role-edit.vue')
  }
};
</script>
<style>
.roles-panel .table-container {
  width: 90vw;
  overflow-x: auto;
}
.roles-panel .table-container table {
  font-size: 12px; /* Set the minimum font size */
}
/* Table with horizontal scroll */
.roles-panel .table-container {
  max-width: 100%;
  overflow-x: auto;
  height: 70vh;
}

.roles-panel .table-fixed {
  overflow-y: auto;
}
.roles-panel table thead th:first-child {
  position: sticky;
  left: 0;
  z-index: 2;
  background: #fff;
}
.roles-panel table tbody th {
  position: sticky;
  left: 0;
  background: white;
  z-index: 1;
}

/* Fixed first column */
.roles-panel.table-fixed thead td:first-child,
.roles-panel.table-fixed tbody td:first-child {
  position: sticky;
  left: 0;
  z-index: 2;
  background-color: #fff;
  max-width: 250px;
  overflow: hidden;

}

.table-fixed thead tr:first-child {
  position: sticky;
  left: 0;
  z-index: 20;
  background-color: #fff;


}

.table-fixed thead tr td:first-child{
  background-color: #959595;
  color:#fff;
}

.table-fixed thead tr {
  position: sticky;
  top: 0;
  background-color: #fff;
  z-index: 20;

}

.selected-role td {
  background: #6d6d6d36 !important;
}
tbody tr:hover {
  cursor: pointer;
}
</style>
