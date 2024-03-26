<template>
    <div class="container-xxl">
<!--      <button class="btn btn-outline-dark" id="toggle-bottom-bar">json editor</button>-->
      <div class="component-header background-light" >FormID: {{ formId }}</div>
      <div class="row">
        <div class="col-sm-col- col-main">
          <div class="col">
            <div class="component-header">Components:</div>
            <div class="col components">
              <div :class="'component-id ' + (index == 0 ? 'active' : '')" v-for="(component, index) in privileges?.config?.componentAccess" :key="component.componentId" v-on:click="handleFormIdClick($event, component.componentId)">
                {{ component.componentId }}</div>
            </div>
            <button id="create-component" class="btn btn-outline-primary" v-on:click="showPopup">New component</button>
          </div>
        </div>
        <uica-editor-item :component-id="componentId" :privileges="privileges"></uica-editor-item>
<!--        <div id="groups-column" class="col col-main">-->
<!--          <div id="bottom-bar" class="d-flex justify-content-between align-items-center">-->
<!--            <div id="editor">{}</div>-->
<!--            <div>-->
<!--              <button class="btn btn-secondary" id="close-bottom-bar">Close</button>-->
<!--            </div>-->
<!--          </div>-->
<!--          <div class="component-header">formID: {{ formId }}</div>-->
<!--            <div class="col mt-3 ">-->
<!--              <div class="ll">-->
<!--                <button id="create-group" v-on:click="handleCreateGroupClick($event)" class="btn btn-primary">New state</button>-->
<!--                <div class="modal-body">-->
<!--                  <select class="form-control" id="selectOption">-->
<!--                    <option value="edit">EDIT</option>-->
<!--                    <option value="read">READ</option>-->
<!--                    <option value="hidden">HIDDEN</option>-->
<!--                    <option value="create">CREATE</option>-->
<!--                  </select>-->
<!--                </div>-->
<!--              </div>-->
<!--              <div id="component-id-header" class="component-header">componentID: {{ componentId }}</div>-->
<!--              <div id="entity-groups"></div>-->

<!--            </div>-->
<!--            <ul class="nav nav-tabs" id="myTab" role="tablist">-->
<!--              <li class="nav-item"  v-for="(key, index) in privilegesMapped" :key="index">-->
<!--                <a :class="'nav-link ' + (index == 'Other' ? 'active' : '')" v-bind:id="index+'-tab'" data-toggle="tab" v-bind:href="'#'+index" role="tab" v-bind:aria-controls="index" aria-selected="true">{{ index }}</a>-->
<!--              </li>-->
<!--            </ul>-->
<!--            <div class="tab-content" id="myTabContent">-->
<!--              <div  v-for="(key, index) in privilegesMapped" :key="index" :class="'tab-pane fade ' + (index == 'Other' ? 'show active' : '')" v-bind:id="index" role="tabpanel" v-bind:aria-labelledby="index+'-tab'">-->
<!--                <div class="item" draggable="true" v-for="(item, index) in key" :key="index">{{ item.id }}</div>-->
<!--              </div>-->
<!--            </div>-->
<!--        </div>-->
      </div>
      <!--     Popup Modal-->
      <div class="modal fade" id="popupModal" tabindex="-1" role="dialog" aria-labelledby="popupModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
          <h2>Create Form</h2>
          <form id="createComponentForm">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="popupModalLabel">Select Option</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <div class="form-group">
                  <label for="name">Name</label>
                  <input type="text" class="form-control" id="name" name="name" required>
                </div>
                <!--            <select class="form-control" id="selectOption">-->
                <!--              <option value="edit">EDIT</option>-->
                <!--              <option value="read">READ</option>-->
                <!--              <option value="hidden">HIDDEN</option>-->
                <!--              <option value="create">CREATE</option>-->
                <!--            </select>-->
              </div>
              <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="addOption"  v-on:click="handleCreate">Add</button>
              </div>
            </div>
          </form>
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
      componentId:undefined,
      componentAccess:[],
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
  // updated(){
  //   // this.main();
  //   $('#toggle-bottom-bar').click(function() {
  //     $('#bottom-bar').addClass('show');
  //     $('#groups-column').addClass('groups-min');
  //   });
  //
  //   $('#close-bottom-bar').click(function() {
  //     $('#bottom-bar').removeClass('show');
  //     $('#groups-column').removeClass('groups-min');
  //   });
  //   console.log("@@@@@@@@@@@@ITEMS", $('.item'));
  //   // Make items draggable
  //   $('.item').draggable({
  //     helper: 'clone',
  //     cursor: 'move'
  //   });
  // },
  computed: {
    formId() {
      return this.$route.query.formId;
    },

  },
  methods:{
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
    // async forceRerender() {
    //   // Remove MyComponent from the DOM
    //   this.renderComponent = false;
    //
    //   // Wait for the change to get flushed to the DOM
    //   await this.$nextTick();
    //
    //   // Add the component back in
    //   this.renderComponent = true;
    // },
    // initEditor(){
    //   this.editor = ace.edit("editor");
    //   this.editor.setTheme("ace/theme/monokai");
    //   this.editor.session.setMode("ace/mode/json");
    //   this.editor.setOptions({
    //     fontSize: "9pt",
    //     // fontFamily: "tahoma",
    //     // useWrapMode: true,
    //     // indentedSoftWrap: false,
    //     // behavioursEnabled: false, // disable autopairing of brackets and tags
    //     // showLineNumbers: true,
    //     wrap: true// hide the gutter
    //   });
    // },
    // main(){
    //   const app = this;
    //
    //   // app.json = app.privileges?.config?.componentAccess[0]?.states;
    //   // app.comonentId = app.privileges?.config?.componentAccess[0]?.componentId;
    //   console.log('app.json', app.json);
    //   app.jsonRow = new Object(app.json);
    //   console.log('jsonRow1', app.jsonRow);
    //   // Initialize Ace Editor
    //
    //   app.editor.setValue(JSON.stringify(app.jsonRow, app.replacer, 2), -1);
    //   // Add event listener for changes
    //   app.editor.on("change", function() {
    //     try {
    //       app.jsonRow = JSON.parse(app.editor.getValue());
    //       app.editor.value = JSON.stringify(app.json);
    //       app.createFromJson(app.jsonRow);
    //       app.editor.container.style.borderColor = "green";
    //     } catch (e) {
    //       app.editor.container.style.borderColor = "red";
    //     }
    //   });
    //   console.log("@@@@@@@@@@@@1");
    //   app.createFromJson(app.jsonRow);
    //   console.log('jsonRow2', app.jsonRow);
    //   // Handle the "Add" button click in the popup
    //   // $('#create-group').click(function() {
    //   //   var selectedOption = $('#selectOption').val();
    //   //   var rev = 0;
    //   //   var group = {
    //   //     state: selectedOption.toUpperCase(),
    //   //     entity: null,
    //   //     rev: rev,
    //   //     privileges: []
    //   //   };
    //   //   console.log('jsonRow3', app.jsonRow);
    //   //   var isExist = app.jsonRow.find(g => g.state === selectedOption.trim().toUpperCase());
    //   //   if(isExist){
    //   //     let findDuplicates = jsonPath(app.jsonRow, "$.[?(@.state=='"+selectedOption.toUpperCase()+"')]");
    //   //     let size = findDuplicates.length-1
    //   //     rev = size + 1;
    //   //     group.rev = rev;
    //   //   }
    //   //   app.jsonRow.push(group);
    //   //   console.log("@@@@@@@@@@@@2");
    //   //   var groupElement = $('<div>').attr('state',selectedOption).attr('rev',rev);
    //   //   var name = $('<div>').addClass('group-header').text(selectedOption.toUpperCase());
    //   //   var groupElementMain = $('<div>').addClass('entity-group').attr('state',selectedOption).attr('rev',rev);
    //   //   groupElementMain.append(name);
    //   //   groupElementMain.append(groupElement);
    //   //
    //   //   var deleteButton = $('<button>').addClass('btn btn-danger btn-sm delete-button').attr('state',selectedOption).attr('rev',rev).text('Delete')
    //   //       .click(e => {
    //   //         groupElementMain.remove();
    //   //         let find = jsonPath(app.jsonRow, "$.[?(@.state=='"+ selectedOption.toUpperCase()+"' && @.rev=="+ $(this).attr("rev")+")]")[0];
    //   //         var groupIndex = app.jsonRow.findIndex(g => g === find);
    //   //         if (groupIndex !== -1) {
    //   //           app.jsonRow.splice(groupIndex, 1);
    //   //         }
    //   //         app.updateJsonRepresentation(app.jsonRow);
    //   //       });
    //   //   console.log("@@@@@@@@@@@@3");
    //   //   $('#entity-groups').append(groupElementMain);
    //   //   $(".entity-group[state="+selectedOption+"][rev="+rev+"]").append(deleteButton);
    //   //   app.makeGroupDroppable(groupElementMain);
    //   //   app.editor.setValue(JSON.stringify(app.jsonRow, app.replacer, 2), -1);
    //   //   app.updateJsonRepresentation(app.jsonRow);
    //   //   $('#popupModal').modal('hide'); // Close the popup
    //   //
    //   // });
    //
    //   console.log("@@@@@@@@@@@@ITEMS", $('.item'));
    //   // Make items draggable
    //   $('.item').draggable({
    //     helper: 'clone',
    //     cursor: 'move'
    //   });
    //   console.log("@@@@@@@@@@@@4");
    //   // Make existing groups droppable
    //   $('.entity-group').each(function() {
    //     app.makeGroupDroppable($(this));
    //   });
    //   console.log("$('.entity-group')", $('.entity-group'));
    //   console.log("@@@@@@@@@@@@5");
    //   // Function to make a group droppabl
    //
    //   // Function to update the JSON representation
    // },
    // handleCreateGroupClick(event){
    //     var selectedOption = $('#selectOption').val();
    //     var rev = 0;
    //     var group = {
    //       state: selectedOption.toUpperCase(),
    //       entity: null,
    //       rev: rev,
    //       privileges: []
    //     };
    //     console.log('jsonRow3', this.jsonRow);
    //     var isExist = this.jsonRow.find(g => g.state === selectedOption.trim().toUpperCase());
    //     if(isExist){
    //       let findDuplicates = jsonPath(this.jsonRow, "$.[?(@.state=='"+selectedOption.toUpperCase()+"')]");
    //       let size = findDuplicates.length-1
    //       rev = size + 1;
    //       group.rev = rev;
    //     }
    //   this.jsonRow.push(group);
    //     console.log("@@@@@@@@@@@@2");
    //     var groupElement = $('<div>').attr('state',selectedOption).attr('rev',rev);
    //     var name = $('<div>').addClass('group-header').text(selectedOption.toUpperCase());
    //     var groupElementMain = $('<div>').addClass('entity-group').attr('state',selectedOption).attr('rev',rev);
    //     groupElementMain.append(name);
    //     groupElementMain.append(groupElement);
    //
    //     var deleteButton = $('<button>').addClass('btn btn-danger btn-sm delete-button').attr('state',selectedOption).attr('rev',rev).text('Delete')
    //         .click(e => {
    //           groupElementMain.remove();
    //           let find = jsonPath(this.jsonRow, "$.[?(@.state=='"+ selectedOption.toUpperCase()+"' && @.rev=="+ $(this).attr("rev")+")]")[0];
    //           var groupIndex = this.jsonRow.findIndex(g => g === find);
    //           if (groupIndex !== -1) {
    //             this.jsonRow.splice(groupIndex, 1);
    //           }
    //           this.updateJsonRepresentation(this.jsonRow);
    //         });
    //     console.log("@@@@@@@@@@@@3");
    //     $('#entity-groups').append(groupElementMain);
    //     $(".entity-group[state="+selectedOption+"][rev="+rev+"]").append(deleteButton);
    //   this.makeGroupDroppable(groupElementMain);
    //   this.editor.setValue(JSON.stringify(this.jsonRow, this.replacer, 2), -1);
    //   this.updateJsonRepresentation(this.jsonRow);
    //     $('#popupModal').modal('hide'); // Close the popup
    //   //
    //   // });
    // },
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
    // updateJsonRepresentation(json) {
    //   var jsonRepresentation = JSON.stringify(json, null, 2);
    //   // jsonRow =  JSON.parse(editor.getValue());
    //   $('#json-viewer').text(jsonRepresentation);
    //   this.json = JSON.stringify(this.jsonRow, this.replacer, 2);
    //
    // },
    // makeGroupDroppable(group) {
    //   const app = this;
    //   console.log('makeGroupDroppable', group);
    //   $(group).droppable({
    //     accept: '.item',
    //     drop: function(event, ui) {
    //
    //       var dropped = ui.draggable;
    //       console.log('makeGroupDroppableEvent', event);
    //       var droppedOn = $(this);
    //       var itemText = dropped.text().trim();
    //       let find = jsonPath(app.jsonRow, "$.[?(@.state=='" + droppedOn.context.getAttribute('state').trim().toUpperCase()+"' && @.rev=="+droppedOn.context.getAttribute('rev')+")]")[0];
    //       var groupIndex = app.jsonRow.findIndex(g => g === find);
    //       //var groupIndex = groups.findIndex(g => g.entity === droppedOn.context.getAttribute('entity').trim().toUpperCase() && g.rev == droppedOn.context.getAttribute('rev'));
    //
    //       // Check for duplicates
    //       if (groupIndex !== -1 && !app.jsonRow[groupIndex].privileges.includes(itemText)) {
    //         //var item = dropped.clone().removeAttr('style').removeClass('ui-draggable ui-draggable-handle');
    //         var newTag = document.createElement('span');
    //         newTag.className = 'tag';
    //         newTag.textContent = itemText;
    //
    //         var closeButton = document.createElement('span');
    //         closeButton.className = 'close';
    //         closeButton.innerHTML = '&times;'; // HTML entity for the multiplication sign (x)
    //         closeButton.onclick = function(event) {
    //           event.stopPropagation(); // Prevent the click event from bubbling up to the parent
    //           droppedOn.context.removeChild(newTag);
    //           let find = jsonPath(app.jsonRow, "$.[?(@.state=='" + droppedOn.context.getAttribute('state').trim().toUpperCase()+"' && @.rev=="+droppedOn.context.getAttribute('rev')+")]")[0];
    //           var groupIndex = app.jsonRow.findIndex(g => g === find);
    //           var index = app.jsonRow[groupIndex].privileges.indexOf(itemText);
    //
    //           if (index !== -1) {
    //             app.jsonRow[groupIndex].privileges.splice(index, 1);
    //             app.editor.setValue(JSON.stringify(app.jsonRow, app.replacer, 2), -1);
    //             app.updateJsonRepresentation(app.jsonRow);
    //           }
    //
    //         };
    //
    //         newTag.appendChild(closeButton);
    //         droppedOn.append(newTag);
    //         app.jsonRow[groupIndex].privileges.push(itemText);
    //         app.editor.setValue(JSON.stringify(app.jsonRow, app.replacer, 2), -1);
    //         app.updateJsonRepresentation(app.jsonRow);
    //       }
    //
    //     }
    //
    //   });
    // },
    // createFromJson(json){
    //   const app = this;
    //   $('#entity-groups').empty();
    //   // var groups = [];
    //   console.log(json);
    //   var revs = new Map();//contains next values of revisions
    //   json.forEach(e => {
    //     var rev = 0;
    //     var state = e.state.toUpperCase();
    //     var entity = e.entity?.toUpperCase();
    //     var stateLower = e.state.toLowerCase();
    //
    //     rev = revs.get(state) != undefined ? revs.get(state) + 1 : rev;
    //     revs.set(state, rev);
    //     e.rev = rev;
    //
    //     var groupElement = $('<div>').attr('state',stateLower).attr('rev',rev);
    //     var name = $('<div>').addClass('group-header').text(state);
    //
    //     var groupElementMain = $('<div>').addClass('entity-group').attr('state',stateLower).attr('rev',rev);
    //     groupElementMain.append(name);
    //     groupElementMain.append(groupElement);
    //     if(entity !== null){
    //       var enityLabelSpan = $('<span>').addClass('entity-label badge badge-dark').text(entity);
    //       groupElementMain.append(enityLabelSpan);
    //     }
    //     var deleteButton = $('<button>').addClass('btn btn-danger btn-sm delete-button').attr('state',stateLower).attr('rev',rev).text('Delete').click(function() {
    //       groupElementMain.remove();
    //       let find = jsonPath(app.jsonRow, "$.[?(@.state=='"+ state +"' && @.rev=="+$(this).attr("rev")+")]")[0];
    //       var groupIndex = app.jsonRow.findIndex(g => g === find);
    //       if (groupIndex !== -1) {
    //         app.jsonRow.splice(groupIndex, 1);
    //       }
    //       app.editor.setValue(JSON.stringify(app.jsonRow, app.replacer, 2), -1);
    //       app.updateJsonRepresentation(app.jsonRow);
    //
    //     });
    //     $('#entity-groups').append(groupElementMain);
    //     $(".entity-group[state="+stateLower+"][rev="+rev+"]").append(deleteButton);
    //     app.makeGroupDroppable(groupElementMain);
    //     app.updateJsonRepresentation(app.jsonRow);
    //     app.json = JSON.stringify(app.jsonRow, app.replacer, 2);
    //     app.createItemsFromJson(groupElement, e.privileges)
    //   })
    // },
    // updateJsonRepresentation(json) {
    //   const replacer = (key, value) => {
    //     if(key == "rev"){ return undefined;}else return value;
    //   }
    //   var jsonRepresentation = JSON.stringify(json, null, 2);
    //   // jsonRow =  JSON.parse(editor.getValue());
    //   $('#json-viewer').text(jsonRepresentation);
    //   this.json = JSON.stringify(this.jsonRow, this.replacer, 2);
    //
    // },
    // createItemsFromJson(groupElement, items) {
    //   const app = this;
    //   items.forEach( item => {
    //     var droppedOn = groupElement;
    //     var itemText = item;
    //     let find = jsonPath(app.jsonRow, "$.[?(@.state=='" + droppedOn.attr('state').toUpperCase()+"' && @.rev==" + droppedOn.attr('rev')+")]")[0];
    //     var groupIndex = app.jsonRow.findIndex(g => g === find);
    //     // Check for duplicates
    //     if (groupIndex !== -1) {
    //       var newTag = document.createElement('span');
    //       newTag.className = 'tag';
    //       newTag.textContent = itemText;
    //
    //       var closeButton = document.createElement('span');
    //       closeButton.className = 'close';
    //       closeButton.innerHTML = '&times;'; // HTML entity for the multiplication sign (x)
    //       closeButton.onclick = function(event) {
    //         event.stopPropagation(); // Prevent the click event from bubbling up to the parent
    //         $(newTag).remove();
    //         let find = jsonPath(app.jsonRow, "$.[?(@.state=='" + droppedOn.attr('state').toUpperCase()+"' && @.rev=="+droppedOn.attr('rev')+")]")[0];
    //         var groupIndex = app.jsonRow.findIndex(g => g === find);
    //         var index = app.jsonRow[groupIndex].privileges.indexOf(itemText);
    //
    //         if (index !== -1) {
    //           app.jsonRow[groupIndex].privileges.splice(index, 1);
    //           app.updateJsonRepresentation(app.jsonRow);
    //           app.editor.setValue(JSON.stringify(app.jsonRow, app.replacer, 2), -1);
    //         }
    //       };
    //
    //       newTag.appendChild(closeButton);
    //       droppedOn.append(newTag);
    //       app.updateJsonRepresentation(app.jsonRow);
    //     }
    //
    //   })
    //
    // }
  },
  components:{
    uicaEditorItem: httpVueLoader('/components/uica-editor-item.vue'),
  }
};
</script>

<style>
//#myTab {
//  margin-bottom: 15px;
//}
//.component-header {
//  margin-bottom: 10px;
//  font-size: 16pt;
//  font-weight: 600;
//}
//.col-main {
//  border-right: 1px solid #ccc;
//}
//.entity-group {
//  min-height: 50px;
//  //border: 1px solid #ddd;
//  border: 2px solid #2c6bff5e;
//  margin-bottom: 15px;
//  padding: 10px;
//  position: relative;
// // box-shadow: 2px 3px 10px 3px rgba(0, 0, 0, 0.2);
//  border-radius: 6px;
//  margin: 10px;
//  overflow: hidden;
//}
//.item {
//  display: inline-block;
//  padding: 5px 10px;
//  margin-right: 5px;
//  margin-bottom: 5px;
//  background-color: #f8f9fa;
//  border: 1px solid #ddd;
//  border-radius: 10px;
//  cursor: pointer;
//  font-size: 8pt;
//  font-weight: 600;
//}
//.item.ui-draggable-dragging {
//  background-color: #dee2e6;
//}
//.delete-button {
//  position: absolute;
//  top: 5px;
//  right: 5px;
//  font-size: 12px;
//  padding: 2px 5px;
//}
//#json-viewer {
//  margin-top: 20px;
//  padding: 10px;
//  border: 1px solid #ddd;
//  border-radius: 5px;
//  background-color: #f8f9fa;
//  height: 100vh;
//  overflow: scroll;
//  overflow-y: auto;
//  white-space: pre-wrap;
//}
//.tag {
//  display: inline-block;
//  //padding: 5px 10px;
//  //margin: 5px;
//  //background-color: #dfdfdf;
//  //border: 1px solid #ddd;
//  //border-radius: 15px;
//  font-size: 8pt;
//  font-weight: 600;
//}
//.close {
//  cursor: pointer;
//  margin-left: 5px;
//  font-size: 10pt;
//}
//.ll {
//  justify-content: center;
//  display: flex;
//  align-items: center;
//}
//#create-group {
//  //height: 50px;
//  margin: 5px;
//}
//.ui-droppable-hover{
//  background: #eaf5ff;
//}
//.ui-droppable{
//  min-height: 50px;
//}
//.group-header{
//  font-weight: 600;
//}
//.container-xxl .row:first-child {
//  margin: 0;
//}
//.container-xxl {
//  flex-direction: column;
//}
//
//
//textarea {
//  width: 100%;
//  height: 200px;
//  font-family: 'Courier New', Courier, monospace;
//}
//.error {
//  background-color: #ffcccc;
//  border: 1px solid #ff0000;
//}
//
//#editor {
//  width: 100%;
//  height: inherit;
//}
//#entity-groups {
//  overflow-y: scroll;
//  max-height: 60vh;
//  padding-right: 20px;
//  display: grid;
//  grid-template-columns: repeat(3, minmax(0, 1fr));
//}
//
//#bottom-bar {
//  position: fixed;
//  bottom: 0;
//  right: 0;
//  width: 100%;
//  background-color: #343a40;
//  color: #fff;
//  //padding: 10px 20px;
//  z-index: 9999;
//  transition: transform 0.3s ease-in-out;
//  transform: translateY(100%);
//  height: 100vh;
//  width: 35vw;
//}
//#bottom-bar.show {
//  transform: translateY(0);
//}
//
//#toggle-bottom-bar {
//  position: fixed;
//  right: 0;
//  bottom: 0;
//  margin: 20px;
//  z-index: 999;
//}
//
//#close-bottom-bar{
//  position: absolute;
//  top: 20px;
//  right: 20px;
//}
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
#component-id-header {
  font-weight: 600;
}
/*.nav-tabs .nav-item {*/
/*  font-size: 8pt;*/
/*}*/
/*.groups-min{*/
/*  max-width: 50vw;*/
/*}*/
/*#groups-column {*/
/*  transition: 0.4s ease-out;*/
/*}*/
/*.tab-pane{*/
/*  padding:5px;*/
/*}*/

/*div[state] {*/
/*  display: flex;*/
/*  flex-direction: column;*/
/*}*/
/*.entity-label {*/
/*  position: absolute;*/
/*  bottom: 5px;*/
/*  right: 5px;*/
/*  font-size: 12px;*/
/*  padding: 2px 5px;*/
//}
</style>
