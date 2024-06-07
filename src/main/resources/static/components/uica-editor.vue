<template>
    <div class="container-xxl">
<!--      <button class="btn btn-outline-dark" id="toggle-bottom-bar">json editor</button>-->
      <div class="component-header background-light align-center" >FormID: {{ formId }} ({{entity}}) <span class="pencil-edit" v-on:click="showPopup"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil" viewBox="0 0 16 16">
        <path d="M12.146.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1 0 .708l-10 10a.5.5 0 0 1-.168.11l-5 2a.5.5 0 0 1-.65-.65l2-5a.5.5 0 0 1 .11-.168zM11.207 2.5 13.5 4.793 14.793 3.5 12.5 1.207zm1.586 3L10.5 3.207 4 9.707V10h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.293zm-9.761 5.175-.106.106-1.528 3.821 3.821-1.528.106-.106A.5.5 0 0 1 5 12.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.468-.325"></path>
      </svg></span></div>
      <div class="row">
        <div class="col-sm-col- col-main">
          <div class="col">
<!--            <div class="component-header">Components:</div>-->
            <div class="col components">
              <div :class="'component-id ' + (index == 0 ? 'active' : '')" v-for="(component, index) in privileges?.config?.componentAccess" :key="component.componentId" v-on:click="handleFormIdClick($event, component.componentId)">
                {{ component.componentId }}</div>
            </div>
<!--            <button id="create-component" class="btn btn-outline-primary" v-on:click="showPopup">New component</button>-->
          </div>
        </div>
        <uica-editor-item :component="componentId" :privilegess="privileges"></uica-editor-item>
      </div>
      <!--     Popup Modal-->
      <div class="modal fade" id="popupModal" tabindex="-1" role="dialog" aria-labelledby="popupModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
<!--          <h2>Create Form</h2>-->

            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="popupModalLabel">Form edit</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <div class="form-group">
                  <form id="editComponentForm">
                    <label for="name">Name</label>
                    <input type="text" class="form-control" id="name" name="name" :value="formId" required>
                    <label for="entity-name">Entity:</label>
                    <input type="text" class="form-control" id="entity-name" name="entity-name" :value="entity" required>
                    <label for="name">Description</label>
                    <input type="text" class="form-control" id="name" name="name" :value="description" required>
                  </form>
                </div>
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Delete</button>
                <button type="button" class="btn btn-primary" id="addOption"  v-on:click="handleCreate">Save</button>
              </div>
            </div>
        </div>
      </div>
    </div>

</template>

<script>
module.exports = {
  data(){
    return{
      editor: undefined,
      privileges: undefined,
      privilegesMapped: undefined,
      componentId:String,
      componentAccess:[],
      entity: String,
      description:String,
      json: [
        {
          "scope": "READ",
          "privileges": [
            "REQUEST_EDIT_BY_ISSUER_OR_TASK_EXECUTOR",
            "REQUEST_EDIT_FOR_VTD_VTC_CNR",
            "REQUEST_EDIT_FOR_RTD_RTC_VTD_VTC_CNR",
            "REQUEST_EDIT_BY_TASK_EXECUTOR",
            "Item 8",
            "READ",
            "Itthrgfeem 9"
          ]
        },
        {
          "scope": "HIDDEN",
          "privileges": [
            "REQUEST_EDIT_FOR_RTD_RTC_VTD_VTC_CNR",
            "REQUEST_EDIT_FOR_VTD_VTC_CNR",
            "REQUEST_EDIT_BY_ISSUER_OR_TASK_EXECUTOR"
          ]
        }
      ],
      jsonRow: [],

      replacer: (key, value) => {
        if(key == "rev"){ return undefined;}else return value;
      }
    }
  },
  async created(){
    // const app = this;
    // this.privilegesMapped = await fetcher.fetch('/uica/privilege/get-all-m', 'GET',{
    //   'Content-Type': 'application/json',
    // });

    this.privileges = await this.fetchPrivilege();
    console.log("MMMMMMMM", this.privileges);
    this.componentAccess = this.privileges?.config?.componentAccess;
    this.entity = this.privileges?.config?.entity;
    this.description = this.privileges?.description;
    this.componentId = this.privileges?.config?.componentAccess[0].componentId;
    // console.log('initComponentId', initComponentId);
    // this.json = jsonPath(this.privileges?.config?.componentAccess,"$[?(@.componentId == '"+initComponentId+"')].states[*]");
    // this.componentId = initComponentId;
    // console.log('json', this.json);
    //
    //
    // this.initEditor();
    // this.main();

  },
   async mounted(){
    const app = this;

     this.$root.$on("saveduica", async(componentId) =>  {
       // app.$forceUpdate();
       console.log("m-saveduica catched", componentId);
       // this.privileges = await this.fetchPrivilege();
       this.componentId = componentId;
       console.log("m-saveduica catched  this.componentId",  this.componentId);
       this.privileges = await this.fetchPrivilege();
       // console.log("privileges",  app.privileges);
       // this.refresh();
     });
  },
  computed: {
    formId() {
      return this.$route.query.formId;
    }
  },
  methods:{
    // async refresh(componentId){
    //   console.log("m-saveduica catched");
    //   this.componentId = componentId
    //   this.privileges = await this.fetchPrivilege();
    //   console.log("privileges",  this.privileges);
    //   this.initEditor();
    //   // this.$forceUpdate();
    // },
    showPopup(){
      $('#popupModal').modal('show');
    },
    handleCreate() {
      document.getElementById('createComponentForm').addEventListener('submit', function (event) {
        event.preventDefault();

        // Get the form data
        var formData = new FormData(event.target);

        // Send the POST request to the server
      });
    $('#popupModal').modal('hide');
    },
    handleFormIdClick(event,id){
      this.componentId = id;
      // this.json = jsonPath(this.privileges?.config?.componentAccess,"$[?(@.componentId == '"+id+"')].states[*]");
      // // this.jsonRow = new Object(this.json);
      // this.main();
      $(".component-id").each( function(i,o){$(o).removeClass('active')});
      //
      $(event.target).addClass("active");
      // // this.forceRerender()
      // console.log('json',JSON.stringify(this.json));
    },
    async fetchPrivilege(){
      return await fetcher.fetch('/uica/get/'+ this.formId, 'GET',{
        'Content-Type': 'application/json',
      });
    },
  },
  components:{
    uicaEditorItem: httpVueLoader('/components/uica-editor-item.vue'),
  }
};
</script>

<style>
.component-id:hover {
  cursor: pointer;
  color: #2c6bff;
}
.active{
  color: #2c6bff;
}

.components {
  height: 60vh;
  overflow-y: scroll;
  margin-bottom: 2em;
}
.component-id {
  margin-bottom: 5px;
}
.component-id:hover {
  cursor: pointer;
  color: #2c6bff;

}
.pencil-edit {
  margin: 10px;
}
.pencil-edit:hover {
  color: #1686ff;
  cursor: pointer;
}
#component-id-header {
  font-weight: 600;
}
</style>
