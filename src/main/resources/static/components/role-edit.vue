
<template>
  <div class="container mt-5">
<!--    <modal-delete :show="modalActivate" :title="title" :message="message" :on-delete="onDelete"></modal-delete>-->
    <modal-delete></modal-delete>

    <h2>{{createNew_ ? 'Create' : 'Edit'}}</h2>
    <form id="createForm" @submit.prevent="handleUpdate">
      <div class="form-group">
        <label for="id">ID</label>
        <input type="text" class="form-control form-control-sm" id="id" name="id" :value="item_?.id" required :readonly="!createNew_">
      </div>
      <div class="form-group">
        <label for="name">Name</label>
        <input type="text" class="form-control form-control-sm" id="name" name="name" :value="item_?.name" required>
      </div>
      <div class="form-group">
        <label for="priority">Priority</label>
        <input type="text" class="form-control form-control-sm" id="sortOrder" name="sortOrder" :value="item_?.sortOrder" required @input="inputListen">
      </div>
      <div class="form-group">
        <label for="description">Description</label>
        <textarea class="form-control form-control-sm" id="description" name="description" rows="3" :value="item_?.description" required></textarea>
      </div>
      <div class="form-group">
        <label for="selectOption">ParentID (if necessary):</label>
        <select class="form-control" id="parentId" name="parentId">
          <option>{{item_?.parent?.id == undefined ? 'Choose ParentID' : item_?.parent?.id}}</option>
          <option v-if="parentId != item_?.id" :value="parentId" v-for="(parentId, index) in parentIds_" :key="parentId">{{parentId}}</option>
        </select>
      </div>
      <button type="submit" class="btn btn-primary">{{createNew_ ? 'Create' : 'Update'}}</button>
      <button  v-if="!createNew_" type="button" class="btn btn-primary btn btn-danger" @click="handleDelete">{{'Delete'}}</button>
    </form>
  </div>
</template>

<script>
module.exports = {
    props:{
      item: Object,
      parentIds: Array,
      createNew: Boolean
    },
  mounted(){
      console.log('item', this.item);
    console.log('parentIds', this.parentIds);
    console.log('createNew', this.createNew);
  },
  watch:{
    item(val){
      this.item_ = val;
    },
    parentIds(val){
      this.parentIds_ = val;
    },
    createNew(val){
      this.createNew_ = val;
    }
  },
   data(){
    return{
      data1: {},
      modalActivate: false,
      modalData:{
        title: 'Confirm Deletion',
        message: 'Are you sure you want to delete this object?',
        action: async () => await this.onDelete()
      },
      item_: this.item,
      parentIds_: this.parentIds,
      createNew_: this.createNew

    }
  },
  // mounted(){
  //     this.$root.$on("delete-object-confirmed", async () => await this.onDelete());
  // },
  methods: {
    async onDelete(){
      console.log("this.selected ", this.item?.id);
      if(this.item?.id != undefined){
        await fetcher.fetch('/role/'+ this.item?.id, 'DELETE',{
          'Content-Type': 'application/json'
        });
      }
      this.createNew_ = true;

      this.sendUpdateMsg();
      this.item_ = {};
    },
    inputListen(event){
      var newValue = event.target.value.replace(new RegExp(/[^\d]/, 'ig'), "");
      event.target.value = newValue;
    },
   async handleUpdate(event) {
        event.preventDefault();
        var formData = new FormData(event.target);
        await fetcher.fetch(this.createNew ? '/role' : '/role/info', this.createNew ? 'POST' : 'PUT',{
              'Content-Type': 'application/json'
            },JSON.stringify(Object.fromEntries(formData)));

     this.sendUpdateMsg();
    },
    sendUpdateMsg(){
      this.$root.$emit("updatedRoles");
    },
    handleDelete() {
      this.$root.$emit("show-delete-modal", this.modalData);

      // event.preventDefault();
      // console.log('formData',formData);
      // console.log('formData.id',formData.id);
      // var formData = new FormData(event.target);
      // await fetcher.fetch('/role/'+id, 'DELETE',{
      //   'Content-Type': 'application/json'
      // },JSON.stringify(Object.fromEntries(formData)));
      //
      // this.$root.$emit("updatedSidebar");
    }
  },
  components:{
    modalDelete:httpVueLoader('/components/modal-delete.vue')
  }
};
</script>
