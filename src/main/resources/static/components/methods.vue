
<template>
  <div class="methods-panel" >
    <sidebar-setup :item="selected" :parent-ids="parentIds" :create-new="createNew" v-if="(selected != undefined || createNew)"></sidebar-setup>
<!--    <router-link class="new-button" to="/new-role">-->

<!--    </router-link>-->
    <div class="container-xxl">
      <div class="table-container">
        <table class="table table-striped table-fixed" id="dynamicTable">
          <thead>
          <tr>
            <th>ID</th>
            <th>Entity</th>
            <th>Description</th>
            <th>Privileges</th>
<!--            <th>Action</th>-->
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in roles" :key="item.key" v-on:click="handleEditRole2($event, item)">
            <td class="id-cell">{{item.key}}</td>
            <td class="priority-cell">{{item.entityType}}</td>
            <td class="description-cell">{{item.description}}</td>
            <td>{{item.privileges}}</td>
<!--            <td class="action-cell">-->
<!--              <router-link :to="{ path: '/edit-role', query: { roleId: item.id }}">-->
<!--                <button class="btn btn-outline-primary btn-sm">Edit</button>-->
<!--              </router-link>-->
<!--              <router-link :to="{ path: '/matrix', query: { roleId: item.id }}">-->
<!--                <button class="btn btn btn-secondary btn-sm">Matrix</button>-->
<!--              </router-link>-->
<!--            </td>-->
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
      createNew: false

    }
  },

  async created(){
    this.roles = await this.getAllRoles();
    this.parentIds = this.roles.filter(e => e.id != this.selected).map(e => e.id);
    console.log('this.roles1', this.roles);
  },
  mounted(){
    this.$root.$on("updatedRoles", async () =>  {
      this.roles =  await this.getAllRoles();
      this.parentIds = this.roles.filter(e => e.id != this.selected).map(e => e.id);
      console.log('this.roles2', this.roles);
    });
  },
  methods: {

    handleNewButton(){
      this.createNew = true;
      this.selected = {};
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
      event.target.parentElement.classList.toggle("selected-role");
      var els = document.getElementsByClassName("selected-role");
      Array.prototype.forEach.call(els, (el) => {
       if(el != event.target.parentElement){
         el.classList.toggle("selected-role");
       }
      });
      // this.sendMessageIdToSideBar(id);
      this.selected = els.length == 0 ? undefined : selected;
      this.createNew = false;
      console.log('handleEditRole2');
    },
    handleEditRole(id){
      router.push({
        to: '/matrix',
        // preserve current path and remove the first char to avoid the target URL starting with `//`
        params: { roleId: id }
      })
    },
    async getAllRoles(){
      return await fetcher.fetch('/method/get-all', 'GET',{
          'Content-Type': 'application/json',
        });
    }
  },
  components: {
    'sidebar-setup': httpVueLoader('/components/sidebar-setup.vue'),
    'roleedit': httpVueLoader('/components/role-edit.vue')
  }
};
</script>
<style>
.table-container {
  width: 90vw;
  overflow-x: auto;
}
.table-container table {
  font-size: 12px; /* Set the minimum font size */
}
/* Table with horizontal scroll */
.table-container {
  max-width: 100%;
  overflow-x: auto;
  height: 70vh;
}

.table-fixed {
  overflow-y: auto;
}
table thead th:first-child {
  position: sticky;
  left: 0;
  z-index: 2;
  background: #fff;
}
table tbody th {
  position: sticky;
  left: 0;
  background: white;
  z-index: 1;
}

/* Fixed first column */
.table-fixed thead td:first-child,
.table-fixed tbody td:first-child {
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
