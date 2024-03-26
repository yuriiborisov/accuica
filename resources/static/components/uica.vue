<template>
  <div class="uica-panel" >
<!--    <sidebar-setup :params="params" v-if="(params.data.selected != undefined || params.data.createNew)"></sidebar-setup>-->
    <!--    <router-link class="new-button" to="/new-role">-->

    <!--    </router-link>-->
    <div class="container-xxl">
      <div class="table-container">
        <table class="table table-striped table-bordered table-fixed" id="dynamicTable">
          <thead>
          <tr>
            <th>ID</th>
            <th>Description</th>
<!--            <th>Config</th>-->
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="(item, index) in items" :key="item.formId">
            <td class="id-cell">{{item.formId}}</td>
            <td class="description-cell">{{item.description}}</td>
<!--            <td>{{item.config}}</td>-->
            <td>
              <router-link :to="{ path: '/uicaeditor', query: { formId: item.formId }} ">
                Edit
              </router-link>
            </td>
<!--            <td class="action-cell">-->
<!--              &lt;!&ndash;              <router-link :to="{ path: '/edit-role', query: { roleId: item.id }}">&ndash;&gt;-->
<!--              &lt;!&ndash;                <button class="btn btn-outline-primary btn-sm">Edit</button>&ndash;&gt;-->
<!--              &lt;!&ndash;              </router-link>&ndash;&gt;-->
<!--              <router-link :to="{ path: '/matrix', query: { roleId: item.id }} ">-->
<!--                <button class="btn btn btn-secondary btn-sm">Matrix</button>-->
<!--              </router-link>-->
<!--            </td>-->
          </tr>
          <!-- Table rows will be inserted here dynamically -->
          </tbody>
        </table>
      </div>
    </div>
<!--    <button type="button" class="new-button btn btn-primary" @click="handleNewButton">Create New Role</button>-->
  </div>
</template>

<script>
module.exports = {
  data(){
    return{
      editor: undefined,
      items:[]
    }
  },
  async created(){
    this.items = await this.getData();
    console.log(this.json);
    var value = this.items;
    // this.editor.setValue(JSON.stringify(value));
  },
  methods:{
    async getData(){
      return await fetcher.fetch('/uica/get-all', 'GET',{
        'Content-Type': 'application/json',
      });
    }
  },
};
</script>

<style>
.row {
  width: 100%;
}
.container-xxl{
  width: 100%;
}

.uica-panel .table-container {
  width: 90vw;
  overflow-x: auto;
}
.uica-panel .table-container table {
  font-size: 12px; /* Set the minimum font size */
}
/* Table with horizontal scroll */
.uica-panel .table-container {
  max-width: 100%;
  overflow-x: auto;
  height: 70vh;
}

.uica-panel .table-fixed {
  overflow-y: auto;
}
.uica-panel  table thead th:first-child {
  position: sticky;
  left: 0;
  z-index: 2;
  background: #fff;
}
.uica-panel  table tbody th {
  position: sticky;
  left: 0;
  background: white;
  z-index: 1;
}

/* Fixed first column */
.uica-panel .table-fixed thead td:first-child,
.uica-panel .table-fixed tbody td:first-child {
  position: sticky;
  left: 0;
  z-index: 2;
  background-color: #fff;
  max-width: 250px;
  overflow: hidden;

}

.uica-panel .table-fixed thead tr:first-child {
  position: sticky;
  left: 0;
  z-index: 20;
  background-color: #fff;


}

.uica-panel .table-fixed thead tr td:first-child{
  background-color: #959595;
  color:#fff;
}

.uica-panel .table-fixed thead tr {
  position: sticky;
  top: 0;
  background-color: #fff;
  z-index: 20;

}
</style>
