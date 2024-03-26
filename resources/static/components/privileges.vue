
<template>
  <div class="privileges-panel">
    <div class="container-xxl">
      <div class="table-container">
        <table class="table table-sm table-striped table-bordered table-fixed" id="dynamicTable">
          <thead>
          <tr>
            <th>ID</th>
            <th>Alt</th>
            <th>Calculated</th>
            <th>Description</th>
            <th>Filter</th>
            <th>UICA</th>
            <th>Filter Value</th>
            <th>Priority Order</th>
            <th>Script</th>
            <th>Applicable</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in privileges" :key="item.id">
            <td class="id-cell">{{item.id}}</td>
            <td class="th-sm">{{item.alt}}</td>
            <td class="th-sm">{{item.calculated}}</td>
            <td class="description-cell">{{item.description}}</td>
            <td class="th-sm">{{item.filter}}</td>
            <td class="th-sm">{{item.uica}}</td>
            <td class="th-sm">{{item.filterValue}}</td>
            <td class="priority-cell">{{item.priorityOrder}}</td>
            <td class="th-sm">{{item.statementScript}}</td>
            <td class="th-sm">{{item.applicable}}</td>
            <td class="action-cell">
              <router-link :to="{ path: '/edit-privilege', query: { privilegeId: item.id }}">
                <button class="btn btn-outline-primary btn-sm">Edit</button>
              </router-link>
<!--              <router-link :to="{ path: '/matrix', query: { roleId: item.id }}">-->
<!--                <button class="btn btn btn-secondary btn-sm">Matrix</button>-->
<!--              </router-link>-->
            </td>
          </tr>
          </tbody>
        </table>
     </div>
    </div>
  </div>
</template>

<script>
module.exports = {
  data(){
    return{
      privileges: [],
    }
  },
  async created(){
    this.privileges =  await this.getAllPrivileges();
  },
  methods: {
    async getAllPrivileges(){
      return await fetcher.fetch('/privilege/get-all', 'GET',{
          'Content-Type': 'application/json',
        });
    }
  }
};
</script>
<style>
.privileges-panel .table-container {
  width: 90vw;
  overflow-x: auto;
}
.privileges-panel .table-container table {
  font-size: 12px; /* Set the minimum font size */
}
/* Table with horizontal scroll */
.privileges-panel .table-container {
  max-width: 100%;
  overflow-x: auto;
  height: 70vh;
}

.privileges-panel .table-fixed {
  overflow-y: auto;
}
.privileges-panel  table thead th:first-child {
  position: sticky;
  left: 0;
  z-index: 2;
  background: #fff;
}
.privileges-panel  table tbody th {
  position: sticky;
  left: 0;
  background: white;
  z-index: 1;
}

/* Fixed first column */
.privileges-panel .table-fixed thead td:first-child,
.privileges-panel .table-fixed tbody td:first-child {
  position: sticky;
  left: 0;
  z-index: 2;
  background-color: #fff;
  max-width: 250px;
  overflow: hidden;

}

.privileges-panel .table-fixed thead tr:first-child {
  position: sticky;
  left: 0;
  z-index: 20;
  background-color: #fff;


}

.privileges-panel .table-fixed thead tr td:first-child{
  background-color: #959595;
  color:#fff;
}

.privileges-panel .table-fixed thead tr {
  position: sticky;
  top: 0;
  background-color: #fff;
  z-index: 20;

}
</style>
