
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
          <!-- <tr>
              <th>#</th>
              <th>Role</th>
              <th>Privileges</th>
              <th>Actions</th>
          </tr> -->
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
            <td v-for ="(item, index) in headers">
              <input type="checkbox" :class="row.entities.includes(item) ? 'checked' : ''"  :checked="row.entities.includes(item)" :disabled="!( row.privilege.applicable == null || row.privilege.applicable.entities.includes(item))" v-on:click="handleCheckboxClick($event)">
            </td>
          </tr>
          <!-- Rows will be generated here -->
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
      rows: []
    }
  },
  computed: {
    roleId() {
      return this.$route.query.roleId;
    },
  },
  methods:{
    sendMessage(){
      this.$root.$emit("msg-from-matrix", JSON.stringify(this.getCheckboxesValues()));
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
              if(inp.classList.contains("checked")){
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
    // loadJson() {
    //   var jsonString = JSON.stringify(this.getCheckboxesValues()); // Replace with your JSON data
    //   var jsonData = JSON.parse(jsonString);
    //   var formattedJson = JSON.stringify(jsonData, null, 2); // Indentation level set to 2 for pretty printing
    //   document.getElementById('jsonViewer').textContent = formattedJson;
    //   hljs.highlightElement(document.getElementById('jsonViewer'));
    // },
    // copyToClipboard() {
    //   var textToCopy = document.getElementById('jsonViewer').textContent;
    //   navigator.clipboard.writeText(textToCopy).then(function() {
    //     alert('Copied to clipboard!');
    //   }, function(err) {
    //     console.error('Could not copy text: ', err);
    //   });
    // },
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
   async getMatrixT(id){
      return await fetcher.fetch('/role/matrix-t/' + id, 'GET',{
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
  // updated: function () {
  //   this.$nextTick(function () {
  //     console.log("UPDATED");
  //   })
  // },
  // async mounted(){
  //
  //   this.rows = await this.getMatrixT(this.roleId);
  // },
  updated(){
    this.sendMessage();
  },
  async created() {

    this.headers = await this.getEntities(this.roleId);
    this.rows = await this.getMatrixT(this.roleId);
    // this.sendMessage();
    // this.headers = entitiesData;
    // this.rows = matrixT;
    // console.log('matrixT', matrixT);
    // console.log('entitiesData', entitiesData);
    // this.generateTableHeader(entitiesData);
    // this.generateTableRows(matrixT, entitiesData);
    //
    // // Add event listeners for save and delete buttons
    // $("tbody input[type='checkbox']").click(function() {
    //   // $(this).checked
    //   // this.prop("checked", !this.checked);
    //   if(!this.checked){
    //     this.classList.remove("checked");
    //   }else{
    //     this.classList.add("checked");
    //   }
    //   console.log(this.getCheckboxesValues());
    //   // this.addClass("checked", !this.checked);
    //   // alert('Checkbox clicked '+ this.prop('checked', true););
    // })
    //
    // $('.save-btn').on('click', function() {
    //   // Handle save button click
    //   this.handleSave(this.getCheckboxesValues());
    //   alert('Save button clicked');
    // });
    //
    // $('.delete-btn').on('click', function() {
    //   // Handle delete button click
    //   alert('Delete button clicked');
    // });
    // loadJson();
    // hljs.highlightAll();
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
</style>
