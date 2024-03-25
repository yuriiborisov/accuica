
<template>
  <div class="matrix-panel">
    <h2>Privileges/Actions matrix for role '{{roleId}}': </h2>
    <div class="container-xxl">
      <div class="table-container">
        <table class="table table-fixed data-show-columns table-striped" id="roleMatrixTable">
          <thead id="roleMatrixTableHeader">
          <tr>
            <td>Privilege</td>
            <td v-for ="(header, index) in headers"><p><strong>{{ header }}</strong></p></td>
          </tr>
          </thead>
          <tbody>
          <tr v-for ="(row, index) in rows">
            <td>
              <p class="privilege_id">
                <strong>{{ row.privilege.id }}</strong>
              </p>
              <p><small>{{ row.privilege.description }}</small>
              </p>
            </td>
            <td v-for ="(item, index) in headers"  :class="isExtended(row.privilege.id, item) ? 'extended' : ''">
              <input type="checkbox"
                     :class="isChecked(row.privilege.id, row.entities, item) ? 'checked' : ''"
                     :checked="isChecked(row.privilege.id,row.entities, item)"
                     :disabled="isDisabled(row.privilege.id,row.privilege.applicable, item)"

                     v-on:click="handleCheckboxClick($event)">
              <p class="extended" v-if="isExtended(row.privilege.id, item)">extended</p>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <button class="btn btn-primary save-btn" v-on:click="this.handleSaveButton">Save</button>
  </div>
</template>

<script>
module.exports = {
  data(){
    return{
      headers: [],
      rows: [],
      parentMatrix: new Map()
    }
  },
  computed: {
    roleId() {
      return this.$route.query.roleId;
    },

  },
  methods:{
    isExtended(privilegeId, item){
      return this.parentMatrix.get(privilegeId)?.includes(item);
    },
    isDisabled(privilegeId, applicable, item){
      if(this.isExtended(privilegeId, item)){
        return true;
      }
      return !( applicable == null || applicable.entities.includes(item));
    },
    isChecked(privilegeId, entities, item){
      // console.log("isChecked:privilegeId", privilegeId);
      // console.log("isChecked:entities", this.parentMatrix.get(privilegeId));
      if(this.isExtended(privilegeId, item)){
        return true;
      }
      return entities.includes(item)
    },
    sendMessage(){
      this.$root.$emit("msg-from-matrix", JSON.stringify(this.getCheckboxesValues()));
    },
    checkParentPrivilege(privilegeId, entity){

    },
    extractEntityIndexes(){
      var entities = document.getElementById('roleMatrixTableHeader').getElementsByTagName('tr')[0].getElementsByTagName('td');
      var entityMap = new Map();
      for (var i = 0; i < entities.length; i++) {
        var item = entities[i].textContent;

        entityMap.set(i, item);
      };
      return entityMap;
    },
    getCheckboxesValues() {
      let tableBody = document.getElementById('roleMatrixTable').getElementsByTagName('tbody')[0];
      let rows = tableBody.getElementsByTagName('tr');

      // Array to hold the extracted values
      var privilegeMap = new Map();
      var entityPrivilegeListMap = new Map();
      var entitiesMap = this.extractEntityIndexes();
      var grantAccesses = [];
      // Loop through each row
      for (let i = 0; i < rows.length; i++) {
        // Get the cells in the current row
        let cells = rows[i].getElementsByTagName('td');

        var privilegeIds = [];
        // Extract the values from the cells
        privilegeMap.set(i, cells[0].getElementsByClassName('privilege_id')[0].textContent);
        for(let j = 0; j < cells.length; j++){
          if(j > 0){
            var inp = cells[j].getElementsByTagName('input')[0];
            if(inp.type === 'checkbox'){
              // if(inp !== undefined && inp.checked){
              if(inp.classList.contains("checked") && !inp.classList.contains("extended")){
                // console.log('privilegeMap', privilegeMap);
                // console.log('privilegeMap', privilegeMap);
                privilegeIds = entityPrivilegeListMap.get(j) == undefined ? [] : entityPrivilegeListMap.get(j);
                privilegeIds.push(privilegeMap.get(i));
                entityPrivilegeListMap.set(j, privilegeIds);
              }
            }
          }
        }




        // Add the row values to the array
      }
      console.log('entityPrivilegeListMap', entityPrivilegeListMap);

      // console.log('privilegeMap',privilegeMap);
      for(let i = 0; i < entitiesMap.size; i++){
        if(i > 0){
          grantAccesses.push({
            entityType: entitiesMap.get(i),
            privilegeIds: entityPrivilegeListMap.get(i) == undefined ? [] : entityPrivilegeListMap.get(i)
          })
        }
      }
      let result = {
        id: this.roleId,
        grantAccesses: grantAccesses
      };      // Return the extracted values
      return result;
    },

    async getEntities(id){
      return await fetcher.fetch('/role/entities/'+id, 'GET',{
          'Content-Type': 'application/json',
          // 'Authorization': authorizationHeader
        });

      //let r = await resp.json();
      //console.log('r', r);
      //return r

    } ,
    async handleSave(data){
      return await fetcher.fetch('/role', 'PUT', {
          'Content-Type': 'application/json',
          // 'Authorization': authorizationHeader
        }, JSON.stringify(data));
    },
   async getMatrix(id){
      return await fetcher.fetch('/role/matrix-t/' + id, 'GET',{
          'Content-Type': 'application/json',
          // 'Authorization': authorizationHeader
        });
    },
    async getMatrixOfParents(id){
      return await fetcher.fetch('/role/matrix-p/' + id, 'GET',{
        'Content-Type': 'application/json',
        // 'Authorization': authorizationHeader
      });
    },
    async handleSaveButton(){
      this.rows = await this.handleSave(this.getCheckboxesValues());
    },
    handleCheckboxClick(event){
      if(!event.target.checked){
        event.target.classList.remove("checked");
      }else{
        event.target.classList.add("checked");
      }
      this.sendMessage();
    }
  },
  updated(){
    this.sendMessage();
  },
  async created() {

    this.headers = await this.getEntities(this.roleId);
    this.rows = await this.getMatrix(this.roleId);
    const current = await fetcher.fetch('/role/' + this.roleId, 'GET', {
      'Content-Type': 'application/json',
      // 'Authorization': authorizationHeader
    });
    const parentId = current.parent?.id;
    if(parentId != undefined){
      const parents = await this.getMatrixOfParents(this.roleId);
      this.parentMatrix = new Map(parents.map(i => [i.privilege.id, i.entities]));
    }
  }
};
</script>
<style>

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
}
table tbody th {
  position: sticky;
  left: 0;
  background: white;
  z-index: 1;
}
.table-fixed tbody td:first-child p {
  padding: 0;
  margin: 0;
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
  line-height: 10pt;
  font-size: 9pt;
}

.table-fixed thead tr:first-child {
  position: sticky;
  left: 0;
  z-index: 20;
  background-color: #fff;
  font-size: 9pt
}

.table-fixed thead tr td:first-child{
  background-color: #959595;
  color:#fff;
  font-size: 9pt
}

.table-fixed thead tr {
  position: sticky;
  top: 0;
  background-color: #fff;
  z-index: 20;
  font-size: 9pt

}
input[type="checkbox"]:hover {
  cursor: pointer;
}

input[type="checkbox"], input[type="radio"] {
  box-sizing: border-box;
  padding: 0;
}
#jsonViewer {
  height: 60vh; /* Fixed height of 400 pixels */
  overflow-y: auto; /* Enable vertical scrolling if content exceeds the height */
}
.extended {
  font-size: 6pt;
  color: #898989;
}
</style>
